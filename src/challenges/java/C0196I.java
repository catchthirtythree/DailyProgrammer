/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/2rnwzf/20150107_challenge_196_intermediate_rail_fence/
 */
package challenges.java;

import java.util.HashMap;
import java.util.Map;

public class C0196I {
	public static void main(String[] args) {
		try {
			int rails = Integer.parseInt(args[1]);
			String method = args[0], text = args[2];
			
			switch (method) {
			case "enc":
				System.out.printf(fmt, method, rails, text, encode(text, rails));
				break;
			case "dec":
				System.out.printf(fmt, method, rails, text, decode(text, rails));
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		// pop first char
	}
	
	public static String encode(String plaintext, int rails) {
		// Assign a rail to each letter.

		int index = 1, up = 1;
		Map<Integer, StringBuilder> set = new HashMap<Integer, StringBuilder>();
		
		for (int i = 0; i < plaintext.length(); ++i) {
			StringBuilder sb = set.get(index);
			if (sb == null) {
				sb = new StringBuilder();
			} sb.append(plaintext.charAt(i));
			
			set.put(index, sb);
			
			index += (index == rails ? (up = -1) : index == 1 ? (up = 1) : up);
		}
		
		// Combine plaintextingbuilders.
		
		StringBuilder encode = new StringBuilder();
		for (StringBuilder sb : set.values())
			encode.append(sb.toString());
		
		return encode.toString();
	}
	
	public static String decode(String ciphertext, int rails) {
		char[] plaintext = new char[ciphertext.length()];
		
		// Top rail.
		
		int index = 0, diff = rails * 2 - 2;
		for (int i = 0; i < ciphertext.length(); ++i) {
			if (i % (diff) == 0)
				plaintext[i] = ciphertext.charAt(index++);
		}
		
		// Middle rails.
		
		for (int i = rails - 2; i >= 1; --i) {
			for (int k = 0, mid; k < ciphertext.length(); ++k) {
				// Find number in the middle of a section/triangle/whatever.
				if ((mid = k * diff + rails - 1) < ciphertext.length()) {
					// Add/remove i from it.
					plaintext[mid - i] = ciphertext.charAt(index++);
					plaintext[mid + i] = ciphertext.charAt(index++);
				}
			}
        }
		
		// Bottom rail.
		
		for (int i = rails - 1; i < ciphertext.length(); ++i) {
			if (i % (diff) == (rails - 1))
				plaintext[i] = ciphertext.charAt(index++);
		}
		
		return String.copyValueOf(plaintext);
	}
	
	private static final String fmt = "%s %d %s -> %s\n";
}