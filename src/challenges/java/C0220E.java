/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/3aqvjn/20150622_challenge_220_easy_mangling_sentences/
 */
package challenges.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class C0220E {
	public static void main(String[] args) {
		String[] input = args;
		
		/* Stream input, map sort to each word, create new array, join array */
		System.out.println(String.join(" ", Stream.of(input).map(in -> sort(in)).toArray(String[]::new)));
	}
	
	public static String sort(String word) {
		/* Remove punctuation, sort string ignoring case */
		String[] sorted = word.replaceAll("[^a-zA-Z]", "").split("");
		Arrays.sort(sorted, new Comparator<String>() {
			public int compare(String s1, String s2) {
	            return s1.toLowerCase().compareTo(s2.toLowerCase());
	        }
		});
		
		/* Build the newly sorted string */
		StringBuilder sb = new StringBuilder();
		for (int i = 0, index = 0; i < word.length(); ++i) {
			if (Character.isLetter(word.charAt(i)))
				sb.append(sorted[index++]);
			else
				sb.append(word.charAt(i));
		}
		
		/* If capitals exist, capitalize the first letter */
		if (!sb.toString().equals(sb.toString().toLowerCase())) {
			return capitalize(sb.toString());
		}
		
		return sb.toString();
	}
	
	public static String capitalize(String word) {
		return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
	}
}