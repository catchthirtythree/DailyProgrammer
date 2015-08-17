/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3h9pde/20150817_challenge_228_easy_letters_in/
 */
package challenges.java;

public class C0228E {
	public static void main(String[] args) {
		for (String s : args) {
			if (sortAlpha(s).equals(s)) {
				System.out.println(s + " IS SORTED");
			} else {
				if (sortReverse(s).equals(s)) {
					System.out.println(s + " IS SORTED");
				} else {
					System.out.println(s + " IS NOT SORTED");
				}
			}
		}
	}
	
	public static String sortAlpha(String s) {
		return s.chars()
				.sorted()
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}
	
	public static String sortReverse(String s) {
		return s.chars()
				.sorted()
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.reverse()
				.toString();
	}
}