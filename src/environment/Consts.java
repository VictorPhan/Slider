package environment;

/**
 * An interface that contains all the constants for all functions
 * @author TB VP
 *
 */
public interface Consts {
	
	/** The total size of the move list array */
	public static final int moveListDimensionality = 4;
	/** The total size of the pieces stored */
	public static final int piecesDimensionality = 3;
	
	/** The threshold for our storage 
	 *  Since the primitive long data type can only go for 32 bits
	 *  We need to utilise boards that are larger than 8x8
	 */
	public static final int BIG_INTEGER_CASE = 8;
	
	public static final int V = 0;
	public static final int H = 1;
	public static final int B = 2;
	
	public static final int V_U = 0;
	public static final int V_L = 1;
	public static final int V_R = 2;
	public static final int V_O = 3;
	
	public static final int H_R = 0;
	public static final int H_U = 1;
	public static final int H_D = 2;
	public static final int H_O = 3;
}
