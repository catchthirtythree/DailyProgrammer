/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/2ug3hx/20150202_challenge_200_easy_floodfill/
 */
package challenges.java;

public class C0200E {
	public static void main(String[] args) {
		/* Parse input */
		
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		
		int p_x = Integer.parseInt(args[args.length - 3]);
		int p_y = Integer.parseInt(args[args.length - 2]);
		char symbol = args[args.length - 1].charAt(0);
		
		char[] map = new char[width * height];
		for (int y = 2; y < height + 2; ++y) {
			for (int x = 0; x < width; ++x) {
				map[x + (y - 2) * width] = args[y].charAt(x);
			}
		}
		
		/* Fill map */
		
		fillMap(map, width, height, p_x, p_y, symbol);
		
		/* Draw filled map */
		
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				System.out.print(map[x + y * width]);
			} System.out.println();
		}
	}
	
	public static void fillMap(char[] map, int width, int height, int x, int y, char symbol) {
		fillMap(map, width, height, x, y, symbol, map[x + y * width]);
	}
	
	/* Includes extension:
	 * Extend your program so that the image 'wraps' around from the bottom to the top, and from the left to the right 
	 * (and vice versa). This makes it so that the top and bottom, and left and right edges of the image are touching 
	 * (like the surface map of a torus).
	 */
	public static void fillMap(char[] map, int width, int height, int x, int y, char symbol, char start) {
		if (x < 0) x += width;
		if (y < 0) y += height;
		
		int coordinate = (x % width) + (y % height) * width;
		if (map[coordinate] == start) {
			map[coordinate] = symbol;
			fillMap(map, width, height, x + 1, y    , symbol, start);
			fillMap(map, width, height, x    , y + 1, symbol, start);
			fillMap(map, width, height, x - 1, y    , symbol, start);
			fillMap(map, width, height, x    , y - 1, symbol, start);
		}
	}
}