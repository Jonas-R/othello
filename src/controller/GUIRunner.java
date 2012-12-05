package controller;

import othello.Othello;
import gui.GUIThread;
import ai.*;
import szte.mi.*;


public class GUIRunner {
	private Othello othello;
	private GUIThread gui;
	private NegamaxABTab ai;
	
	private boolean humanBlack = false;
	
	public GUIRunner() {
		gui = new GUIThread();
		ai = new NegamaxABTab();
		startGame();
	}
	
	public void startGame() {
		othello = new Othello();
		humanBlack = !humanBlack;
		ai.init(humanBlack ? 1 : 0, 1000, new java.util.Random());
		gui.start();
		gui.scheduleReset(humanBlack ? 0 : 1);
		while(!gui.isFinished()) {
			try { Thread.sleep(100); }
			catch (InterruptedException ex) {
				System.err.println(ex);
			}
		}
		
		boolean humanTurn = humanBlack;
		Move prevMove = null;
		
		while (othello.gameStatus() == 0) {
			if (humanTurn) {
				gui.scheduleMakeMove(prevMove);
				while (gui.getNextMove() == null && !gui.cannotMove()) {
					try { Thread.sleep(100); }
					catch (InterruptedException ex) {
						System.err.println(ex);
					}
				}
				if (!gui.cannotMove()) { othello.makeMove((byte) (humanBlack ? 2 : 1), gui.getNextMove()); }
				prevMove = gui.getNextMove();
				humanTurn = false;
			}
			else {
				prevMove = ai.nextMove(prevMove, 1000, 1000);
				othello.makeMove((byte) (humanBlack ? 1 : 2), prevMove);
				humanTurn = true;
			}
		}
		
		gui.scheduleGameOver(othello.gameStatus());
		while (!gui.startNewGame()) {
			try { Thread.sleep(100); }
			catch (InterruptedException ex) {
				System.err.println(ex);
			}
		}
		startGame();
	}
	public static void main(String[] args) {
		new GUIRunner();
	}
}