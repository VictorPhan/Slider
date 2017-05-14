package environment;

import java.util.ArrayList;

import jeigen.DenseMatrix;
import neural_network.NeuralNetwork;
import player.AIPlayer;

// aim is for each position encountered, play 12 moves and then update by TD leaf algo
public class TestDriver {
	static final double tdLambda = 0.7;
	static final double learningRate = 0.00001;
	static final int lookAhead = 12;
	public static void main(String[] args) {
		ArrayList<ArrayList<double[]>> outerTensors = new ArrayList<ArrayList<double[]>>();
		Parse.initScan();
		Position p = Parse.parseBoard();
		AIPlayer ai = new AIPlayer();
		ai.setPrintMove(false);
		
		while(p.gs == GameState.PLAYING) {
			ArrayList<double[]> tt = ai.makeMoveLearn(p);
			if(tt.size() == 1) {
				throw new Error("while gaming");
			}
			outerTensors.add(tt);
			p.draw();
		}
		
		updateWeightMatrixL(ai.e.nn, outerTensors);
		Parse.closeScan();
	}
	
	public static double heaviside(double x) {
		if(x > 0) {
			return 1;
		}
		return 0;
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
				System.out.println(tensors.get(t).size());
				double delOut = tensors.get(t).get(0)[0] - 
						tensors.get(Math.min(n+lookAhead, tensors.size()-1)).get(0)[0];
				DenseMatrix delH2 = (nn.OUT.weightMatrix.cols(0, nn.OUT.weightMatrix.cols-1).t().mul(delOut)).
						mul(nn.H2.weightMatrix.mmul(new DenseMatrix(new double[][] 
								{concat(tensors.get(t).get(2), bias)}).t()).gt(0));
				DenseMatrix delH1 = (nn.H2.weightMatrix.cols(0, nn.H2.weightMatrix.cols-1).t().mmul(delH2)).
						mul(nn.H1.weightMatrix.mmul(new DenseMatrix(new double[][] 
								{concat(tensors.get(t).get(3), bias)}).t()).gt(0));
				// update the grads
				DenseMatrix x2 = new DenseMatrix(new double[][] {concat(tensors.get(t).get(1), bias)});
				DenseMatrix x1 = new DenseMatrix(new double[][] {concat(tensors.get(t).get(2), bias)});
				DenseMatrix s  = new DenseMatrix(new double[][] {concat(tensors.get(t).get(3), bias)});
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
	
	public static double[] elementWiseAdd(double[] a, double[] b) {
		double[] c = new double[a.length];
		for(int i=0; i<a.length; i++) {
			c[i] = a[i] + b[i];
		}
		return c;
	}
	
	public static double[] scalarMultiply(double[] a, double s) {
		double[] c = new double[a.length];
		for(int i=0; i<a.length; i++) {
			c[i] = a[i]*s;
		}
		return c;
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
