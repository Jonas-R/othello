package ai;

import java.util.ArrayList;
import java.util.Random;
import othello.*;
import szte.mi.*;

public class RandomAI implements Player{
	private Random rnd;
	private Othello o;
	private int me;
	private int opp;
	
	public void init(int order, long t, Random rnd) {
		o = new Othello();
		this.rnd = rnd;
		if (order == 0) {
			me = 2; opp = 1;
		} else {
			me = 1; opp = 2;
		}
	}
	
	public Move nextMove(Move prevMove, long topponent, long t) {
		if (prevMove != null) {
			o.makeMove(opp, prevMove);
		}
		ArrayList<Move> moves = o.getValidMoves(me);
		if (moves.isEmpty()) return null;
		
		Move move = moves.get(rnd.nextInt(moves.size()));
		o.makeMove(me, move);
		return move;
	}
	
}
