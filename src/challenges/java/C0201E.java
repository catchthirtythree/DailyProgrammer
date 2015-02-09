package challenges.java;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class C0201E {
	public static void main(String[] args) {
		String date = String.join("-", args);
		
		LocalDate from = LocalDate.now();
		LocalDate to = LocalDate.parse(date);
		
		System.out.printf("There are %d days between %s and %s.", ChronoUnit.DAYS.between(from, to), from, to);
	}
}