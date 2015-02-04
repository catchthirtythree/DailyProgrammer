/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/2d8yk5/8112014_challenge_175_easy_bogo/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class C0175E {
	public static void main(String[] args) {
		String N = args[0], M = args[1];
		
		List<Character> chars = new ArrayList<Character>();
		for (char c : N.toCharArray()) {
			chars.add(c);
		}

		int iters = 0;
		StringBuilder sb;
		boolean sorted = false;
		while (!sorted) {
			Collections.shuffle(chars);
			
			sb = new StringBuilder();
			for (Character c : chars)
				sb.append(c);
			
			if (M.equals(sb.toString())) 
				sorted = true;
			
			++iters;
		}
		
		System.out.println(iters + " operations.");
	}
}