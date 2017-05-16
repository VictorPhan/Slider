package playerTBVP;

import java.math.BigInteger;
import java.util.ArrayList;

import environment.MoveList;
import environment.Position;
import environment.Side;

/*
 * Action represents a single posible action that can be applied to (quiet) or supplied to a position.
 */
public class Action {
	
	static final int U = 0;
	static final int R = 1;
	static final int DL= 2;
	static final int O = 3;
	public double score;
	public int direction;
	public long bitboard;
	public BigInteger bigBitboard;
	public ArrayList<double[]> nnTensor;
	
	public Action(int direction, long bitboard) {
		this.direction = direction;
		this.bitboard = bitboard;
	}
	
	public Action(int direction, BigInteger bigBitboard) {
		this.direction = direction;
		this.bigBitboard = bigBitboard;
	}

	public static ArrayList<Action> generateActions(long[] moves) {
		ArrayList<Action> actions = new ArrayList<Action>();
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			generateSubActions(actions, moves[i], i);
		}
		return actions;
	}
	
	public static ArrayList<Action> generateActions(BigInteger[] moves) {
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
	
	private static void generateSubActions(ArrayList<Action> actions, BigInteger fullMoves, int direction) {
		while(!fullMoves.equals(0)) {
			BigInteger singularMove = fullMoves.shiftRight(fullMoves.bitLength() - 1);
			actions.add(new Action(direction, singularMove));
			fullMoves = fullMoves.subtract(singularMove);
		}
	}

	public static Position applyAction(Position p, Action a) {
		Position nextP;
		if(Position.dimen < Position.BIG_INT_CASE) {
			nextP = new Position(p.getPieces().clone(), p.sidePlaying, p.gHistory.clone());
		} else {
			nextP = new Position(p.getBigPieces().clone(), p.sidePlaying, p.gHistory.clone());
		}
		
		if(a == null) {
			nextP.swapPlayers();
			return nextP;
		}
		
		if(Position.dimen < Position.BIG_INT_CASE){
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
				if(p.sidePlaying == Side.H) {
					newBB = legalBB << Position.dimen;
				}
				else {
					newBB = legalBB << 1;
				}
				break;
			case O:
				break;
			}
			nextP.setCurrPieces(nextP.getCurrPieces() & ~legalBB | newBB);
			return nextP;
		} else {
		
			BigInteger bigLegalBB = a.bigBitboard;
			BigInteger bigNewBB = BigInteger.ZERO;
			
			switch(a.direction) {
			case R:
				bigNewBB = bigLegalBB.shiftRight(1);
				break;
			case U:
				bigNewBB = bigLegalBB.shiftRight(Position.dimen);
				break;
			case DL:
				if(p.sidePlaying == Side.H) {
					bigNewBB = bigLegalBB.shiftLeft(Position.dimen);
				}
				else {
					bigNewBB = bigLegalBB.shiftLeft(1);
				}
				break;
			case O:
				break;
			}
			nextP.setCurrPieces(nextP.getBigCurrPieces().and(bigLegalBB.not()).or(bigNewBB));
			return nextP;
		}
		
		
	}

	public static void supplyAction(Position p, Action bestAction) {
		if(Position.dimen < Position.BIG_INT_CASE) {
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
				if(p.sidePlaying == Side.H) {
					newBB = legalBB << Position.dimen;
				}
				else {
					newBB = legalBB << 1;
				}
				break;
			case O:
				break;
			}
			p.setCurrPieces((p.getCurrPieces() & (~legalBB)) | newBB);
			
		} else {
			BigInteger bigLegalBB = bestAction.bigBitboard;
			BigInteger bigNewBB = BigInteger.ZERO;
			
			switch(bestAction.direction) {
			case R:
				bigNewBB = bigLegalBB.shiftRight(1);
				break;
			case U:
				bigNewBB = bigLegalBB.shiftRight(Position.dimen);
				break;
			case DL:
				if(p.sidePlaying == Side.H) {
					bigNewBB = bigLegalBB.shiftLeft(Position.dimen);
				}
				else {
					bigNewBB = bigLegalBB.shiftLeft(1);
				}
				break;
			case O:
				break;
			}
			p.setCurrPieces((p.getBigCurrPieces().and(bigLegalBB.not()).or(bigNewBB)));
		}
	}
	
	/**
	 * toString class to print in chess notation
	 * @param color 
	 */
	public String toString(Side color) {
		if(color == Side.H) {
			color = Side.V;
		}
		else {
			color = Side.H;
		}
		int i=0;
		while(bitboard % 2 == 0) {
			bitboard /= 2;
			i++;
		}
		int col = Position.dimen-i%Position.dimen+96;
		int row = Position.dimen-i/Position.dimen;
		String from = Character.toString((char) (col)).concat(Integer.toString(row));
		String to = "";
		switch(direction) {
		case U:
			to = Character.toString((char) (col)).concat(Integer.toString(row+1));
			break;
		case R:
			to = Character.toString((char) (col+1)).concat(Integer.toString(row));
			break;
		case DL:
			if(color==Side.H) {
				to = Character.toString((char) (col)).concat(Integer.toString(row-1));
			}
			else {
				to = Character.toString((char) (col-1)).concat(Integer.toString(row));
			}
			break;
		case O:
			to = "+";
			break;
		}
		return from.concat(to);
	}
	
}
