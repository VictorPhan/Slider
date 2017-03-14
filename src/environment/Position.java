package environment;

/**
 * 
 * @author Victor
 *
 */
public class Position {
	
	public static final int H = 0;
	public static final int V = 1;
	int dimension;
	long [] pieces;
	
	public Position(int dimension, long [] pieces) {
		this.dimension = dimension;
		this.pieces = pieces;
	}

}
