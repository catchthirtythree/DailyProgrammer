/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/2vc5xq/20150209_challenge_201_easy_counting_the_days/
 */
package challenges.java;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class C0201E {
	public static void main(String[] args) {
		String date = String.join("-", args);
		
		LocalDate from = LocalDate.now();
		/* Pre: month/day must have a leading 0. */
		LocalDate to = LocalDate.parse(date);
		
		System.out.printf("There are %d days between %s and %s.", ChronoUnit.DAYS.between(from, to), from, to);
	}
}