package ai;

import java.util.ArrayList;
import java.util.Random;

import othello.Othello;
import szte.mi.*;

public class PositionalAI implements Player{
	private static final byte[][] SQUARE_VALUE = {
		{ 18,  4,  16, 12, 12, 16,  4, 18 },
		{ 4,  2,   6,  8,  8,  6,  2,  4 },
		{ 16,  6,  14, 10, 10, 14,  6, 16 },
		{ 12,  8,  10,  0,  0, 10,  8, 12 },
		{ 12,  8,  10,  0,  0, 10,  8, 12 },
		{ 16,  6,  14, 10, 10, 14,  6, 16 },
		{ 4,  2,   6,  8,  8,  6,  2,  4 },
		{ 18,  4,  16, 12, 12, 16,  4, 18 }
		};
	private double SQUARE_MULT = 1.0;
	private double STABLE_MULT = 1.0;
	private double MOBILE_MULT = 1.0;
	
	
	
	private Othello o;
	private boolean isWhite;
	private int me;
	private int opp;
	
	public void init(int order, long t, Random rnd) {
		o = new Othello();
		isWhite = order == 1;
		me = isWhite ? 1 : 2;
		opp = isWhite ? 2 : 1;
	}
	
	public Move nextMove(Move prevMove, long topponent, long t) {
		if (prevMove != null) {
			o.makeMove(opp, prevMove);
		}
		
		ArrayList<Move> moves = o.getValidMoves(me);
		if (moves.isEmpty()) return null;
		
		Move bestMove = null;
		double bestScore = -10000;
		for (Move move : moves) {
			 double score = calculateScore(o.simulateMove(me, move));
			 if (score > bestScore) {
				 bestMove = move;
				 bestScore = score;
			 }
		}
		o.makeMove(me, bestMove);
		return bestMove;
	}
	
	private double calculateScore(Othello sim) {
		int[][] board = sim.getBoard();
		double squareValue = squareValue(board);
		double stableValue = ((double) (sim.countStableTokens(me) - sim.countStableTokens(opp))) / 64.0;
		double mobilityValue = ((double) (sim.getValidMoves(me).size() - sim.getValidMoves(opp).size())) / 15.0;
		return (squareValue * SQUARE_MULT) + (stableValue * STABLE_MULT) + (mobilityValue * MOBILE_MULT);
		
	}
	
	
	private double squareValue(int[][] board) {
		int oppScore = 0;
		int myScore = 0;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y] == me)
					myScore += SQUARE_VALUE[x][y];
				
				else if (board[x][y] == opp)
					oppScore += SQUARE_VALUE[x][y];
			}
		}
		
		return ((double) (myScore - oppScore)) / 584.0;
	}

	public double getSQUARE_MULT() {
		return SQUARE_MULT;
	}

	public void setSQUARE_MULT(double SQUARE_MULT) {
		this.SQUARE_MULT = SQUARE_MULT;
	}

	public double getSTABLE_MULT() {
		return STABLE_MULT;
	}

	public void setSTABLE_MULT(double STABLE_MULT) {
		this.STABLE_MULT = STABLE_MULT;
	}

	public double getMOBILE_MULT() {
		return MOBILE_MULT;
	}

	public void setMOBILE_MULT(double MOBILE_MULT) {
		this.MOBILE_MULT = MOBILE_MULT;
	}
	
	
}
