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
	 * Constructor initializing dimension and initial position
	 * @param dimen
	 * @param pieces
	 */
	public Position(long[] pieces, Side sidePlaying, int dimen) {
		Position.dimen = dimen;
		this.pieces = pieces;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor for position
	 * @param pieces 
	 */
	public Position(long [] pieces, Side sidePlaying, GameHistory gh) {
		this.pieces = pieces;
		this.gHistory = gh;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor for position
	 * @param pieces 
	 */
	public Position(long [] pieces, Side sidePlaying) {
		this.pieces = pieces;
		updateBoard(sidePlaying);
	}
	
	/**
	 * Constructor for n > 8 case
	 * @param dimension 
	 * @param bigPieces
	 */
	public Position(BigInteger[] bigPieces, Side sidePlaying, int dimen) {
		Position.dimen = dimen;
		this.bigPieces = bigPieces;
		updateBoard(sidePlaying);
	}
	
	public void updateBoard(Side playing) {
		sidePlaying = playing;
		updateMoveList();
		checkGameState();
	}
	
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
	
	public void draw() {
		System.out.println(Parse.boardToString(this));
	}
	
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
	
	public void setCurrPieces(long newPieces, Side opponent) {
		if(sidePlaying == Side.H) {
			pieces[H] = newPieces;
		}
		else if(sidePlaying == Side.V) {
			pieces[V] = newPieces;
		}
		else {
			throw new Error("Game state not in playing!");
		}
		updateBoard(opponent);
	}
	
	public void swapPlayers() {
		if(sidePlaying == Side.H) {
			sidePlaying = Side.V;
		}
		else {
			sidePlaying = Side.H;
		}
	}
}
