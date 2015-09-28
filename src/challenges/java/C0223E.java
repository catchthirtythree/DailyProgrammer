/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3d4fwj/20150713_challenge_223_easy_garland_words/
 */
package challenges.java;

public class C0223E {
	public static void main(String[] args) {
		System.out.println(garland(args[0]));
	}
	
	public static int garland(String word) {
		int degree = 0;
		for (int i = 1; i < word.length(); ++i)
			if (word.endsWith(word.substring(0, i)))
				degree = i;
		return degree;
	}
}