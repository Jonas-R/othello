package ai;

import java.util.Random;

import othello.Othello;
import szte.mi.*;

public class MiniMaxAI implements Player {
	private Othello o;
	private boolean isWhite;
	private int me;
	private int opp;
	
	private final int MAX_DEPTH = 4;
	private Heuristic heuristic = new Heuristic();
	
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
		
		Move bestMove = null;
		double bestScore = Double.NEGATIVE_INFINITY;
		
		if (o.getValidMoves(me).isEmpty()) return null;
		
		for (Move move : o.getValidMoves(me)) {
			double score = minValue(o.simulateMove(me, move), 0);
			if (score > bestScore) {
				bestMove = move;
				bestScore = score;
			}
		}
		
		o.makeMove(me, bestMove);
		return bestMove;
	}
	
	public double maxValue(Othello state, int depth) {
		int res = state.gameStatus();
		if (res != 0) {
			if (res == me) return Double.MAX_VALUE;
			else if (res == opp) return -Double.MAX_VALUE;
			else return 0; //Is this sensible?
		}
		
		double score = Double.NEGATIVE_INFINITY;
		
		if (state.getValidMoves(me).isEmpty()) return minValue(state, depth);
		
		for (Move move : state.getValidMoves(me)) {
			if (depth > MAX_DEPTH) score = Math.max(score, heuristic.calculateScore(state, me));
			else score = Math.max(score, minValue(state.simulateMove(me, move), ++depth));
		}
		return score;
	}
	
	public double minValue(Othello state, int depth) {
		int res = state.gameStatus();
		if (res != 0) {
			if (res == me) return Double.MAX_VALUE;
			else if (res == opp) return -Double.MAX_VALUE;
			else return 0; //Is this sensible?
		}
		
		double score = Double.POSITIVE_INFINITY;
		
		if (state.getValidMoves(opp).isEmpty()) return maxValue(state, depth);
		
		for (Move move : state.getValidMoves(opp)) {
			if (depth > MAX_DEPTH) score = Math.min(score, heuristic.calculateScore(state, opp));
			else score = Math.min(score, maxValue(state.simulateMove(opp, move), ++depth));
		}
		return score;
	}
	
	public void setHeuristic(Heuristic heuristic) {
		this.heuristic = heuristic;
	}
	
	public Heuristic getHeuristic() {
		return this.heuristic;
	}

}
