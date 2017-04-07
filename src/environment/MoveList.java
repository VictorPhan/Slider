package environment;

import java.math.BigInteger;

/**
 * Generates possible moves for the current player
 * @author TB VP
 *
 */
public class MoveList {
	
	protected static final int V = 0;
	protected static final int H = 1;
	protected static final int B = 2;
	
	protected static final int VU = 0;
	protected static final int VR = 1;
	protected static final int VL = 2;
	protected static final int VO = 3;
	
	protected static final int HU = 0;
	protected static final int HR = 1;
	protected static final int HD = 2;
	protected static final int HO = 3;
	
	public static final int MOVE_TYPES = 4;
	
	protected static long leftCol, rightCol, topRow;
	protected static BigInteger bigLeftCol, bigRightCol, bigTopRow;
	
	public long [] moves = new long[MOVE_TYPES];
	public BigInteger [] bigMoves = new BigInteger[MOVE_TYPES];
	
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
	
	public void updateMoveList(long[] pieces, Side sidePlaying) {
		if(sidePlaying == Side.H) {
			moves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			moves = generateVMoves(pieces);
		}
	}
	
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
		vm[VR] = ((pieces[V] >>> 1) & ~occupied & ~leftCol) << 1;
		vm[VO] = pieces[V] & topRow;
		return vm;
	}
	public BigInteger [] generateVMoves(BigInteger[] pieces) {
		BigInteger bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		BigInteger[] vm = new BigInteger[MOVE_TYPES];
		vm[VU] = ((pieces[V].shiftRight(Position.dimen)).
				and(bigOccupied.not())).shiftLeft(Position.dimen);
		vm[VL] = (((pieces[V].shiftLeft(1)).and(bigOccupied.not())).
				and(bigRightCol.not())).shiftRight(1);
		vm[VR] = (((pieces[V].shiftRight(1)).and(bigOccupied.not())).
				and(bigLeftCol.not())).shiftLeft(1);
		vm[VO] = pieces[V].and(bigTopRow);
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
		hm[HR] = ((pieces[H] >>> 1) & ~occupied & ~leftCol) << 1;
		hm[HU] = ((pieces[H] >>> Position.dimen) & ~occupied) << Position.dimen;
		hm[HD] = ((pieces[H] << Position.dimen) & ~occupied) >>> Position.dimen;
		hm[HO] = pieces[H] & rightCol;
		return hm;
	}
	public BigInteger [] generateHMoves(BigInteger[] pieces) {
		BigInteger bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		BigInteger[] hm = new BigInteger[MOVE_TYPES];
		hm[HR] = (((pieces[H].shiftRight(1)).and(bigOccupied.not())).
				and(bigLeftCol.not())).shiftLeft(1);
		hm[HU] = ((pieces[H].shiftRight(Position.dimen)).
				and(bigOccupied.not())).shiftLeft(Position.dimen);
		hm[HD] = ((pieces[H].shiftLeft(Position.dimen)).
				and(bigOccupied.not())).shiftRight(Position.dimen);
		hm[HO] = pieces[H].and(bigRightCol);
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
		
		if(dimen <= Position.BIG_INT_CASE) {
			leftCol = bigLeftCol.longValue();
			rightCol = bigRightCol.longValue();
			topRow = bigTopRow.longValue();
		}
	}
	
}
