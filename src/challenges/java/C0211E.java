/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/338p28/20150420_challenge_211_easy_the_name_game/
 */
package challenges.java;

public class C0211E {
	private static final String song = "%1$s, %1$s, bo-%2$s, \nBonana, fanna, fo-%3$s, \nFee, fy, mo-%4$s, \n%1$s!";
	
	public static void main(String[] args) {
		String name = args[0], 
			suffix = ("AEIOU".contains(name.substring(0, 1)) ? name.toLowerCase() : name.substring(1, name.length())), 
			bName = "b" + suffix, 
			fName = "f" + suffix, 
			mName = "m" + suffix;
		
		switch (name.charAt(0)) {
		case 'B': bName = suffix; break;
		case 'F': fName = suffix; break;
		case 'M': mName = suffix; break;
		}
		
		System.out.print(String.format(song, name, bName, fName, mName));
	}
}