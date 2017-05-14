package player;

import environment.Position;
import top_end.SliderPlayer;

public abstract class Player implements SliderPlayer{
	public abstract void makeMove(Position p);
}
