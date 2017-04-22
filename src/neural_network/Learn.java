package neural_network;

import environment.GameState;
import environment.Parse;
import environment.Position;
import exceptions.InvalidMoveException;
import player.AIPlayer;

/**
 * Training the AI by temporal difference learning
 * @author Victor
 *
 */

public class Learn {
	
	static final int movesTD = 5;
	
	public static void main(String[] args) throws InvalidMoveException {
		Parse.initScan();
		Position p = Parse.parseBoard();
		AIPlayer ai = new AIPlayer();
		ai.setPrintMove(false);
		for(int i=0; i<movesTD; i++) {
			ai.makeMove(p);
			if(p.gs != GameState.PLAYING) {
				break;
			}
		}
		Parse.closeScan();
	}

}
