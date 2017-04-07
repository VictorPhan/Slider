/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *            COMP30024 Artificial Intelligence - Semester 1 2017            *
 *                      Project A - Slider Move Generation                   *
 *                                                                           *
 *          Submission by: Tin Bao <tinb> and Victor Phan <victorp1>         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package environment;

import java.math.BigInteger;

/**
 * The current position of the game space
 * 
 * @author TB VP
 *
 */
public class Position {

	/** The dimension of the board (fixed number) */
	public static int dimen;
	/** All the possible moves for all pieces */
	public MoveList ml;
	/** Positions of all pieces in long type */
	private long[] pieces = new long[Run.PIECE_TYPES];
	/** Positions of all pieces in big integer type */
	private BigInteger[] bigPieces = new BigInteger[Run.PIECE_TYPES];

	/**
	 * Constructor initializing dimension and initial position
	 * 
	 * @param dimen: the dimension of the board
	 * @param pieces: the position of all pieces on the current board
	 */
	public Position(int dimen, long[] pieces) {
		Position.dimen = dimen;
		this.pieces = pieces;
		ml = new MoveList(dimen, pieces);
	}

	/**
	 * Constructor for position after the first state
	 * 
	 * @param pieces: the position of all pieces on the current board
	 */
	public Position(long[] pieces) {
		this.pieces = pieces;
		ml = new MoveList(pieces);
	}

	/**
	 * Constructor for big integer case
	 * 
	 * @param dimen: the dimension of the board
	 * @param bigPieces: the position of all pieces on the current board
	 */
	public Position(int dimen, BigInteger[] bigPieces) {
		Position.dimen = dimen;
		this.bigPieces = bigPieces;
		ml = new MoveList(dimen, bigPieces);
	}

	/**
	 * Constructor for position after the first state
	 * 
	 * @param bigPieces: the position of all pieces on the current board
	 */
	public Position(BigInteger[] bigPieces) {
		this.bigPieces = bigPieces;
		ml = new MoveList(bigPieces);
	}

	/**
	 * Gets a specific piece type's position for long
	 * 
	 * @param i: index postion of the piece type
	 * @return a specific piece type by i
	 */
	public long getPieces(int i) {
		return pieces[i];
	}

	/**
	 * Gets a specific piece type's position for big integer
	 * 
	 * @param i: index postion of the piece type
	 * @return a specific piece type by i
	 */
	public BigInteger getBigPieces(int i) {
		return bigPieces[i];
	}

	/**
	 * Gets all the pieces of long type
	 * 
	 * @return all the pieces
	 */
	public long[] getPieces() {
		return pieces;
	}

	/**
	 * Gets all the pieces of big integer type
	 * 
	 * @return all the pieces
	 */
	public BigInteger[] getBigPieces() {
		return bigPieces;
	}

	/**
	 * Gets the dimension
	 * 
	 * @return dimension
	 */
	public static int getdimen() {
		return dimen;
	}

	/**
	 * Draws the board in the specification's notation (input)
	 */
	public void draw() {
		System.out.println(Parse.boardToString(this));
	}
}
