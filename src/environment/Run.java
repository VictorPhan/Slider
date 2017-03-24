package environment;

/**
 * The main file for the AI agent
 * @author TB VP
 *
 */
public class Run implements Consts {
	
	public static void main(String[] args) {
		
		Position init = Parse.parseBoard();
		System.out.println("");
		System.out.println(Parse.boardToString(init));
		System.out.println(Parse.bitBoardToBoardString(init.ml.hMoves[H_D]));
		
		
		/*// Part of testing
		long [] pieces = {0b011000000, 0b000100100, 0b000000001};
		pieces[1] = pieces[1]>>1;
		Position b = new Position(3, pieces);
		System.out.println(Parse.boardToString(init));
		init.getPieces()[Position.V] = init.getPieces()[Position.V] >> Position.getDimension();
		//System.out.println(Long.toBinaryString(~(init.getPieces(V) | init.getPieces(H))));
		//long temp = 0b0;
		//System.out.println(Long.toBinaryString(temp) + "\n" + Long.toBinaryString(~temp));
		*/
	}
}
