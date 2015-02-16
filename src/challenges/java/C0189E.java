/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/2mlfxp/20141117_challenge_189_easy_hangman/
 */
package challenges.java;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import challenges.java.util.Util;

public class C0189E {
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		List<String> words = Util.getLinesFromFile(Util.WORDLIST);

		// Prompt user for a difficulty.
		Difficulty diff = chooseDifficulty(keyboard);
		
		// Pick a random word (based on difficulty) strip punctuation.
		String word;
		while (true) {
			if (Difficulty.isInDifficulty(diff, word = words.get((int) (Math.random() * words.size()))))
				break;
		}
		
		char[] chars = new char[word.length()];
		Arrays.fill(chars, '_');
		String display = new String(chars);
		
		// Game loop.
			// Display hangman stuff.
			
			// Prompt user for a letter.
			String letter = chooseLetter(keyboard);
			
			if (word.contains(letter)) {
				int from = -1;
				while ((from = word.indexOf(letter, from + 1)) > 0) {
					char[] t = display.toCharArray();
					t[from] = letter.charAt(0);
					display = String.valueOf(t);
				}
			}
			
			// Does letter exist?
				// Yes: keep going.
				// No: increment faults.
			
			// Is gameover?
			
		System.out.println(word + ", " + display);
	}
	
	private  static Difficulty chooseDifficulty(Scanner keyboard) {
		Difficulty difficulty = null;
		boolean valid = false;
		
		do {
			displayDifficulties();
			
			try {
				difficulty = Difficulty.values()[keyboard.nextInt()];
				valid = true;
			} catch (InputMismatchException e) {
				keyboard.next();
				
				System.out.println("Invalid Input, please enter an Integer.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please enter an integer between 0 and " + (Difficulty.values().length - 1) + ".");
			} finally {
				keyboard.nextLine();
				System.out.println();
			}
		} while (!valid);
		
		return difficulty;
	}
	
	private static String chooseLetter(Scanner keyboard) {
		String letter;
		boolean valid = false;
		
		do {
			System.out.print("Choose a word: ");
			letter = keyboard.nextLine();
			
			if (letter.matches("[A-Za-z]"))
				valid = true;
			
			System.out.printf("%s\n\n", valid ? letter + " is a valid letter." : letter + " is not a valid letter, try again.");
		} while (!valid);
		
		return letter;
	}
	
	private static void displayDifficulties() {
		System.out.println("Choose from the list of difficulties:");
		for (Difficulty sc : Difficulty.values())
			System.out.printf("\t%d: %s\n", sc.ordinal(), sc.name());
		System.out.print("Input a command: ");
	}
}

enum Difficulty {
	EASY(3, 7), 
	MEDIUM(7, 9), 
	HARD(9);
	
	int start, end;
	Difficulty(int start) {
		this(start, Integer.MAX_VALUE);
	}
	
	Difficulty(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	static boolean isInDifficulty(Difficulty diff, String word) {
		return (word.length() >= diff.start && word.length() <= diff.end);
	}
}