package environment;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position {
	
	public static final int V = 0;
	public static final int H = 1;
	public static int dimension;
	private long [] pieces;
	
	/**
	 * Constructor initializing dimension and initial position
	 * @param dimension
	 * @param pieces
	 */
	public Position(int dimension, long [] pieces) {
		Position.dimension = dimension;
		this.pieces = pieces;
	}
	
	/**
	 * Constructor for position
	 * @param pieces
	 */
	public Position(long [] pieces) {
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
	
}
