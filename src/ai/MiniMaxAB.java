package ai;

import java.util.Random;
import java.util.ArrayList;

import othello.Othello;
import szte.mi.*;

public class MiniMaxAB implements Player {
	private Othello o;
	private int me;
	private int opp;
	public int nodes_searched = 0;
	public int cuts = 0;
	private final int MAX_DEPTH = 4;
	private Evaluation heuristic = new Evaluation();
	
	public void init(int order, long t, Random rnd) {
		o = new Othello();
		me = order == 1 ? 1 : 2;
		opp = me == 1 ? 2 : 1;
	}
	
	public Move nextMove(Move prevMove, long topponent, long t) {
		o.makeMove(opp, prevMove);
		
		int bestMove = -1;
		double bestScore = Double.NEGATIVE_INFINITY;
		
		ArrayList<Integer> moves = o.getValidMoves(me);
		if (moves.isEmpty()) {
			o.makeMove(me, -1);
			return null;
		}
		
		for (int move : moves) {
			double score = minValue(o.simulateMove(me, move), MAX_DEPTH, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (score > bestScore) {
				bestMove = move;
				bestScore = score;
			}
		}
		
		o.makeMove(me, bestMove);
		return new Move(bestMove % 8, bestMove / 8); 
	}
	
	public double maxValue(Othello state, int depth, double alpha, double beta) {
		
		if (depth <= 0) return heuristic.calculateScore(state, me);
		
		ArrayList<Integer> moves = state.getValidMoves(me);
		if (moves.isEmpty()) {
			int res = state.gameStatus();
			if (res != 0) {
				return heuristic.calculateEndScore(state, me, res);
			}
			return minValue(state.simulateMove(me, -1), depth, alpha, beta);
		}
		
		double score = Double.NEGATIVE_INFINITY;
		for(int move : moves) {
			nodes_searched++;
			score = Math.max(score, minValue(state.simulateMove(me, move), depth - 1, alpha, beta));

			if (score >= beta) {
				cuts++;
				return score;
			}
			alpha = Math.max(alpha, score);
		}
		return score;
	}
	
	public double minValue(Othello state, int depth, double alpha, double beta) {
		if (depth <= 0) return heuristic.calculateScore(state, me);
		
		
		ArrayList<Integer> moves = state.getValidMoves(opp);
		if (moves.isEmpty()) {
			int res = state.gameStatus();
			if (res != 0) {
				return heuristic.calculateEndScore(state, opp, res);
			}
			return maxValue(state.simulateMove(opp, -1), depth, alpha, beta);
		}
		
		double score = Double.POSITIVE_INFINITY;
		for (int move : moves) {
			nodes_searched++;
			score = Math.min(score, maxValue(state.simulateMove(opp, move), depth - 1, alpha, beta));

			if (score <= alpha) {
				cuts++;
				return score;
			}
			beta = Math.min(beta, score);
		}
		return score;
	}
	
	public void setHeuristic(Evaluation heuristic) {
		this.heuristic = heuristic;
	}
	
	public Evaluation getHeuristic() {
		return this.heuristic;
	}

}
