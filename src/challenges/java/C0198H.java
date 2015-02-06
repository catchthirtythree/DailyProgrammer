/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/2tfs0b/20150123_challenge_198_hard_words_with_enemies/
 */
package challenges.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import challenges.java.util.Util;

public class C0198H {
	private static enum BattleResult { WIN, LOSS, TIE; }
	
	private static enum Difficulty { EASY, MEDIUM, HARD, RANDOM; }

	public static class Trie {
		public Node root;

	    public Trie() {
	        root = new Node();
	        root.children = new Node[ALPHABET];
	    }

	    class Node {
	        public char data;
	    	public boolean word;
	        public Node[] children;
	        public Node() {}
	        public Node(char data) {
	        	this.data = data;
	        	this.children = new Node[ALPHABET];
	        }
	    }
	    
	    public void add(String data) {
	    	Node node = root;
	    	
	    	int length = data.length();
	    	for (int level = 0; level < length; ++level) {
	    		char c = data.charAt(level);
	    		int i = convertCharToInt(c);
	    		if (node.children[i] == null) {
	    			node.children[i] = new Node(c);
	    		}

	    		node = node.children[i];
	    	}
	    	
	    	node.word = true;
	    }
	    
	    public boolean exists(String data) {
	    	Node node = root;
	    	
	    	int length = data.length();
	    	for (int level = 0; level < length; ++level) {
	    		char c = data.charAt(level);
	    		int i = convertCharToInt(c);
	    		if (node.children[i] == null) {
	    			return false;
	    		}

	    		node = node.children[i];
	    	}
	    	
	    	return node.word;
	    }
	    
	    public List<String> findAll(String data) {
	    	List<String> words = new ArrayList<String>();
	    	
	    	String chars = removeDuplicates(data);
	    	for (char c : chars.toCharArray()) {
	    		int i = convertCharToInt(c);
	    		if (root.children[i] != null) {
	    			findAll(data.replaceFirst("" + c, ""), words, root.children[i], "" + c);
	    		}
	    	}
	    	
	    	return words;
	    }
	    
	    private static int convertCharToInt(char c) {
	    	return Character.toLowerCase(c) - 97;
	    }
	    
	    private void findAll(String data, List<String> words, Node next, String curr) {
	    	if (next.word) words.add(curr); 
	    	
	    	String chars = removeDuplicates(data);
	    	for (char c : chars.toCharArray()) {
	    		int i = convertCharToInt(c);
	    		if (next.children[i] != null) {
	    			findAll(data.replaceFirst("" + c, ""), words, next.children[i], curr + c);
	    		}
	    	}
	    }
	    
	    private String removeDuplicates(String word){
	        return word.replaceAll("(.)(?=.*\\1)", "");
	    }
	    
	    private static final int ALPHABET = 26;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		List<String> words = Util.getLinesFromFile(Util.WORDLIST);
		
		Trie trie = new Trie() {{
			words.forEach(word -> add(word));
		}};

		String hand, comp, user;
		List<String> possibilities;
		int turn = 0, p_score = 0, c_score = 0;
		System.out.println("Welcome to Words against Enemies.\n");
		
		Difficulty difficulty = chooseDifficulty(keyboard);
		int rounds = chooseRounds(keyboard);
		
		while (rounds > turn) {
			hand = generateHand(14);
			possibilities = trie.findAll(hand);
			
			Collections.sort(possibilities, new Comparator<String>() {
				@Override public int compare(String arg0, String arg1) {
					return Integer.compare(arg0.length(), arg1.length());
				}
			});
			
			System.out.printf("Turn %d -- You: %d Computer: %d\n", turn++, p_score, c_score);
			System.out.println("--------------------------------------");
			System.out.printf("Current hand: %s\n", hand.replaceAll(".(?=.)", "$0 "));
			
			user = chooseWord(keyboard, possibilities);
			comp = chooseAIWord(keyboard, possibilities, difficulty);
			
			List<Character> p = new ArrayList<Character>(), c = new ArrayList<Character>();
			for (char ch : user.toCharArray()) p.add(ch);
			for (char ch : comp.toCharArray()) c.add(ch);
			
			fight(p, c);
			
			p_score += p.size();
			c_score += c.size();
		}
		
		System.out.printf("Final Score -> You: %d Computer: %d\n", p_score, c_score);
	}
	
	private static String chooseAIWord(Scanner keyboard, List<String> possibilities, Difficulty difficulty) {
		switch (difficulty) {
			case EASY:
				return possibilities.get(0);
			
			case MEDIUM:
				return possibilities.get(possibilities.size() / 2);
			
			case HARD:
				return possibilities.get(possibilities.size() - 1);
				
			case RANDOM:
				return possibilities.get((int) (Math.random() * possibilities.size()));
				
			default:
				return "";
		}
	}
	
	private static String chooseWord(Scanner keyboard, List<String> possibilities) {
		String user;
		boolean valid = false;
		
		do {
			System.out.print("Choose a word: ");
			user = keyboard.nextLine();
			
			if (possibilities.contains(user))
				valid = true;
			
			System.out.printf("%s\n\n", valid ? user + " is a valid word." : user + " is not a valid word, try again.");
		} while (!valid);
		
		return user;
	}
	
	private static int chooseRounds(Scanner keyboard) {
		int choice = 0;
		boolean valid = false;
		
		do {
			System.out.print("Choose the amount of rounds to play: ");
			
			try {
				choice = keyboard.nextInt();
				valid = true;
			} catch (InputMismatchException e) {
				keyboard.next();
				
				System.out.println("Invalid Input, please enter an Integer.");
			} finally {
				keyboard.nextLine();
				System.out.println();
			}
		} while (!valid);
		
		return choice;
	}
	
	private static Difficulty chooseDifficulty(Scanner keyboard) {
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
	
	private static void displayDifficulties() {
		System.out.println("Choose the AI difficulty:");
		for (Difficulty sc : Difficulty.values())
			System.out.printf("\t%d: %s\n", sc.ordinal(), sc.name());
		System.out.print("Input a number: ");
	}
	
	private static BattleResult fight(List<Character> player, List<Character> ai) {
		String temp_player = player.toString(), temp_ai = ai.toString();
		
		for (int i = 0; i < player.size(); ++i) {
			Character c = player.get(i);
			int index;
			if ((index = ai.indexOf(c)) >= 0) {
				player.remove(i--);
				ai.remove(index);
			}
		}
		
		int s_size = (temp_player.length() > temp_ai.length() ? temp_player.length() : temp_ai.length());
		System.out.printf("Battle:\nplayer : %" + s_size + "s -> %s\nai     : %" + s_size + "s -> %s\n\n",
				temp_player, player, 
				temp_ai, ai
		);
		
		return player.size() == ai.size() ? BattleResult.TIE : player.size() > ai.size() ? BattleResult.WIN : BattleResult.LOSS;
	}
	
	private static String generateHand(int size) {
		List<Character> characters = new ArrayList<Character>();
		char[] vowels = "aeiou".toCharArray(), consonants = "bcdfghjklmnpqrstvwxzy".toCharArray();
		
		for (int i = 0; i < size; ++i) {
			if (i % 3 == 0)
				characters.add(vowels[(int) (Math.random() * vowels.length)]);
			else
				characters.add(consonants[(int) (Math.random() * consonants.length)]);
		}
		
		Collections.shuffle(characters);
		
		StringBuilder sb = new StringBuilder();
		for (char c : characters)
			sb.append(c);
		
		return sb.toString();
	}
}