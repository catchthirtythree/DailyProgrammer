package challenges.java;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import challenges.java.util.Util;

public class C0237E {
	public static void main(String[] args) throws IOException {
		List<String> words = Util.getLinesFromFile(Util.WORDLIST);
		
		String[] keyboards = args;
		for (String keyboard : keyboards) {
			System.out.println(keyboard + ": " + find(keyboard, words));
		}
	}
	
	public static String find(String keyboard, List<String> dict) {
		return dict.stream()
				.filter(word -> word.matches("[" + keyboard + "]+"))
				.max(new Comparator<String>() {
					@Override public int compare(String arg0, String arg1) {
						return Integer.compare(arg0.length(), arg1.length());
					}
				}).get();
	}
}