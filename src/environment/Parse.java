package environment;

import java.math.BigInteger;
import java.util.Scanner;
import java.lang.Error;

/**
 * Parses through the input data from run
 * @author TB VP
 *
 */
public class Parse {
	
	public static final int V = 0;
	public static final int H = 1;
	public static final int B = 2;
	
	/**
	 * Reads input position and returns an initialized bitboard
	 * @return
	 */
	public static Position parseBoard() {
		
		Position board;
		Scanner s = new Scanner(System.in);
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
		if(dimen > Run.BIG_INT_CASE){
			BigInteger[] bigPieces;
			bigPieces = fromRawString2(line);	
			board = new Position(dimen, bigPieces);
		} else {
			long[] pieces;
			pieces = fromRawString(line);
			board = new Position(dimen, sidePlaying, pieces);
		}
		
		s.close();
		return board;
	}
	
	/**
	 * Converts a raw input string into the long [] bitboards
	 * @param line
	 * @return
	 */
	
	public static long[] fromRawString(String line) {
		long [] smallPieces = new long[Run.PIECE_TYPES];
		String vPieces = "";
		String hPieces = "";
		String bPieces = "";
		for(int i=0; i<line.length(); i++) {
			if(line.charAt(i) == 'V') {
				vPieces = vPieces.concat("1");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
			else if(line.charAt(i) == 'H') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("1");
				bPieces = bPieces.concat("0");
			}
			else if(line.charAt(i) == 'B') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("1");
			}
			else if(line.charAt(i) == '+'){
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
			else {
				throw new Error("Only the characters 'B', 'H', 'V', '+' are accepted for board input.");
			}
		}
		smallPieces[V] = new BigInteger(vPieces, 2).longValue();
		smallPieces[H] = new BigInteger(hPieces, 2).longValue();
		smallPieces[B] = new BigInteger(bPieces, 2).longValue();
		return smallPieces;
	}
	
	/**
	 * Converts a raw input string into the long [] bitboards
	 * @param line
	 * @return
	 */
	public static BigInteger[] fromRawString2(String line) {
		BigInteger[] bigPieces = new BigInteger[Run.PIECE_TYPES];
		String vPieces = "";
		String hPieces = "";
		String bPieces = "";
		for(int i=0; i<line.length(); i++) {
			if(line.charAt(i) == 'V') {
				vPieces = vPieces.concat("1");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
			else if(line.charAt(i) == 'H') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("1");
				bPieces = bPieces.concat("0");
			}
			else if(line.charAt(i) == 'B') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("1");
			}
			else if(line.charAt(i) == '+'){
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
			else {
				throw new Error("Only the characters 'B', 'H', 'V', '+' are accepted for board input.");
			}
		}
		bigPieces[V] = new BigInteger(vPieces, 2);
		bigPieces[H] = new BigInteger(hPieces, 2);
		bigPieces[B] = new BigInteger(bPieces, 2);
		return bigPieces;
	}
	
	/**
	 * Converts Position object into printable string
	 * @param board
	 * @return
	 */
	public static String boardToString(Position board) {
		String output = "";
		String HPieces, VPieces, BPieces;
		if(Position.getdimen() > Run.BIG_INT_CASE){
			HPieces = bitBoardToString(board.getBigPieces()[H]);
			VPieces = bitBoardToString(board.getBigPieces()[V]);
			BPieces = bitBoardToString(board.getBigPieces()[B]);
		} else {
			HPieces = bitBoardToString(board.getPieces()[H]);
			VPieces = bitBoardToString(board.getPieces()[V]);
			BPieces = bitBoardToString(board.getPieces()[B]);
		}
		for(int i=0; i<Math.pow(Position.dimen, 2); i++) {
			if(HPieces.charAt(i) == '1') {
				output = output.concat("H");
			}
			else if(VPieces.charAt(i) == '1') {
				output = output.concat("V");
			}
			else if(BPieces.charAt(i) == '1') {
				output = output.concat("B");
			}
			else {
				output = output.concat("+");
			}
		}
		return stringToBoardString(output, Position.dimen);
	}
	
	/**
	 * Converts a long bitboard into a printable string
	 * @param bitboard
	 * @return
	 */
	public static String bitBoardToBoardString(long bitboard) {
		return stringToBoardString(bitBoardToString(bitboard), Position.dimen);
	}
	
	public static String bitBoardToBoardString(BigInteger bitboard) {
		return stringToBoardString(bitBoardToString(bitboard), Position.dimen);
	}
	
	/**
	 * Formats a flat string to a printable board.
	 * @param bitBoard
	 * @return
	 */
	private static String stringToBoardString(String bitBoard, int dimen) {
		String output = "";
		
		for(int i=0; i<dimen; i++) {
			output = output.concat("\n");
			output = output.concat(reverseString(spaceShuffle(
					bitBoard.substring(dimen*i, dimen*(i+1)))));
		}
		return reverseString(output);
	}
	
	/**
	 * Converts a long bitboard into a flat string
	 * @param bitboard
	 * @return
	 */
	private static String bitBoardToString(long bitboard) {
		String output = "";
		int leadingZeros = (int) Math.pow(Position.dimen, 2) - 
				Long.toBinaryString(bitboard).length();
		for(int j=0; j<leadingZeros; j++) {
			output = output.concat("0");
		}
		output = output.concat(Long.toBinaryString(bitboard));
		return output;
	}
	
	private static String bitBoardToString(BigInteger bitboard) {
		String output = "";
		int leadingZeros = (int) Math.pow(Position.dimen, 2) - 
				bitboard.toString(2).length();
		for(int j=0; j<leadingZeros; j++) {
			output = output.concat("0");
		}
		output = output.concat(bitboard.toString(2));
		return output;
	}
	
	/**
	 * Reverses string
	 * @param s the unreversed input
	 * @return the reversed string
	 */
	private static String reverseString(String s) {
		String output = "";
		for(int i=s.length()-1; i>=0; i--) {
			output = output.concat(s.substring(i, i+1));
		}
		return output;
	}
	
	/**
	 * Adds spacing to the output (for neatness)
	 * @param s The unspaced input board
	 * @return the spaced output board
	 */
	private static String spaceShuffle(String s) {
		String spacedOutput = "";
		for(int i = 0; i < s.length(); i++){
			spacedOutput += s.charAt(i) + " ";
		}
		return spacedOutput;
	}
	
}
