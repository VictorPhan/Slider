package environment;

import java.math.BigInteger;

/**
 * A state of the game space. Consists of the location of all the pieces,
 * whether the game is playing, tied, or a side has won, and all the possible
 * actions a particular player can take from the current state.
 * @author TB VP
 *
 */
public class Position {
	protected static final int V = 0;
	protected static final int H = 1;
	public static final int PIECE_TYPES = 3;
	public static final int BIG_INT_CASE = 8;
	public GameState gs;
	public Side sidePlaying;
	public static int dimen;
	public MoveList ml = null;
	private long[] pieces = new long[PIECE_TYPES];
	private BigInteger[] bigPieces = new BigInteger[PIECE_TYPES];
	public GameHistory gHistory = new GameHistory();
	
	/**
	 * Clones the position attached to the method
	 * @return the new instance of the position, devoid of reference
	 */
	public Position copyPosition() {
		return new Position(pieces.clone(), sidePlaying, gHistory.clone());
	}
	
	/**
	 * Constructor initializing dimension and initial position
	 * @param pieces the pieces at the beginning of the board
	 * @param sidePlaying the side that this player is on
	 * @param dimen dimension of the board
	 */
	public Position(long[] pieces, Side sidePlaying, int dimen) {
		Position.dimen = dimen;
		this.pieces = pieces;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor for when cloning and need to keep the state
	 * @param pieces the pieces at the beginning of the board
	 * @param sidePlaying the side that this player is on
	 * @param gh the history of the game played so far
	 */
	public Position(long [] pieces, Side sidePlaying, GameHistory gh) {
		this.pieces = pieces;
		this.gHistory = gh;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor when playing the game
	 * @param pieces the pieces at the beginning of the board
	 * @param sidePlaying the side that this player is on
	 */
	public Position(long [] pieces, Side sidePlaying) {
		this.pieces = pieces;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor for n > 8 case
	 * @param bigPieces their pieces in bitboard (big int)
	 * @param sidePlaying the side that this player is on
	 * @param dimen dimension of the board
	 */
	public Position(BigInteger[] bigPieces, Side sidePlaying, int dimen) {
		Position.dimen = dimen;
		this.bigPieces = bigPieces;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor for when cloning and need to keep the state for n > 8
	 * @param bigPieces the pieces at the beginning of the board (big int)
	 * @param sidePlaying the side that this player is on
	 * @param gh the history of the game played so far
	 */
	public Position(BigInteger [] bigPieces, Side sidePlaying, GameHistory gh) {
		this.bigPieces = bigPieces;
		this.gHistory = gh;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor when playing the game for n > 8
	 * @param bigPieces the pieces at the beginning of the board (big int)
	 * @param sidePlaying the side that this player is on
	 */
	public Position(BigInteger [] bigPieces, Side sidePlaying) {
		this.bigPieces = bigPieces;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Updates the board for the current side
	 * @param playing the side that is playing
	 */
	public void updateBoard(Side playing) {
		sidePlaying = playing;
		updateMoveList();
		checkGameState();
	}
	
	/**
	 * Checks if the game has been won yet or not
	 */
	public void checkGameState() {
		if(Long.bitCount(pieces[H])==0) {
			gs = GameState.H_WON;
		}
		else if(Long.bitCount(pieces[V])==0) {
			gs = GameState.V_WON;
		}
		else if(MoveList.checkDraw(pieces) || gHistory.threeFoldRepitition(pieces)) {
			gs = GameState.DRAW;
		}
		else {
			gs = GameState.PLAYING;
		}
	}
	
	/**
	 * Updates the possible move list
	 */
	public void updateMoveList() {
		if(ml==null) {
			if(Position.dimen <= Position.BIG_INT_CASE) {
				ml = new MoveList(pieces, sidePlaying, dimen);
			}
			else {
				ml = new MoveList(bigPieces, sidePlaying, dimen);
			}
		}
		else {
			ml.updateMoveList(pieces, sidePlaying);
		}
	}
	
	/**
	 * Constructor for after the first state
	 * @param bigPieces
	 */
	public Position(BigInteger[] bigPieces) {
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

	public BigInteger[] getBigPieces() {
		return bigPieces;
	}

	public void setBigPieces(BigInteger[] bigPieces) {
		this.bigPieces = bigPieces;
	}
	
	/**
	 * Draws the current position
	 */
	public void draw() {
		System.out.println(Parse.boardToString(this));
	}
	
	/**
	 * Gets the current specified player's own pieces
	 * @return their pieces in long notation
	 */
	public long getCurrPieces() {
		if(sidePlaying == Side.H) {
			return pieces[H];
		}
		else if(sidePlaying == Side.V) {
			return pieces[V];
		}
		else {
			throw new Error("Game state not in playing!");
		}
	}
	
	/**
	 * Sets the current player's pieces
	 * @param newPieces the new pieces coming in
	 */
	public void setCurrPieces(long newPieces) {
		if(sidePlaying == Side.H) {
			pieces[H] = newPieces;
		}
		else if(sidePlaying == Side.V) {
			pieces[V] = newPieces;
		}
		else {
			throw new Error("Game state not in playing!");
		}
		// TOGGLED OFF FOR REFEREE //
		swapPlayers();
	}
	
	/**
	 * Sets the current player's pieces (big int)
	 * @param newBigPieces the new pieces coming in
	 */
	public void setCurrPieces(BigInteger newBigPieces) {
		if(sidePlaying == Side.H) {
			bigPieces[H] = newBigPieces;
		}
		else if(sidePlaying == Side.V) {
			bigPieces[V] = newBigPieces;
		}
		else {
			throw new Error("Game state not in playing!");
		}
		// TOGGLED OFF FOR REFEREE //
		swapPlayers();
	}
	
	/**
	 * Swaps the players around for updating moves
	 */
	public void swapPlayers() {
		if(sidePlaying == Side.H) {
			sidePlaying = Side.V;
		}
		else {
			sidePlaying = Side.H;
		}
		// TOGGLED OFF FOR REFEREE //
		updateBoard(sidePlaying);
	}
}
