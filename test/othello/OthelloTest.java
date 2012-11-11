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
		assertTrue("Move(2,3) missing!", moves.contains(new Move(2,3)));
		assertTrue("Move missing!", moves.contains(new Move(3,2)));
		assertTrue("Move missing!", moves.contains(new Move(4,5)));
		assertTrue("Move missing!", moves.contains(new Move(5,4)));
	}

}
