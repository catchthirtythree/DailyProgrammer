/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/341c03/20150427_challenge_212_easy_r%C3%B6varspr%C3%A5ket/
 */
package challenges.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class C0212E {
	private static final String consonents = "bcdfghjklmnpqrstvwxyz";
	
	private static final String r_robberify = "(?i)[" + consonents + "]";
	private static final String r_unrobberify = "(?i)[" + consonents + "]o[" + consonents + "]";

	public static void main(String[] args) {
		String text = String.join(" ", args);

		System.out.println(text);
		System.out.println(robberify(text));
		System.out.println(unrobberify(robberify(text)));
	}
	
	public static String robberify(String text) {
		StringBuffer output = new StringBuffer();
		Matcher m = Pattern.compile(r_robberify).matcher(text);
		
	    while (m.find()) {
	        m.appendReplacement(output, m.group() + "o" + m.group().toLowerCase());
	    } m.appendTail(output);
	    
	    return output.toString();
	}
	
	public static String unrobberify(String s) {
		StringBuffer output = new StringBuffer();
		Matcher m = Pattern.compile(r_unrobberify).matcher(s);
		
		while (m.find()) {
			String group = m.group();
			if (Character.toLowerCase(group.charAt(0)) == Character.toLowerCase(group.charAt(group.length() - 1))) {
				group = group.substring(0, 1);
			}
			
			m.appendReplacement(output, group);
	    } m.appendTail(output);
	    
	    return output.toString();
	}
}