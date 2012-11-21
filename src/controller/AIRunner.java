package controller;

import java.util.Random;

import szte.mi.*;
import ai.PositionalAI;
import othello.Othello;

public class AIRunner {	
	private Random rnd;
	
	public double[] optimize() {
		Random rnd = new Random();
		PositionalAI p1 = new PositionalAI();
		PositionalAI p2 = new PositionalAI();
		
		p1.setMOBILE_MULT(27.20356571142153);
		p1.setSQUARE_MULT(72.4803353242168);
		p1.setSTABLE_MULT(93.9212265071403);
		
		for (int i = 0; i < 10000; i++) {
			p2.setMOBILE_MULT(rnd.nextDouble() * 100.0);
			p2.setSQUARE_MULT(rnd.nextDouble() * 100.0);
			p2.setSTABLE_MULT(rnd.nextDouble() * 100.0);
			int res = simulate(p1,p2,50);
			
			if (res == 2) {
				p1 = p2;
				p2 = new PositionalAI();
				System.out.println(p1.getMOBILE_MULT() + " " + p1.getSTABLE_MULT() + " " + p1.getSQUARE_MULT());
			}
		}
		
		double[] optimal = { p1.getMOBILE_MULT(), p1.getSTABLE_MULT(), p1.getSQUARE_MULT() };
		return optimal;
	}
	
	public int simulate(Player p1, Player p2, int n) {
		int score = 0;
		score += playN(p1, p2, n/2);
		score -= playN(p2, p1, n/2);
		if (score > 0) return 1;
		else return 2;
	}
	
	public int playN(Player white, Player black, int n) {
		int scoreW = 0; int scoreB = 0;
		
		for (int i = 0; i < n; i++) {
			Othello o = new Othello();
			white.init(1, 9000000000L, rnd);
			black.init(0, 9000000000L, rnd);
			boolean blackMove = true;
			Move prevMove = null;
			
			while (o.gameStatus() == 0) {
				Move move = blackMove ? black.nextMove(prevMove, 90000000L, 90000000L) : white.nextMove(prevMove, 90000000L, 90000000L);
				if (move != null)
					o.makeMove(blackMove ? 2 : 1, move);
				prevMove = move;
				blackMove = !blackMove;
			}
			if (o.gameStatus() == 1) scoreW++;
			else if (o.gameStatus() == 2) scoreB++;
		}
		return scoreW - scoreB;
	}
	
	public static void main(String[] args) {
		AIRunner run = new AIRunner();
		double[] res = run.optimize();
		for(double i : res) System.out.println(i);
	}
}
