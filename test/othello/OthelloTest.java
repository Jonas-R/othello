package othello;


import java.util.ArrayList;
import static org.junit.Assert.*;
import szte.mi.*;
import org.junit.Test;

public class OthelloTest {

	@Test
	public void testBoard() {
		Othello o = new Othello();
		assertTrue("(3,3) is white", o.getPosition((byte)3, (byte)3) == 1);
		assertTrue("(4,4) is white", o.getPosition((byte)4, (byte)4) == 1);
		assertTrue("(4,3) is black", o.getPosition((byte)4, (byte)3) == 2);
		assertTrue("(3,4) is black", o.getPosition((byte)3, (byte)4) == 2);
	}
	
	@Test
	public void testGetValidMoves() {
		Othello o = new Othello();
		ArrayList<Integer> moves = o.getValidMoves(2);
		assertTrue("Number of valid moves is not 4!", moves.size() == 4);
		assertTrue("Move (2,3) missing!", moves.contains((3 * 8) + 2));
		assertTrue("Move (3,2) missing!", moves.contains((2 * 8) + 3));
		assertTrue("Move (4,5) missing!", moves.contains((5 * 8) + 4));
		assertTrue("Move (5,4) missing!", moves.contains((4 * 8) + 5));
	}
	
	@Test
	public void testMakeMoves() {
		Othello o = new Othello();
		o.makeMove(2, new Move(3,2));
		o.makeMove(1, new Move(2,2));
		o.makeMove(2, new Move(4,5));
		o.makeMove(1, new Move(5,5));
		assertTrue("Incorrect num of white tokens (" + o.getWhiteTokens() + ")"
				, o.getWhiteTokens() == 4);
		assertTrue("Incorrect num of black tokens (" + o.getBlackTokens() + ")"
				, o.getBlackTokens() == 4);
		ArrayList<Integer> moves = o.getValidMoves(2);
		assertTrue("Number of valid moves is not 4!", moves.size() == 4);
		assertTrue("Move (1,2) missing!", moves.contains((2 * 8) + 1));
		assertTrue("Move (2,3) missing!", moves.contains((3 * 8) + 2));
		assertTrue("Move (5,4) missing!", moves.contains((4 * 8) + 5));
		assertTrue("Move (6,5) missing!", moves.contains((5 * 8) + 6));
	}
}
