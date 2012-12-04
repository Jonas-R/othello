package othello;

import static org.junit.Assert.*;
import org.junit.Test;

public class AdjacencyTest {
	@Test
	public void testAdjacency() {
		assertTrue("(5,7) is missing!", ((BitBoard.adjacency[(7 * 8) + 6] & (1L << ((7 * 8) + 5)))) != 0);
		assertTrue("(5,6) is missing!", ((BitBoard.adjacency[(7 * 8) + 6] & (1L << ((6 * 8) + 5)))) != 0);
		assertTrue("(0,4) is missing!", ((BitBoard.adjacency[(3 * 8) + 1] & (1L << ((4 * 8) + 0)))) != 0);
	}
}
