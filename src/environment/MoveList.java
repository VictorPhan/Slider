/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 *            COMP30024 Artificial Intelligence - Semester 1 2017            *
 *                      Project A - Slider Move Generation                   *
 *                                                                           *
 *          Submission by: Tin Bao <tinb> and Victor Phan <victorp1>         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package environment;

import java.math.BigInteger;

/**
 * Generates possible moves for the current player
 * @author TB VP
 *
 */
public class MoveList {
	
	public static final int V = 0;
	public static final int H = 1;
	public static final int B = 2;
	
	public static final int VU = 0;
	public static final int VL = 1;
	public static final int VR = 2;
	public static final int VO = 3;
	
	public static final int HR = 0;
	public static final int HU = 1;
	public static final int HD = 2;
	public static final int HO = 3;
	
	public static long leftCol, rightCol, topRow;
	public static BigInteger BleftCol, BrightCol, BtopRow;
	long [] vMoves = new long[Run.MOVE_TYPES];
	long [] hMoves = new long[Run.MOVE_TYPES];
	long occupied;
	BigInteger [] bigvMoves = new BigInteger[Run.MOVE_TYPES];
	BigInteger [] bighMoves = new BigInteger[Run.MOVE_TYPES];
	BigInteger bigOccupied;
	
	public MoveList(int dimen, long[] pieces) {
		generateUsefulBitboards(dimen);
		occupied = pieces[B] | pieces[V] | pieces[H];
		vMoves = generateVMoves(pieces);
		hMoves = generateHMoves(pieces);
	}
	
	public MoveList(long[] pieces) {
		occupied = pieces[B] | pieces[V] | pieces[H];
		vMoves = generateVMoves(pieces);
		hMoves = generateHMoves(pieces);
	}
	
	public MoveList(int dimen, BigInteger[] pieces) {
		generateUsefulBitboards(dimen);
		bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		bigvMoves = generateVMoves(pieces);
		bighMoves = generateHMoves(pieces);
	}
	
	public MoveList(BigInteger[] pieces) {
		bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		bigvMoves = generateVMoves(pieces);
		bighMoves = generateHMoves(pieces);
	}
	
	/**
	 * Generate all the possible moves for the V pieces.
	 * @param pieces
	 * @return
	 */
	public long [] generateVMoves(long[] pieces) {
		long[] vm = new long[Run.MOVE_TYPES];
		vm[VU] = ((pieces[V] >>> Position.dimen) & ~occupied) << Position.dimen;
		vm[VL] = ((pieces[V] << 1) & ~occupied & ~rightCol) >>> 1;
		vm[VR] = ((pieces[V] >>> 1) & ~occupied & ~leftCol) << 1;
		vm[VO] = pieces[V] & topRow;
		return vm;
	}
	public BigInteger [] generateVMoves(BigInteger[] pieces) {
		BigInteger[] vm = new BigInteger[Run.MOVE_TYPES];
		vm[VU] = ((pieces[V].shiftRight(Position.dimen)).and(bigOccupied.not())).shiftLeft(Position.dimen);
		vm[VL] = (((pieces[V].shiftLeft(1)).and(bigOccupied.not())).and(BrightCol.not())).shiftRight(1);
		vm[VR] = (((pieces[V].shiftRight(1)).and(bigOccupied.not())).and(BleftCol.not())).shiftLeft(1);
		vm[VO] = pieces[V].and(BtopRow);
		return vm;
	}
	
	/**
	 * Generate all the possible moves for the H pieces.
	 * @param pieces
	 * @return
	 */
	public long [] generateHMoves(long[] pieces) {
		long[] hm = new long[Run.MOVE_TYPES];
		hm[HR] = ((pieces[H] >>> 1) & ~occupied & ~leftCol) << 1;
		hm[HU] = ((pieces[H] >>> Position.dimen) & ~occupied) << Position.dimen;
		hm[HD] = ((pieces[H] << Position.dimen) & ~occupied) >>> Position.dimen;
		hm[HO] = pieces[H] & rightCol;
		return hm;
	}
	public BigInteger [] generateHMoves(BigInteger[] pieces) {
		BigInteger[] hm = new BigInteger[Run.MOVE_TYPES];
		hm[HR] = (((pieces[H].shiftRight(1)).and(bigOccupied.not())).and(BleftCol.not())).shiftLeft(1);
		hm[HU] = ((pieces[H].shiftRight(Position.dimen)).and(bigOccupied.not())).shiftLeft(Position.dimen);
		hm[HD] = ((pieces[H].shiftLeft(Position.dimen)).and(bigOccupied.not())).shiftRight(Position.dimen);
		hm[HO] = pieces[H].and(BrightCol);
		return hm;
	}
	
	public int nVMoves() {
		int numMoves = 0;
		if(Position.dimen > Run.BIG_INT_CASE){
			for(int i = 0; i < Run.MOVE_TYPES; i++) {
				numMoves += bigvMoves[i].bitCount();
			}
		} else {
			for(int i = 0; i < Run.MOVE_TYPES; i++) {
				numMoves += Long.bitCount(vMoves[i]);
			}
		}
		return numMoves;
	}
	
	public int nHMoves() {
		int numMoves = 0;
		if(Position.dimen > Run.BIG_INT_CASE){
			for(int i = 0; i < Run.MOVE_TYPES; i++) {
				numMoves += bighMoves[i].bitCount();
			}
		} else {
			for(int i = 0; i < Run.MOVE_TYPES; i++) {
				numMoves += Long.bitCount(hMoves[i]);
			}
		}
		return numMoves;
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
			BleftCol = new BigInteger(sLeftCol, 2);
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
			BrightCol = new BigInteger(sRightCol, 2);
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
			BtopRow = new BigInteger(sTopRow, 2);
		}
		
		if(dimen <= Run.BIG_INT_CASE) {
			leftCol = BleftCol.longValue();
			rightCol = BrightCol.longValue();
			topRow = BtopRow.longValue();
		}
	}
	
}
