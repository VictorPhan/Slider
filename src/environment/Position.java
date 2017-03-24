package environment;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position implements Consts {
	
	/**
	 * Static variables
	 */
	public static int dimension;
	
	/**
	 * Non-static variables
	 */
	private long [] pieces;
	MoveList ml;
	
	/**
	 * Constructor initializing dimension and initial position
	 * @param dimension
	 * @param pieces
	 */
	public Position(int dimension, long [] pieces) {
		Position.dimension = dimension;
		this.pieces = pieces;
		ml = new MoveList(pieces);
	}
	
	/**
	 * Constructor for position
	 * @param pieces
	 */
	public Position(long [] pieces) {
		this.pieces = pieces;
	}
	
	public long [] getPieces() {
		return pieces;
	}
	
	public long getPieces(int i) {
		return pieces[i];
	}

	public void setPieces(long[] pieces) {
		this.pieces = pieces;
	}

	public static int getDimension() {
		return dimension;
	}
	
}
