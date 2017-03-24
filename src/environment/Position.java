package environment;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position implements Consts {
	
	public static int dimen;
	public static long leftCol, rightCol, topRow;
	private long[] pieces;
	MoveList ml;
	
	/**
	 * Constructor initializing dimension and initial position
	 * @param dimen
	 * @param pieces
	 */
	public Position(int dimen, long [] pieces) {
		Position.dimen = dimen;
		generateUsefulBitboards(dimen);
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

	public static int getdimen() {
		return dimen;
	}
	
	/**
	 * TODO: Generates leftCol, rightCol and topRow bitboards
	 * @param dimen
	 */
	private void generateUsefulBitboards(int dimen) {
		
	}
	
}
