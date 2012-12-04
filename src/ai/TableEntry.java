package ai;

import othello.BitBoard;

public class TableEntry {
	public BitBoard board;
	public int bestMove;
	
	public TableEntry(BitBoard board, int bestMove) {
		this.board = board;
		this.bestMove = bestMove;
	}
}
