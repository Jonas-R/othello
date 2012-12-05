
public class GenerateMasks {
	public static void main(String[] args) {
		long right = 0L;
		long left = 0L;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (x != 0)
					right |= 1L << (y * 8) + x;
				if (x != 7)
					left |= 1L << (y * 8) + x;
			}
		}
		System.out.println("private final long MASK_RIGHT = " + right + "L;");
		System.out.println("private final long MASK_LEFT = " + left + "L;");
	}
}
