
public class DirectionList {
	private static final int[][] directions = { {-1,-1}, {-1,0}, {0,-1}, {0,1}, {-1,1},  {1,-1}, {1,0}, {1,1} };
	public static void main(String[] args) {
		int[][] directionlist = new int[64][8];
		for (int i = 0; i < 64; i++) {
			int x = i % 8; int y = i / 8;
			int[] list = new int[8];
			for (int n = 0; n < 8; n++) {
				int tx = x + directions[n][0]; int ty = y + directions[n][1];
				if (tx < 0 || tx > 7 || ty < 0 || ty > 7) {
					list[n] = -1;
				}
				else list[n] = (ty * 8) + tx;
			}
			directionlist[i] = list;
		}
		System.out.println("public static final int[][] directionlist = {");
		for (int i = 0; i < 64; i++) {
			System.out.print("{ ");
			for (int n = 0; n < 8; n++) {
				System.out.print(directionlist[i][n]);
				if (n < 7) System.out.print(", ");
			}
			System.out.println(" },");
		}
		System.out.println("};");
	}
}
