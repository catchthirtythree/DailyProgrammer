/**
 * @author Michael
 * 
 * https://www.reddit.com/r/dailyprogrammer/comments/3ddpms/20150715_challenge_223_intermediate_eel_of_fortune/
 */
package challenges.java;

import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class C0223I {
	public static void main(String[] args) {
		final String secret = args[0], board = args[1];
		
		final BiFunction<String, String, Boolean> problem = 
				(a, b) -> b.equals(
					a.chars()
						.mapToObj(c -> (char) c)
						.filter(c -> b.indexOf(c) > -1)
						.map(c -> Character.toString(c))
						.collect(Collectors.joining()));
		
		System.out.println(problem.apply(secret, board));
	}
}