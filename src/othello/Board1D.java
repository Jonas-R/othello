package othello;

import java.util.ArrayList;

import szte.mi.Move;

public class Board1D implements Board{
	private int[] board;
	private final int[][] directions = { {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1} };
	
	public Board1D() {
		this.board = new int[64];
		
		for (int i = 0; i < 64; i++)
			board[i] = 0;
	}
	
	public Board1D(int[] board) { this.board = board; }
	
	public int getPosition(int x, int y) { return board[(x * 8) + y]; }
	public int getPosition(Move move) { return board[(move.x * 8) + move.y]; }
	
	public void setPosition(int x, int y, int player) { board[(x * 8) + y] = player; }
	public void setPosition(Move move, int player) { board[(move.x * 8) + move.y] = player; }
	
	public int countTokens(int player) {
		int count = 0;
		for (int i = 0; i < 64; i++) {
				if (board[i] == player) count++;
		}
		return count;
	}
	
	public int countStableTokens(int player) {
		int[][] corners = { {0,0}, {0,7}, {7,0}, {7,7} };
		int count = 0;
		
		for (int[] corner : corners) {
			int x = corner[0]; int y = corner[1];
			if (this.getPosition(x, y) != player) break;
			
			count++;
			
			for (int[] direction : directions) {
				x += direction[0]; y += direction[1];
				while (x < 8 && y < 8 && x >= 0 && y >= 0) {
					if (this.getPosition(x, y) == player) count += 1;
					else break;
					x += direction[0]; y += direction[1];
				}
			}
		}
		
		return count;
	}
	
	public boolean isValidMove(int player, int n) {
		return isValidMove(player, n % 8, n / 8);
	}
	
	public boolean isValidMove(int player, int x, int y) {
		if (getPosition(x, y) != 0) return false;

		int me = player;
		int opp = (player == 1 ? 2 : 1);
		boolean turned;
		int i, j;

		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if ((dx != 0) || (dy != 0)) {
					turned = false;
					i = x + dx;
					j = y + dy;

					while ((i >= 0) && (i < 8) && (j >= 0) && (j < 8) && (getPosition(i, j) == opp)) {
						turned = true;
						i = i + dx;
						j = j + dy;
					}
					if (turned && (i >= 0) && (i < 8) && (j >= 0) && (j < 8) && (getPosition(i, j) == me)) return true; 
				}
			}
		}
		return false;
	}
	
	public ArrayList<Move> getValidMoves(int player) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (isValidMove(player, x, y)) moves.add(new Move(x,y));
			}
		}
		return moves;
	}
	
	public boolean hasValidMove(int player) {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (isValidMove(player, x, y)) return true;
			}
		}
		return false;
	}
	
	public Board copyBoard() {
		int[] new_board = new int[64];
		for (int i = 0; i < 64; i++) {
				new_board[i] = board[i];
		}
		return new Board1D(new_board);
	}
}
