package environment;

/**
 * The main file for the AI agent
 * @author TB VP
 *
 */
public class Run {
	public static void main(String[] args) {
		long [] pieces = {0b011000000>>3, 0b000100100};
		Position init = new Position(3, pieces);
		System.out.println(Parse.boardToString(init));
	}
}
