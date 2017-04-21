package neural_network;

import java.util.Arrays;

import environment.GameState;
import environment.Parse;
import environment.Position;
import exceptions.InvalidMoveException;
import player.AIPlayer;
import player.Evaluation;

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
		AIPlayer ai = new AIPlayer(p.sidePlaying);
		ai.setPrintMove(false);
		for(int i=0; i<movesTD; i++) {
			ai.makeMove(p);
			System.out.println(
					Arrays.toString(
							Evaluation.nn.evaluateLearn(
									Evaluation.createInputLayer(p)).get(2)));
			if(p.gs != GameState.PLAYING) {
				break;
			}
		}
		Parse.closeScan();
	}

}
