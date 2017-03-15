package environment;

/**
 * The main file for the AI agent
 * @author TB VP
 *
 */
public class Run {
	public static void main(String[] args) {
		//long [] pieces = {0b011000000, 0b000100100};
		//pieces[1] = pieces[1]>>1;
		//Position b = new Position(3, pieces);
		//System.out.print(Parse.boardToString(b));
		Position init = Parse.parseBoard();
		System.out.println("\n");
		System.out.println(Parse.boardToString(init));
	}
}
