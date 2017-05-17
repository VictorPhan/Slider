package playerTBVP;

import java.util.ArrayList;

import environment.GameHistory;
import environment.GameState;
import environment.MoveList;
import environment.Position;
import environment.Side;
import environment.Parse;
import neural_network.Evaluation;
import playerTBVP.AIPlayerAdapter;
import top_end.Move;

public class AIPlayer extends Player {
	
	public static int MAX_DEPTH = 8;
	char illegalMove;
	/** The move made print */
	boolean printMove = false;
	public Evaluation e;
	public Position curr;
	public String currentMove;
	
	public AIPlayer() {
		if(Position.dimen == 4) {
			setDepth(9);
		}
		else if(Position.dimen == 5) {
			setDepth(12);
		}
		else if(Position.dimen == 6) {
			setDepth(5);
		}
		else if(Position.dimen == 7) {
			setDepth(4);
		}
		// TOGGLED OFF FOR REFEREE //
		//e = new Evaluation();
	}
	
	public static void setDepth(int depth) {
		MAX_DEPTH = depth;
	}
	
	/**
	 * Checks if the previous move is a pass
	 * @param ml list of moves
	 * @return whether it is true or not
	 */
	public boolean checkPass(long[] ml) {
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			if(Long.bitCount(ml[i])!=0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * The machine learning version of alpha beta
	 * @param p the current position
	 * @param loud
	 * @return the utility values for the network
	 */
	public ArrayList<double[]> makeMoveLearn(Position p, boolean loud) {
		// Check if position is won
		if(p.gs != GameState.PLAYING) {
			return e.evaluateLearn(p);
		}
		
		if(checkPass(p.ml.moves)) {
			p.setCurrPieces(p.getCurrPieces());
			return e.evaluateLearn(p);
		}
		
		Action bestAction = alphaBetaLearn(p);
		if(loud) {
			Action.supplyAction(p, bestAction);
		}
		
		if(bestAction.nnTensor.size()==1) {
			throw new Error("makeMoveLearn tensor size == 1");
		}
		
		return bestAction.nnTensor;
	}
	
	/**
	 * The machine learning version of alpha beta
	 * @param p the current position
	 * @return direction and the bitboard for the corresponding move
	 */
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
				p.draw();
				throw new Error("acrions");
			}
			if(a.score == v) {
				return a;
			}
		}
		return null; // will return error. this line shouldn't be reached.
	}

	/**
	 * The machine learning version of max value
	 * @param p the current position
	 * @param actions list of possible actions
	 * @param depth current depth
	 * @param alpha 
	 * @param beta
	 * @return max value representing the opponent's best move
	 */
	public ArrayList<double[]> maxValueLearn(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return e.evaluateLearn(p);
		}
		ArrayList<double[]> v = new ArrayList<double[]>();
		v.add(new double[] {Evaluation.V_WIN_SCORE});
		
		// passing move
		if(actions.size() == 0) {
			Position newPos = Action.applyAction(p, null);
			ArrayList<Action> newActions = Action.generateActions(newPos.ml.moves);
			v = minValueLearn(newPos, newActions, depth + 1, alpha, beta);
			return v;
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
	
	/**
	 * The machine learning version of min value
	 * @param p the current position
	 * @param actions list of possible actions
	 * @param depth current depth
	 * @param alpha 
	 * @param beta
	 * @return min value representing the opponent's best move
	 */
	public ArrayList<double[]> minValueLearn(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return e.evaluateLearn(p);
		}
		ArrayList<double[]> v = new ArrayList<double[]>();
		v.add(new double[] {Evaluation.H_WIN_SCORE});
		
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
		else {
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
			GameHistory.addHistory("-");
			return 0;

		}
		
		// Otherwise run alpha beta algorithm
		Action bestAction = alphaBeta(p);
		Action.supplyAction(p, bestAction);
		String move = bestAction.toString(p.sidePlaying);
		currentMove = move;
		
		if(printMove == true) {
			System.out.println(bestAction.score);
			if(p.sidePlaying == Side.H) {
				System.out.println("H player move: " + move);
			}
			else {
				System.out.println("V player move: " + move);
			}
		}
		GameHistory.addHistory(move);
		return bestAction.score;
	}
	
	/**
	 * Applies alpha beta on the search
	 * @param p the current position
	 * @return direction and the bitboard for the corresponding move
	 */
	public Action alphaBeta(Position p) {
		ArrayList<Action> actions;
		if(Position.dimen < Position.BIG_INT_CASE){
			actions = Action.generateActions(p.ml.moves);
		} else {
			actions = Action.generateActions(p.ml.bigMoves);
		}
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
	
	/**
	 * Maximising choice in alpha beta search.
	 * @param p the current position
	 * @param actions list of possible actions
	 * @param depth current depth
	 * @param alpha
	 * @param beta
	 * @return max value representing the opponent's best move
	 */
	public double maxValue(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return e.evaluate(p);
		}
		double v = Evaluation.V_WIN_SCORE;
		
		// passing move
		if(actions.size() == 0) {
			Position newPos = Action.applyAction(p, null);
			ArrayList<Action> newActions;
			if(Position.dimen < Position.BIG_INT_CASE){
				newActions = Action.generateActions(newPos.ml.moves);
			} else {
				newActions = Action.generateActions(newPos.ml.bigMoves);
			}
			v = Math.max(v, minValue(newPos, newActions, depth + 1, alpha, beta));
			alpha = Math.max(alpha, v);
			if(alpha >= beta) {
				return v;
			}
		}
		else {
			for(Action a : actions) {
				Position newPos = Action.applyAction(p, a);
				ArrayList<Action> newActions;
				if(Position.dimen < Position.BIG_INT_CASE){
					newActions = Action.generateActions(newPos.ml.moves);
				} else {
					newActions = Action.generateActions(newPos.ml.bigMoves);
				}
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
	
	/**
	 * Minimising choice in alpha beta search.
	 * @param p the current position
	 * @param actions list of possible actions
	 * @param depth current depth
	 * @param alpha 
	 * @param beta
	 * @return min value representing the opponent's best move
	 */
	public double minValue(Position p, ArrayList<Action> actions, int depth, double alpha, double beta) {
		if(cutOffTest(p, depth)) {
			return e.evaluate(p);
		}
		double v = Evaluation.H_WIN_SCORE;
		
		// passing move
		if(actions.size() == 0) {
			Position newPos = Action.applyAction(p, null);
			ArrayList<Action> newActions;
			if(Position.dimen < Position.BIG_INT_CASE){
				newActions = Action.generateActions(newPos.ml.moves);
			} else {
				newActions = Action.generateActions(newPos.ml.bigMoves);
			}
			v = Math.min(v, maxValue(newPos, newActions, depth + 1, alpha, beta));
			beta = Math.min(beta, v);
			if(beta <= alpha) {
				return v;
			}
		}
		else {
		// for each possible action, apply maximising on those actions
			for(Action a : actions) {
				Position newPos = Action.applyAction(p, a);
				ArrayList<Action> newActions;
				if(Position.dimen < Position.BIG_INT_CASE){
					newActions = Action.generateActions(newPos.ml.moves);
				} else {
					newActions = Action.generateActions(newPos.ml.bigMoves);
				}
				v = Math.min(v, maxValue(newPos, newActions, depth + 1, alpha, beta));
				a.score = v;
				beta = Math.min(beta, v);
				if(beta <= alpha) {
					a.score = v;
					return v;
				}
			}
		}
		return v;
	}

	/**
	 * The cut off test for the search algorithm
	 * @param p the current position
	 * @param depth current depth
	 * @return whether it should be cut off or not
	 */
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
		curr = Parse.parseBoard(dimension, player, board);
		e = new Evaluation();
	}

	@Override
	public void update(Move move) {
		curr = (AIPlayerAdapter.moveToBitboard(move, curr)).copyPosition();
		curr.updateBoard(curr.sidePlaying);
	}

	@Override
	public Move move() {
		Position prevBoard = curr.copyPosition();
		makeMove(curr);
		curr.updateBoard(curr.sidePlaying);	
		return AIPlayerAdapter.bitboardToMove(curr, prevBoard, currentMove);
	}
}
