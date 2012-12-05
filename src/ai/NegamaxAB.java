package ai;

import java.util.Random;
import java.util.ArrayList;

import othello.Othello;
import szte.mi.*;

public class NegamaxAB implements Player {
	private Othello o;
	private int me;
	private int opp;
	public int nodes_searched = 0;
	public int cuts = 0;
	
	private final int MAX_DEPTH = 5;
	private Evalutation heuristic = new Evalutation();
	
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
			double score = -negamax(o.simulateMove(me, move), MAX_DEPTH, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, -1);
			if (score > bestScore) {
				bestMove = move;
				bestScore = score;
			}
		}
		
		o.makeMove(me, bestMove);
		System.out.println(bestMove % 8 + " " + bestMove / 8);
		return new Move(bestMove % 8, bestMove / 8); 
	}
	
	public double negamax(Othello state, int depth, double alpha, double beta, int player) {
		if (depth <= 0) {
			return player * heuristic.calculateScore(state, me);
		}
		
		ArrayList<Integer> moves = state.getValidMoves(player == 1 ? me : opp);
		if (moves.isEmpty()) {
			int res = state.gameStatus();
			if (res != 0) {
				return player * heuristic.calculateEndScore(state, me, res);
			}
			return negamax(state.simulateMove(player == 1 ? me : opp, -1), depth, -beta, -alpha, -player);
		}
		
		double score;
		for (int move : moves) {
			nodes_searched++;
			score = -negamax(state.simulateMove(player == 1 ? me : opp, move), depth - 1, -beta, -alpha, -player);
			if (score >= beta) {
				cuts++;
				return score;
			}
			if (score >= alpha)
				alpha = score;
		}
		return alpha;
	}

}
