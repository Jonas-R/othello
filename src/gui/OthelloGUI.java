package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import szte.mi.*;
import othello.Othello;





public class OthelloGUI {
	private MyButton[] buttons;
	private JLabel gameStatus;
	private JButton newGame;
	private JLabel standings;
	private JFrame frame;
	
	private int scoreYou = 0;
	private int scoreCom = 0;
	
	//true if human player is player1 (white)
	private boolean player1;
	private Othello othello = new Othello();
	private Move humanMove =  null;
	private boolean startNewGame = false;
	
	public void showGUI() {
		frame = new JFrame("Othello");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(700,700));
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		
		
		int buttonHeight = frame.getHeight() / 10;
		int buttonWidth = frame.getWidth() / 10;
		
		buttons = new MyButton[64];
		for (int i = 0; i < 64; i++) { 
			buttons[i] = new MyButton();
			buttons[i].setPreferredSize(new Dimension(buttonHeight, buttonWidth));
			buttons[i].addActionListener(new MyButtonHandler(i));
			buttons[i].setEnabled(false);
			constraints.weightx = constraints.weighty = 1.0 / 8.0;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = i % 8;
			constraints.gridy = i / 8;
			frame.add(buttons[i], constraints);
		}
		
		
		gameStatus = new JLabel();
		gameStatus.setHorizontalAlignment(JLabel.CENTER);
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.gridwidth = 8;
		frame.add(gameStatus, constraints);
		
		newGame = new JButton("<html><p style=\"font-family: monospace; font-size: 16\">New Game</p></html>");
		newGame.setPreferredSize(new Dimension(buttonHeight, buttonWidth));
		newGame.setEnabled(false);
		constraints.gridx = 0;
		constraints.gridy = 9;
		constraints.gridwidth = 8;
		frame.add(newGame, constraints);
		
		standings = new JLabel("<html><p style=\"font-family: monospace; font-size: 16\">You: " + 
							   scoreYou + "<br>Com: " + scoreCom + "</p></html>");
		standings.setHorizontalAlignment(JLabel.CENTER);
		standings.setPreferredSize(new Dimension(buttonHeight, buttonWidth));
		constraints.gridx = 8;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		frame.add(standings, constraints);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void repaint() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				buttons[(y*8) + x].setPlayer(othello.getPosition(x, y));
			}
		}
	}
	
	
	
	public void gameOver(int result) {
		if (result == 1) {
			gameStatus.setText(player1 ? "You have won!" : "Com has won!");
			if (player1) scoreYou++;
			else scoreCom ++;
		}
		else if (result == 2) {
			gameStatus.setText(player1 ? "Com has won!" : "You have won!");
			if (player1) scoreCom++;
			else scoreYou++;
		}
		else if (result == 3)
			gameStatus.setText("The game has ended in a tie!");
		standings.setText("<html><p style=\"font-family: monospace; font-size: 16\">You: " + 
				   scoreYou + "<br>Com: " + scoreCom + "</p></html>");
		disableInput();
		newGame.setEnabled(true);
	}
	
	public void init(int order) {
		startNewGame = false;
		othello = new Othello();
		newGame.setEnabled(false);
		gameStatus.setText("");
		player1 = (order == 1) ? true : false;
		repaint();
	}
	
	public Move makeMove(Move prevMove) {
		if (prevMove != null) {
			buttons[(prevMove.y * 8) + prevMove.x].setPlayer(player1 ? 2 : 1);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
			othello.makeMove(player1 ? 2 : 1, prevMove);
			repaint();
		}
			
		if (othello.getValidMoves(player1 ? 1 : 2).isEmpty()) {
			gameStatus.setText("You do not have any valid moves!");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
			return null;
		}
		
		enableInput(othello.getValidMoves(player1 ? 1 : 2));
		while (humanMove == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
		Move move = humanMove;
		humanMove = null;
		
		
		
		
		othello.makeMove(player1 ? 1 : 2, move);
		repaint();
		return move;
	}
	
	public void enableInput(ArrayList<Move> moves) {
		disableInput();
		for (Move move : moves)
			buttons[(move.y * 8) + move.x].setEnabled(true);
	}
	
	public void disableInput() {
		for (MyButton button : buttons)
			button.setEnabled(false);
	}
	
	public boolean startNewGame() {
		return startNewGame;
	}

	private class MyButtonHandler implements ActionListener {
		private int cell;
		public MyButtonHandler(int cell) {
			this.cell = cell;
		}
		public void actionPerformed(ActionEvent e) {
			if (cell >= 64) {
				startNewGame = true;
			}
			else {
				OthelloGUI.this.humanMove = new Move(cell % 8, cell / 8);
				OthelloGUI.this.disableInput();
			}
		}
	}
}

