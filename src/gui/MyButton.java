package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class MyButton extends JButton{
	// determines whether the cell is occupied
	// 0: not occupied 1: occupied by white
	// 2: occupied by black
	private int player = 0;
	
	public void setPlayer(int player) {
		this.player = player;
		repaint();
	}
	
	public int getPlayer() {
		return player;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING
							, RenderingHints.VALUE_ANTIALIAS_ON);
		
		super.paintComponent(g2D);
		
		if (player != 0) {
			
			this.setEnabled(false);
			//g2D.setStroke(new BasicStroke(5F));
			if(player == 1) g2D.setColor(Color.WHITE);
			else g2D.setColor(Color.BLACK);
			g2D.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
		}
		
	}
}
