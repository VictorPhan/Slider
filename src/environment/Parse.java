package environment;

import java.math.BigInteger;
import java.util.Scanner;

import exceptions.InvalidMoveException;

import java.lang.Error;

/**
 * Contains all the read in and print out functioanlities
 * @author TB VP
 *
 */
public class Parse {
	
	// avoiding integer overflow at 2^63
	public static final long SPECIALCASE = 0b1000000000000000000000000000000000000000000000000000000000000000L;
	
	public static final int V = 0;
	public static final int H = 1;
	public static final int B = 2;
	
	static Scanner s = null;
	
	/**
	 * Initializes the scanner
	 */
	public static void initScan() {
		s = new Scanner(System.in);
	}
	
	/**
	 * Closes the scanner
	 */
	public static void closeScan() {
		s.close();
	}
	
	/**
	 * Reads board dimensionality, which side is immediately playing,
	 * and an input position to return an initialized Position object
	 * @return the initial position of the board
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
			bigPieces = fromRawStringBig(line);	
			board = new Position(bigPieces, sidePlaying, dimen);
		} else {
			long[] pieces;
			pieces = fromRawString(line);
			board = new Position(pieces, sidePlaying, dimen);
		}
		return board;
	}
	
	/**
	 * Initializes the board for the spec Referee 
	 * @param dimen dimension of the board
	 * @param side which player
	 * @param initPos the whole board 
	 * @return the initial position of the board
	 */
	public static Position parseBoard(int dimen, char side, String initPos) {
		
		Position board;
		String reversedLine = "";
		Side sidePlaying;
		
		if(side == 'H') {
			sidePlaying = Side.H;
		}
		else if(side == 'V') {
			sidePlaying = Side.V;
		}
		else {
			throw new Error("Only the characters 'H' or 'V' are accepted as side playing input.");
		}
		
		/* 
		 * Gets each line info on the board and makes 
		 * a single String from that.
		 */
		for(String line : initPos.split("\n")){
			reversedLine = line + reversedLine;
		}
		reversedLine = reversedLine.replaceAll("\\s+","");
		
		/* Checks the dimension of the board and decides type */
		if(dimen >= Position.BIG_INT_CASE){
			BigInteger[] bigPieces;
			bigPieces = fromRawStringBig(reversedLine);	
			board = new Position(bigPieces, sidePlaying, dimen);
		} else {
			long[] pieces;
			pieces = fromRawString(reversedLine);
			board = new Position(pieces, sidePlaying, dimen);
		}
		return board;
	}
	
	/**
	 * Converts a raw input string into the long [] bitboards
	 * @param line 
	 * @return
	 */
	public static long[] fromRawString(String line) {
		long [] smallPieces = new long[Position.PIECE_TYPES];
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
	public static BigInteger[] fromRawStringBig(String line) {
		BigInteger[] bigPieces = new BigInteger[Position.PIECE_TYPES];
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
		if(Position.dimen > Position.BIG_INT_CASE){
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
	
	/**
	 * Converts a BigInteger bitboard into a printable string
	 * @param bitboard
	 * @return
	 */
	public static String bitBoardToBoardString(BigInteger bitboard) {
		return stringToBoardString(bitBoardToString(bitboard), Position.dimen);
	}
	
	/**
	 * Reads a move in algebraic notation, returns an int[]
	 * where frd[0] = file, frd[1] = rank, frd[2] = direction
	 * Files and ranks will take integer values over [0, Position.dimen-1]
	 * @param color 
	 * @throws InvalidMoveException 
	 */
	public static int[] readMove(Side color) throws InvalidMoveException {
		if(s == null) {
			throw new Error("Scanner not initialised");
		}
		
		if(color == Side.H) {
			System.out.print("H player move: ");
		}
		else {
			System.out.print("V player move: ");
		}
		
		String move = s.next();
		if(move.length()!=4) {
			if(move.length()==3) {
				int file = (int) move.charAt(0) - 97;
				int rank = Integer.parseInt(Character.toString(move.charAt(1))) - 1;
				int direction = (int) move.charAt(2);
				if(direction == (int) '+') {
					if(color == Side.H) {
						direction = (int) 'R';
					}
					else {
						direction = (int) 'U';
					}
				}
				int[] frd = {file, rank, direction};
				GameHistory.addHistory(move);
				return frd;
			}
			throw new InvalidMoveException();
		}
		
		int file = (int) move.charAt(0) - 97;
		int rank = Integer.parseInt(Character.toString(move.charAt(1))) - 1;
		int f2 = (int) move.charAt(2) - 97;
		int r2 = Integer.parseInt(Character.toString(move.charAt(3))) - 1;
		int direction = getDirection(file, rank, f2, r2);
		int[] frd = {file, rank, direction};
		GameHistory.addHistory(move);
		return frd;
	}
	
	public static int getDirection(int f1, int r1, int f2, int r2) throws InvalidMoveException {
		int hDisp = f2-f1;
		int vDisp = r2-r1;
		if(hDisp==1) {
			if(vDisp==0) {
				return (int) 'R';
			}
		}
		else if(hDisp==-1) {
			if(vDisp==0) {
				return (int) 'L';
			}
		}
		else if(hDisp==0) {
			if(vDisp==1) {
				return (int) 'U';
			}
			else if(vDisp==-1) {
				return (int) 'D';
			}
		}
		throw new InvalidMoveException();
	}
	
	/**
	 * Inserts the appropriate newline characters into the bitBoard
	 * to print the board as a square
	 */
	public static String stringToBoardString(String bitBoard, int dimen) {
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
	public static String bitBoardToString(long bitboard) {
		String output = "";
		int leadingZeros = (int) Math.pow(Position.dimen, 2) - 
				Long.toBinaryString(bitboard).length();
		for(int j=0; j<leadingZeros; j++) {
			output = output.concat("0");
		}
		output = output.concat(Long.toBinaryString(bitboard));
		return output;
	}
	
	/**
	 * Converts a bigInteger bitboard into a flat string
	 * @param bitboard
	 * @return
	 */
	public static String bitBoardToString(BigInteger bitboard) {
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
	
	/**
	 * File and rank into a bitboard of size dimen with 0s and a single 1
	 * on the specified square
	 * @param file
	 * @param row
	 * @return
	 */
	public static long frToBitboard(int file, int row) {
		if(Position.dimen==Position.BIG_INT_CASE && file==0 && row==0) {
			return SPECIALCASE;
		}
		return pow(2, -file + Position.dimen*(Position.dimen - row) - 1);
	}
	
	/**
	 * Integer power, more efficient than using Math.pow as it avoids
	 * the double type operations
	 */
	public static long pow(int a, int b) {
		long result = 1;
		for(int i = 0; i < b; i++) {
			result *= a;
		}
		return result;
	}
	
	/**
	 * Integer power, more efficient than using Math.pow as it avoids
	 * the double type operations
	 */
	public static BigInteger bigPow(int a, int b) {
		BigInteger result = BigInteger.ONE;
		for(int i = 0; i < b; i++) {
			result = result.multiply(BigInteger.valueOf(a));
		}
		return result;
	}
	
}
