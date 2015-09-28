/**
 * @author Michael
 * 
 * https://www.reddit.com/r/dailyprogrammer/comments/3l61vx/20150916_challenge_232_intermediate_where_should/
 */
package challenges.java;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;
import challenges.java.util.Util;

public class C0232I {
	public static class Point {
		public double x, y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}
	
	public static void main(String[] args) throws IOException {
		List<String> data = Util.getLinesFromFile(FILENAME);
		List<Point> points = data.stream()
				.map(d -> new Point(Double.parseDouble(d.split(",")[0]), Double.parseDouble(d.split(",")[1])))
				.collect(Collectors.toList());
		
		/* Java */
		Pair<Point, Point> closestPair = null;
		double minDistance = Double.POSITIVE_INFINITY;
		for (Point pc : points) {
			for (Point pk : points) {
				if (pc.equals(pk)) continue;
				if (nn(pc.x, pc.y, pk.x, pk.y) < minDistance) {
					minDistance = nn(pc.x, pc.y, pk.x, pk.y);
					closestPair = new Pair<Point, Point>(pc, pk);
				}
			}
		}
		
		System.out.println(closestPair.getKey() + ", " + closestPair.getValue() + " with a min distance of " + minDistance);
		
		/* Java 8 Min Distance */
		minDistance = points.stream()
			.flatMapToDouble(p1 -> points.stream()
					.filter(p2 -> !p2.equals(p1))
					.mapToDouble(p2 -> nn(p1.x, p1.y, p2.x, p2.y)))
			.min()
			.getAsDouble();
		
		System.out.println(minDistance);

		/* Java 8 Min Pair (not worth the trouble it took to write this; insanely slow) */
		Comparator<Pair<Point, Point>> pCompare = new Comparator<Pair<Point, Point>>() {
			@Override public int compare(Pair<Point, Point> p1, Pair<Point, Point> p2) {
				return Double.compare(
						nn(p1.getKey().x, p1.getKey().y, p1.getValue().x, p1.getValue().y), 
						nn(p2.getKey().x, p2.getKey().y, p2.getValue().x, p2.getValue().y));
			}
		};
		
		Pair<Point, Point> minPair = points.stream()
			.flatMap(p1 -> points.stream()
					.filter(p2 -> !p2.equals(p1))
					.map(p2 -> new Pair<Point, Point>(p1, p2)))
			.min(pCompare)
			.get();
		
		System.out.println(minPair.getKey() + ", " + minPair.getValue());
		
	}
	
	public static double nn(double cx, double cy, double tx, double ty) {
		return Math.abs(cx - tx) +  Math.abs(cy - ty);
	}
	
	private static final String FILENAME = "ext/" + C0232I.class.getSimpleName();
}