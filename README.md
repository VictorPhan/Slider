# Proposed AI Agent for Slider

*Tin Bao, Victor Phan - The University of Melbourne*

## File Structure
* Consts: contains all the necessary indexing constants for all the classes. May split this into separate enum classes.
* MoveList: generates all the possible moves from a position. Instantiated by the Position class.
* Parse: reads in the board from System.in and generates a Position. Contains other useful conversion functions.
* Position: contains information about the current board position. A factor in board state (other factors are who's moving, who's won).
* Run: the main file.

## Board Representation
The **Bitboard** is chosen as the board representation of the game, owing to its advantage in efficiency in computing certain operations on the board which would otherwise take longer in certain Square Centric implementations such as Mailbox and 0x88.
A *position* will contain multiple bitboards, one for each of the piece types—B, H or V. Each bitboard is an integer value represented as a binary in twos complement, the digits representing whether the particular piece type of the bitboard is occupying the square. There is a bijection mapping between the squares of the board (of cardinality n^2) and an integer with n^2 bits. This method is particularly advantageous in the n<=8 case where the bitboards can be stored in the long[] data type. For larger dimensions, BigInteger and its equivalent operations are chosen as the data type to store the bitboard.

## Move Generation
A position will contain a MoveList object which stores the bitboards for all the possible types of moves. Move generation becomes a menial task owing to the bitshifting algorithms in play. For instance, if we wanted to generate all the possible moves that V can make to the left, we can create it in one line of code!—`((pieces[V] << 1) & ~occupied & ~rightCol) >>> 1`. The code here takes the bitboard pieces[V], shifts the bits left by 1, causing all the pieces to move to the left, bitwise AND that with all the non-occupied squares, bitwise AND it further with all the non-right column squares to prevent warping around the board, and finally shifting the bits right by 1 to return to the piece positions. This gives us a bitboard with all the V pieces that can possibly move to the left. Likewise, similar methods are applied onto all other bitboards. For this particular example, the number of possible moves for V to the left is then `Long.bitCount(vMoves[V_L])`.
