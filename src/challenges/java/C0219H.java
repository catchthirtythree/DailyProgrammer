/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/3aewlg/20150617_challenge_219_hard_the_cave_of_prosperity/
 */
package challenges.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import challenges.java.util.Util;

public class C0219H {
	private static final String FILENAME = "ext/" + C0219H.class.getSimpleName();
	
	@SuppressWarnings({ "serial", "unused" }) public static void main(String[] args) throws IOException {
		/* Get input from file */
		List<String> input = Util.getLinesFromFile(FILENAME);
		
		/* Initialize variables */
		double capacity = Double.parseDouble(input.remove(0));
		int num_nuggets = Integer.parseInt(input.remove(0));
		List<Double> nuggets = input.stream()
				.map(in -> Double.parseDouble(in))
				.collect(Collectors.toList());
		
		/* Get all combinations of nuggets that are less than the capacity of the bag */
		final List<List<Double>> combinations = new ArrayList<List<Double>>() {{
			IntStream.range(0, nuggets.size())
				.boxed()
				.flatMap(i -> combinations(nuggets, i)
						.filter(st -> st.stream()
								.reduce(0.0, (x, y) -> x + y) < capacity)).forEach(fm -> add(fm));;
		}};
		
		/* Display the best possible result */
		System.out.println(combinations.stream().max((x, y) -> x.stream().reduce(0.0, (a, b) -> a + b).compareTo(y.stream().reduce(0.0, (c, d) -> c + d))).get());
	}
	
	/* http://stackoverflow.com/questions/28515516/enumeration-combinations-of-k-elements-using-java-8 */
	public static <E> Stream<List<E>> combinations(List<E> l, int size) {
	    if (size == 0) {
	        return Stream.of(Collections.emptyList()); 
	    } else {
	        return IntStream.range(0, l.size()).boxed().
	            <List<E>> flatMap(i -> combinations(l.subList(i+1, l.size()), size - 1).map(t -> pipe(l.get(i), t)));
	    }
	}

	private static <E> List<E> pipe(E head, List<E> tail) {
	    List<E> newList = new ArrayList<>(tail);
	    newList.add(0, head);
	    return newList;
	}
}