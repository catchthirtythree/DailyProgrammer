/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3ofsyb/20151012_challenge_236_easy_random_bag_system/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class C0236E {
	private static final int THRESHOLD = 50;

	public static void main(String[] args) {
		List<Character> 
			bag = new ArrayList<Character>(), 
			chars = Arrays.asList('O', 'I', 'S', 'Z', 'L', 'J', 'T');
		IntStream
			.iterate(0, n -> n + 1)
			.peek(n -> {
				Collections.shuffle(chars);
				bag.addAll(chars);
			}).allMatch(n -> bag.size() < THRESHOLD);
		System.out.println(bag.subList(0, THRESHOLD));
		
		/* Verification: make sure there are no doubles. */
		boolean valid = IntStream
			.iterate(0, n -> n + chars.size())
			.limit((int) (Math.ceil(bag.size() / chars.size())))
			.allMatch(n -> bag.subList(n, n + chars.size() < THRESHOLD ? n + chars.size() : n + THRESHOLD - n)
					.stream()
					.distinct()
					.count() == (n + chars.size() < THRESHOLD ? chars.size() : THRESHOLD - n));
		
		System.out.println(valid);
	}

	/* Alternate way to shuffle before I realized I could put code blocks in peek() */
	public static List<Character> shuffle(List<Character> list) {
		return new Random()
			.ints(0, list.size())
			.distinct().limit(list.size())
			.mapToObj(i -> list.get(i))
			.collect(Collectors.toList());
	}
}