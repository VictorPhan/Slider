package player;

import environment.GameState;
import environment.MoveList;
import environment.Position;
import environment.Side;

public class Evaluation {
		static int V = 0;
		static int H = 1;
	public static double evaluate(Position p, Side color) {
		if(p.gs == GameState.H_WON) {
			return Double.POSITIVE_INFINITY;
		}
		else if(p.gs == GameState.V_WON) {
			return Double.NEGATIVE_INFINITY;
		}
		long[] pieces = p.getPieces();
		double pieceDiff = Long.bitCount(pieces[V])-Long.bitCount(pieces[H]);
		long[] hMoves = MoveList.generateHMoves(p.getPieces());
		long[] vMoves = MoveList.generateVMoves(p.getPieces());
		double mobilityDiff = 0;
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			mobilityDiff += Long.bitCount(hMoves[i])-Long.bitCount(vMoves[i]);
		}
		mobilityDiff = mobilityDiff/(Long.bitCount(pieces[V]) + Long.bitCount(pieces[H]));
		double score = pieceDiff + mobilityDiff;
		if(color == Side.V) {
			score *= -1;
		}
		return score;
	}
}
