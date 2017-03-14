package environment;

public class Parse {
	/**
	 * Converts a board into a string.
	 * Currently needs to combine H and V pieces.
	 * @param board
	 * @return
	 */
	public static String boardToString(Position board) {
		String output = "";
		for(int i=0; i<2; i++) {
			output = output.concat(Long.toBinaryString(board.pieces[i]));
			output = output.concat("\n");
		}
		return output;
	}
}
