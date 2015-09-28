/**
 * @author Michael
 * 
 * https://www.reddit.com/r/dailyprogrammer/comments/3ddpms/20150715_challenge_223_intermediate_eel_of_fortune/
 */
package challenges.java;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import challenges.java.util.Util;

public class C0223I {
	public static void main(String[] args) throws IOException {
		/* Initial Challenge */
		final String secret = args[0], board = args[1];
		
		final BiFunction<String, String, Boolean> problem = 
				(a, b) -> b.equals(
					a.chars()
						.mapToObj(c -> (char) c)
						.filter(c -> b.indexOf(c) > -1)
						.map(c -> Character.toString(c))
						.collect(Collectors.joining()));
				
		System.out.printf("problem(%s, %s) -> %b\n", 
				secret, 
				board, 
				problem.apply(secret, board));
		
		/* Optional Challenge #1 */
		final String count_problem = "rrizi";
		final List<String> lines = Util.getLinesFromFile(Util.WORDLIST);
		
		System.out.printf("problem count for '%s' -> %d\n", 
				count_problem, 
				lines.stream()
					.filter(word -> problem.apply(word, count_problem))
					.count());
	}
	
	public static char[] next_string(char[] string) {
		for (int i = string.length; i --> 0;) {
			if ((int) (string[i] = (char) ((string[i] + 1) % 123)) == 0) {
				string[i] += 97;
			} else {
				break;
			}
		}
		
		return string;
	}
}