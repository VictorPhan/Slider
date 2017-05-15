package environment;

import java.util.ArrayList;

import jeigen.DenseMatrix;
import neural_network.Evaluation;
import neural_network.NeuralNetwork;
import player.AIPlayer;
import player.Human;

// aim is for each position encountered, play lookAhead moves and then update by TD leaf algo
public class TestDriver {
	static final double tdLambda = 0.7;
	static final double learningRate = 0.000001;
	static final int lookAhead = 6;
	static final int numGames = 30;
	static int xOut = 0;
	static int xH2 = 1;
	static int xH1 = 2;
	static int x0 = 3;
	public static void main(String[] args) {
		Parse.initScan();
		Position p = Parse.parseBoard();
		
		AIPlayer ai = new AIPlayer();
		ai.setPrintMove(false);
		
		for(int i=0; i<numGames; i++) {
			double score = 0;
			Position p2 = p.copyPosition();
			ArrayList<ArrayList<double[]>> outerTensors = new ArrayList<ArrayList<double[]>>();
			while(score != 10. && score != -10.  && p2.gs != GameState.DRAW) {
				ArrayList<double[]> tt = ai.makeMoveLearn(p2);
				outerTensors.add(tt);
				score = tt.get(0)[0];
				System.out.println(score);
				p2.draw();
			}
			updateWeightMatrixL(ai.e.nn, outerTensors);
		}
		
		ai.setPrintMove(true);
		Human h = new Human();
		Run.playGame(p, false, h, ai);
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
							recipSech2(nn.OUT.weightMatrix.mul(new DenseMatrix(new double[][] 
									{concat(tensors.get(t).get(xH2), bias)})).getValues()[0]);
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
			nn.OUT.weightMatrix = nn.OUT.weightMatrix.add(gradOut.mul(learningRate));
			nn.H2.weightMatrix	= nn.H2.weightMatrix.add(gradH2.mul(learningRate));
			nn.H1.weightMatrix	= nn.H1.weightMatrix.add(gradH1.mul(learningRate));
			// and update weight matrix here
		}
		// then print final weight matrix here
		System.out.println(nn.H1.weightMatrix);
		System.out.println(nn.H2.weightMatrix);
		System.out.println(nn.OUT.weightMatrix);
	}
	
	public static double recipSech2(double x) {
		return (Evaluation.H_WIN_SCORE-1)/Math.pow(Math.cosh(x), 2);
	}
	
	public static double[] concat(double[] a, double[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   double[] c = new double[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
}
