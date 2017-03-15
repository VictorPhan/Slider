package environment;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position {
	
	public static final int H = 0;
	public static final int V = 1;
	private int dimension;
	private long [] pieces;

	public Position(int dimension, long [] pieces) {
		this.dimension = dimension;
		this.pieces = pieces;
	}
	
	public long[] getPieces() {
		return pieces;
	}

	public void setPieces(long[] pieces) {
		this.pieces = pieces;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
}
