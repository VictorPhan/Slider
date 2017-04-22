package environment;

import java.util.ArrayList;
import java.util.Arrays;

import jeigen.DenseMatrix;
import neural_network.Evaluation;
import neural_network.NeuralNetwork;
import player.AIPlayer;

public class TestDriver {
	static final int movesTD = 5;
	static final int cases = 6;
	public static void main(String[] args) {
		Parse.initScan();
		Position p = Parse.parseBoard();
		AIPlayer ai = new AIPlayer();
		ai.setPrintMove(false);
		for(int i=0; i<cases; i++) {
			p = Parse.parseBoard();
			System.out.println(ai.e.evaluate(p));
			printArrayDouble(Evaluation.nn.evaluateLearn(Evaluation.createInputLayer(p)));
		}
		Parse.closeScan();
	}
	
	public static void printArrayDouble(ArrayList<double[]> dd) {
		for(double[] d: dd) {
			System.out.println(Arrays.toString(d));
		}
	}
}
