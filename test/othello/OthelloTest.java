package othello;

import java.util.ArrayList;
import static org.junit.Assert.*;

import org.junit.Test;

public class OthelloTest {

	@Test
	public void testGetValidMoves() {
		Othello o = new Othello();
		ArrayList<Move> moves = o.getValidMoves(2);
		assertTrue("Number of valid moves is not 4!", moves.size() == 4);
		assertTrue("Move (2,3) missing!", moves.contains(new Move(2,3)));
		assertTrue("Move (3,2) missing!", moves.contains(new Move(3,2)));
		assertTrue("Move (4,5) missing!", moves.contains(new Move(4,5)));
		assertTrue("Move (5,4) missing!", moves.contains(new Move(5,4)));
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
		ArrayList<Move> moves = o.getValidMoves(2);
		assertTrue("Number of valid moves is not 4!", moves.size() == 4);
		assertTrue("Move (1,2) missing!", moves.contains(new Move(1,2)));
		assertTrue("Move (2,3) missing!", moves.contains(new Move(2,3)));
		assertTrue("Move (5,4) missing!", moves.contains(new Move(5,4)));
		assertTrue("Move (6,5) missing!", moves.contains(new Move(6,5)));
	}

}
