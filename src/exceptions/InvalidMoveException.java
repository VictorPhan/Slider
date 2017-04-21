package exceptions;

/**
 * Thrown for the Human Player
 * @author Victor
 *
 */
public class InvalidMoveException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidMoveException() {
		super("Invalid move");
	}
	
}
