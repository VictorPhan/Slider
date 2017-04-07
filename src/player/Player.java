package player;

import environment.Position;
import exceptions.InvalidMoveException;

public abstract class Player {
	public abstract void makeMove(Position p) throws InvalidMoveException;
}
