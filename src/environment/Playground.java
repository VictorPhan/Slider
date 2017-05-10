package environment;

import jeigen.DenseMatrix;

public class Playground {
	public static void main(String[] args) {
		DenseMatrix dm1 = new DenseMatrix(new double[][] {{2,-3, -2},{0, 9,-8}});
		dm1 = dm1.gt(0);
		System.out.println(dm1.toString());
		
	}
}
