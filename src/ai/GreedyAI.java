package ai;

import java.util.ArrayList;
import java.util.Random;

import othello.Othello;
import szte.mi.*;

public class GreedyAI implements Player {
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
		int bestScore = -65;
		for (Move move : moves) {
			Othello sim = new Othello(o.getBoard()).simulateMove(me, move);	
			int score = isWhite ? sim.getWhiteTokens() - sim.getBlackTokens() : 
				sim.getBlackTokens() - sim.getWhiteTokens();
			if (score > bestScore) {
				bestScore = score;
				bestMove = move;
			}
		}
		o.makeMove(me, bestMove);
		return bestMove;
	}
	
}
