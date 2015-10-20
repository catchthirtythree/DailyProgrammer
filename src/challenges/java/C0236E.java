/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3ofsyb/20151012_challenge_236_easy_random_bag_system/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Arrays;
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
			.range(0, (int) Math.ceil((double) THRESHOLD / chars.size()))
			.forEach(n -> bag.addAll(shuffle(chars)));
		System.out.println(bag.subList(0, THRESHOLD));
	}

	public static List<Character> shuffle(List<Character> list) {
		return new Random()
			.ints(0, list.size())
			.distinct().limit(list.size())
			.mapToObj(i -> list.get(i))
			.collect(Collectors.toList());
	}
}