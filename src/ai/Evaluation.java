package ai;

import othello.Othello;
import othello.BitBoard;

public class Evaluation{
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
		{  0.50, 5.00, -0.40, 0.50, -0.25 }
	};
	
	private static final double[][] new_weights = {
		{ -0.292004,  0.897710, -0.117397,  0.000000,  0.000000 },
		{  0.197536,  0.103138,  0.185013,  0.000000,  0.455419 },
	    { -0.061512, -0.016005,  0.017398,  0.000000,  0.368442 },
		{ -0.085101,  0.011403, -0.034181,  0.000000, -0.043484 },
	    { -0.047108, -0.045986,  0.012786,  6.156152, -0.201902 },
	    {  0.024639, -0.021743,  0.059385, -2.543458, -0.040722 },
		{  0.017816,  0.025525,  0.031669,  0.094433, -0.012093 },
	    { -0.013623,  0.023953,  0.028343,  0.338930,  0.031060 },
	    { -0.008966,  0.031218,  0.036539,  0.126614,  0.061794 },
		{  0.036206,  0.050685,  0.085759,  0.235211,  0.000912 },
		{  0.068191,  0.047548,  0.110094,  0.341888, -0.049438 },
		{  0.079445,  0.022539,  0.133541,  0.458684, -0.040816 },
		{  0.096387,  0.005900,  0.174586,  0.390960,  0.034885 },
		{  0.123815, -0.018097,  0.250849,  0.328884,  0.156542 },
		{  0.190603, -0.054746,  0.309249,  0.206309,  0.243466 },
		{  0.920262,  0.040376, -0.076053,  0.055835, -0.056980 }
	};
	
	
	public Evaluation() { }

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
		double[] curWeights = new_weights[state.getNumMoves() / 4];
		return (curWeights[0] * (double) pieceDiff) + (curWeights[1] * (double) mobilityDiff) + (curWeights[2] * (double) potMobilityDiff) +
			   (curWeights[3] * (double) cornerDiff) + (curWeights[4] * (double) xDiff);
	}
	
	public double calculateEndScore(Othello state, int player, int res) {
		if (res == player) return Double.MAX_VALUE;
		if (res == 3) return 0;
		else return -Double.MAX_VALUE;
	}
	
	public int getCornerDiff(Othello state, int player, int opp) {
		int diff = 0;
		int val;
		for (int n : corners) {
			val = state.getPosition(n);
			if (val == player) diff += 1;
			else if (val == opp) diff -= 1;
		}
		
		return diff;
	}
	
	public int getXSquaresDiff(Othello state, int player, int opp) {
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
