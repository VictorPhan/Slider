package player;

import environment.Position;
import exceptions.InvalidMoveException;

public abstract class Player {
	public abstract Position makeMove(Position p) throws InvalidMoveException;
}
