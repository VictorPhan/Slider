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
	
	public static final double H_WIN_SCORE = 1; //Double.POSITIVE_INFINITY;
	public static final double V_WIN_SCORE = -1; //Double.NEGATIVE_INFINITY;
	
	static int s = 6;
	
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
		ArrayList<double[]> tensor = nn.evaluateLearn(createInputLayer(p));
		if (p.gs == GameState.DRAW) {
			tensor.get(0)[0] = 0;
		}
		else if(p.gs == GameState.H_WON) {
			tensor.get(0)[0] = H_WIN_SCORE;
		}
		else if(p.gs == GameState.V_WON) {
			tensor.get(0)[0] = V_WIN_SCORE;
		}
		return tensor;
	}
	
	public static double[] createInputLayer(Position p) {
		return metaInfo(p);
	}
	
	public static double[] metaInfo(Position p) {
		long[] pieces = p.getPieces();
		double[] numberPieces = {Long.bitCount(pieces[V])-Long.bitCount(pieces[H])};
		
		long[] hMoves = MoveList.generateHMoves(pieces);
		long[] vMoves = MoveList.generateVMoves(pieces);
		
		double[] moves = new double[MoveList.MOVE_TYPES];
		for(int i=0; i<MoveList.MOVE_TYPES; i+=2) {
			moves[i] = Long.bitCount(hMoves[i])-Long.bitCount(vMoves[i]);
		}
		
		// Distance of all the pieces from the end
		double[] hDist = {getDistanceV(pieces[V])-getDistanceH(pieces[H])};
		return concat(concat(numberPieces, moves), hDist);
	}
	
	public static int getDistanceH(long h) {
		int distance = 0;
		while(h != 0) {
			long singular = Long.highestOneBit(h);
			h -= singular;
			int i=0;
			while(singular % 2 == 0) {
				singular /= 2;
				i++;
			}
			distance += 2 * i%Position.dimen;
		}
		return distance;
	}
	
	public static int getDistanceV(long v) {
		int distance = 0;
		while(v != 0) {
			long singular = Long.highestOneBit(v);
			v -= singular;
			int i=0;
			while(singular % 2 == 0) {
				singular /= 2;
				i++;
			}
			distance += 2 * i/Position.dimen;
		}
		return distance;
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
	
	public static double[] concat(double[] a, double[] b) {
	   int aLen = a.length;
	   int bLen = b.length;
	   double[] c = new double[aLen+bLen];
	   System.arraycopy(a, 0, c, 0, aLen);
	   System.arraycopy(b, 0, c, aLen, bLen);
	   return c;
	}
}
