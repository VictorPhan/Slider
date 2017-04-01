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
		Player p1 = new Human(Side.H);
		curr.draw();
		try {
			curr = p1.makeMove(curr);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
		Parse.closeScan();
	}
}
