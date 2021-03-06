package neural_network;

import java.util.Arrays;

import environment.Position;
import jeigen.DenseMatrix;

public class Layer {
	
	// The weightMatrix which includes every weight
	public DenseMatrix weightMatrix;
	double scalingFactor = 100;
	
	/**
	 * Initialise weightMatrix with random values
	 * @param rows
	 * @param cols
	 */
	public Layer(int rows, int cols) {
		this.weightMatrix = DenseMatrix.rand(rows, cols).sub(0.5).div(2);
	}
	
	/**
	 * Initialise weightMatrix with pre-defined values
	 * @param weightMatrix
	 */
	public Layer(DenseMatrix weightMatrix) {
		this.weightMatrix = weightMatrix;
	}
	
	/**
	 * Return the output vector from applying weightMatrix and reLu
	 * @param input
	 * @return
	 */
	public double[] output(double[] input) {
		DenseMatrix inputVector = new DenseMatrix(new double[][] {input});
		return reLu(weightMatrix.mmul(inputVector.t()).getValues());
	}
	
	public double[] outputTanh(double[] input) {
		DenseMatrix inputVector = new DenseMatrix(new double[][] {input});
		return Arrays.stream(weightMatrix.mmul(inputVector.t()).getValues()).
				map(x -> Math.tanh(x)).toArray();
	}
	
	/**
	 * Rectified linear unit activation function
	 * @param rawOutput
	 * @return
	 */
	public static double[] reLu(double[] rawOutput) {
		return Arrays.stream(rawOutput).map(x -> reLu(x)).toArray();
	}
	
	public static double reLu(double x) {
		if(x < 0) {
			return 0;
		}
		return x;
	}
	
}
