package environment;

import java.math.BigInteger;

/**
 * Generates possible moves for the current player
 * @author TB VP
 *
 */
public class MoveList {
	
	static final int V = 0;
	static final int H = 1;
	static final int B = 2;
	
	static final int VU = 0;
	static final int VO = 1;
	static final int VL = 2;
	static final int VR = 3;
	
	static final int HR = 0;
	static final int HO = 1;
	static final int HD = 2;
	static final int HU = 3;
	
	public static final int MOVE_TYPES = 4;
	
	public static long leftCol, rightCol, topRow, bottomRow;
	public static BigInteger bigLeftCol, bigRightCol, bigTopRow, bigBottomRow;
	
	public long [] moves = new long[MOVE_TYPES];
	public BigInteger [] bigMoves = new BigInteger[MOVE_TYPES];
	
	/**
	 * Constructor	 
	 */
	public MoveList(long[] pieces, Side sidePlaying, int dimen) {
		generateUsefulBitboards(dimen);
		if(sidePlaying == Side.H) {
			moves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			moves = generateVMoves(pieces);
		}
	}
	
	public MoveList(long[] pieces, Side sidePlaying) {
		if(sidePlaying == Side.H) {
			moves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			moves = generateVMoves(pieces);
		}
	}
	
	public MoveList(BigInteger[] pieces, Side sidePlaying, int dimen) {
		generateUsefulBitboards(dimen);
		if(sidePlaying == Side.H) {
			bigMoves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			bigMoves = generateVMoves(pieces);
		}
	}
	
	public MoveList(BigInteger[] pieces, Side sidePlaying) {
		if(sidePlaying == Side.H) {
			bigMoves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			bigMoves = generateVMoves(pieces);
		}
	}
	
	/**
	 * Updates the moveList attributes to the latest board position
	 */
	public void updateMoveList(long[] pieces, Side sidePlaying) {
		if(sidePlaying == Side.H) {
			moves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			moves = generateVMoves(pieces);
		}
	}
	
	/**
	 * CHeck for a draw in the position
	 */
	public static boolean checkDraw(long[] pieces) {
		long[] hm = generateHMoves(pieces);
		long[] vm = generateVMoves(pieces);
		for(int i=0; i<MOVE_TYPES; i++) {
			if(Long.bitCount(hm[i])!=0 || Long.bitCount(vm[i])!=0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Generate all the possible moves for the V pieces.
	 * @param pieces
	 * @return
	 */
	public static long [] generateVMoves(long[] pieces) {
		long occupied = pieces[B] | pieces[V] | pieces[H];
		long[] vm = new long[MOVE_TYPES];
		vm[VU] = ((pieces[V] >>> Position.dimen) & ~occupied) << Position.dimen;
		vm[VL] = ((pieces[V] << 1) & ~occupied & ~rightCol) >>> 1;
		vm[VO] = ((pieces[V] >>> 1) & ~occupied & ~leftCol) << 1;
		vm[VR] = pieces[V] & topRow;
		return vm;
	}
	
	public static BigInteger [] generateVMoves(BigInteger[] pieces) {
		BigInteger bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		BigInteger[] vm = new BigInteger[MOVE_TYPES];
		vm[VU] = ((pieces[V].shiftRight(Position.dimen)).
				and(bigOccupied.not())).shiftLeft(Position.dimen);
		vm[VL] = (((pieces[V].shiftLeft(1)).and(bigOccupied.not())).
				and(bigRightCol.not())).shiftRight(1);
		vm[VO] = (((pieces[V].shiftRight(1)).and(bigOccupied.not())).
				and(bigLeftCol.not())).shiftLeft(1);
		vm[VR] = pieces[V].and(bigTopRow);
		return vm;
	}
	
	/**
	 * Generate all the possible moves for the H pieces.
	 * @param pieces
	 * @return
	 */
	public static long [] generateHMoves(long[] pieces) {
		long occupied = pieces[B] | pieces[V] | pieces[H];
		long[] hm = new long[MOVE_TYPES];
		hm[HO] = ((pieces[H] >>> 1) & ~occupied & ~leftCol) << 1;
		hm[HR] = ((pieces[H] >>> Position.dimen) & ~occupied) << Position.dimen;
		hm[HD] = (((pieces[H] & ~bottomRow) << Position.dimen) & ~occupied) >>> Position.dimen;
		hm[HU] = pieces[H] & rightCol;
		return hm;
	}
	
	public static BigInteger [] generateHMoves(BigInteger[] pieces) {
		BigInteger bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		BigInteger[] hm = new BigInteger[MOVE_TYPES];
		hm[HO] = (((pieces[H].shiftRight(1)).and(bigOccupied.not())).
				and(bigLeftCol.not())).shiftLeft(1);
		hm[HR] = ((pieces[H].shiftRight(Position.dimen)).
				and(bigOccupied.not())).shiftLeft(Position.dimen);
		hm[HD] = (((pieces[H].and(bigBottomRow.not())).shiftLeft(Position.dimen)).
				and(bigOccupied.not())).shiftRight(Position.dimen);
		hm[HU] = pieces[H].and(bigRightCol);
		return hm;
	}
	
	/**
	 * Generates leftCol, rightCol and topRow bitboards
	 * @param dimen
	 */
	private void generateUsefulBitboards(int dimen) {
		/**
		 * Left column filled with 1s bitboard
		 */
		{
			String sLeftCol= "1";
			for(int i=0; i<dimen-1; i++) {
				sLeftCol = sLeftCol.concat("0");
			}
			for(int i=0; i<dimen-1; i++) {
				sLeftCol = sLeftCol.concat(sLeftCol);
			}
			bigLeftCol = new BigInteger(sLeftCol, 2);
		}
		
		/**
		 * Right column filled with 1s bitboard
		 */
		{
			String sRightCol = "";
			for(int i=0; i<dimen-1; i++) {
				sRightCol = sRightCol.concat("0");
			}
			sRightCol = sRightCol.concat("1");
			for(int i=0; i<dimen-1; i++) {
				sRightCol = sRightCol.concat(sRightCol);
			}
			bigRightCol = new BigInteger(sRightCol, 2);
		}
		
		/**
		 * Top row filled with 1s bitboard
		 */
		{
			String sTopRow = "";
			for(int i=0; i<dimen*(dimen-1); i++) {
				sTopRow = sTopRow.concat("0");
			}
			for(int i=0; i<dimen; i++) {
				sTopRow = sTopRow.concat("1");
			}
			bigTopRow = new BigInteger(sTopRow, 2);
		}
		
		/**
		 * Bottom row filled with 1s bitboard
		 */
		{
			String sTopRow = "";
			for(int i=0; i<dimen; i++) {
				sTopRow = sTopRow.concat("1");
			}
			for(int i=0; i<dimen*(dimen-1); i++) {
				sTopRow = sTopRow.concat("0");
			}
			bigBottomRow = new BigInteger(sTopRow, 2);
		}
		
		if(dimen <= Position.BIG_INT_CASE) {
			leftCol = bigLeftCol.longValue();
			rightCol = bigRightCol.longValue();
			topRow = bigTopRow.longValue();
			bottomRow = bigBottomRow.longValue();
		}
	}
	
}
