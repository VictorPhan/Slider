package player;

import environment.Parse;
import environment.Position;
import environment.Side;
import exceptions.InvalidMoveException;

public class Human extends Player {
	
	Side color;
	
	public Human(Side color) {
		this.color = color;
		if(color == Side.H) {
			int R = 0;
			int U = 1;
			int D = 2;
		}
		else if(color == Side.V) {
			int U = 0;
			int L = 1;
			int R = 2;
		}
	}
	
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
