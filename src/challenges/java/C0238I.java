/**
 * @author Michael
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/3qjnil/20151028_challenge_238_intermediate_fallout/
 */
package challenges.java;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import challenges.java.util.Util;

public class C0238I {
	public static final Random rand = new Random();
	public static final int GUESSES = 4;
	
	public static void main(String[] args) throws IOException {
		try (Scanner scanner = new Scanner(System.in)) {
			List<String> lines = Util.getLinesFromFile(Util.WORDLIST);
			Collections.shuffle(lines);
			
			System.out.print("Choose a difficulty (0-4): ");
			int difficulty = getWordLengthFromDifficulty(scanner.nextInt());
			
			List<String> words = lines
				.stream()
				.filter(line -> line.length() == difficulty)
				//.sorted((s1, s2) -> Integer.compare(rand.nextInt(), rand.nextInt())) not transitive
				.collect(Collectors.toList())
				.subList(0, 10);
			
			String password = words.get(rand.nextInt(words.size()));
			System.out.println(String.join("\n", words));
			
			int length = password.length();
			for (int tries = 0; tries < GUESSES; ++tries) {
				System.out.print("Choose a word: ");
				String guess = scanner.next().toLowerCase();
				
				long correct = guess(password, guess);
				if (correct == length) {
					System.out.print("You win!");
					return;
				}
				
				System.out.printf("%d/%d correct.\n", correct, length);
			}
			
			System.out.printf("You lose, the word was %s.", password);
		}
	}
	
	public static int getWordLengthFromDifficulty(int difficulty) {
		switch (difficulty) {
		case 0: return rand.nextInt(5 - 4) + 4;
		case 1: return rand.nextInt(8 - 6) + 6;
		case 2: return rand.nextInt(10 - 9) + 9;
		case 3: return rand.nextInt(12 - 11) + 11;
		case 4: return rand.nextInt(15 - 13) + 13;
		default: return -1;
		}
	}
	
	public static long guess(String password, String guess) {
		return IntStream.range(0, password.length()).filter(n -> password.charAt(n) == guess.charAt(n)).count();
	}
}