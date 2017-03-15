package environment;

/**
 * Parses through the input data from run
 * @author TB VP
 *
 */
public class Parse {
	/**
	 * Converts a board into a string.
	 * Currently needs to combine H and V pieces.
	 * @param board the current position of the board
	 * @return the binary output of the board's current position
	 */
	public static String boardToString(Position board) {
		String newOutput = "";
		for(int i=0; i<2; i++) {
			newOutput = newOutput.concat(bitBoardToBoardString(board.getPieces()[i]));
			newOutput = newOutput.concat("\n");
		}
		return newOutput;
	}
	
	public static String bitBoardToBoardString(long bitboard) {
		return stringToBoardString(bitBoardToString(bitboard));
	}
	
	public static String stringToBoardString(String bitBoard) {
		String output = "";
		
		for(int i=0; i<Position.dimension; i++) {
			output = output.concat("\n");
			output = output.concat(reverseString(
					bitBoard.substring(Position.dimension*i, Position.dimension*(i+1))));
		}
		return reverseString(output);
	}
	
	private static String bitBoardToString(long bitboard) {
		String output = "";
		int leadingZeros = (int) Math.pow(Position.dimension, 2) - 
				Long.toBinaryString(bitboard).length();
		for(int j=0; j<leadingZeros; j++) {
			output = output.concat("0");
		}
		output = output.concat(Long.toBinaryString(bitboard));
		return output;
	}
	
	private static String reverseString(String s) {
		String output = "";
		for(int i=s.length()-1; i>=0; i--) {
			output = output.concat(s.substring(i, i+1));
		}
		return output;
	}
}
