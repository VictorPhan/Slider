package environment;

import java.util.ArrayList;

import jeigen.DenseMatrix;
import neural_network.NeuralNetwork;
import player.AIPlayer;

// aim is for each position encountered, play 12 moves and then update by TD leaf algo
public class TestDriver {
	static final double tdLambda = 0.7;
	static final double learningRate = 0.1;
	public static void main(String[] args) {
		ArrayList<ArrayList<double[]>> outerTensors = new ArrayList<ArrayList<double[]>>();
		Parse.initScan();
		Position p = Parse.parseBoard();
		AIPlayer ai = new AIPlayer();
		ai.setPrintMove(false);
		
		while(p.gs == GameState.PLAYING) {
			outerTensors.add(ai.makeMoveLearn(p));
			p.draw();
		}
		
		updateWeightMatrixL(ai.e.nn, outerTensors);
		Parse.closeScan();
	}
	
	private static void
	updateWeightMatrixL(NeuralNetwork nn, ArrayList<ArrayList<double[]>> tensors) {
		// first compute temporal differences
		double[] d = new double[tensors.size()-1];
		for(int i=0; i<d.length; i++) {
			d[i] = tensors.get(i+1).get(0)[0]-tensors.get(i).get(0)[0];
		}
		// then for n=0,...,N-1
		for(int n=0; n<tensors.size()-1; n++) {
			gradOut
			gradH2
			gradH1
			for(int t=n; t<Math.min(n+12, tensors.size()-12); t++) {
				double lambdaSum = 0;
				for(int j=t; j<Math.min(n+12, tensors.size()-12); j++) {
					lambdaSum+=Math.pow(tdLambda, j-t)*d[t];
				}
				delOut = tensors.get(t).get(0) - tensors.get(Math.min(n+12, tensors.size()-12)).get(0);
				delH2 = nn.OUT.weightMatrix.t() times
				delH2
				// update the grads
				gradOut
				gradH2
				gradH1
			}
			nn.OUT.weightMatrix
			nn.H2.weightMatrix
			nn.H1.weightMatrix
			// and update weight matrix here
		}
		// then print final weight matrix here
		System.out.println(nn.OUT.weightMatrix.toString());
		System.out.println(nn.H2.weightMatrix);
		System.out.println(nn.H1.weightMatrix);
	}
	
	private static void 
	updateWeightMatrix(NeuralNetwork nn, ArrayList<ArrayList<double[]>> tensors) {
		double[] s = new double[tensors.get(0).get(1).length];
		for(int t=0; t<tensors.size()-2; t++) {
			double td = 0;
			s = elementWiseAdd(s, scalarMultiply(tensors.get(t).get(1), 
					tensors.get(t).get(0)[0]-tensors.get(t+1).get(0)[0]));
			for(int j=t; j<tensors.size()-2; j++) {
				td += Math.pow(tdLambda, j-t) * (tensors.get(t+1).get(0)[0]-tensors.get(t).get(0)[0]);
			}
			s = scalarMultiply(s, td);
		}
		DenseMatrix delta = new DenseMatrix(new double[][] {s});
		nn.OUT.weightMatrix = nn.OUT.weightMatrix.add(delta).mul(0.000001);
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
}
