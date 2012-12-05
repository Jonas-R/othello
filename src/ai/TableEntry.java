package ai;

import othello.BitBoard;

public class TableEntry {
	public static final byte EXACT = 0;
	public static final byte ALPHA = 1;
	public static final byte BETA = 2;
	public BitBoard board;
	//public int bestMove;
	public int depth;
	public double score;
	public byte flag;
	
	public TableEntry(BitBoard board, int depth, double score, byte flag) {
		this.board = board;
		//this.bestMove = bestMove;
		this.depth = depth;		
		this.score = score;
	}
}
