/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3e0hmh/20150720_challenge_224_easy_shuffling_a_list/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class C0224E {
	/**
	 * Split the deck perfectly in half and weave them into each other.
	 * @param list
	 */
	public static <E> void faro_shuffle(List<E> list) {
		List<E> c = new ArrayList<E>();
		
		IntStream.iterate(0, i -> i + 1)
			.limit(list.size() / 2)
			.forEach(i -> {
				c.add(list.get(i));
				c.add(list.get(i + list.size() / 2));
			});

		for (int i = 0; i < c.size(); ++i) {
			list.set(i, c.get(i));
		}
	}
	
	/**
	 * Cyclic permutations.
	 * @param list
	 */
	public static <E> void fisher_yates_shuffle(List<E> list) {
		IntStream.iterate(1, i -> i + 1)
			.limit(list.size() - 1)
			.forEach(i -> Collections.swap(list, i, (int) (Math.random() * i)));
	}
	
	/**
	 * Generate a random order for the list using Random.
	 * @param list
	 */
	public static <E> void random_shuffle(List<E> list) {
		List<Integer> c = new Random().ints(0, list.size())
			.distinct()
			.limit(list.size())
			.mapToObj(i -> i)
			.collect(Collectors.toList());
		
		IntStream.iterate(0, i -> i + 1)
			.limit(list.size())
			.forEach(i -> Collections.swap(list, i, c.get(i)));
	}
}