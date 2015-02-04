/**
 * @author Michael
 * 
 * Based off: http://www.geeksforgeeks.org/ugly-numbers/
 * http://www.reddit.com/r/dailyprogrammer/comments/2snhei/20150116_challenge_197_hard_crazy_professor/
 */
package challenges.java;

import java.math.BigDecimal;

public class C0197H {
	private static final int ith = 1000000;
	
	private static final long[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long n = getNthUglyNumber(ith);
		long end = System.currentTimeMillis();
		
		System.out.printf("%d in %dms.", n, (end - start));
	}
	
	public static long getNthUglyNumber(int n) {
		long[] ugly_numbers = new long[n];
		
		long[] 
			powers = {0, 0, 0, 0, 0, 0, 0, 0},
			next_multiple = {2, 3, 5, 7, 11, 13, 17, 19};
		
		long next = 1;
		for (int i = 0; i < n; ++i) {
			if (i == 0) {
				ugly_numbers[i] = next;
				continue;
			}
			
			next = min(next_multiple);
			
			ugly_numbers[i] = next;
			
			for (int k = 0; k < primes.length; ++k) {
				if (next == next_multiple[k]) {
					powers[k] = powers[k] + 1;
					next_multiple[k] = ugly_numbers[new BigDecimal(powers[k]).intValueExact()] * primes[k];
				}
			}
		} 
		
		return next;
	}
	
	public static long min(long[] list) {
		long max = -1;
		for (Long l : list)
			if (max == -1 || l < max) max = l;
		return max;
	}
}