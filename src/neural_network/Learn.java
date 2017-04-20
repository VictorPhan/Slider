package neural_network;

import environment.GameState;
import environment.Parse;
import environment.Position;
import exceptions.InvalidMoveException;
import player.AIPlayer;
import player.Evaluation;

// training the AI by temporal difference learning

public class Learn {

	public static void main(String[] args) throws InvalidMoveException {
		Parse.initScan();
		Position p = Parse.parseBoard();
		AIPlayer ai = new AIPlayer(p.sidePlaying);
		ai.setPrintMove(false);
		System.out.println(Evaluation.evaluateLearn(p));
		for(int i=0; i<12; i++) {
			ai.makeMove(p);
			if(p.gs != GameState.PLAYING) {
				break;
			}
		}
		System.out.println(Evaluation.evaluateLearn(p));
		Parse.closeScan();
	}

}
