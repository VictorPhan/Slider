package player;

import environment.Parse;
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
	
	public static final char PLUS = '+';
	
	/**
	 * Converts from our bitboard representation to the Move (spec) format
	 * @param newP The new state of the board in position
	 * @param prev The old position of the board
	 * @return The new move being played by us in Move format
	 */
	public static Move bitboardToMove(Position newP, Position prev, String move) {
		long diff, prevState, newState;
		int index, newIndex, x, y, newX, newY;
		Direction d;
				
		//System.out.printf("PLAYER %s TURN!\n", newP.sidePlaying);
		//System.out.println(String.format("%25s", Long.toBinaryString(prev.getCurrPieces())).replace(' ', '0'));
		//System.out.println(String.format("%25s", Long.toBinaryString(newP.getCurrPieces())).replace(' ', '0'));
		
		diff = newP.getCurrPieces() ^ prev.getCurrPieces();
		prevState = diff & prev.getCurrPieces();
		newState = diff & newP.getCurrPieces();
		
		//System.out.println(String.format("%25s", Long.toBinaryString(prevState)).replace(' ', '0'));
		//System.out.println(String.format("%25s", Long.toBinaryString(newState)).replace(' ', '0'));
		
		int removePadding = 64 - (Position.dimen*Position.dimen);
		
		index = Long.numberOfLeadingZeros(prevState) - removePadding;
		y = (int)(index/Position.dimen);
		x = index - (y * Position.dimen);
		
		newIndex = Long.numberOfLeadingZeros(newState) - removePadding;
		newY = (int) Math.floor(newIndex / Position.dimen);
		newX = newIndex - (newY * Position.dimen);
		/*
		System.out.println(index);
		System.out.println(x);
		System.out.println(y);
		System.out.println(newIndex);
		System.out.println(newX);
		System.out.println(newY);
		*/
		char[] chessMove = move.toCharArray();
		
		if((chessMove[2] == PLUS) && (newP.sidePlaying == Side.H)) {
			d = Direction.RIGHT;
		} else {
			if((newX - x) == 1) {
				d = Direction.RIGHT;
			} else if((newX - x) == -1) {
				d = Direction.LEFT;
			} else if((newY - y) == 1) {
				d = Direction.UP;
			} else if((newY - y) == -1) {
				d = Direction.DOWN;
			} else {
				return null;
			}
		}
		
		//System.out.printf("%s PLAYING: ",newP.sidePlaying);
		//System.out.printf("(%d, %d)",x, y);
		//System.out.println(d);
		Move newMove = new Move(x, y, d);
		return newMove;
	}
	
	public static Move bbMove(String bestMove, Position curr) {
		
		int x, y, newX, newY;
		Direction d;
		
		char[] chessMove = bestMove.toCharArray(); 
		
		if(chessMove[2] == '+') {
			x = chessMove[0]-97;
			y = chessMove[1]-49;
			if(curr.sidePlaying == Side.H){
				d = Direction.RIGHT;
			} else {
				d = Direction.UP;
			}
			
		} else {
			y = chessMove[1]-49;
			newY = chessMove[3]-49;
			x = chessMove[0]-97;
			newX = chessMove[2]-97;
			
			if((newX - x) == 1) {
				d = Direction.RIGHT;
			} else if((newX - x) == -1) {
				d = Direction.LEFT;
			} else if((newY - y) == 1) {
				d = Direction.UP;
			} else if((newY - y) == -1) {
				d = Direction.DOWN;
			} else {
				return null;
			}
		}
		
		//System.out.printf("x:%d, y:%d, nx:%d, ny:%d\n", x, y, newX, newY);
		//System.out.println(d);
		
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
		
		//System.out.printf("(%d, %d)",move.i, move.j);
		//System.out.println(move.d);
		
		Position updatedBoard = curr.copyPosition();
		updatedBoard.swapPlayers();
		
		long newMove = Parse.pow(2, Position.dimen*Position.dimen - 1);
		//System.out.println(String.format("%25s", Long.toBinaryString(updatedBoard.getCurrPieces())).replace(' ', '0'));
		
		newMove = newMove >> move.i;
		newMove = newMove >> (Position.dimen * move.j);
		long original = newMove;
		//System.out.println("OG POSITION: ");
		//System.out.println(String.format("%25s", Long.toBinaryString(original)).replace(' ', '0'));
		
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
		
		//System.out.println("NEW MOVE: ");
		//System.out.println(String.format("%25s", Long.toBinaryString(newMove)).replace(' ', '0'));
		
		newMove = (updatedBoard.getCurrPieces() | newMove) ^ original;
		//System.out.println("NEW MOVE WITH BOARD: ");
		//System.out.println(String.format("%25s", Long.toBinaryString(newMove)).replace(' ', '0'));
		
		//System.out.println(String.format("%16s", Long.toBinaryString(curr.getCurrPieces())).replace(' ', '0'));
		updatedBoard.setCurrPieces(newMove);
		updatedBoard.swapPlayers();
		//System.out.printf("%s UPDATING: \n", curr.sidePlaying);
		//updatedBoard.draw();
		return updatedBoard;
	}
}
