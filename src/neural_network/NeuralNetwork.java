package neural_network;

import java.util.ArrayList;

import environment.Position;

public class NeuralNetwork {
	/* The modalities of the input to the first hidden layer—
	 * global, piece-centric and square-centric features,
	 * second hidden layer and output layer
	 */
	public Layer H1;
	public Layer H2;
	public Layer OUT;
	
	public NeuralNetwork(int s, int h1, int h2) {
		if(Position.dimen == 5) {
			H1 = new Layer(NeuralNetwork_Params.H1_5);
			H2 = new Layer(NeuralNetwork_Params.H2_5);
			OUT = new Layer(NeuralNetwork_Params.OUT_5);
		}
		else {
			H1	 = new Layer(s, s+1);
			H2   = new Layer(h2, s+1);
			OUT  = new Layer(1, h2+1);
		}
	}
	
	public double evaluate(double[] input) {
		double[] bias = {1};
		return 	OUT.outputNoReLu(concat(
				H2.output(concat(
				H1.output(concat(
				input, bias)), bias)), bias))[0];
	}
	
	public void printWeights() {
		System.out.println("H1:\n" + H1.weightMatrix);
		System.out.println("H2:\n" + H2.weightMatrix);
		System.out.println("OUT:\n" + OUT.weightMatrix);
	}
	
	public double[] concat(double[] a, double[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   double[] c = new double[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}

	public ArrayList<double[]> evaluateLearn(double[] input) {
		double[] bias = {1};
		ArrayList<double[]> tensor = new ArrayList<double[]>();
		tensor.add(0, input);
		tensor.add(0, H1.output(concat(tensor.get(0), bias)));
		tensor.add(0, H2.output(concat(tensor.get(0), bias)));
		tensor.add(0, OUT.outputNoReLu(concat(tensor.get(0), bias)));
		return tensor;
	}
	
}