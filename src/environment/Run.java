/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *            COMP30024 Artificial Intelligence - Semester 1 2017            *
 *                      Project A - Slider Move Generation                   *
 *                                                                           *
 *          Submission by: Tin Bao <tinb> and Victor Phan <victorp1>         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package environment;

/**
 * The main file for the AI agent
 * 
 * @author TB VP
 *
 */
public class Run {

	/** The number of possible move directions */
	public static final int MOVE_TYPES = 4;
	/** The total number of different pieces */
	public static final int PIECE_TYPES = 3;
	/**
	 * Begins to use big integers to store the data after the board is greater
	 * than size 8 because the long data type runs out of space after 32 bits
	 */
	public static final int BIG_INT_CASE = 8;

	/**
	 * Main function
	 * 
	 * @param args: the argument line inputs (board)
	 */
	public static void main(String[] args) {
		/* Initialises the board by parsing in the input */
		Position init = Parse.parseBoard();

		/* Debug: draws the board state */
		// init.draw();

		/* Prints out number of legal H moves */
		System.out.println(init.ml.nHMoves());
		/* Prints out number of legal V moves */
		System.out.println(init.ml.nVMoves());
	}
}
