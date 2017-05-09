package player;

import java.util.ArrayList;

import environment.GameHistory;
import environment.GameState;
import environment.MoveList;
import environment.Position;
import environment.Side;
import environment.Parse;
import neural_network.Evaluation;
import top_end.Move;
import player.AIPlayerAdapter;

public class AIPlayer extends Player {
	
	public int MAX_DEPTH = 4;
	char illegalMove;
	boolean printMove = true;
	public Evaluation e = new Evaluation();
	public Position curr;
	
	public AIPlayer() {
	}
	
	public boolean checkPass(long[] ml) {
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			if(Long.bitCount(ml[i])!=0) {
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<double[]> makeMoveLearn(Position p) {
		// Check if position is won
		if(p.gs != GameState.PLAYING) {
			return e.evaluateLearn(p);
		}
		
		if(checkPass(p.ml.moves)) {
			p.setCurrPieces(p.getCurrPieces());
			return e.evaluateLearn(p);
		}
		
		Action bestAction = alphaBetaLearn(p);
		Action.supplyAction(p, bestAction);
		
		if(bestAction.nnTensor.size()==1) {
			throw new Error("makeMoveLearn tensor size == 1");
		}
		
		return bestAction.nnTensor;
	}
	
	private Action alphaBetaLearn(Position p) {
		ArrayList<Action> actions = Action.generateActions(p.ml.moves);
		double v;
		if(p.sidePlaying == Side.H) {
			v = maxValueLearn(p, actions, 0, Evaluation.V_WIN_SCORE, Evaluation.H_WIN_SCORE).get(0)[0];
		}
		else {
			v = minValueLearn(p, actions, 0, Evaluation.V_WIN_SCORE, Evaluation.H_WIN_SCORE).get(0)[0];
		}
		for(Action a : actions) {
			if(a.nnTensor.size() == 1) {
				System.out.println(a.nnTensor.get(0)[0]);
				throw new Error("acrions");
			}
			if(a.score == v) {
				return a;
			}
		}
		return null; // will return error. this line shouldn't be reached.
	}

	public ArrayList<double[]> maxValueLearn(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return e.evaluateLearn(p);
		}
		ArrayList<double[]> v = new ArrayList<double[]>();
		v.add(new double[] {Evaluation.V_WIN_SCORE * 2});
		
		// passing move
		if(actions.size() == 0) {
			Position newPos = Action.applyAction(p, null);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			ArrayList<double[]> u = minValueLearn(newPos, newActions, depth + 1, alpha, beta);
			if(v.get(0)[0] <= u.get(0)[0]) {
				v = u;
			}
			alpha = Math.max(alpha, v.get(0)[0]);
			if(alpha >= beta) {
				return v;
			}
		}
		else {
			for(Action a : actions) {
				Position newPos = Action.applyAction(p, a);
				ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
				ArrayList<double[]> u = minValueLearn(newPos, newActions, depth + 1, alpha, beta);
				if(v.get(0)[0] <= u.get(0)[0]) {
					v = u;
				}
				a.score = v.get(0)[0];
				a.nnTensor = v;
				alpha = Math.max(alpha, v.get(0)[0]);
				if(alpha >= beta) {
					return v;
				}
			}
		}
		return v;
	}
	
	public ArrayList<double[]> minValueLearn(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return e.evaluateLearn(p);
		}
		ArrayList<double[]> v = new ArrayList<double[]>();
		v.add(new double[] {Evaluation.H_WIN_SCORE * 2});
		
		// passing move
		if(actions.size() == 0) {
			Position newPos = Action.applyAction(p, null);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			ArrayList<double[]> u = maxValueLearn(newPos, newActions, depth + 1, alpha, beta);
			if(v.get(0)[0] >= u.get(0)[0]) {
				v = u;
			}
			beta = Math.min(beta, v.get(0)[0]);
			if(beta <= alpha) {
				return v;
			}
		}
		for(Action a : actions) {
			Position newPos = Action.applyAction(p, a);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			ArrayList<double[]> u = maxValueLearn(newPos, newActions, depth + 1, alpha, beta);
			if(v.get(0)[0] >= u.get(0)[0]) {
				v = u;
			}
			a.score = v.get(0)[0];
			a.nnTensor = v;
			beta = Math.min(beta, v.get(0)[0]);
			if(beta <= alpha) {
				return v;
			}
		}
		return v;
	}

	@Override
	public double makeMove(Position p) {
		// Check for passing move
		if(checkPass(p.ml.moves)) {
			if(printMove == true) {
				if(p.sidePlaying == Side.H) {
					System.out.println("H player move: Pass");
				}
				else {
					System.out.println("V player move: Pass");
				}
			}
			p.setCurrPieces(p.getCurrPieces());
			GameHistory.addHistory("—");
			return 0;
		}
		
		// TODO: include transposition tables
		Action bestAction = alphaBeta(p);
		Action.supplyAction(p, bestAction);
		String move = bestAction.toString(p.sidePlaying);
		
		if(printMove == true) {
			System.out.println(bestAction.score);
			if(p.sidePlaying == Side.H) {
				System.out.println("H player move: " + move);
			}
			else {
				System.out.println("V player move: " + move);
			}
		}
		curr = p;
		GameHistory.addHistory(move);
		return bestAction.score;
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
			return e.evaluate(p);
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
			return e.evaluate(p);
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

	@Override
	public void init(int dimension, String board, char player) {
		Parse.initScan(board);
		curr = Parse.parseBoard(dimension, player);
	}

	@Override
	public void update(Move move) {
		curr = AIPlayerAdapter.moveToBitboard(move, curr);
	}

	@Override
	public Move move() {
		Position prevBoard = curr;
		makeMove(curr);
		return AIPlayerAdapter.bitboardToMove(curr, prevBoard);
	}
}
