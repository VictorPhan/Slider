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
		String output = "";
		for(int i=0; i<2; i++) {
			output = output.concat(Long.toBinaryString(board.getPieces(i)));
			output = output.concat("\n");
		}
		return output;
	}
}
