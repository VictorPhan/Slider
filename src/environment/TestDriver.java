package environment;

import java.util.ArrayList;

import jeigen.DenseMatrix;
import neural_network.NeuralNetwork;
import player.AIPlayer;

// aim is for each position encountered, play 12 moves and then update by TD leaf algo
public class TestDriver {
	public static void main(String[] args) {
		ArrayList<ArrayList<double[]>> tensors = new ArrayList<ArrayList<double[]>>();
		Parse.initScan();
		Position p = Parse.parseBoard();
		AIPlayer ai = new AIPlayer();
		ai.setPrintMove(false);
		tensors.add(ai.e.nn.getInOut(p));
		for(int i=0; i<movesTD; i++) {
			ai.makeMove(p);
			tensors.add(ai.e.nn.getInOut(p));
			if(p.gs != GameState.PLAYING) {
				break;
			}
		}
		ai.setPrintMove(true);
		
		System.out.println("game 1");
		Position p2 = Parse.parseBoard();
		Run.playGame(p2, false, ai, ai);
		
		updateWeightMatrix(ai.e.nn, tensors);
		
		System.out.println("game 2");
		Position p3 = Parse.parseBoard();
		Run.playGame(p3, false, ai, ai);
		Parse.closeScan();
	}
	private static void 
	updateWeightMatrix(NeuralNetwork nn, ArrayList<ArrayList<double[]>> tensors) {
		double[] s = new double[tensors.get(0).get(1).length];
		for(int t=0; t<tensors.size()-2; t++) {
			double td = 0;
			s = elementWiseAdd(s, scalarMultiply(tensors.get(t).get(1), 
					tensors.get(t).get(0)[0]-tensors.get(t+1).get(0)[0]));
			for(int j=t; j<tensors.size()-2; j++) {
				td += Math.pow(0.7, j-t) * (tensors.get(t+1).get(0)[0]-tensors.get(t).get(0)[0]);
			}
			s = scalarMultiply(s, td);
		}
		DenseMatrix delta = new DenseMatrix(new double[][] {s});
		nn.OUT.weightMatrix = nn.OUT.weightMatrix.add(delta).mul(0.5);
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
