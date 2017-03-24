package environment;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position implements Consts {
	
	public static int dimen;
	private long [] pieces;
	MoveList ml;
	
	/**
	 * Constructor initializing dimension and initial position
	 * @param dimen
	 * @param pieces
	 */
	public Position(int dimen, long [] pieces) {
		Position.dimen = dimen;
		this.pieces = pieces;
		ml = new MoveList(pieces);
		MoveList.vWipe = MoveList.createVWipe(dimen);
		MoveList.hWipe = MoveList.createHWipe(dimen);
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
	
}
