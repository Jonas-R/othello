import othello.*;

public class AdjacencyList {
	private static final int[][] directions = { {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1} };
	
	public static void main(String[] args) {
		long[] adjacency = new long[64];
		for (int i = 0; i < 64; i++) {
			int x = i % 8; int y = i / 8;
			if ((x == 0 && y == 0) || (x == 7 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 7)) {
				adjacency[i] = 0L;
			}
			else {
				long bits = 0L;
				for (int[] direction : directions) {
					int tx = x + direction[0]; int ty = y + direction[1];
					
					if (tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
						bits |= (1L << ((ty * 8) + tx));
					}
						
				}
				adjacency[i] = bits;
			}
		}
		System.out.println("public static final long[] adjacency = {");
		for (int i = 0; i < 64; i++) {
			System.out.println(adjacency[i] + "L,");
		}
		System.out.println("};");
	}
}
