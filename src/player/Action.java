package player;

import java.util.ArrayList;

import environment.MoveList;
import environment.Position;
import environment.Side;

public class Action {
	
	static final int U = 0;
	static final int R = 1;
	static final int DL= 2;
	static final int O = 3;
	double score;
	int direction;
	long bitboard;
	
	public Action(int direction, long bitboard) {
		this.direction = direction;
		this.bitboard = bitboard;
	}

	public static ArrayList<Action> generateActions(long[] moves) {
		ArrayList<Action> actions = new ArrayList<Action>();
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			generateSubActions(actions, moves[i], i);
		}
		return actions;
	}

	private static void generateSubActions(ArrayList<Action> actions, long fullMoves, int direction) {
		while(fullMoves != 0) {
			long singularMove = Long.highestOneBit(fullMoves);
			actions.add(new Action(direction, singularMove));
			fullMoves -= singularMove;
		}
	}

	public static Position applyAction(Position p, Action a, Side color) {
		Position nextP = new Position(p.getPieces().clone(), color);
		Side opponent;
		if(color == Side.H) {
			opponent = Side.V;
		}
		else {
			opponent = Side.H;
		}
		
		long legalBB = a.bitboard;
		long newBB = 0;
		
		switch(a.direction) {
			case R:
				newBB = legalBB >>> 1;
				break;
			case U:
				newBB = legalBB >>> Position.dimen;
				break;
			case DL:
				if(color == Side.H) {
					newBB = legalBB << Position.dimen;
				}
				else {
					newBB = legalBB << 1;
				}
				break;
			case O:
				break;
		}
		nextP.setCurrPieces(nextP.getCurrPieces() & ~legalBB | newBB, opponent);
		return nextP;
	}

	public static void supplyAction(Position p, Action bestAction) {
		Side color = p.sidePlaying;
		Side opponent;
		if(color == Side.H) {
			opponent = Side.V;
		}
		else {
			opponent = Side.H;
		}
		
		long legalBB = bestAction.bitboard;
		long newBB = 0;
		
		switch(bestAction.direction) {
			case R:
				newBB = legalBB >>> 1;
				break;
			case U:
				newBB = legalBB >>> Position.dimen;
				break;
			case DL:
				if(color == Side.H) {
					newBB = legalBB << Position.dimen;
				}
				else {
					newBB = legalBB << 1;
				}
				break;
			case O:
				break;
		}
		//System.out.println(Parse.bitBoardToBoardString(legalBB));
		//System.out.println(Parse.bitBoardToBoardString(newBB));
		//System.out.println(Parse.bitBoardToBoardString(p.getCurrPieces()));
		p.setCurrPieces((p.getCurrPieces() & (~legalBB)) | newBB, opponent);
	}
	
}
