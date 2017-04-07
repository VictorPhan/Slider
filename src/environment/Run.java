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
	
	static String moveHistory = "";
	
	public static void main(String[] args) {
		Parse.initScan();
		Position curr = Parse.parseBoard();
		boolean hFirst;
		int moveNum = 1;
		
		if(curr.sidePlaying == Side.H) {
			hFirst = true;
		}
		else {
			hFirst = false;
		}
		
		Player ph = new Human(Side.H);
		Player pv = new Human(Side.V);
		curr.draw();
		while(curr.gs==GameState.PLAYING) {
			if(curr.sidePlaying==Side.H) {
				if(hFirst) {
					addHistory(Integer.toString(moveNum) + ".");
					moveNum++;
				}
				try {
					ph.makeMove(curr);
				} catch (InvalidMoveException e) {
					e.printStackTrace();
				}
			}
			else {
				if(!hFirst) {
					addHistory(Integer.toString(moveNum) + ".");
					moveNum++;
				}
				try {
					pv.makeMove(curr);
				} catch (InvalidMoveException e) {
					e.printStackTrace();
				}
			}
			curr.draw();
		}
		addHistory(curr.gs.toString());
		System.out.println(moveHistory);
		Parse.closeScan();
	}
	
	public static void addHistory(String move) {
		moveHistory = moveHistory.concat(move).concat(" ");
	}
	
}
