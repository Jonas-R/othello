package ai;

import java.util.ArrayList;
import java.util.Random;

import othello.Othello;
import szte.mi.*;

public class Heuristic{
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
		
	private double MOBILE_MULT = 18.712309014023454;
	private double SQUARE_MULT = 60.618487461116466;
	private double STABLE_MULT = 35.74903416192723;	
	
	public Heuristic() { }
	
	public Heuristic(double mobile, double square, double stable) {
		this.MOBILE_MULT = mobile;
		this.SQUARE_MULT = square;
		this.STABLE_MULT = stable;
	}

	public double calculateScore(Othello sim, int player) {
/*		int res = sim.gameStatus();
		if (res != 0) {
			if (res == me) return Double.MAX_VALUE;
			else if (res == opp) return -Double.MAX_VALUE;
			else return 0;
		}
*/
		int opp = player == 1 ? 2 : 1;
		
		int[][] board = sim.getBoard();
		double squareValue = squareValue(board, player, opp);
		double stableValue = ((double) (sim.countStableTokens(player) - sim.countStableTokens(opp))) / 64.0;
		double mobilityValue = ((double) (sim.getValidMoves(player).size() - sim.getValidMoves(opp).size())) / 15.0;
		return (squareValue * SQUARE_MULT) + (stableValue * STABLE_MULT) + (mobilityValue * MOBILE_MULT);
		
	}
	
	
	private double squareValue(int[][] board, int player, int opp) {
		int oppScore = 0;
		int myScore = 0;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y] == player)
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