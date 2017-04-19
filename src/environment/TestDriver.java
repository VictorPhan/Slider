package environment;

import neural_network.NeuralNetwork;
import player.Evaluation;

public class TestDriver {
	public static void main(String[] args) {
		Parse.initScan();
		Position p = Parse.parseBoard();
		NeuralNetwork nn = new NeuralNetwork
				(4, (Position.dimen-1)*5*2+4, Position.dimen * Position.dimen, 
				4, (Position.dimen-1)*5*2+4, Position.dimen * Position.dimen, 
				4+(Position.dimen-1)*5*2+4+Position.dimen * Position.dimen);
		System.out.println(nn.evaluate(Evaluation.createInputLayer(p)));
		Parse.closeScan();
	}
}
