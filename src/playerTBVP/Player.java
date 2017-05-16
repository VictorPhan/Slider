package playerTBVP;

import environment.Position;

public abstract class Player implements top_end.SliderPlayer{
	/**
	 * Suggests a move to be played by the player
	 * @param p the current position of the board
	 * @return the utility value of the move
	 */
	public abstract double makeMove(Position p);

}
