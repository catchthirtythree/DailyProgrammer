/**
 * @author Michael
 * 
 * https://www.reddit.com/r/dailyprogrammer/comments/3r7wxz/20151102_challenge_239_easy_a_game_of_threes/
 */
package challenges.java;

import java.util.stream.IntStream;

public class C0239E {
	public static void main(String[] args) {
		IntStream
			.iterate(Integer.parseInt(args[0]), n -> (n + n % 3 - 1) / 3)
			.peek(System.out::println)
			.allMatch(n -> n > 1);
	}
}