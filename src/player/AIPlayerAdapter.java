package player;

import environment.Position;
import environment.Side;
import top_end.Move;
import top_end.Move.Direction;

/**
 * Adapter class to serve as indirection to the project spec
 * @author TB VP
 *
 */
public class AIPlayerAdapter {
	
	/**
	 * Converts from our bitboard representation to the Move (spec) format
	 * @param newP The new state of the board in position
	 * @param prev The old position of the board
	 * @return The new move being played by us in Move format
	 */
	public static Move bitboardToMove(Position newP, Position prev) {
		long diff, prevState, newState;
		Direction d;
		
		if(prev.sidePlaying == Side.H) {
			diff = newP.getPieces()[1] ^ prev.getPieces()[1];
			diff = ~diff;
			prevState = diff & prev.getPieces()[1];
			newState = diff & newP.getPieces()[1];
		} else if (prev.sidePlaying == Side.V) {
			diff = newP.getPieces()[0] ^ prev.getPieces()[0];
			diff = ~diff;
			prevState = diff & prev.getPieces()[0];
			newState = diff & newP.getPieces()[0];
		} else {
			throw new Error("Board ERROR");
		}
		
		int index = Long.numberOfLeadingZeros(prevState);
		int y = (int) Math.floor(index / Position.dimen);
		int x = Long.numberOfLeadingZeros(prevState << y*Position.dimen);
		
		int newIndex = Long.numberOfLeadingZeros(newState);
		int newY = (int) Math.floor(newIndex / Position.dimen);
		int newX = Long.numberOfLeadingZeros(prevState << y*Position.dimen);
		
		if((newX - x) == 1) {
			d = Direction.RIGHT;
		} else if((newX - x) == -1) {
			d = Direction.LEFT;
		} else if((newY - y) == 1) {
			d = Direction.UP;
		} else if((newY - y) == -1) {
			d = Direction.DOWN;
		} else {
			throw new Error("Move direction ERROR");
		}
		
		Move newMove = new Move(x, y, d);
		return newMove;
	}
	
	/**
	 * Converts the Move (spec format) to our bitboard representation
	 * @param move The move that the opponent is playing
	 * @param curr The current state of the board before the move
	 * @return The new board state after the opponent's move
	 */
	public static Position moveToBitboard(Move move, Position curr) {
		
		if(move == null){
			return curr;
		}
		long newMove = 0L;
		System.out.println(move.i);
		newMove = newMove ^ (1L << move.i);
		newMove = newMove << (Position.dimen * move.j);
		if(move.d == Direction.RIGHT) {
			newMove = newMove >>> 1;
		} else if(move.d == Direction.LEFT) {
			newMove = newMove << 1;
		} else if(move.d == Direction.UP) {
			newMove = newMove >>> Position.dimen;
		} else if(move.d == Direction.DOWN) {
			newMove = newMove << Position.dimen;
		} else {
			throw new Error("Move direction ERROR");
		}
		
		if(curr.sidePlaying == Side.H) {
			newMove = curr.getPieces()[1] | newMove;
		} else {
			newMove = curr.getPieces()[0] | newMove;
		}
		
		curr.setCurrPieces(newMove);
		return curr;
	}
}
