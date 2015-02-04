/**
 * @author Michael
 *
 * part 1: http://www.reddit.com/r/dailyprogrammer/comments/2tr6yn/2015126_challenge_199_bank_number_banners_pt_1/
 * part 2: http://www.reddit.com/r/dailyprogrammer/comments/2u0fyx/2015126_challenge_199_bank_number_banners_pt_2/
 */
package challenges.java;

public class C0199E {
	public static void main(String[] args) {
		String fax = convertStringToFax(args[0]);
		System.out.println(fax);
		
		String ints = convertFaxToString(fax);
		System.out.println(ints);
	}
	
	/* Part 1 */
	private static String convertStringToFax(String ints) {
		StringBuilder sb = new StringBuilder();
		
		String input = ints;
		for (int i = 0; i < 3; ++i) {
			int line = offset * chars.length() / (offset * offset) * i;
			for (char c : input.toCharArray()) {
				int n = Character.getNumericValue(c);
				sb.append(chars.substring(line + offset * n, line + offset * n + offset));
			} sb.append('\n');
		}
		
		return sb.toString();
	}
	
	/* Part 2 */
	private static String convertFaxToString(String fax) {
		StringBuilder sb = new StringBuilder();
		
		String f = fax.replace("\n", "");	
		int f_len = f.length() / (offset * offset);
		int c_len = chars.length() / (offset * offset);
		for (int i = 0; i < f_len; ++i) {
			for (int k = 0; k < c_len; ++k) {
				boolean eq = true;
				for (int j = 0; j < 3; ++j) {
					int f_line = offset * f_len * j;
					int c_line = offset * c_len * j;
					if (!f.substring(f_line + offset * i, f_line + offset * i + offset).equals(chars.substring(c_line + offset * k, c_line + offset * k + offset))) {
						eq = false;
						break;
					}
				}
				
				if (eq) {
					sb.append(k);
					break;
				}
			}
		}
		
		return sb.toString();
	}
	
	private static final int offset = 3;
	private static final String chars 
		= " _     _  _     _  _  _  _  _ "
		+ "| |  | _| _||_||_ |_   ||_||_|"
		+ "|_|  ||_  _|  | _||_|  ||_| _|";
}