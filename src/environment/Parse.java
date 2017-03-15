package environment;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Parses through the input data from run
 * @author TB VP
 *
 */
public class Parse {
	/**
	 * Reads input position and returns an initialized bitboard
	 * @return
	 */
	public static Position parseBoard() {
		Position board;
		Scanner s = new Scanner(System.in);
		String line = "";
		int dimension;
		long [] pieces;
		
		dimension = s.nextInt();
		for(int i=0; i<dimension; i++) {
			line = s.nextLine();
		}
		pieces = fromRawString(line.replaceAll("\\s+",""));
		board = new Position(dimension, pieces);
		s.close();
		return board;
	}
	
	/**
	 * Converts Position object into printable string
	 * @param board
	 * @return
	 */
	public static String boardToString(Position board) {
		String output = "";
		String HPieces = bitBoardToString(board.getPieces()[Position.H]);
		String VPieces = bitBoardToString(board.getPieces()[Position.V]);
		for(int i=0; i<Math.pow(Position.dimension, 2); i++) {
			if(HPieces.charAt(i) == '1') {
				output = output.concat("H");
			}
			else if(VPieces.charAt(i) == '1') {
				output = output.concat("V");
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
		return stringToBoardString(bitBoardToString(bitboard), Position.dimension);
	}
	
	/**
	 * Converts a raw input string into the long [] bitboards
	 * @param line
	 * @return
	 */
	private static long [] fromRawString(String line) {
		long [] pieces = new long[2];
		String vPieces = "";
		String hPieces = "";
		for(int i=0; i<line.length(); i++) {
			if(line.charAt(i) == 'V') {
				vPieces = vPieces.concat("1");
				hPieces = hPieces.concat("0");
			}
			else if(line.charAt(i) == 'H') {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("1");
			}
			else {
				vPieces = vPieces.concat("0");
				hPieces = hPieces.concat("0");
			}
		}
		pieces[Position.V] = new BigInteger(vPieces, 2).longValue();
		pieces[Position.H] = new BigInteger(hPieces, 2).longValue();
		return pieces;
	}
	
	/**
	 * Formats a flat string to a printable board.
	 * @param bitBoard
	 * @return
	 */
	private static String stringToBoardString(String bitBoard, int dimension) {
		String output = "";
		
		for(int i=0; i<dimension; i++) {
			output = output.concat("\n");
			output = output.concat(reverseString(
					bitBoard.substring(dimension*i, dimension*(i+1))));
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
		int leadingZeros = (int) Math.pow(Position.dimension, 2) - 
				Long.toBinaryString(bitboard).length();
		for(int j=0; j<leadingZeros; j++) {
			output = output.concat("0");
		}
		output = output.concat(Long.toBinaryString(bitboard));
		return output;
	}
	
	/**
	 * Reverses string
	 * @param s
	 * @return
	 */
	private static String reverseString(String s) {
		String output = "";
		for(int i=s.length()-1; i>=0; i--) {
			output = output.concat(s.substring(i, i+1));
		}
		return output;
	}
}
