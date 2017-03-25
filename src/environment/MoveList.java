package environment;

import java.math.BigInteger;

/**
 * Generates possible moves for the current player
 * @author TB VP
 *
 */
public class MoveList implements Consts {
	
	public static long leftCol, rightCol, topRow;
	public static BigInteger BleftCol, BrightCol, BtopRow;
	long [] vMoves = new long[moveListDimensionality];
	long [] hMoves = new long[moveListDimensionality];
	long occupied;
	BigInteger [] bigvMoves = new BigInteger[moveListDimensionality];
	BigInteger [] bighMoves = new BigInteger[moveListDimensionality];
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
		long[] vm = new long[moveListDimensionality];
		vm[V_U] = ((pieces[V] >>> Position.dimen) & ~occupied) << Position.dimen;
		vm[V_L] = ((pieces[V] << 1) & ~occupied & ~rightCol) >>> 1;
		vm[V_R] = ((pieces[V] >>> 1) & ~occupied & ~leftCol) << 1;
		vm[V_O] = pieces[V] & topRow;
		return vm;
	}
	public BigInteger [] generateVMoves(BigInteger[] pieces) {
		BigInteger[] vm = new BigInteger[moveListDimensionality];
		vm[V_U] = ((pieces[V].shiftRight(Position.dimen)).and(bigOccupied.not())).shiftLeft(Position.dimen);
		vm[V_L] = (((pieces[V].shiftLeft(1)).and(bigOccupied.not())).and(BrightCol.not())).shiftRight(1);
		vm[V_R] = (((pieces[V].shiftRight(1)).and(bigOccupied.not())).and(BleftCol.not())).shiftLeft(1);
		vm[V_O] = pieces[V].and(BtopRow);
		return vm;
	}
	
	/**
	 * Generate all the possible moves for the H pieces.
	 * @param pieces
	 * @return
	 */
	public long [] generateHMoves(long[] pieces) {
		long[] hm = new long[moveListDimensionality];
		hm[H_R] = ((pieces[H] >>> 1) & ~occupied & ~leftCol) << 1;
		hm[H_U] = ((pieces[H] >>> Position.dimen) & ~occupied) << Position.dimen;
		hm[H_D] = ((pieces[H] << Position.dimen) & ~occupied) >>> Position.dimen;
		hm[H_O] = pieces[H] & rightCol;
		return hm;
	}
	public BigInteger [] generateHMoves(BigInteger[] pieces) {
		BigInteger[] hm = new BigInteger[moveListDimensionality];
		hm[H_R] = (((pieces[H].shiftRight(1)).and(bigOccupied.not())).and(BleftCol.not())).shiftLeft(1);
		hm[H_U] = ((pieces[H].shiftRight(Position.dimen)).and(bigOccupied.not())).shiftLeft(Position.dimen);
		hm[H_D] = ((pieces[H].shiftLeft(Position.dimen)).and(bigOccupied.not())).shiftRight(Position.dimen);
		hm[H_O] = pieces[H].and(BrightCol);
		return hm;
	}
	
	public int nVMoves() {
		int numMoves = 0;
		if(Position.dimen > BIG_INTEGER_CASE){
			for(int i = 0; i < moveListDimensionality; i++) {
				numMoves += bigvMoves[i].bitCount();
			}
		} else {
			for(int i = 0; i < moveListDimensionality; i++) {
				numMoves += Long.bitCount(vMoves[i]);
			}
		}
		return numMoves;
	}
	
	public int nHMoves() {
		int numMoves = 0;
		if(Position.dimen > BIG_INTEGER_CASE){
			for(int i = 0; i < moveListDimensionality; i++) {
				numMoves += bighMoves[i].bitCount();
			}
		} else {
			for(int i = 0; i < moveListDimensionality; i++) {
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
		
		if(dimen <= BIG_INTEGER_CASE) {
			leftCol = BleftCol.longValue();
			rightCol = BrightCol.longValue();
			topRow = BtopRow.longValue();
		}
	}
	
}
