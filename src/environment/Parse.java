package environment;

import java.math.BigInteger;
import java.util.Scanner;
import java.lang.Error;

/**
 * Parses through the input data from run
 * 
 * @author TB VP
 *
 */
public class Parse {
	// avoiding integer overflow at 2^63
	public static final long SPECIALCASE = 0b1000000000000000000000000000000000000000000000000000000000000000L;
	public static final int V = 0;
	/** Position of H pieces in the array */
	public static final int H = 1;
	/** Position of B blocks in the array */
	public static final int B = 2;

	static Scanner s = null;
	
	public static void initScan() {
		s = new Scanner(System.in);
	}
	
	public static void closeScan() {
		s.close();
	}

	/**
	 * Reads and parses the input board and translates it into bitboard
	 * 
	 * @return the initialized bitboard
	 */
	public static Position parseBoard() {
		if(s == null) {
			throw new Error("Scanner not initialised");
		}
		
		Position board;
		String line = "";
		int dimen;
		char side;
		Side sidePlaying;
		
		dimen = s.nextInt();
		side = s.next().charAt(0);
		
		if(side == 'H') {
			sidePlaying = Side.H;
		}
		else if(side == 'V') {
			sidePlaying = Side.V;
		}
		else {
			s.close();
			throw new Error("Only the characters 'H' or 'V' are accepted as side playing input.");
		}
		
		/* 
		 * Gets each line info on the board and makes 
		 * a single String from that.
		 */
		for(int i = 0; i < dimen + 1; i++) {
			line = s.nextLine() + line;
		}
		line = line.replaceAll("\\s+","");
		
		/* Checks the dimension of the board and decides type */
		if(dimen > Position.BIG_INT_CASE){
			BigInteger[] bigPieces;
			bigPieces = fromBigRawString(line);
			board = new Position(dimen, bigPieces);
		} else {
			long[] pieces;
			pieces = fromRawString(line);
			board = new Position(dimen, sidePlaying, pieces);
		}

		s.close();

		/* Debug: runtime */
		/* long endTime = System.nanoTime(); 
		 * long totalTime = (endTime - startTime)/1000000; 
		 * System.out.println("Runtime = " + totalTime + "ms");
		 */
		return board;
	}

	/**
	 * Converts a raw input string into long bitboards
	 * 
	 * @param line: the whole board in string notation
	 * @return binary of the current board in an array
	 */
	public static long[] fromRawString(String line) {
		long [] smallPieces = new long[Position.PIECE_TYPES];
		String vPieces = "";
		String hPieces = "";
		String bPieces = "";
		/* Repeat for every piece type */
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == 'V') {
				vPieces = vPieces.concat("1");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			} else if (line.charAt(i) == 'H') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("1");
				bPieces = bPieces.concat("0");
			} else if (line.charAt(i) == 'B') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("1");
			} else if (line.charAt(i) == '+') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
			/* Incorrect piece notation error */
			else {
				throw new Error("Only the characters 'B', 'H', 'V', '+' are accepted for"
						+ " board input.");
			}
		}
		/* Positions of all 3 types of pieces are stored in an array */
		smallPieces[V] = new BigInteger(vPieces, 2).longValue();
		smallPieces[H] = new BigInteger(hPieces, 2).longValue();
		smallPieces[B] = new BigInteger(bPieces, 2).longValue();
		return smallPieces;
	}

	/**
	 * Converts a raw input string into big integer bitboard
	 * 
	 * @param line: the whole board in string notation
	 * @return binary of the current board in an array
	 */

	public static BigInteger[] fromRawString2(String line) {
		BigInteger[] bigPieces = new BigInteger[Position.PIECE_TYPES];
		String vPieces = "";
		String hPieces = "";
		String bPieces = "";
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == 'V') {
				vPieces = vPieces.concat("1");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			} else if (line.charAt(i) == 'H') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("1");
				bPieces = bPieces.concat("0");
			} else if (line.charAt(i) == 'B') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("1");
			} else if (line.charAt(i) == '+') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
			/* Incorrect piece notation error */
			else {
				throw new Error("Only the characters 'B', 'H', 'V', '+' are accepted for"
						+ " board input.");
			}
		}
		/* Positions of all 3 types of pieces are stored in an array */
		bigPieces[V] = new BigInteger(vPieces, 2);
		bigPieces[H] = new BigInteger(hPieces, 2);
		bigPieces[B] = new BigInteger(bPieces, 2);
		return bigPieces;
	}

	/**
	 * Converts Position object into printable string (used for testing)
	 * 
	 * @param board: The bitboard version of the current board
	 * @return A formatted string for the viewer to see the board state
	 */
	public static String boardToString(Position board) {
		String output = "";
		String HPieces, VPieces, BPieces;
		if(Position.dimen > Position.BIG_INT_CASE){
			HPieces = bitBoardToString(board.getBigPieces()[H]);
			VPieces = bitBoardToString(board.getBigPieces()[V]);
			BPieces = bitBoardToString(board.getBigPieces()[B]);
		} else {
			HPieces = bitBoardToString(board.getPieces()[H]);
			VPieces = bitBoardToString(board.getPieces()[V]);
			BPieces = bitBoardToString(board.getPieces()[B]);
		}
		/* Converting to string */
		for (int i = 0; i < Math.pow(Position.dimen, 2); i++) {
			if (HPieces.charAt(i) == '1') {
				output = output.concat("H");
			} else if (VPieces.charAt(i) == '1') {
				output = output.concat("V");
			} else if (BPieces.charAt(i) == '1') {
				output = output.concat("B");
			} else {
				output = output.concat("+");
			}
		}
		return stringToBoardString(output, Position.dimen);
	}

	/**
	 * Converts a long bitboard into a printable string
	 * 
	 * @param bitboard: the current state of the board in a long array
	 * @return String form of the current board
	 */
	public static String bitBoardToBoardString(long bitboard) {
		return stringToBoardString(bitBoardToString(bitboard), Position.dimen);
	}

	/**
	 * Converts a big integer bitboard into a printable string
	 * 
	 * @param bitboard: the current state of the board in a big integer array
	 * @return String form of the current board
	 */
	public static String bitBoardToBoardString(BigInteger bitboard) {
		return stringToBoardString(bitBoardToString(bitboard), Position.dimen);
	}

	/**
	 * Formats a flat string to a printable board.
	 * 
	 * @param bitBoard: the unformatted board
	 * @param dimen: the dimension of the board
	 * @return A formatted board (notation same as specification input)
	 */
	
	public static int[] readMove() {
		if(s == null) {
			throw new Error("Scanner not initialised");
		}
		String move = s.next();
		int file = (int) move.charAt(0) - 97;
		int rank = Integer.parseInt(Character.toString(move.charAt(1))) - 1;
		int direction = (int) move.charAt(2);
		int[] frd = {file, rank, direction};
		return frd;
	}
	
	private static String stringToBoardString(String bitBoard, int dimen) {
		String output = "";
		
		/* Formats the string */
		for (int i = 0; i < dimen; i++) {
			output = output.concat("\n");
			output = output.concat(reverseString(
					addSpace(bitBoard.substring(dimen * i, dimen * (i + 1)))));
		}
		return reverseString(output);
	}

	/**
	 * Converts a long bitboard into a flat string
	 * 
	 * @param bitboard: the current board in long type
	 * @param dimen: the dimension of the board
	 * @return a flat string notation of the board
	 */
	private static String bitBoardToString(long bitboard) {
		String output = "";
		int leadingZeros = (int) Math.pow(Position.dimen, 2) - 
				Long.toBinaryString(bitboard).length();
		for (int j = 0; j < leadingZeros; j++) {
			output = output.concat("0");
		}
		output = output.concat(Long.toBinaryString(bitboard));
		return output;
	}

	/**
	 * Converts a big integer bitboard into a flat string
	 * 
	 * @param bitboard: the current board in big integer type
	 * @param dimen: the dimension of the board
	 * @return a flat string notation of the board
	 */
	private static String bitBoardToString(BigInteger bitboard) {
		String output = "";
		int leadingZeros = (int) Math.pow(Position.dimen, 2) - 
				bitboard.toString(2).length();
		for (int j = 0; j < leadingZeros; j++) {
			output = output.concat("0");
		}
		output = output.concat(bitboard.toString(2));
		return output;
	}

	/**
	 * Reverses string
	 * 
	 * @param s: the input string
	 * @return the reversed string
	 */
	private static String reverseString(String s) {
		String output = "";
		for (int i = s.length() - 1; i >= 0; i--) {
			output = output.concat(s.substring(i, i + 1));
		}
		return output;
	}

	/**
	 * Adds spacing to the output (for neatness)
	 * 
	 * @param s: The unspaced input board
	 * @return the spaced output board
	 */
	private static String addSpace(String s) {
		String spacedOutput = "";
		for (int i = 0; i < s.length(); i++) {
			spacedOutput += s.charAt(i) + " ";
		}
		return spacedOutput;
	}

	public static long frToBitboard(int file, int row) {
		if(Position.dimen==Position.BIG_INT_CASE && file==0 && row==0) {
			return SPECIALCASE;
		}
		return pow(2, -file + Position.dimen*(Position.dimen - row) - 1);
	}
	
	public static long pow(int a, int b) {
		long result = 1;
		for(int i = 0; i < b; i++) {
			result *= a;
		}
		return result;
	}
	
}
