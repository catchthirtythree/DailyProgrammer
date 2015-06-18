/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/38fjll/20150603_challenge_217_intermediate_space_code/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.util.Pair;

public class C0217I {
	private static final String fmt = "%9s: %s\n";
	
	@SuppressWarnings("serial") public static void main(String[] args) {
		/* Define lines and retrieve from input */
		List<String[]> lines = new ArrayList<String[]>() {{
			Stream.of(args).forEach(line -> add(((String) line).trim().replaceAll("\\s+", " ").split(" ")));
		}};
		
		/* Define decipher functions */
		Map<String, Function<String[], String[]>> funcs = new HashMap<String, Function<String[], String[]>>() {{
			put("Omicron V", x -> Stream.of(x).map(c -> String.valueOf((char) (Integer.parseInt(c) ^ 16))).toArray(String[]::new));
			put("Hoth",      x -> Stream.of(x).map(c -> String.valueOf((char) (Integer.parseInt(c) - 10))).toArray(String[]::new));
			put("Ryza IV",   x -> Stream.of(x).map(c -> String.valueOf((char) (Integer.parseInt(c) +  1))).toArray(String[]::new));
			put("Htrae",     x -> new StringBuilder(String.join("", Stream.of(x).map(c -> String.valueOf((char) (Integer.parseInt(c)))).toArray(String[]::new))).reverse().toString().split(""));
		}};
		
		/* Define rate function */
		Function<String[], Integer> rate = x -> Stream.of(x)
				.filter(s -> s.matches("\\w|\\s"))
				.collect(Collectors.toList()).size() / x.length;
		
		/* Get results for each line with each function */
		final List<Pair<String, String[]>> results = new ArrayList<Pair<String, String[]>>() {{
			lines.stream()
				.forEach(line -> funcs
					.forEach((name, func) -> add(new Pair<String, String[]>(name, func.apply(line)))));
		}};
		
		/* Filter bad results and display */
		results.stream()
			.filter(p -> rate.apply(p.getValue()) == 1)
			.forEach((p) -> System.out.printf(fmt, p.getKey(), Arrays.toString(p.getValue())));
	}
}