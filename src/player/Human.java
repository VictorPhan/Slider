package player;

import environment.GameHistory;
import environment.MoveList;
import environment.Parse;
import environment.Position;
import environment.Side;
import exceptions.InvalidMoveException;
import top_end.Move;

public class Human extends Player {
	
	int U = 0;
	int R = 1;
	int D = 2;
	int L = 2;
	int O = 3;
	char illegalMove;
	
	public boolean checkPass(long[] ml) {
		for(int i=0; i<MoveList.MOVE_TYPES; i++) {
			if(Long.bitCount(ml[i])!=0) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public double makeMove(Position p) {
		if(checkPass(p.ml.moves)) {
			if(p.sidePlaying == Side.H) {
				System.out.println("H player move: Pass");
			}
			else {
				System.out.println("V player move: Pass");
			}
			p.swapPlayers();
			GameHistory.addHistory("—");
			return 0;
		}
		
		int[] frd;
		try {
			frd = Parse.readMove(p.sidePlaying);
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
					if(p.sidePlaying==Side.H && frd[0]+1==Position.dimen) {
						legalBB = userMoveBB & p.ml.moves[O];
					}
					else {
						legalBB = userMoveBB & p.ml.moves[R];
						newBB 	= legalBB >>> 1;
					}
					break;
				case 'U':
					if(p.sidePlaying==Side.V && frd[1]+1==Position.dimen) {
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
			
			p.setCurrPieces(p.getCurrPieces() & ~legalBB | newBB);
			
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void init(int dimension, String board, char player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(int dimension, String board, char player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(int dimension, String board, char player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}

}
