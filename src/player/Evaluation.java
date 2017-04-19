package player;

import environment.GameState;
import environment.MoveList;
import environment.Position;
import environment.Side;

import java.util.Arrays;

/**
 * Evaluation class. POSITIVE INFINITY is H's win, NEGATIVE INFINITY is V's win
 * @author Victor
 *
 */
public class Evaluation {
	
	static final int V = 0;
	static final int H = 1;
	static final int B = 2;
	
	static final int VU = 0;
	static final int VR = 1;
	static final int VL = 2;
	static final int VO = 3;
	
	static final int HU = 0;
	static final int HR = 1;
	static final int HD = 2;
	static final int HO = 3;
		
	public static double evaluate(Position p) {
		if (p.gs == GameState.DRAW) {
			return 0;
		}
		else if(p.gs == GameState.H_WON) {
			return Double.POSITIVE_INFINITY;
		}
		else if(p.gs == GameState.V_WON) {
			return Double.NEGATIVE_INFINITY;
		}
		
		double[] input = createInputLayer(p);
		System.out.println(Arrays.toString(input));
		return 0;
	}
	
	public static double[] createInputLayer(Position p) {
		/* Global has 4 features—SidePlaying, #H pieces, #V pieces, #B pieces
		 * Piece centric is location of each piece, including directional mobility of each moving piece
		 * Square centric includes the 'bled' board
		 */
		double[] inG = new double[4];
		double[] inP = new double[(Position.dimen-1)*5*2+4];
		double[] inS = new double[Position.dimen * Position.dimen];
		
		long vPieces = p.getPieces(V);
		long hPieces = p.getPieces(H);
		long bPieces = p.getPieces(B);
		
		// Global features
		{
			// Side playing
			if(p.sidePlaying == Side.H) {
				inG[0] = 1;
			}
			else {
				inG[0] = -1;
			}
			
			// # each piece type normalised
			inG[1+V] = Long.bitCount(vPieces);
			inG[1+H] = Long.bitCount(hPieces);
			inG[1+B] = Long.bitCount(bPieces);
		}
		
		// Piece centric features
		{
			int i=0;
			while(vPieces != 0) {
				long piece = Long.highestOneBit(vPieces);
				long[] vMoves = MoveList.generateVMoves(p.getPieces());
				double[] tempXY = getNormXY(piece);
				inP[0+i] = tempXY[0];
				inP[1+i] = tempXY[1];
				inP[2+i] = Long.bitCount(piece & (vMoves[VU] | vMoves[VO]));
				inP[3+i] = Long.bitCount(piece & vMoves[VR]);
				inP[4+i] = Long.bitCount(piece & vMoves[VL]);
				vPieces -= piece;
				i+=5;
			}
			i=(Position.dimen-1)*5;
			while(hPieces != 0) {
				long piece = Long.highestOneBit(hPieces);
				long[] hMoves = MoveList.generateHMoves(p.getPieces());
				double[] tempXY = getNormXY(piece);
				inP[0+i] = tempXY[0];
				inP[1+i] = tempXY[1];
				inP[2+i] = Long.bitCount(piece & (hMoves[HU] | hMoves[HO]));
				inP[3+i] = Long.bitCount(piece & hMoves[HR]);
				inP[4+i] = Long.bitCount(piece & hMoves[HD]);
				hPieces -= piece;
				i+=5;
			}
			
			i=(Position.dimen-1)*5*2;
			while(bPieces != 0) {
				long piece = Long.highestOneBit(bPieces);
				double[] tempXY = getNormXY(piece);
				inP[0+i] = tempXY[0];
				inP[1+i] = tempXY[1];
				bPieces -= piece;
			}
		}
		System.out.println(Arrays.toString(inP));
		// Square centric features
		
		return null;
	}
	
	public static double[] getNormXY(long bit) {
		int i=0;
		while(bit % 2 == 0) {
			bit /= 2;
			i++;
		}
		double[] XY = {	((double)Position.dimen-i%Position.dimen)/Position.dimen,
						((double)Position.dimen-i/Position.dimen)/Position.dimen};
		return XY;
	}
	
}
