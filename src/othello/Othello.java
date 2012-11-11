package othello;
import java.util.ArrayList;

public class Othello {
	//Number of moves made. == number of tokens - 4
	private int num_moves;
	
	//0: not occupied 1: white 2: black
	private int[][] board;
	
	private int white_tokens;
	private int black_tokens;
	
	private final int[][] directions = { {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1} };
	
	public int getWhite_tokens() {
		return white_tokens;
	}

	public int getBlack_tokens() {
		return black_tokens;
	}
	
	public int getNumMoves() {
		return num_moves;
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
		num_moves = 0;
		white_tokens = black_tokens = 2;
	}
	
	public int getValue(Move move) {
		return board[move.x][move.y];
	}
	
	public boolean isValidMove(int player, Move move) {
		if (getValue(move) != 0) return false;
		int opponent = (player == 1) ? 2 : 1;
		for(int[] direction : directions) {
			int x = move.x; int y = move.y;
			x += direction[0]; y += direction[1];
			if (x >= 8 || x < 0 || y >= 8 || y < 0 || 
					board[x][y] != opponent) continue;
			x += direction[0]; y += direction[1];
			while (x < 8 && x >= 0 && y < 8 && y >= 0) {
				if (board[x][y] == 0) break;
				else if (board[x][y] == player) return true;
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
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < 8; i++) {
			for (int n = 0; n < 8; n++) {
				s += board[i][n] + " ";
			}
			s += "\n";
		}
		return s;
	}
}
