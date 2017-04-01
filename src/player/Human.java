package player;

import environment.Parse;
import environment.Position;

public class Human extends Player {
	
	@Override
	public Position makeMove(Position p) {
		Parse.readMove();
		return null;
	}

}
