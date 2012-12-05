package controller;

import java.util.Random;

import szte.mi.*;
import ai.*;
import othello.Othello;

public class AIRunner {	
	
	public int simulate(Player p1, Player p2, int n) {
		int score = 0;
		score += playN(p1, p2, n/2);
		score -= playN(p2, p1, n/2);
		if (score > 0) return 1;
		else return 2;
	}
	
	public int playN(Player white, Player black, int n) {
		long startTime = System.nanoTime();
		int scoreW = 0; int scoreB = 0;
		
		for (int i = 0; i < n; i++) {
			Othello o = new Othello();
			white.init(1, 9000000000L, new Random());
			black.init(0, 9000000000L, new Random());
			boolean blackMove = true;
			Move prevMove = null;
			
			while (o.gameStatus() == 0) {
				Move move = blackMove ? black.nextMove(prevMove, 90000000L, 90000000L) : white.nextMove(prevMove, 90000000L, 90000000L);
				if (move != null)
					o.makeMove((blackMove ? 2 : 1), move);
				prevMove = move;
				blackMove = !blackMove;
			}
			if (o.gameStatus() == 1) scoreW++;
			else if (o.gameStatus() == 2) scoreB++;
			System.out.println(o);
		}
		long endTime = System.nanoTime();
		System.out.println(((double) (endTime - startTime)) / (Math.pow(10.0, 9.0)));
		return scoreW - scoreB;
	}
	
	public static void main(String[] args) {		
		AIRunner run = new AIRunner();
		NegamaxABTab white = new NegamaxABTab();
		NegamaxABTab black = new NegamaxABTab();
		System.out.println(run.simulate(white, black, 10));
	}
}
