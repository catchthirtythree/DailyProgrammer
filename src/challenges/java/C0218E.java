/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/38yy9s/20150608_challenge_218_easy_making_numbers/
 */
package challenges.java;

import java.math.BigInteger;

public class C0218E {
	public static void main(String[] args) {
		String input = String.join("", args);
		
		int steps = 0;
		String current = input;
		boolean palindromic = false;
		while (!(palindromic = isPalindrome(current))) {
			current = toInt(current).add(toInt(reverse(current))).toString();
			++steps;
			
			if (steps > 10000) break;
		}
		
		if (palindromic) {
			System.out.printf("%s became palindromic in %d steps: %s.", input, steps, current);
		} else {
			System.out.printf("%s is not palindromic.", input);
		}
	}
	
	public static boolean isPalindrome(String s) {
		return s.equals(reverse(s));
	}
	
	public static String reverse(String s) {
		return new StringBuffer(s).reverse().toString();
	}
	
	public static BigInteger toInt(String s) {
		return new BigInteger(s);
	}
}