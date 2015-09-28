/**
 * @author Michael
 * 
 * Dijsktra's algorithm modified from: http://www.algolist.com/code/java/Dijkstra%27s_algorithm
 * http://www.reddit.com/r/dailyprogrammer/comments/2sfs8f/20150114_challenge_197_intermediate_food_delivery/
 */
package challenges.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class C0197I {
	static enum Interval {
		MORNING(0600, 1000),
		NOON(1000, 1500),
		AFTERNOON(1500, 2200),
		NIGHT(2200, 0600);
		
		int start, end;
		Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		static boolean isInInterval(Interval inter, int time) {
			return (time >= inter.start && time <= inter.end);
		}
	}
	
	static class Vertex implements Comparable<Vertex> {
		public char name;
		public List<Edge> adjacencies;
		
		public int minDistance = Integer.MAX_VALUE;
		public Vertex previous;
		
		public Vertex(char name) {
			this.name = name;
			this.adjacencies = new ArrayList<Edge>();
		}
		
		@Override public int compareTo(Vertex other) {
			return Integer.compare(minDistance, other.minDistance);
		}
		
		@Override public String toString() {
			return String.valueOf(name);
		}
	}

	static class Edge {
		public String name;
		public Vertex target;
		public int[] weights;
		
		public Edge(String name, Vertex target, int[] weights) {
			this.name = name;
			this.target = target;
			this.weights = weights;
		}
	}
	
	public static void main(String[] args) throws IOException {		
		// Get the data from the file.
		List<String> data = getStreets(FILENAME);
	
		// Build the street objects.
		Map<Character, Vertex> graph = buildGraph(data);
		
		char start = args[0].charAt(0), end = args[1].charAt(0);
		int time = Integer.parseInt(args[2]);
		
		// Compute paths from vertex.
		computePaths(graph.get(start), time);
		
		// Compute paths to vertex.
		List<Vertex> path = getShortestPathTo(graph.get(end));
		
		System.out.println("Shortest path to " + graph.get(end) + ": " + graph.get(end).minDistance + "mins");
		
		for (Vertex v : path) {
			Vertex u = v.previous;
			for (Edge e : v.adjacencies) {
				if (u != null && u.equals(e.target))
					System.out.println(v.minDistance + ": " + e.name);
			}
		}
	}
	
	public static void computePaths(Vertex source, int time) {
		source.minDistance = 0;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

			// Visit each edge exiting u
			for (Edge e : u.adjacencies) {
				Vertex v = e.target;
				
				int weight = 0;
				Interval[] intervals = Interval.values();
				for (Interval inter : intervals) {
					if (Interval.isInInterval(inter, time)) {
						weight = e.weights[inter.ordinal()];
						break;
					}
				}
				
				int distance = u.minDistance + weight;
				if (distance < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distance;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}

	public static List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}
	
	public static Map<Character, Vertex> buildGraph(List<String> data) {
		Map<Character, Vertex> map = new HashMap<Character, Vertex>();
		
		// Build the vertices.
		
		for (String d : data) {
			String[] line = d.split(",");
			
			// Get vertex data.
			char start = line[0].charAt(0), end = line[1].charAt(0);
			if (map.get(start) == null || map.get(end) == null) {
				map.put(start, new Vertex(start));
				map.put(end, new Vertex(end));
			} else continue;			
		}
		
		// Build the edges.
		Vertex v;
		for (String d : data) {
			String[] line = d.split(",");
			
			// Get vertex data.
			char start = line[0].charAt(0), end = line[1].charAt(0);
			String name = line[2];
			int[] times = new int[] {
				Integer.parseInt(line[3]),
				Integer.parseInt(line[4]),
				Integer.parseInt(line[5]),
				Integer.parseInt(line[6])
			};
			
			v = map.get(start);
			v.adjacencies.add(new Edge(name, map.get(end), times));
			map.put(start, v);
			
			v = map.get(end);
			v.adjacencies.add(new Edge(name, map.get(start), times));
			map.put(end, v);
		}
		
		return map;
	}
	
	public static List<String> getStreets(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));

		String line;
		List<String> streets = new ArrayList<String>();
		
		while ((line = br.readLine()) != null) {
			streets.add(line);
		} br.close();
		
		return streets;
	}
	
	private static final String FILENAME = "ext/" + C0197I.class.getSimpleName();
}