/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3q9vpn/20151026_challenge_238_easy_consonants_and_vowels/
 */
package challenges.java;

public class C0238E {
	private static final String CONSONANTS = "bcdfghjklmnpqrstvwxyz";
	private static final String VOWELS = "aeiou";
	
	public static void main(String[] args) throws Exception {
		System.out.println(convert(args[0]));
	}
	
	public static String convert(String str) {
		return str
			.chars()
			.map(ch -> {
				boolean upper = Character.isUpperCase(ch);
				if (Character.toUpperCase(ch) == 'C') {
					ch = random(CONSONANTS);
				} else if (Character.toUpperCase(ch) == 'V') {
					ch = random(VOWELS);
				}
				return upper ? Character.toUpperCase(ch) : ch;
			}).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
	
	public static char random(String str) {
		return str.charAt((int) (Math.random() * str.length()));
	}
}