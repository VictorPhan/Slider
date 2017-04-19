package environment;

import neural_network.*;
import player.Evaluation;

import static jeigen.Shortcuts.*;

import java.util.Arrays;

import jeigen.DenseMatrix;
import jeigen.SparseMatrixLil;

public class TestDriver {
	public static void main(String[] args) {
		Parse.initScan();
		Position p = Parse.parseBoard();
		Evaluation.createInputLayer(p);
		Parse.closeScan();
	}
}
