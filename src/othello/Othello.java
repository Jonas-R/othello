package othello;
import java.util.ArrayList;

public class Othello {
	//Number of moves made. == number of tokens - 4
	private int numMoves;
	
	//0: not occupied 1: white 2: black
	private int[][] board;
	
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
	
	public int getValue(Move move) {
		return board[move.x][move.y];
	}
	
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
