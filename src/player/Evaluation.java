package player;

import environment.GameState;
import environment.Position;
import environment.Side;
import static jeigen.Shortcuts.*;

/**
 * Evaluation class. POSITIVE INFINITY is H's win, NEGATIVE INFINITY is V's win
 * @author Victor
 *
 */
public class Evaluation {
		static int V = 0;
		static int H = 1;
	public static double evaluate(Position p, Side color) {
		if (p.gs == GameState.DRAW) {
			return 0;
		}
		else if(p.gs == GameState.H_WON) {
			return Double.POSITIVE_INFINITY;
		}
		else if(p.gs == GameState.V_WON) {
			return Double.NEGATIVE_INFINITY;
		}
		
		return 0;
	}
}
