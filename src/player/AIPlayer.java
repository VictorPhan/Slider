package player;

import environment.GameHistory;
import environment.MoveList;
import environment.Position;
import environment.Side;
import exceptions.InvalidMoveException;

public class AIPlayer extends Player {
	
	Side color;
	Side opponent;
	int U = 0;
	int R = 1;
	int D = 2;
	int L = 2;
	int O = 3;
	char illegalMove;
	
	public AIPlayer(Side color) {
		this.color = color;
		if(color == Side.H) {
			illegalMove = 'L';
			opponent = Side.V;
		}
		else if(color == Side.V) {
			illegalMove = 'D';
			opponent = Side.H;
		}
	}
	
	public boolean checkPass(long[] ml) {
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			if(Long.bitCount(ml[i])!=0) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void makeMove(Position p) throws InvalidMoveException {
		if(checkPass(p.ml.moves)) {
			if(color == Side.H) {
				System.out.print("H player move: Pass");
			}
			else {
				System.out.println("V player move: Pass");
			}
			p.setCurrPieces(p.getCurrPieces(), opponent);
			GameHistory.addHistory("—");
			return;
		}
		
		// First implement minimax algorithm, test on small board case (size 3), include transposition tables
		
		
	}
	

}
