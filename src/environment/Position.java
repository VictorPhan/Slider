package environment;

import java.math.BigInteger;

/**
 * The current position of the game space
 * @author TB VP
 *
 */
public class Position {
	
	public static final int V = 0;
	public static final int H = 1;
	public static final int B = 2;
	public static int dimension;
	private long [] pieces;
	private BigInteger[] bigPieces;
	
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
	
	/**
	 * Constructor for n > 8 case
	 * @param dimension 
	 * @param bigPieces
	 */
	public Position(int dimension, BigInteger[] bigPieces){
		Position.dimension = dimension;
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

	public static int getDimension() {
		return dimension;
	}
	
	public BigInteger[] getBigPieces() {
		return bigPieces;
	}

	public void setBigPieces(BigInteger[] bigPieces) {
		this.bigPieces = bigPieces;
	}
}
