package environment;

import java.util.ArrayList;
import java.util.HashMap;

import types.MutableInteger;

public class GameHistory {
	
	static String moveHistory = "";
	public HashMap<ArrayList<Long>, MutableInteger> positionCounter = 
			new HashMap<ArrayList<Long>, MutableInteger>();
	private static int threeFoldCase = 3;
	
	/**
	 * Add a string to the move history which may be printed at the end of the game
	 * @param move
	 */
	public static void addHistory(String move) {
		moveHistory = moveHistory.concat(move).concat(" ");
	}
	
	/**
	 * Adds final string output when game has ended
	 * @param curr
	 */
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
	 * the H pieces and the V pieces. Does not store the ordering of the positions.
	 * @param pieces
	 * @return
	 */
	public boolean threeFoldRepitition(long[] pieces) {
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
	
	@SuppressWarnings("unchecked")
	public GameHistory clone() {
		GameHistory ghClone = new GameHistory();
		ghClone.positionCounter = (HashMap<ArrayList<Long>, MutableInteger>) this.positionCounter.clone();
		return ghClone;
	}
	
}
