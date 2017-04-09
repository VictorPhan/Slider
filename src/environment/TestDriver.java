package environment;

import java.util.Arrays;
//import exceptions.InvalidMoveException;

public class TestDriver {

	public static void main(String[] args) {
		// Overflow at 2^63
		/*System.out.println(Long.bitCount(0));
		
		Parse.initScan();
		Position.dimen = 8;
		int[] frd = null;
		try {
			frd = Parse.readMove(Side.V);
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long b = Parse.frToBitboard(frd[0], frd[1]);
		System.out.println(b);
		System.out.println(Parse.bitBoardToBoardString(b));
		Parse.closeScan();*/
		long[] l1 = {1,2,3};
		long[] l2 = {1,2,3};
		if(Arrays.equals(l1, l2)) {
			System.out.println("same");
		}
		else {
			System.out.println("not same");
		}
	}

}
