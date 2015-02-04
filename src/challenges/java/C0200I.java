package challenges.java;

public class C0200I {
	public static void main(String[] args) {
		/* Parse arguments. */
		
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		
		char[] map = new char[width * height];
		for (int y = 2; y < height + 2; ++y) {
			for (int x = 0; x < width; ++x) {
				map[x + (y - 2) * width] = args[y].charAt(x);
			}
		}
		
		boolean[] seen = new boolean[width * height];
		
		/* Find rectangles. */

		char c;
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				if ((c = map[x + y * width]) == FLOOR_TILE) continue;
			}
		}
		
		/* Display map. */
		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				System.out.print(map[x + y * width]);
			} System.out.println();
		}
	}
	
	private static final char FLOOR_TILE = '.';
}