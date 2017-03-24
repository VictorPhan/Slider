package environment;

import java.math.BigInteger;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position implements Consts {
	
	public static int dimen;
	private long[] pieces = new long[piecesDimensionality];
	private BigInteger[] bigPieces = new BigInteger[piecesDimensionality];
	MoveList ml;
	public static final int V = 0;
	public static final int H = 1;
	public static final int B = 2;
	
	/**
	 * Constructor initializing dimension and initial position
	 * @param dimen
	 * @param pieces
	 */
	public Position(int dimen, long [] pieces) {
		Position.dimen = dimen;
		this.pieces = pieces;
		ml = new MoveList(dimen, pieces);
	}
	
	/**
	 * Constructor for position
	 * @param pieces 
	 */
	public Position(long [] pieces) {
		this.pieces = pieces;
		ml = new MoveList(pieces);
	}
	
	/**
	 * Constructor for n > 8 case
	 * @param dimension 
	 * @param bigPieces
	 */
	public Position(int dimen, BigInteger[] bigPieces){
		Position.dimen = dimen;
		this.bigPieces = bigPieces;
	}
	
	/**
	 * Constructor for after the first state
	 * @param bigPieces
	 */
	public Position(BigInteger[] bigPieces){
		this.bigPieces = bigPieces;
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
}
