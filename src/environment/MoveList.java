package environment;

public class MoveList implements Consts {
	
	long [] vMoves = new long[3];
	long [] hMoves = new long[3];
	long occupied;
	
	public MoveList(long[] pieces) {
		occupied = (pieces[B]) | (pieces[V]) | (pieces[H]);
		vMoves = generateVMoves(pieces);
		hMoves = generateHMoves(pieces);
	}
	
	/**
	 * Generate all the possible moves for the V pieces.
	 * TODO: wipe out the column edges.
	 * @param pieces
	 * @return
	 */
	public long [] generateVMoves(long[] pieces) {
		long[] vm = new long[3];
		vm[V_U] = (pieces[V] >> Position.dimension) & ~occupied;
		vm[V_L] = (pieces[V] >> -1) & ~occupied;
		vm[V_R] = (pieces[V] >> 1) & ~occupied;
		return vm;
	}
	
	/**
	 * Generate all the possible moves for the H pieces.
	 * TODO: wipe out the row edges
	 * @param pieces
	 * @return
	 */
	public long [] generateHMoves(long[] pieces) {
		long[] hm = new long[3];
		hm[H_R] = (pieces[V] >> 1) & ~occupied;
		hm[H_U] = (pieces[V] >> Position.dimension) & ~occupied;
		hm[H_D] = (pieces[V] >> -Position.dimension) & ~occupied;
		return hm;
	}
	
}
