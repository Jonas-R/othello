package othello;
import java.util.ArrayList;

public class Othello {
	//Number of moves made. == number of tokens - 4
	private int numMoves;
	
	//0: not occupied 1: white 2: black
	private int[][] board;
	
	//The number of white/black tokens on the board
	private int whiteTokens;
	private int blackTokens;
	
	private final int[][] directions = { {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1} };
	
	public int getWhiteTokens() {
		return whiteTokens;
	}

	public int getBlackTokens() {
		return blackTokens;
	}
	
	public int getNumMoves() {
		return numMoves;
	}
	
	/**
	 * Constructs a new empty board with the standard starting formation.
	 */
	public Othello() {
		board = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int n = 0; n < 8; n++) board[i][n] = 0;
		}
		//starting positions
		board[3][3] = 1;
		board[4][4] = 1;
		board[3][4] = 2;
		board[4][3] = 2;
		numMoves = 0;
		whiteTokens = blackTokens = 2;
	}
	
	private int getValue(Move move) {
		return board[move.x][move.y];
	}
	
	/**
	 * Returns the status of the game.
	 * @return 0 if undecided, 1 if white wins, 2 if black wins, 3 for a tie.
	 */
	public int gameStatus() {
		if (!getValidMoves(1).isEmpty() || !getValidMoves(2).isEmpty())
			return 0;
		else if (whiteTokens > blackTokens) return 1;
		else if (whiteTokens < blackTokens) return 2;
		else return 3;
	}
	
	/**
	 * Tests if a player can make a provided move.
	 * @param player 1 for white, 2 for black.
	 * @param move The position where player moves.
	 * @return true if the move can be made else false.
	 */
	public boolean isValidMove(int player, Move move) {
		if (getValue(move) != 0) return false;
		int opponent = (player == 1) ? 2 : 1;
		for(int[] direction : directions) {
			int x = move.x + direction[0];
			int y = move.y + direction[1];
			boolean turned = false;
			while (x < 8 && x >= 0 && y < 8 && y >= 0) {
				if (board[x][y] == 0)
					break;
				else if (board[x][y] == opponent)
					turned = true;
				else if(board[x][y] == player && !turned)
					break;
				else return true;
				x += direction[0]; y += direction[1];
			}
		}
		return false;
	}
	
	/**
	 * Gets all valid moves for a player.
	 * @param player 1 for white, 2 for black.
	 * @return An ArrayList containing the moves.
	 */
	public ArrayList<Move> getValidMoves(int player) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Move move = new Move(x,y);
				if (isValidMove(player, move)) moves.add(move);
			}
		}
		return moves;
	}
	
	/**
	 * Makes a move.
	 * @param player 1 for white, 2 for black.
	 * @param move The position to move to.
	 */
	public void makeMove(int player, Move move) {
		if (player != 1 && player != 2) 
			throw new IllegalArgumentException("Player has to be 1 or 2; not " + player);
		if (!isValidMove(player, move))
			throw new IllegalArgumentException("Move (" + move.x + "," + move.y + ") is not valid!");

		board[move.x][move.y] = player;
		if (player == 1)  whiteTokens++; else blackTokens++;
		numMoves++;
		int opponent = player == 1 ? 2 : 1;
		
		
		for (int[] direction : directions) {
			ArrayList<Move> turns = new ArrayList<Move>();
			int x = move.x + direction[0];
			int y = move.y + direction[1];
			while(x < 8 && x >= 0 && y < 8 && y >= 0) {
				if (board[x][y] == opponent)
					turns.add(new Move(x,y));
				else if (board[x][y] == player && !turns.isEmpty())
					turn(player, turns);
				else break;
				x += direction[0]; y += direction[1];
			}
		}
	}

	private void turn(int player, ArrayList<Move> turns) {
		for (Move turn : turns) {
			if (board[turn.x][turn.y]!= player) {
				board[turn.x][turn.y] = player;
				if (player == 1) whiteTokens++; else whiteTokens--;
				if (player == 1) blackTokens--; else blackTokens++;
			}
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < 8; i++) {
			for (int n = 0; n < 8; n++) {
				s += board[n][i] + " ";
			}
			s += "\n";
		}
		return s;
	}
}
