package neural_network;

import environment.GameState;
import environment.MoveList;
import environment.Parse;
import environment.Position;
import environment.Side;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Evaluation class.
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
	
	public static final double H_WIN_SCORE = 10; //Double.POSITIVE_INFINITY;
	public static final double V_WIN_SCORE = -10; //Double.NEGATIVE_INFINITY;
	
	static int s = Position.dimen * Position.dimen;
	
	public NeuralNetwork nn = new NeuralNetwork(s, s, s);
		
	public double evaluate(Position p) {
		if (p.gs == GameState.DRAW) {
			return 0;
		}
		else if(p.gs == GameState.H_WON) {
			return H_WIN_SCORE;
		}
		else if(p.gs == GameState.V_WON) {
			return V_WIN_SCORE;
		}
		else {
			return nn.evaluate(createInputLayer(p));
		}
	}
	
	public ArrayList<double[]> evaluateLearn(Position p) {
		ArrayList<double[]> tensor = new ArrayList<double[]>();
		if (p.gs == GameState.DRAW) {
			tensor.add(new double[] {0, 0, 0, 0});
			return tensor;
		}
		else if(p.gs == GameState.H_WON) {
			tensor.add(new double[] {H_WIN_SCORE});
			return tensor;
		}
		else if(p.gs == GameState.V_WON) {
			tensor.add(new double[] {V_WIN_SCORE});
			return tensor;
		}
		else {
			return nn.evaluateLearn(createInputLayer(p));
		}
	}
	
	public static double[] createInputLayer(Position p) {
		/* Global has 4 features—SidePlaying, #H pieces, #V pieces, #B pieces
		 * Piece centric is location of each piece, including directional mobility of each moving piece
		 * Square centric includes the 'bled' board
		 */
		double[] inS = bleedBoard(p.getPieces(), p.sidePlaying);
		return inS;
	}
	
	public static double[] bleedBoard(long[] pieces, Side s) {
		long occupied = pieces[B] | pieces[V] | pieces[H];
		long[] oldP;
		long[] newP = pieces.clone();
		do {
			oldP = Arrays.copyOf(newP, newP.length);
			if(s == Side.H) {
				long[] hMoves = MoveList.generateHMoves(newP);
				newP[H] = oldP[H] | 
						(((hMoves[HU] >>> Position.dimen) & ~MoveList.bottomRow) | 
						((hMoves[HR] >>> 1) & ~MoveList.leftCol) | 
						((hMoves[HD] & ~MoveList.bottomRow) << Position.dimen)) & ~occupied;
				s = Side.V;
			}
			else {
				long[] vMoves = MoveList.generateVMoves(newP);
				newP[V] = oldP[V] | 
						(((vMoves[VU] >>> Position.dimen) & ~MoveList.bottomRow) | 
						((vMoves[VR] >>> 1) & ~MoveList.leftCol) | 
						((vMoves[VL] << 1) & ~MoveList.rightCol)) & ~occupied;
				s = Side.H;
			}
		} while(!Arrays.equals(newP, oldP));
		
		double[] returnPieces = new double[Position.dimen * Position.dimen];
		String hBytes = Parse.bitBoardToString(newP[H]);
		String vBytes = Parse.bitBoardToString(newP[V]);
		for(int i=0; i<Position.dimen*Position.dimen; i++) {
			returnPieces[i] = vBytes.charAt(i) - hBytes.charAt(i);
		}
		return returnPieces;
	}
}
