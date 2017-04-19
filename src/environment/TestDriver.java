package environment;

import java.util.Arrays;

import player.Evaluation;

public class TestDriver {
	public static void main(String[] args) {
		Parse.initScan();
		Position p = Parse.parseBoard();
		Parse.closeScan();
	}
}
