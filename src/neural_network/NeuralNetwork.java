package neural_network;

import java.util.Arrays;

public class NeuralNetwork {
	/* The modalities of the input to the first hidden layer—
	 * global, piece-centric and square-centric features,
	 * second hidden layer and output layer
	 */
	Layer H1_G;
	Layer H1_P;
	Layer H1_S;
	Layer H2;
	Layer OUT;
	
	/*
	 * g=global inputs, ng=global outputs
	 * p=piece inputs, np=piece outputs
	 * s=square inputs, s=square outputs
	 * h2=second hidden layer output
	 */
	int g, p, s, ng, np, ns, h2;
	
	public NeuralNetwork(int g, int p, int s, int ng, int np, int ns, int h2) {
		this.g = g;
		this.p = p;
		this.s = s;
		this.ng = ng;
		this.np = np;
		this.ns = ns;
		this.h2 = h2;
		H1_G = new Layer(ng, g);
		H1_P = new Layer(np, p);
		H1_S = new Layer(ns, s);
		H2   = new Layer(h2, ng+np+ns);
		OUT  = new Layer(1, h2);
	}
	
	public double evaluate(double[] input) {
		double[] h1g = H1_G.output(Arrays.copyOfRange(input, 0, g));
		double[] h1p = H1_P.output(Arrays.copyOfRange(input, g, g+p));
		double[] h1s = H1_S.output(Arrays.copyOfRange(input, g+p, g+p+s));
		double[] h1 = concat(concat(h1g, h1p), h1s);
		return (OUT.outputNoReLu(H2.output(h1)))[0];
	}
	
	public double evaluateLearn(double[] input) {
		double[] h1g_in = Arrays.copyOfRange(input, 0, g);
		double[] h1p_in = Arrays.copyOfRange(input, g, g+p);
		double[] h1s_in = Arrays.copyOfRange(input, g+p, g+p+s);
		double[] h1g_out = H1_G.output(h1g_in);
		double[] h1p_out = H1_P.output(h1p_in);
		double[] h1s_out = H1_S.output(h1s_in);
		double[] h2_in = concat(concat(h1g_out, h1p_out), h1s_out);
		double[] h2_out = H2.output(h2_in);
		double[] out_in = h2_out;
		double[] out_out = OUT.outputNoReLu(out_in);
		return out_out[0];
	}
	
	public void printWeights() {
		System.out.println("H1_G:\n" + H1_G.weightMatrix);
		System.out.println("H1_P:\n" + H1_P.weightMatrix);
		System.out.println("H1_S:\n" + H1_S.weightMatrix);
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
	
}