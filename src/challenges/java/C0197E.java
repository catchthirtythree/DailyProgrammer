/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/2s7ezp/20150112_challenge_197_easy_isbn_validator/
 */
package challenges.java;

public class C0197E {
	public static void main(String[] args) {
		String isbn = "0-7475-3269-9".replaceAll("-", "");

		int index = 0, sum = 0;
		for (char c : isbn.toCharArray()) {
			sum += ((c == 'X' ? 10 : c) - 48) * (10 - index++);
		}

		System.out.printf("%s isbn.", (sum % 11 == 0 ? "Valid" : "Invalid"));
	}
}