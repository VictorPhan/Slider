package environment;

import java.util.ArrayList;
import player.Action;

public class TestDriver {

	public static void main(String[] args) {
		Parse.initScan();
		Position curr = Parse.parseBoard();
		for(int i=0; i<4; i++) {
			System.out.println(Parse.bitBoardToBoardString(curr.ml.moves[i]));
		}
		Parse.closeScan();
	}

}
