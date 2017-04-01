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
		Player human = new Human();
		curr.draw();
		try {
			curr = human.makeMove(curr);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
		Parse.closeScan();
	}
}
