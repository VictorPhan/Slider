package player;

import environment.Parse;
import environment.Position;
import environment.Side;
import exceptions.InvalidMoveException;

public class Human extends Player {
	
	Side color;
	int U = 0;
	int R = 1;
	int D = 2;
	int L = 2;
	char illegalMove;
	
	public Human(Side color) {
		this.color = color;
		if(color == Side.H) {
			illegalMove = 'L';
		}
		else if(color == Side.V) {
			illegalMove = 'D';
		}
	}
	
	@Override
	public Position makeMove(Position p) throws InvalidMoveException {
		int[] frd = Parse.readMove();
		
		/* Check for invalid side move */
		if(	frd[2] == illegalMove) {
			throw new InvalidMoveException();
		}
		
		switch(frd[2]) {
		case 'R':
			
		case 'U':
			
		case 'D':
			
		case 'L':
			
		}
		
		return null;
	}

}
