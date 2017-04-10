package player;

import environment.GameState;
import environment.Position;

public class Evaluation {

	public static double evaluate(Position p) {
		if(p.gs == GameState.V_WON) {
			return Double.POSITIVE_INFINITY;
		}
		else if(p.gs == GameState.H_WON) {
			return Double.NEGATIVE_INFINITY;
		}
		long[] pieces = p.getPieces();
		return Long.bitCount(pieces[0])-Long.bitCount(pieces[1]);
	}

}
