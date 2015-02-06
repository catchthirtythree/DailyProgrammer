/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/2mlfxp/20141117_challenge_189_easy_hangman/
 */
package challenges.java;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import challenges.java.util.Util;

public class C0189E {
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource") Scanner keyboard = new Scanner(System.in).useDelimiter("\\r\\n");
		List<String> words = Util.getLinesFromFile(Util.WORDLIST);

		// Prompt user for a difficulty.
		Difficulty diff = chooseDifficulty(keyboard);
		
		// Pick a random word (based on difficulty) strip punctuation.
		String word;
		while (true) {
			if (Difficulty.isInDifficulty(diff, word = words.get((int) (Math.random() * words.size()))))
				break;
		}
		
		// Prompt user for a letter.
		
		// Does letter exist?
			// Yes: Keep going.
			// No: display hangman; increment faults.
		
		// Rematch?
	}
	
	public static Difficulty chooseDifficulty(Scanner keyboard) {
		// Get the list of user commands.
		Difficulty[] commands = Difficulty.values();
		
		while (true) {
			displayDifficulties(commands);
			
			try {
				// Get the user's choice.
				int choice = keyboard.nextInt();
				
				// Perform user action.
				return commands[choice];
			} catch (InputMismatchException e) {
				keyboard.next();
				
				System.out.println("Invalid Input, please enter an Integer.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please enter an integer between 0 and " + (commands.length - 1) + ".");
			} System.out.println();
		}
	}
	
	private static void displayDifficulties(Difficulty[] commands) {
		System.out.println("Choose from the list of difficulties:");
		for (Difficulty sc : commands)
			System.out.printf("\t%d: %s\n", sc.ordinal(), sc.name());
		System.out.print("Input a command: ");
	}
}

enum Difficulty {
	EASY(5, 7), 
	MEDIUM(7, 9), 
	HARD(9), 
	NONE(0);
	
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