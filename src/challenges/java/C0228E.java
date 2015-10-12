/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3h9pde/20150817_challenge_228_easy_letters_in/
 */
package challenges.java;

public class C0228E {
	public static void main(String[] args) {
		for (String s : args) {
			StringBuilder sb = sort(s);
			if (sb.toString().equals(s)) {
				System.out.println(s + " IN ORDER");
			} else {
				if (sb.reverse().toString().equals(s)) {
					System.out.println(s + " REVERSE ORDER");
				} else {
					System.out.println(s + " NOT IN ORDER");
				}
			}
		}
	}
	
	public static StringBuilder sort(String s) {
		return s.chars()
			.sorted()
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
	}
}
