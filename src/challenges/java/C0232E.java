/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3kx6oh/20150914_challenge_232_easy_palindromes/
 */
package challenges.java;

import java.util.stream.IntStream;

public class C0232E {
	public static void main(String[] args) {
		String input = String.join(" ", args);
		System.out.println(isPalindrome(input) == isPalindrome8(input));
	}
	
	public static boolean isPalindrome(String s) {
		return (s = s.replaceAll("[^\\w]", "")
				.toLowerCase())
				.equals(new StringBuffer(s).reverse().toString());
	}

	public static boolean isPalindrome8(String s) {
		int len;
		final String p = s.toLowerCase().replaceAll("[^a-z]", "");
		return IntStream.range(0, (len = p.length()) / 2)
				.filter(i -> p.charAt(i) == p.charAt(len - 1 - i))
				.count() == len / 2;
	}
}