package ai;

import othello.Othello;
import othello.BitBoard;

public class Evalutation{
	private static final int[] corners = { 0, 7, 56, 63 };
	private static final int[] xsquares = { 1, 6, 8, 9, 13, 14, 48, 49, 54, 55, 57, 62 };
	private static final double[][] weights = {
//      pieces, mobil, potmob, corn, xdiff
		{ -0.25, 0.30,  0.50, 2.00, -5.00 },
		{ -0.25, 0.35,  0.50, 3.00, -4.50 },
		{ -0.35, 0.35,  0.60, 4.00, -4.00 },
		{ -0.50, 0.45,  0.65, 5.00, -3.75 },
		{ -0.35, 0.50,  0.60, 4.00, -3.50 },
		{ -0.25, 1.00,  0.55, 3.50, -3.25 },
		{ -0.20, 1.25,  0.40, 3.00, -3.00 },
		{ -0.10, 1.50,  0.30, 2.50, -2.75 },
		{  0.00, 2.00,  0.20, 2.00, -2.50 },
		{  0.10, 2.25,  0.10, 1.50, -2.25 },
		{  0.15, 2.50,  0.00, 1.25, -2.00 },
		{  0.25, 3.00, -0.10, 1.00, -1.50 },
		{  0.35, 3.50, -0.20, 0.75, -1.25 },
		{  0.45, 4.00, -0.30, 0.50, -0.75 },
		{  0.50, 5.00, -0.40, 0.50, -0.25 },
		{  0.50, 5.00, -0.40, 0.50, -0.25 },
	};
	public Evalutation() { }

	public double calculateScore(Othello state, int player) {
		
		int opp = player == 1 ? 2 : 1;
		int pieceDiff;
		
		if (player == 1) {
			if (state.getWhiteTokens() == 0) return -10000.0;
			pieceDiff = state.getWhiteTokens() - state.getBlackTokens();
		}
		else {
			if (state.getBlackTokens() == 0) return -10000.0;
			pieceDiff = state.getBlackTokens() - state.getWhiteTokens();
		}
		
		int[] mobility = state.getBoard().getMobilityDiffs(player);
		int potMobilityDiff = mobility[0];
		int mobilityDiff = mobility[1];
		
		int cornerDiff = getCornerDiff(state, player, opp); 
		int xDiff = getXSquaresDiff(state, player, opp);
		double[] curWeights = weights[state.getNumMoves() / 4];
		return (curWeights[0] * (double) pieceDiff) + (curWeights[1] * (double) mobilityDiff) + (curWeights[2] * (double) potMobilityDiff) +
			   (curWeights[3] * (double) cornerDiff) + (curWeights[4] * (double) xDiff);
	}
	
	public double calculateEndScore(Othello state, int player, int res) {
		if (res == player) return Double.MAX_VALUE;
		if (res == 3) return 0;
		else return -Double.MAX_VALUE;
	}
	
	private int getCornerDiff(Othello state, int player, int opp) {
		int diff = 0;
		int val;
		for (int n : corners) {
			val = state.getPosition(n);
			if (val == player) diff += 1;
			else if (val == opp) diff -= 1;
		}
		
		return diff;
	}
	
	private int getXSquaresDiff(Othello state, int player, int opp) {
		int diff = 0;
		int val;
		for (int n : xsquares) {
			val = state.getPosition(n);
			if (val == player) diff += 1;
			else if (val == opp) diff -= 1;
		}
		
		return diff;
	}
}
