package othello;
import java.util.ArrayList;
import szte.mi.Move;

public class Othello {
	//Number of moves made. == number of tokens - 4
	private int numMoves;
	
	private int numNullMoves;
	
	//0: not occupied 1: white 2: black
	private BitBoard board;
	
	//The number of white/black tokens on the board
	private int whiteTokens;
	private int blackTokens;
	
	/**
	 * Constructs a new empty board with the standard starting formation.
	 */
	public Othello() {
		board = new BitBoard();

		//starting positions
		board.setPosition(3, 3, 1);
		board.setPosition(4, 4, 1);
		board.setPosition(3, 4, 2);
		board.setPosition(4, 3, 2);
		numMoves = 0;
		whiteTokens = blackTokens = 2;
		numNullMoves = 0;
	}
	
	/**
	 * Clones <b>this</b>.
	 * @param old_Othello The Othello object to clone.
	 */
	public Othello(Othello old_Othello) {
		this.board = old_Othello.board.copyBoard();
		this.whiteTokens = old_Othello.whiteTokens;
		this.blackTokens = old_Othello.blackTokens;
		this.numMoves = old_Othello.numMoves;
		this.numNullMoves = old_Othello.numNullMoves;
	}
	
	/**
	 * Returns the status of the game.
	 * @return 0 if undecided, 1 if white wins, 2 if black wins, 3 for a tie.
	 */
	public int gameStatus() {
		if (hasValidMove(1) || hasValidMove(2))
			return 0;
		else if (whiteTokens > blackTokens) return 1;
		else if (whiteTokens < blackTokens) return 2;
		else return 3;
	}
	
	
	/**
	 * Gets all valid moves for a player.
	 * @param player 1 for white, 2 for black.
	 * @return An ArrayList containing the moves.
	 */
	public ArrayList<Integer> getValidMoves(int player) {
		return board.getValidMoves(player);
	}
	
	/**
	 * Tests if <b>player</b> has valid moves.
	 * @param player 1: white, 2: black
	 * @return True if <b>player</b> has at least one valid move.
	 */
	public boolean hasValidMove(int player) {
		return board.hasValidMove(player);
	}
	
	/**
	 * Makes a move.
	 * 
	 * Does not check if any of the inputs is valid
	 * and does not check whether the move itself is valid.
	 * @param player 1: white, 2: black
	 * @param move The position to move to.
	 */
	public void makeMove(int player, Move move) {
		if (move == null) {
			numNullMoves++;
			return;
		}
		
//		if (player != 1 && player != 2) 
//			throw new IllegalArgumentException("Player has to be 1 or 2; not " + player);
//		if (!isValidMove(player, move))
//			throw new IllegalArgumentException("Move (" + move.x + "," + move.y + ") is not valid!");
		
		numMoves++;
		int turns = board.makeMove(player, (move.y * 8) + move.x);
		if (player == 1) whiteTokens += turns + 1; else whiteTokens -= turns;
		if (player == 1) blackTokens -= turns; else blackTokens += turns + 1;
	}
	
	/**
	 * Makes a move.
	 * 
	 * Does not check if any of the inputs is valid
	 * and does not check whether the move itself is valid.
	 * @param player 1: white, 2: black
	 * @param move The position to move to.
	 */
	public void makeMove(int player, int n) {
		if (n == -1) {
			numNullMoves++;
			return;
		}
		
//		if (player != 1 && player != 2) 
//			throw new IllegalArgumentException("Player has to be 1 or 2; not " + player);
//		if (!isValidMove(player, move))
//			throw new IllegalArgumentException("Move (" + move.x + "," + move.y + ") is not valid!");
		
		numMoves++;
		
		int turns = board.makeMove(player, n);
		if (player == 1) whiteTokens += turns; else whiteTokens -= turns;
		if (player == 1) blackTokens -= turns; else blackTokens += turns;
	}
	
	/**
	 * Simulates a move.
	 * @param player 1: white, 2: black
	 * @param n The index to move to.
	 * @return An Othello instance where the move has been made.
	 */
	public Othello simulateMove(int player, int n) {
		Othello o = new Othello(this);
		o.makeMove(player, n);
		return o;
	}
	
	public int getPosition(int x, int y) { return board.getPosition(x,y); }
	
	public int getPosition(int n) { return board.getPosition(n); }
	
	public BitBoard getBoard() {
		return board;
	}
	
	public int getNumOfNullMoves() {
		return this.numNullMoves;
	}
	
	public int getWhiteTokens() {
		return whiteTokens;
	}

	public int getBlackTokens() {
		return blackTokens;
	}
	
	public int getNumMoves() {
		return numMoves;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				s += board.getPosition(x, y) + " ";
			}
			s += "\n";
		}
		return s;
	}
}
