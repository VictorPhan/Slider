package environment;

public class TestDriver {

	public static void main(String[] args) {
		// Overflow at 2^63
		System.out.println(Long.bitCount(0));
		
		Parse.initScan();
		Position.dimen = 8;
		int[] frd = Parse.readMove();
		long b = Parse.frToBitboard(frd[0], frd[1]);
		System.out.println(b);
		System.out.println(Parse.bitBoardToBoardString(b));
		Parse.closeScan();
	}

}
