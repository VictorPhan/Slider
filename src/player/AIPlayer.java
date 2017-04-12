package player;

import java.util.ArrayList;

import environment.GameHistory;
import environment.GameState;
import environment.MoveList;
import environment.Position;
import environment.Side;

public class AIPlayer extends Player {
	
	public int MAX_DEPTH = 4;
	Side color;
	Side opponent;
	char illegalMove;
	
	public AIPlayer(Side color) {
		this.color = color;
		if(color == Side.H) {
			illegalMove = 'L';
			opponent = Side.V;
		}
		else if(color == Side.V) {
			illegalMove = 'D';
			opponent = Side.H;
		}
	}
	
	public boolean checkPass(long[] ml) {
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			if(Long.bitCount(ml[i])!=0) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void makeMove(Position p) {
		// Check for passing move
		if(checkPass(p.ml.moves)) {
			if(color == Side.H) {
				System.out.println("H player move: Pass");
			}
			else {
				System.out.println("V player move: Pass");
			}
			p.setCurrPieces(p.getCurrPieces(), opponent);
			GameHistory.addHistory("—");
			return;
		}
		
		// First implement minimax algorithm, test on small board case (size 3), include transposition tables
		Action bestAction = alphaBeta(p);
		Action.supplyAction(p, bestAction);
		String move = bestAction.toString(color);
		System.out.println(bestAction.score);
		if(color == Side.H) {
			System.out.println("H player move: " + move);
		}
		else {
			System.out.println("V player move: " + move);
		}
		GameHistory.addHistory(move);
	}
	
	/**
	 * Returns a long[] containing respectively the direction and the bitboard for the corresponding move
	 * @param p
	 * @return
	 */
	public Action alphaBeta(Position p) {
		ArrayList<Action> actions = Action.generateActions(p.ml.moves);
		double v = 0;
		if(p.sidePlaying == Side.H) {
			v = maxValue(p, actions, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		}
		else {
			v = minValue(p, actions, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		}
		for(Action a : actions) {
			if(a.score == v) {
				return a;
			}
		}
		return actions.get(0);
	}
	
	public double maxValue(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return Evaluation.evaluate(p, color);
		}
		double v = Double.NEGATIVE_INFINITY;
		
		for(Action a : actions) {
			a.score = v;
			Position newPos = Action.applyAction(p, a);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			v = Math.max(v, minValue(newPos, newActions, depth + 1, alpha, beta));
			if(v >= beta) {
				a.score = v;
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}
	
	public double minValue(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return Evaluation.evaluate(p, color);
		}
		double v = Double.POSITIVE_INFINITY;
		for(Action a : actions) {
			a.score = v;
			Position newPos = Action.applyAction(p, a);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			v = Math.min(v, maxValue(newPos, newActions, depth + 1, alpha, beta));
			if(v <= alpha) {
				a.score = v;
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}

	private boolean cutOffTest(Position p, int depth) {
		if(p.gs != GameState.PLAYING || depth >= MAX_DEPTH) {
			return true;
		}
		return false;
	}

}
