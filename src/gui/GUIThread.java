package gui;

import szte.mi.Move;

public class GUIThread extends Thread {
	private OthelloGUI gui;
	
	private Move prevMove;
	private Move nextMove;
	private boolean cannotMove;
	
	private int result;
	private int order;
	private boolean startNewGame;
	
	// 1 for move, 2 for reset, 3 for game over
	private int nextAction;
	
	public GUIThread() {
		gui = new OthelloGUI();
		gui.showGUI();
		nextMove = null;
		prevMove = null;
		nextAction = 0;
		startNewGame = false;
	}
	
	public void scheduleMakeMove(Move prevMove) {
		nextAction = 1;
		this.prevMove = prevMove;
		nextMove = null;
		cannotMove = false;
	}
	
	private void makeMove() { 
		nextMove = gui.makeMove(prevMove);
		if (nextMove == null)
			cannotMove = true;
		}
	
	public void scheduleReset(int order) { 
		this.order = order;
		startNewGame = false;
		nextAction = 2;
	}
	
	private void reset() { 
		gui.init(order);
	}
	
	public boolean isFinished() { return nextAction == 0; }
	
	public void scheduleGameOver(int result) {
		this.result = result;
		nextAction = 3;
	}
	
	public void gameOver() {
		gui.gameOver(result);
		while (!gui.startNewGame()) {
			try { sleep(100); }
			catch (InterruptedException ex) {
				System.err.println(ex);
			}
		}
		startNewGame = true;
		
	}
	
	public void run() {
		while (true) {
			if (nextAction == 1) {
				makeMove();
				nextAction = 0;
			}
			else if (nextAction == 2) {
				reset();
				nextAction = 0;
			}
			else if (nextAction == 3) {
				gameOver();
				nextAction = 0;
			}
			try { sleep(100); }
			catch (InterruptedException ex) {
				System.err.println(ex);
			}
		}
	}

	public Move getNextMove() {
		return nextMove;
	}
	
	public boolean cannotMove() {
		return cannotMove;
	}
	
	public boolean startNewGame() {
		return startNewGame;
	}
	
	
}
