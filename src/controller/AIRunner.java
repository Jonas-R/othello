package controller;

import java.util.Random;

import szte.mi.*;
import ai.*;
import othello.Othello;

public class AIRunner {	
	private Random rnd;
	
	public double[] optimize() {
		Random rnd = new Random();
		MiniMaxAI p1 = new MiniMaxAI();
		MiniMaxAI p2 = new MiniMaxAI();
		
		int count = 0; 
		while(count < 5) {
			
			p2.setHeuristic(new Heuristic(rnd.nextDouble() * 100.0, rnd.nextDouble() * 100.0, rnd.nextDouble() * 100.0));
			int res = simulate(p1,p2,2);
			
			if (res == 2) {
				System.out.println(p2.getHeuristic().getMOBILE_MULT() + " " + p2.getHeuristic().getSQUARE_MULT() + " " + p2.getHeuristic().getSTABLE_MULT());
				p1.setHeuristic(new Heuristic(
						p2.getHeuristic().getMOBILE_MULT(), p2.getHeuristic().getSQUARE_MULT(),
						p2.getHeuristic().getSTABLE_MULT()
				));
				count = 0;
			}
			else count++;
			if (res == 1) {
				System.out.println("lost");
			}
		}
		
		double[] optimal = { p1.getHeuristic().getMOBILE_MULT(),
							 p1.getHeuristic().getSQUARE_MULT(),
							 p1.getHeuristic().getSTABLE_MULT() };
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
		//AIRunner run = new AIRunner();
		//double[] res = run.optimize();
		//for(double i : res) System.out.println(i);
		
		AIRunner run = new AIRunner();
		System.out.println(run.playN(new MiniMaxAI(), new GreedyAI(), 1));
	}
}
