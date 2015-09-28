/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/3840rp/20150601_challenge_217_easy_lumberjack_pile/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.List;

public class C0217E {
	public static void main(String[] args) {
		int size = Integer.parseInt(args[0]);
		int logs = Integer.parseInt(args[1]);
		
		List<Integer> piles = new ArrayList<Integer>();
		for (int i = 2; i < args.length; ++i) {
			piles.add(Integer.parseInt(args[i]));
		}

		while (logs-- > 0) {
			int min = piles.stream().min(Integer::min).get();
			piles.set(piles.indexOf(min), min + 1);
		}
		
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < size; ++y) {
			for (int x = 0; x < size; ++x) {
				sb.append(piles.get(x + y * size) + " ");
			} sb.append("\n");
		}
		
		System.out.println(sb);
	}
}