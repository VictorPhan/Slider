package player;

import environment.Position;
import top_end.SliderPlayer;

public abstract class Player implements top_end.SliderPlayer{
	public abstract double makeMove(Position p);

}
