package environment;

/**
 * The main file for the AI agent
 * @author TB VP
 *
 */
public class Run implements Consts {
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		Position init = Parse.parseBoard();
		System.out.println(init.ml.nHMoves());
		System.out.println(init.ml.nVMoves());
		
		long endTime   = System.nanoTime();
		long totalTime = (endTime - startTime) / 1000000;
		System.out.println("Runtime = " + totalTime + "ms");
	}
}
