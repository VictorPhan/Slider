package player;

import java.util.ArrayList;

import environment.GameHistory;
import environment.GameState;
import environment.MoveList;
import environment.Position;
import environment.Side;

public class AIPlayer extends Player {
	
	public int MAX_DEPTH = 5;
	public Side color;
	Side opponent;
	char illegalMove;
	boolean printMove = true;
	
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
			if(printMove == true) {
				if(color == Side.H) {
					System.out.println("H player move: Pass");
				}
				else {
					System.out.println("V player move: Pass");
				}
			}
			p.setCurrPieces(p.getCurrPieces(), opponent);
			GameHistory.addHistory("—");
			return;
		}
		
		// TODO: include transposition tables
		Action bestAction = alphaBeta(p);
		Action.supplyAction(p, bestAction);
		String move = bestAction.toString(color);
		
		if(printMove == true) {
			System.out.println(bestAction.score);
			if(color == Side.H) {
				System.out.println("H player move: " + move);
			}
			else {
				System.out.println("V player move: " + move);
			}
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
			v = maxValue(p, actions, 0, Evaluation.V_WIN_SCORE, Evaluation.H_WIN_SCORE);
		}
		else {
			v = minValue(p, actions, 0, Evaluation.V_WIN_SCORE, Evaluation.H_WIN_SCORE);
		}
		for(Action a : actions) {
			if(a.score == v) {
				return a;
			}
		}
		return null; // will return error. this line shouldn't be reached.
	}
	
	public double maxValue(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return Evaluation.evaluate(p);
		}
		double v = Evaluation.V_WIN_SCORE;
		
		// passing move
		if(actions.size() == 0) {
			Position newPos = Action.applyAction(p, null);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			v = Math.max(v, minValue(newPos, newActions, depth + 1, alpha, beta));
			alpha = Math.max(alpha, v);
			if(alpha >= beta) {
				return v;
			}
		}
		else {
			for(Action a : actions) {
				Position newPos = Action.applyAction(p, a);
				ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
				v = Math.max(v, minValue(newPos, newActions, depth + 1, alpha, beta));
				a.score = v;
				alpha = Math.max(alpha, v);
				if(alpha >= beta) {
					a.score = v;
					return v;
				}
			}
		}
		return v;
	}
	
	public double minValue(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return Evaluation.evaluate(p);
		}
		double v = Evaluation.H_WIN_SCORE;
		
		// passing move
		if(actions.size() == 0) {
			Position newPos = Action.applyAction(p, null);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			v = Math.min(v, maxValue(newPos, newActions, depth + 1, alpha, beta));
			beta = Math.min(beta, v);
			if(beta <= alpha) {
				return v;
			}
		}
		for(Action a : actions) {
			Position newPos = Action.applyAction(p, a);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			v = Math.min(v, maxValue(newPos, newActions, depth + 1, alpha, beta));
			a.score = v;
			beta = Math.min(beta, v);
			if(beta <= alpha) {
				a.score = v;
				return v;
			}
		}
		return v;
	}

	private boolean cutOffTest(Position p, int depth) {
		if(p.gs != GameState.PLAYING || depth >= MAX_DEPTH) {
			return true;
		}
		return false;
	}
	
	public void setPrintMove(boolean b) {
		printMove = b;
	}

}
