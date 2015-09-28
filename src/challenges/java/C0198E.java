/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/2syz7y/20150119_challenge_198_easy_words_with_enemies/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.List;

public class C0198E {
	public static void main(String[] args) {
		List<Character> s1 = new ArrayList<Character>(), s2 = new ArrayList<Character>();
		
		for (Character c : args[0].toCharArray()) s1.add(c);
		for (Character c : args[1].toCharArray()) s2.add(c);
		
		String s1tmp = s1.toString(), s2tmp = s2.toString();
		
		difference(s1, s2);
		
		System.out.printf("s1: %s -> %s\ns2: %s -> %s\n\n%s.\n",
				s1tmp, s1, 
				s2tmp, s2, 
				s1.size() == s2.size() ? "Tie" : s1.size() > s2.size() ? "s1 wins" : "s2 wins"
		);
	}
	
	public static void difference(List<Character> l1, List<Character> l2) {
		for (int i = 0; i < l1.size(); ++i) {
			Character c = l1.get(i);
			int index;
			if ((index = l2.indexOf(c)) >= 0) {
				l1.remove(i--);
				l2.remove(index);
			}
		}
	}
}