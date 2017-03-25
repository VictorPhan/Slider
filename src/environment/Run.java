package environment;

/**
 * The main file for the AI agent
 * @author TB VP
 *
 */
public class Run implements Consts {
	
	public static void main(String[] args) {
		
		Position init = Parse.parseBoard();
		System.out.println(init.ml.nHMoves());
		System.out.println(init.ml.nVMoves());
		
	}
}
