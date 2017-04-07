package environment;

import exceptions.InvalidMoveException;

public class TestDriver {

	public static void main(String[] args) {
		// Overflow at 2^63
		System.out.println(Long.bitCount(0));
		
		Parse.initScan();
		Position.dimen = 8;
		int[] frd = null;
		try {
			frd = Parse.readMove();
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long b = Parse.frToBitboard(frd[0], frd[1]);
		System.out.println(b);
		System.out.println(Parse.bitBoardToBoardString(b));
		Parse.closeScan();
	}

}
