/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *            COMP30024 Artificial Intelligence - Semester 1 2017            *
 *                      Project A - Slider Move Generation                   *
 *                                                                           *
 *          Submission by: Tin Bao <tinb> and Victor Phan <victorp1>         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package environment;

/**
 * The main file for the AI agent
 * @author TB VP
 *
 */
public class Run {
	
	public static final int MOVE_TYPES = 4;
	public static final int PIECE_TYPES = 3;
	public static final int BIG_INT_CASE = 8;
	
	public static void main(String[] args) {
		Position init = Parse.parseBoard();
		
		System.out.println(init.ml.nHMoves());
		System.out.println(init.ml.nVMoves());
	}
}
