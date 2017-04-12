package environment;


public class TestDriver {

	public static void main(String[] args) {
		long bitboard = 0b000010000;
		int i=0;
		Position.dimen = 3;
		System.out.println(Parse.bitBoardToBoardString(bitboard));
		while(bitboard % 2 == 0) {
			bitboard /= 2;
			i++;
		}
		int row = Position.dimen-i/Position.dimen;
		int col = Position.dimen-i%Position.dimen+96;
		System.out.println(Character.toString((char) col).concat(Integer.toString(row)));
		/*Parse.initScan();
		Position curr = Parse.parseBoard();
		for(int i=0; i<4; i++) {
			System.out.println(Parse.bitBoardToBoardString(curr.ml.moves[i]));
		}
		Parse.closeScan();*/
	}

}
