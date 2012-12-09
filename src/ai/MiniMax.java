package ai;

import java.util.Random;
import java.util.ArrayList;

import othello.Othello;
import szte.mi.*;

public class MiniMax implements Player {
	private Othello o;
	private int me;
	private int opp;
	
	private final int MAX_DEPTH = 2;
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
			double score = minValue(o.simulateMove(me, move), 0);
			if (score > bestScore) {
				bestMove = move;
				bestScore = score;
			}
		}
		
		o.makeMove(me, bestMove);
		return new Move(bestMove % 8, bestMove / 8); 
	}
	
	public double maxValue(Othello state, int depth) {
		if (depth > MAX_DEPTH) return heuristic.calculateScore(state, me);
		
		ArrayList<Integer> moves = state.getValidMoves(me);
		if (moves.isEmpty()) {
			int res = state.gameStatus();
			if (res != 0) {
				if (res == me) return Double.MAX_VALUE;
				else if (res == opp) return -Double.MAX_VALUE;
				else return -Double.MAX_VALUE; //Is this sensible?
			}
			return minValue(state.simulateMove(me, -1), depth);
		}
		
		double score = Double.NEGATIVE_INFINITY;
		for(int move : moves) {
			score = Math.max(score, minValue(state.simulateMove(me, move), depth + 1));
		}
		return score;
	}
	
	public double minValue(Othello state, int depth) {
		if (depth > MAX_DEPTH) return heuristic.calculateScore(state, me);
		
		
		ArrayList<Integer> moves = state.getValidMoves(opp);
		if (moves.isEmpty()) {
			int res = state.gameStatus();
			if (res != 0) {
				if (res == me) return Double.MAX_VALUE;
				else if (res == opp) return -Double.MAX_VALUE;
				else return -Double.MAX_VALUE; //Is this sensible?
			}
			return maxValue(state.simulateMove(opp, -1), depth);
		}
		
		double score = Double.POSITIVE_INFINITY;
		for (int move : moves) {
			score = Math.min(score, maxValue(state.simulateMove(opp, move), depth + 1));
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
