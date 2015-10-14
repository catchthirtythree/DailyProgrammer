/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3opin7/20151014_challenge_236_intermediate_fibonacciish/cvzkkwe
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C0236I {
	public static void main(String[] args) {
		System.out.println(fibBrute(38695577906193299L));
	}
	
	public static List<Long> fibBrute(long n) {
		List<Long> seq;
		for (long i = 1; ; ++i) {
			if (n % i == 0) {
				long start = 0, f1 = i;
				seq = new ArrayList<Long>(Arrays.asList(start, f1));
				while (f1 < n) seq.add(f1 = (start + (start = f1)));
				if (f1 == n) break;
			}
		}
		return seq;
	}
}