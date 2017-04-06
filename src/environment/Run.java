package environment;

import exceptions.InvalidMoveException;
import player.Human;
import player.Player;

/**
 * The main file
 * @author TB VP
 *
 */
public class Run {
	
	public static void main(String[] args) {
		Parse.initScan();
		Position curr = Parse.parseBoard();
		Player ph = new Human(Side.H);
		Player pv = new Human(Side.V);
		curr.draw();
		while(curr.gs==GameState.PLAYING) {
			if(curr.sidePlaying==Side.H) {
				try {
					ph.makeMove(curr);
				} catch (InvalidMoveException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					pv.makeMove(curr);
				} catch (InvalidMoveException e) {
					e.printStackTrace();
				}
			}
			curr.draw();
		}
		Parse.closeScan();
	}
}
