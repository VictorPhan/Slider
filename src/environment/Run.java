package environment;

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
		curr = human.makeMove(curr);
		Parse.closeScan();
	}
}
