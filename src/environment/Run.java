package environment;

import player.AIPlayer;
import player.Human;
import player.Player;

/*
 * Run class to play a single game between chosen players
 */
public class Run {
	
	public static void main(String[] args) {
		Parse.initScan();
		Position curr = Parse.parseBoard();
		boolean hFirst;
		
		System.out.println("");
		
		if(curr.sidePlaying == Side.H) {
			hFirst = true;
		}
		else {
			hFirst = false;
		}
		
		Player pv = new Human();
		Player ph = new AIPlayer();
		
		playGame(curr, hFirst, ph, pv);
		
		GameHistory.addFinalHistory(curr);
		
		System.out.println(GameHistory.moveHistory);
		Parse.closeScan();
	}
	
	public static void playGame(Position curr, boolean hFirst, Player ph, Player pv) {
		int moveNum = 1;
		while(curr.gs==GameState.PLAYING) {
			if(curr.sidePlaying==Side.H) {
				if(hFirst) {
					GameHistory.addHistory(Integer.toString(moveNum) + ".");
					moveNum++;
				}
				ph.makeMove(curr);
			}
			else {
				if(!hFirst) {
					GameHistory.addHistory(Integer.toString(moveNum) + ".");
					moveNum++;
				}
				pv.makeMove(curr);
			}
			curr.draw();
		}
	}
	
}
