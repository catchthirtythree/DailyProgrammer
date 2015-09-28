/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/2w84hl/20150216_challenge_202_easy_i_am_bender_please/
 */
package challenges.java;

public class C0202E {
	public static void main(String[] args) {
		String input = args[0];
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i+= 8) {
			sb.append((char) Integer.parseInt(input.substring(i, i + 8), 2));
		}
		
		System.out.println(sb);
	}
}