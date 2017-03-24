package environment;

public class MoveList implements Consts {
	
	long [] vMoves = new long[4];
	long [] hMoves = new long[4];
	long occupied;
	
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
		long[] vm = new long[3];
		vm[V_U] = (pieces[V] >> Position.dimen) & ~occupied;
		vm[V_L] = (pieces[V] << 1) & ~occupied & ~Position.rightCol;
		vm[V_R] = (pieces[V] >> 1) & ~occupied & ~Position.leftCol;
		return vm;
	}
	
	/**
	 * Generate all the possible moves for the H pieces.
	 * TODO: wipe out the row edges. count moving off the board as a move
	 * @param pieces
	 * @return
	 */
	public long [] generateHMoves(long[] pieces) {
		long[] hm = new long[3];
		hm[H_R] = (pieces[H] >> 1) & ~occupied & ~Position.leftCol;
		hm[H_U] = (pieces[H] >> Position.dimen) & ~occupied;
		hm[H_D] = (pieces[H] << Position.dimen) & ~occupied;
		return hm;
	}
	
}
