package environment;

import java.util.ArrayList;

import jeigen.DenseMatrix;
import neural_network.Evaluation;
import neural_network.NeuralNetwork;
import player.AIPlayer;
import player.Human;

// aim is for each position encountered, play lookAhead moves and then update by TD leaf algo
public class Learner {
	
	static final double tdLambda = 0.01;
	static final double learningRate = 0.01;
	static final int lookAhead = 12;
	static final int numGames = 5;
	static int xOut = 0;
	static int xH2 = 1;
	static int xH1 = 2;
	static int x0 = 3;
	static boolean humanTraining = true;
	static boolean presettedMatrix = true;
	
	public static void main(String[] args) {
		NeuralNetwork.setPreset(presettedMatrix);
		AIPlayer.setDepth(lookAhead);
		Parse.initScan();
		Position p = Parse.parseBoard();
		
		AIPlayer ai = new AIPlayer();
		Human h = new Human();
		ai.setPrintMove(false);
		
		if(humanTraining) {
			double score = 0;
			Position p2 = p.copyPosition();
			ArrayList<ArrayList<double[]>> outerTensors = new ArrayList<ArrayList<double[]>>();
			while(/*score != 10. && score != -10. && */p2.gs == GameState.PLAYING) {
				if(p2.sidePlaying==Side.V) {
					ArrayList<double[]> tt = ai.makeMoveLearn(p2, true);
					outerTensors.add(tt);
					score = tt.get(0)[0];
				}
				else {
					// quiet move, allows AI to consider but not make the move
					ArrayList<double[]> tt = ai.makeMoveLearn(p2, false);
					h.makeMove(p2);
					outerTensors.add(tt);
					score = tt.get(0)[0];
				}
				System.out.println(score);
				p2.draw();
			}
			updateWeightMatrixL(ai.e.nn, outerTensors);
		}
		else {
			for(int i=0; i<numGames; i++) {
				double score = 0;
				Position p2 = p.copyPosition();
				ArrayList<ArrayList<double[]>> outerTensors = new ArrayList<ArrayList<double[]>>();
				while(p2.gs == GameState.PLAYING) {
					ArrayList<double[]> tt = ai.makeMoveLearn(p2, true);
					outerTensors.add(tt);
					score = tt.get(0)[0];
					System.out.println(score);
					p2.draw();
				}
				updateWeightMatrixL(ai.e.nn, outerTensors);
			}
		}
		
		Parse.closeScan();
	}
	
	private static void
	updateWeightMatrixL(NeuralNetwork nn, ArrayList<ArrayList<double[]>> tensors) {
		double[] bias = {1};
		// first compute temporal differences
		double[] d = new double[tensors.size()-1];
		for(int i=0; i<d.length; i++) {
			d[i] = tensors.get(i+1).get(0)[0]-tensors.get(i).get(0)[0];
		}
		// then for n=0,...,N-1
		for(int n=0; n<tensors.size()-1; n++) {
			DenseMatrix gradOut = DenseMatrix.zeros(nn.OUT.weightMatrix.rows, nn.OUT.weightMatrix.cols);
			DenseMatrix gradH2 = DenseMatrix.zeros(nn.H2.weightMatrix.rows, nn.H2.weightMatrix.cols);
			DenseMatrix gradH1 = DenseMatrix.zeros(nn.H1.weightMatrix.rows, nn.H1.weightMatrix.cols);
			for(int t=n; t<Math.min(n+lookAhead, tensors.size()-1); t++) {
				// the temporal difference sum
				double lambdaSum = 0;
				for(int j=t; j<Math.min(n+lookAhead, tensors.size()-1); j++) {
					lambdaSum+=Math.pow(tdLambda, j-t)*d[t];
				}
				
				// dels for backpropagation
				double delOut = (tensors.get(t).get(xOut)[0] - 
						tensors.get(Math.min(n+lookAhead, tensors.size()-1)).get(xOut)[0]) * 
							(1-Math.pow(tensors.get(t).get(0)[0], 2));
				
				DenseMatrix delH2 = (nn.OUT.weightMatrix.cols(0, nn.OUT.weightMatrix.cols-1).t().mul(delOut)).
						mul(nn.H2.weightMatrix.mmul(new DenseMatrix(new double[][] 
								{concat(tensors.get(t).get(xH1), bias)}).t()).gt(0));
				
				DenseMatrix delH1 = (nn.H2.weightMatrix.cols(0, nn.H2.weightMatrix.cols-1).t().mmul(delH2)).
						mul(nn.H1.weightMatrix.mmul(new DenseMatrix(new double[][] 
								{concat(tensors.get(t).get(x0), bias)}).t()).gt(0));
				
				// update the grads
				DenseMatrix x2 = new DenseMatrix(new double[][] {concat(tensors.get(t).get(xH2), bias)});
				DenseMatrix x1 = new DenseMatrix(new double[][] {concat(tensors.get(t).get(xH1), bias)});
				DenseMatrix s  = new DenseMatrix(new double[][] {concat(tensors.get(t).get(x0), bias)});
				gradOut = gradOut.add(x2.mul(delOut).mul(lambdaSum));
				gradH2  = gradH2.add(delH2.mmul(x1).mul(lambdaSum));
				gradH1  = gradH1.add(delH1.mmul(s).mul(lambdaSum));
			}
			// and update weight matrix here
			nn.OUT.weightMatrix = nn.OUT.weightMatrix.add(gradOut.mul(learningRate));
			nn.H2.weightMatrix	= nn.H2.weightMatrix.add(gradH2.mul(learningRate));
			nn.H1.weightMatrix	= nn.H1.weightMatrix.add(gradH1.mul(learningRate));
		}
		// then print final weight matrix here
		System.out.println(nn.H1.weightMatrix);
		System.out.println(nn.H2.weightMatrix);
		System.out.println(nn.OUT.weightMatrix);
	}
	
	// derivative of the tanh for backpropagation
	public static double recipSech2(double x) {
		return (Evaluation.H_WIN_SCORE-1)/Math.pow(Math.cosh(x), 2);
	}
	
	// concat two double arrays
	public static double[] concat(double[] a, double[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   double[] c = new double[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
}
