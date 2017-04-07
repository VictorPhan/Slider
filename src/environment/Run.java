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
		
		System.out.println("");
		
		if(curr.sidePlaying == Side.H) {
			hFirst = true;
		}
		else {
			hFirst = false;
		}
		
		Player ph = new Human(Side.H);
		Player pv = new Human(Side.V);
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
		
		if(curr.gs==GameState.DRAW) {
			addHistory("#=");
		}
		else if(curr.gs==GameState.H_WON) {
			addHistory("#H");
		}
		else if(curr.gs==GameState.V_WON) {
			addHistory("#V");
		}
		else {
			throw new Error("Game hasn't ended.");
		}
		
		System.out.println(moveHistory);
		Parse.closeScan();
	}
	
	public static void addHistory(String move) {
		moveHistory = moveHistory.concat(move).concat(" ");
	}
	
}
