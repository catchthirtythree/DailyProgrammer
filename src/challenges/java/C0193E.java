/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/2ptrmp/20141219_challenge_193_easy_acronym_expander/
 */
package challenges.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class C0193E {
	public static Map<String, String> sets = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L; {
		put("lol", "laugh out loud");
	    put("dw", "don't worry");
	    put("hf", "have fun");
	    put("gg", "good game");
	    put("brb", "be right back");
	    put("g2g", "got to go");
	    put("wtf", "what the fuck");
	    put("wp", "well played");
	    put("gl", "good luck");
	    put("imo", "in my opinion");
	}};
	
	public static void main(String[] args) {
		String input = "imo that was wp. Anyway I've g2g";
		
		for (Entry<String, String> set : sets.entrySet()) {
			input = input.replaceAll("\\b" + set.getKey() + "\\b", set.getValue());
		}
		
		System.out.println(input);
	}
}