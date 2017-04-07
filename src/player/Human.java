package player;

import environment.MoveList;
import environment.Parse;
import environment.Position;
import environment.Run;
import environment.Side;
import exceptions.InvalidMoveException;

public class Human extends Player {
	
	Side color;
	Side opponent;
	int U = 0;
	int R = 1;
	int D = 2;
	int L = 2;
	int O = 3;
	char illegalMove;
	
	public Human(Side color) {
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
			Run.addHistory("—");
			return;
		}
		
		int[] frd = Parse.readMove(color);
		
		/* Check for invalid side move */
		if(frd[2] == illegalMove) {
			throw new InvalidMoveException();
		}
		
		/* Check for legal move, generates a bitboard from user input move, AND it with the
		 * moveList bitboard then check if there is a 1 bit -> legal move available */
		long userMoveBB = Parse.frToBitboard(frd[0], frd[1]);
		long legalBB = 0;
		long newBB = 0;
		
		switch(frd[2]) {
			case 'R':
				if(color==Side.H && frd[0]+1==Position.dimen) {
					legalBB = userMoveBB & p.ml.moves[O];
				}
				else {
					legalBB = userMoveBB & p.ml.moves[R];
					newBB 	= legalBB >>> 1;
				}
				break;
			case 'U':
				if(color==Side.V && frd[1]+1==Position.dimen) {
					legalBB = userMoveBB & p.ml.moves[O];
				}
				else {
					legalBB = userMoveBB & p.ml.moves[U];
					newBB	= legalBB >>> Position.dimen;
				}
				break;
			case 'D':
				legalBB = userMoveBB & p.ml.moves[D];
				newBB 	= legalBB << Position.dimen;
				break;
			case 'L':
				legalBB = userMoveBB & p.ml.moves[L];
				newBB 	= legalBB << 1;
				break;
			default:
				throw new InvalidMoveException();
		}
		
		if(Long.bitCount(legalBB) == 0) {
			throw new InvalidMoveException();
		}
		
		p.setCurrPieces(p.getCurrPieces() & ~legalBB | newBB, opponent);
	}

}
