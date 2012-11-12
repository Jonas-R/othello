package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import othello.Othello;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;



public class OthelloGUI extends JFrame {
	private Othello othello;
	private MyButton[] buttons;
	private JLabel gameStatus;
	private JButton newGame;
	private JLabel standings;
	
	private int scoreYou;
	private int scoreCom;
	
	//true if human is player1
	private boolean player1;
	
	public OthelloGUI() {
		super("Othello");
		
		othello = new Othello();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600,600));
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		int buttonHeight = this.getHeight() / 10;
		int buttonWidth = this.getWidth() / 10;
		
		buttons = new MyButton[64];
		for (int i = 0; i < 64; i++) { 
			buttons[i] = new MyButton();
			buttons[i].setPreferredSize(new Dimension(buttonHeight, buttonWidth));
			constraints.weightx = constraints.weighty = 1.0 / 8.0;
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = i % 8;
			constraints.gridy = i / 8;
			add(buttons[i], constraints);
		}
		
		
		gameStatus = new JLabel();
		gameStatus.setHorizontalAlignment(JLabel.CENTER);
		constraints.gridx = 0;
		constraints.gridy = 8;
		constraints.gridwidth = 8;
		add(gameStatus, constraints);
		
		newGame = new JButton("<html><p style=\"font-family: monospace; font-size: 16\">New Game</p></html>");
		newGame.setPreferredSize(new Dimension(buttonHeight, buttonWidth));
		newGame.setEnabled(false);
		constraints.gridx = 0;
		constraints.gridy = 9;
		constraints.gridwidth = 8;
		add(newGame, constraints);
		
		standings = new JLabel("<html><p style=\"font-family: monospace; font-size: 16\">You: " + 
							   scoreYou + "<br>Com: " + scoreCom + "</p></html>");
		standings.setHorizontalAlignment(JLabel.CENTER);
		standings.setPreferredSize(new Dimension(buttonHeight, buttonWidth));
		constraints.gridx = 8;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(standings, constraints);
		
		pack();
		setVisible(true);
	}
	
	public void gameOver() {
		if (othello.gameStatus() == 1) {
			gameStatus.setText(player1 ? "You have won!" : "Com has won!");
			if (player1) scoreYou++;
			else scoreCom ++;
		}
		else if (othello.gameStatus() == 2) {
			gameStatus.setText(player1 ? "Com has won!" : "You have won!");
			if (player1) scoreCom++;
			else scoreYou++;
		}
		else if (othello.gameStatus() == 3)
			gameStatus.setText("The game has ended in a tie!");
		standings.setText("<html><p style=\"font-family: monospace; font-size: 16\">You: " + 
				   scoreYou + "<br>Com: " + scoreCom + "</p></html>");
		for (int i = 0; i < 64; i++) buttons[i].setEnabled(false);
		newGame.setEnabled(true);
	}
	
	public void reset() {
		othello = new Othello();
		for (int i = 0; i < 64; i++) {
			buttons[i].setEnabled(true);
			buttons[i].setPlayer(0);
		}
		newGame.setEnabled(false);
		gameStatus.setText("");
		player1 = !player1;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new OthelloGUI();
			}
		});
	}
}

