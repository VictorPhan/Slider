package environment;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Parses through the input data from run
 * @author TB VP
 *
 */
public class Parse implements Consts {
	/**
	 * Reads input position and returns an initialized bitboard
	 * @return
	 */
	public static Position parseBoard() {
		/* The current position of the board */
		Position board;
		Scanner s = new Scanner(System.in);
		String line = "";
		/* Given (n) value */
		int dimen;
		
		/* Processes the dimens given (board) */
		dimen = s.nextInt();
		
		/* 
		 * Gets each line info on the board and makes 
		 * a single String from that.
		 */
		for(int i = 0; i < dimen + 1; i++) {
			line = s.nextLine() + line;
		}
		line = line.replaceAll("\\s+","");
		
		/* Checks the dimension of the board and decides type */
		if(dimen > 8){
			BigInteger[] bigPieces;
			bigPieces = fromRawString2(line);	
			board = new Position(dimen, bigPieces);
		} else {
			long[] pieces;
			pieces = fromRawString(line);
			board = new Position(dimen, pieces);
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
		long [] smallPieces = new long[piecesDimensionality];
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
			else {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
		}
		smallPieces[Position.V] = new BigInteger(vPieces, 2).longValue();
		smallPieces[Position.H] = new BigInteger(hPieces, 2).longValue();
		smallPieces[Position.B] = new BigInteger(bPieces, 2).longValue();
		return smallPieces;
	}
	
	/**
	 * Converts a raw input string into the long [] bitboards
	 * @param line
	 * @return
	 */
	
	public static BigInteger[] fromRawString2(String line) {
		BigInteger[] bigPieces = new BigInteger[piecesDimensionality];
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
			else {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
				bPieces = bPieces.concat("0");
			}
		}
		bigPieces[Position.V] = new BigInteger(vPieces, 2);
		bigPieces[Position.H] = new BigInteger(hPieces, 2);
		bigPieces[Position.B] = new BigInteger(bPieces, 2);
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
		if(Position.getdimen() > 8){
			HPieces = bitBoardToString(board.getBigPieces()[Position.H]);
			VPieces = bitBoardToString(board.getBigPieces()[Position.V]);
			BPieces = bitBoardToString(board.getBigPieces()[Position.B]);
		} else {
			HPieces = bitBoardToString(board.getPieces()[Position.H]);
			VPieces = bitBoardToString(board.getPieces()[Position.V]);
			BPieces = bitBoardToString(board.getPieces()[Position.B]);
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
		return stringToBoardString(output, Position.dimension);
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
