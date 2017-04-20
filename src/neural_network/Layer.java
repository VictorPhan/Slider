package neural_network;

import java.util.Arrays;
import jeigen.DenseMatrix;

public class Layer {
	
	// The weightMatrix which includes every weight
	public DenseMatrix weightMatrix;
	
	/**
	 * Initialise weightMatrix with random values
	 * @param rows
	 * @param cols
	 */
	public Layer(int rows, int cols) {
		this.weightMatrix = DenseMatrix.rand(rows, cols);
		weightMatrix = weightMatrix.sub(0.5);
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
	
	public double[] outputNoReLu(double[] input) {
		DenseMatrix inputVector = new DenseMatrix(new double[][] {input});
		return weightMatrix.mmul(inputVector.t()).getValues();
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
