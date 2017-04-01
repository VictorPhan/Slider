package player;

import environment.Parse;
import environment.Position;
import environment.Side;
import exceptions.InvalidMoveException;

public class Human extends Player {
	
	@Override
	public Position makeMove(Position p) throws InvalidMoveException {
		int[] frd = Parse.readMove();
		
		/* Check for invalid side move */
		if(	frd[2] == 'D' && p.sidePlaying == Side.V ||
			frd[2] == 'L' && p.sidePlaying == Side.H) {
			throw new InvalidMoveException();
		}
		
		return null;
	}

}
