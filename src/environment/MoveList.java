package environment;

import java.math.BigInteger;

public class MoveList implements Consts {
	
	public static long leftCol, rightCol, topRow;
	long [] vMoves = new long[moveListDimensionality];
	long [] hMoves = new long[moveListDimensionality];
	long occupied;
	
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
	
	/**
	 * Generate all the possible moves for the V pieces.
	 * TODO: wipe out the column edges. count moving off the board as a move
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
	
	/**
	 * Generate all the possible moves for the H pieces.
	 * TODO: wipe out the row edges. count moving off the board as a move
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
	
	public int nVMoves() {
		int numMoves = 0;
		for(int i=0; i<moveListDimensionality; i++) {
			numMoves += Long.bitCount(vMoves[i]);
		}
		return numMoves;
	}
	
	public int nHMoves() {
		int numMoves = 0;
		for(int i=0; i<moveListDimensionality; i++) {
			numMoves += Long.bitCount(hMoves[i]);
		}
		return numMoves;
	}
	
	/**
	 * TODO: Generates leftCol, rightCol and topRow bitboards
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
			leftCol = new BigInteger(sLeftCol, 2).longValue();
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
			rightCol = new BigInteger(sRightCol, 2).longValue();
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
			topRow = new BigInteger(sTopRow, 2).longValue();
		}
	}
	
}
