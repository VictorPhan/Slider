package environment;

import java.util.ArrayList;
import java.util.HashMap;

import types.MutableInteger;

public class GameHistory {
	
	static String moveHistory = "";
	static HashMap<ArrayList<Long>, MutableInteger> positionCounter = 
			new HashMap<ArrayList<Long>, MutableInteger>();
	private static int threeFoldCase = 3;
	
	public static void addHistory(String move) {
		moveHistory = moveHistory.concat(move).concat(" ");
	}
	
	public static void addFinalHistory(Position curr) {
		if(curr.gs==GameState.DRAW) {
			addHistory("#=");
		}
		else if(curr.gs==GameState.H_WON) {
			addHistory("#H");
		}
		else if(curr.gs==GameState.V_WON) {
			addHistory("#V");
		}
		else {
			throw new Error("Game hasn't ended.");
		}
	}
	
	/**
	 * Checks for a repeated position by using a HashMap positionCounter.
	 * The key is piecesWrapper which stores piece centric information about
	 * the H pieces and the V pieces.
	 * @param pieces
	 * @return
	 */
	public static boolean threeFoldRepitition(long[] pieces) {
		ArrayList<Long> piecesWrapper = new ArrayList<Long>();
		for(int i=0; i<Position.PIECE_TYPES-1; i++) {
			piecesWrapper.add(pieces[i]);
		}
		MutableInteger initValue = new MutableInteger(1);
		MutableInteger oldValue = positionCounter.put(piecesWrapper, initValue);
		if(oldValue!=null) {
			initValue.set(oldValue.get() + 1);
			if(initValue.equals(threeFoldCase)) {
				return true;
			}
		}
		return false;
	}
	
}
