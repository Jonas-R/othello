package ai;

import java.util.Random;
import java.util.ArrayList;

import othello.Othello;
import szte.mi.*;

public class MiniMaxAI implements Player {
	private Othello o;
	private int me;
	private int opp;
	
	private TranspositionTable table;
	
	private final int MAX_DEPTH = 12;
	private Evalutation heuristic = new Evalutation();
	
	public void init(int order, long t, Random rnd) {
		table = new TranspositionTable(2^18, 4, rnd);
		o = new Othello();
		me = order == 1 ? 1 : 2;
		opp = me == 1 ? 2 : 1;
	}
	
	public Move nextMove(Move prevMove, long topponent, long t) {
		o.makeMove(opp, prevMove);
		/*int saved = table.retrieve(o.getBoard());
		if (saved != -1) {
			System.out.println(saved);
			o.makeMove(me, saved);
			return new Move(saved % 8, saved / 8);
		}
		System.out.println("Nope");*/
		
		
		int bestMove = -1;
		double bestScore = Double.NEGATIVE_INFINITY;
		
		ArrayList<Integer> moves = o.getValidMoves(me);
		if (moves.isEmpty()) {
			o.makeMove(me, -1);
			return null;
		}
		
		for (int move : moves) {
			double score = minValue(o.simulateMove(me, move), 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (score > bestScore) {
				bestMove = move;
				bestScore = score;
			}
		}
		
		/*table.insert(new TableEntry(o.getBoard(), bestMove));*/
		o.makeMove(me, bestMove);
		return new Move(bestMove % 8, bestMove / 8); 
	}
	
	public double maxValue(Othello state, int depth, double alpha, double beta) {
		if (depth > MAX_DEPTH) return heuristic.calculateScore(state, me);
		
		double score = Double.NEGATIVE_INFINITY;
		
		ArrayList<Integer> moves = state.getValidMoves(me);
		if (moves.isEmpty()) {
			int res = state.gameStatus();
			if (res != 0) {
				if (res == me) return Double.MAX_VALUE;
				else if (res == opp) return -Double.MAX_VALUE;
				else return -Double.MAX_VALUE; //Is this sensible?
			}
			return minValue(state.simulateMove(me, -1), depth, alpha, beta);
		}
		
		for(int move : moves) {
			score = Math.max(score, minValue(state.simulateMove(me, move), ++depth, alpha, beta));
			
			if (score >= beta)
				return score;
			alpha = Math.max(alpha, score);
		}
		return score;
	}
	
	public double minValue(Othello state, int depth, double alpha, double beta) {		
		if (depth > MAX_DEPTH) return heuristic.calculateScore(state, me);
		
		double score = Double.POSITIVE_INFINITY;
		
		ArrayList<Integer> moves = state.getValidMoves(opp);
		if (moves.isEmpty()) {
			int res = state.gameStatus();
			if (res != 0) {
				if (res == me) return Double.MAX_VALUE;
				else if (res == opp) return -Double.MAX_VALUE;
				else return -Double.MAX_VALUE; //Is this sensible?
			}
			return maxValue(state.simulateMove(opp, -1), depth, alpha, beta);
		}
		
		for (int move : moves) {
			score = Math.min(score, maxValue(state.simulateMove(opp, move), ++depth, alpha, beta));
			if (score <= alpha) 
				return score;
			beta = Math.min(beta, score);
		}
		return score;
	}
	
	public void setHeuristic(Evalutation heuristic) {
		this.heuristic = heuristic;
	}
	
	public Evalutation getHeuristic() {
		return this.heuristic;
	}

}
