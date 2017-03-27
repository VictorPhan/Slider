package environment;

import java.math.BigInteger;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position {
	
	public Side sidePlaying;
	public static int dimen;
	public MoveList ml;
	private long[] pieces = new long[Run.PIECE_TYPES];
	private BigInteger[] bigPieces = new BigInteger[Run.PIECE_TYPES];
	
	/**
	 * Constructor initializing dimension and initial position
	 * @param dimen
	 * @param pieces
	 */
	public Position(int dimen, Side sidePlaying, long [] pieces) {
		Position.dimen = dimen;
		this.pieces = pieces;
		this.sidePlaying = sidePlaying;
		ml = new MoveList(pieces, sidePlaying, dimen);
	}
	
	/**
	 * Constructor for position
	 * @param pieces 
	 */
	public Position(long [] pieces, Side sidePlaying) {
		this.pieces = pieces;
		this.sidePlaying = sidePlaying;
		ml = new MoveList(pieces, sidePlaying);
	}
	
	/**
	 * Constructor for n > 8 case
	 * @param dimension 
	 * @param bigPieces
	 */
	public Position(int dimen, BigInteger[] bigPieces){
		Position.dimen = dimen;
		this.bigPieces = bigPieces;
		ml = new MoveList(bigPieces, sidePlaying, dimen);
	}
	
	/**
	 * Constructor for after the first state
	 * @param bigPieces
	 */
	public Position(BigInteger[] bigPieces){
		this.bigPieces = bigPieces;
		ml = new MoveList(bigPieces, sidePlaying);
	}
	
	/**
	 * @param i index postion of the piece
	 * @return A specific piece by i
	 */
	public long getPieces(int i) {
		return pieces[i];
	}
	
	public BigInteger getBigPieces(int i) {
		return bigPieces[i];
	}
	
	public long [] getPieces() {
		return pieces;
	}

	public void setPieces(long[] pieces) {
		this.pieces = pieces;
	}

	public static int getdimen() {
		return dimen;
	}

	public BigInteger[] getBigPieces() {
		return bigPieces;
	}

	public void setBigPieces(BigInteger[] bigPieces) {
		this.bigPieces = bigPieces;
	}
	
	public void draw() {
		System.out.println(Parse.boardToString(this));
	}
}
