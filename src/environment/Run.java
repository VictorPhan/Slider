package environment;

/**
 * The main file for the AI agent
 * @author TB VP
 *
 */
public class Run {
	
	public static final int V = 0;
	public static final int H = 1;
	public static final int B = 2;
	
	public static void main(String[] args) {
		//long [] pieces = {0b011000000, 0b000100100, 0b000000001};
		//pieces[1] = pieces[1]>>1;
		//Position b = new Position(3, pieces);
		//System.out.print(Parse.boardToString(b));
		Position init = Parse.parseBoard();
		//System.out.println(Parse.boardToString(init));
		//init.getPieces()[Position.V] = init.getPieces()[Position.V] >> Position.getDimension();
		System.out.println(Parse.boardToString(init));
		//System.out.println(Long.toBinaryString(~(init.getPieces(V) | init.getPieces(H))));
		//long temp = 0b0;
		//System.out.println(Long.toBinaryString(temp) + "\n" + Long.toBinaryString(~temp));
	}
}
