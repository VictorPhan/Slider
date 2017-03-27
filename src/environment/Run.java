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
		Position curr = Parse.parseBoard();
		curr.draw();
	}
}
