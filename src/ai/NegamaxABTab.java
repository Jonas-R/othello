package ai;

import java.util.Random;
import java.util.ArrayList;

import othello.Othello;
import szte.mi.*;

public class NegamaxABTab implements Player {
	private Othello o;
	private int me;
	private int opp;
	private TranspositionTable table = null;
	
	private final int MAX_DEPTH = 5;
	private Evalutation heuristic = new Evalutation();
	
	public void init(int order, long t, Random rnd) {
		if (table == null) {
			table = new TranspositionTable(1 << 19, 4, rnd);
		}
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
		return new Move(bestMove % 8, bestMove / 8); 
	}
	
	public double negamax(Othello state, int depth, double alpha, double beta, int player) {
		byte flag = TableEntry.ALPHA;
		double saved = table.probeTable(state.getBoard(), depth, alpha, beta);
		if (!Double.isNaN(saved)) {
			return saved;
		}
					
		if (depth <= 0) {
			saved = player * heuristic.calculateScore(state, me);
			table.insert(new TableEntry(state.getBoard().copyBoard(), depth, saved, TableEntry.EXACT));
			return saved;
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
			score = -negamax(state.simulateMove(player == 1 ? me : opp, move), depth - 1, -beta, -alpha, -player);
			if (score >= beta) {
				table.insert(new TableEntry(state.getBoard().copyBoard(), depth, beta, TableEntry.BETA));
				return score;
			}
			if (score > alpha) {
				flag = TableEntry.EXACT;
				alpha = score;
			}
		}
		table.insert(new TableEntry(state.getBoard().copyBoard(), depth, alpha,  flag));
		return alpha;
	}
	
	public TranspositionTable getTable() {
		return table;
	}

}
