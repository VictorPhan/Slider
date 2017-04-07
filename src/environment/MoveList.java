package environment;

import java.math.BigInteger;

/**
 * Generates possible moves for the current player
 * 
 * @author TB VP
 *
 */
public class MoveList {
	/* Representation of the piece values in the array */
	/** Position of V pieces in the array */
	public static final int V = 0;
	/** Position of H pieces in the array */
	public static final int H = 1;
	/** Position of B blocks in the array */
	public static final int B = 2;

	/* The positions where V pieces could go */
	/** V up position */
	public static final int VU = 0;
	/** V left position */
	public static final int VL = 1;
	/** V right position */
	public static final int VR = 2;
	/** V outside the board position */
	public static final int VO = 3;

	/* The positions where H pieces could go */
	/** H right position */
	public static final int HR = 0;
	/** H up position */
	public static final int HU = 1;
	/** H down position */
	public static final int HD = 2;
	/** H outside the board position */
	public static final int HO = 3;

	/* The bitboard that prevents pieces going past the boundaries */
	public static long leftCol, rightCol, topRow;
	public static BigInteger bigLeftCol, bigRightCol, bigTopRow;

	/* All of the moves for long data type */
	long[] vMoves = new long[Run.MOVE_TYPES];
	long[] hMoves = new long[Run.MOVE_TYPES];
	long occupied;

	/* All of the moves for big integer data type */
	BigInteger[] bigvMoves = new BigInteger[Run.MOVE_TYPES];
	BigInteger[] bighMoves = new BigInteger[Run.MOVE_TYPES];
	BigInteger bigOccupied;

	/**
	 * Constructor for the movelist
	 * 
	 * @param dimen: the dimension of the board
	 * @param pieces: the position of all pieces in long type
	 */
	public MoveList(int dimen, long[] pieces) {
		generateUsefulBitboards(dimen);
		occupied = pieces[B] | pieces[V] | pieces[H];
		if(sidePlaying == Side.H) {
			moves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			moves = generateVMoves(pieces);
		}
	}

	/**
	 * Constructor for the movelist after an initial board is created
	 * 
	 * @param pieces: the position of all pieces in long type
	 */
	public MoveList(long[] pieces) {
		occupied = pieces[B] | pieces[V] | pieces[H];
		if(sidePlaying == Side.H) {
			moves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			moves = generateVMoves(pieces);
		}
	}

	/**
	 * Constructor for the movelist
	 * 
	 * @param dimen: the dimension of the board
	 * @param pieces: the position of all pieces in big integer type
	 */
	public MoveList(int dimen, BigInteger[] pieces) {
		generateUsefulBitboards(dimen);
		bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		if(sidePlaying == Side.H) {
			bigMoves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			bigMoves = generateVMoves(pieces);
		}
	}

	/**
	 * Constructor for the movelist after an initial board is created
	 * 
	 * @param pieces: the position of all pieces in big integer type
	 */
	public MoveList(BigInteger[] pieces) {
		bigOccupied = pieces[B].or(pieces[V]).or(pieces[H]);
		if(sidePlaying == Side.H) {
			bigMoves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			bigMoves = generateVMoves(pieces);
		}
	}
	
	public void updateMoveList(long[] pieces, Side sidePlaying) {
		occupied = pieces[B] | pieces[V] | pieces[H];
		if(sidePlaying == Side.H) {
			moves = generateHMoves(pieces);
		}
		else if (sidePlaying == Side.V) {
			moves = generateVMoves(pieces);
		}
	}

	/**
	 * Generates all the possible moves for the V pieces
	 * 
	 * @param pieces: the position of all pieces in long type
	 * @return the possible moves for all V pieces
	 */
	public long[] generateVMoves(long[] pieces) {
		long[] vm = new long[Run.MOVE_TYPES];
		vm[VU] = ((pieces[V] >>> Position.dimen) & ~occupied) << Position.dimen;
		vm[VL] = ((pieces[V] << 1) & ~occupied & ~rightCol) >>> 1;
		vm[VR] = ((pieces[V] >>> 1) & ~occupied & ~leftCol) << 1;
		vm[VO] = pieces[V] & topRow;
		return vm;
	}

	/**
	 * Generates all the possible moves for the V pieces
	 * 
	 * @param pieces: the position of all pieces in big integer type
	 * @return the possible moves for all V pieces
	 */
	public BigInteger[] generateVMoves(BigInteger[] pieces) {
		BigInteger[] vm = new BigInteger[Run.MOVE_TYPES];
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
	 * Generates all the possible moves for the H pieces
	 * 
	 * @param pieces: the position of all pieces in long type
	 * @return the possible moves for all H pieces
	 */
	public long[] generateHMoves(long[] pieces) {
		long[] hm = new long[Run.MOVE_TYPES];
		hm[HR] = ((pieces[H] >>> 1) & ~occupied & ~leftCol) << 1;
		hm[HU] = ((pieces[H] >>> Position.dimen) & ~occupied) << Position.dimen;
		hm[HD] = ((pieces[H] << Position.dimen) & ~occupied) >>> Position.dimen;
		hm[HO] = pieces[H] & rightCol;
		return hm;
	}

	/**
	 * Generates all the possible moves for the H pieces
	 * 
	 * @param pieces: the position of all pieces in big integer type
	 * @return the possible moves for all H pieces
	 */
	public BigInteger[] generateHMoves(BigInteger[] pieces) {
		BigInteger[] hm = new BigInteger[Run.MOVE_TYPES];
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
	 * Gives boards that prevent pieces from moving off the edge
	 * 
	 * @param dimen: the dimension of the board
	 */
	private void generateUsefulBitboards(int dimen) {
		/**
		 * Left column filled with 1s bitboard
		 */
		{
			String sLeftCol = "1";
			for (int i = 0; i < dimen - 1; i++) {
				sLeftCol = sLeftCol.concat("0");
			}
			for (int i = 0; i < dimen - 1; i++) {
				sLeftCol = sLeftCol.concat(sLeftCol);
			}
			bigLeftCol = new BigInteger(sLeftCol, 2);
		}

		/**
		 * Right column filled with 1s bitboard
		 */
		{
			String sRightCol = "";
			for (int i = 0; i < dimen - 1; i++) {
				sRightCol = sRightCol.concat("0");
			}
			sRightCol = sRightCol.concat("1");
			for (int i = 0; i < dimen - 1; i++) {
				sRightCol = sRightCol.concat(sRightCol);
			}
			bigRightCol = new BigInteger(sRightCol, 2);
		}

		/**
		 * Top row filled with 1s bitboard
		 */
		{
			String sTopRow = "";
			for (int i = 0; i < dimen * (dimen - 1); i++) {
				sTopRow = sTopRow.concat("0");
			}
			for (int i = 0; i < dimen; i++) {
				sTopRow = sTopRow.concat("1");
			}
			bigTopRow = new BigInteger(sTopRow, 2);
		}

		/* Big integer case for boards over dimension 8 */
		if (dimen <= Run.BIG_INT_CASE) {
			leftCol = bigLeftCol.longValue();
			rightCol = bigRightCol.longValue();
			topRow = bigTopRow.longValue();
		}
	}

}
