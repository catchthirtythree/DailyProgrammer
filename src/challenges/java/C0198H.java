/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/2tfs0b/20150123_challenge_198_hard_words_with_enemies/
 */
package challenges.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class C0198H {
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
	    
	    public boolean search(String data) {
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
	    
	    private static int convertCharToInt(char c) {
	    	return Character.toLowerCase(c) - 97;
	    }
	    
	    /* http://stackoverflow.com/posts/18166130/revisions */
	    private String removeDuplicates(String word){
	        return word.replaceAll("(.)(?=.*\\1)", "");
	    }
	    
	    private static final int ALPHABET = 26;
	}
	
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		List<String> words = getLinesFromFile("ext/" + C0198H.class.getSimpleName());
		
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
			
			System.out.printf("\nTurn %d -- Points -> You: %d Computer: %d\n", turn++, p_score, c_score);
			System.out.println("--------------------------------------");
			System.out.printf("Current letters: %s\n", hand.replaceAll(".(?=.)", "$0 "));
			
			user = chooseWord(keyboard, possibilities);
			comp = chooseAIWord(keyboard, possibilities, difficulty);
			
			List<Character> p = new ArrayList<Character>(), a = new ArrayList<Character>();
			for (char c : user.toCharArray()) p.add(c);
			for (char c : comp.toCharArray()) a.add(c);
			
			fight(p, a);
			
			p_score += p.size();
			c_score += a.size();
		}
		
		System.out.printf("\nFinal Score -> You: %d Computer: %d\n", p_score, c_score);
	}
	
	private static String chooseAIWord(Scanner keyboard, List<String> possibilities, Difficulty difficulty) {
		String comp = "";
		
		switch (difficulty) {
		case EASY:
			comp = possibilities.get((int) (Math.random() * possibilities.size()));
			break;
		case HARD:
			List<String> length = new ArrayList<String>();
			int max = 0;
			for (int i = 0; i < possibilities.size(); ++i) {
				String s = possibilities.get(i);
				if (s.length() > max) {
					length.clear();
					length.add(s);
					max = s.length();
				} else if (s.length() == max) {
					length.add(s);
				}
			}
			
			comp = length.get((int) (Math.random() * length.size()));
			break;
		}
		
		return comp;
	}
	
	private static String chooseWord(Scanner keyboard, List<String> possibilities) {
		String user;
		boolean exists = false;
		do {
			System.out.print("Choose a word: ");
			user = keyboard.nextLine();
			
			if (possibilities.contains(user))
				exists = true;
			
			System.out.printf("%s\n\n", exists ? user + " exists." : user + " does not exist, try again.");
		} while (!exists);
		
		return user;
	}
	
	private static int chooseRounds(Scanner keyboard) {
		while (true) {
			System.out.print("Choose the amount of rounds to play: ");
			
			try {
				int input = keyboard.nextInt();
				keyboard.nextLine();
				return input;
			} catch (InputMismatchException e) {
				keyboard.next();
				
				System.out.println("Invalid Input, please enter an Integer.");
			}
		}
	}
	
	private static Difficulty chooseDifficulty(Scanner keyboard) {
		while (true) {
			displayDifficulties();
			
			try {
				return Difficulty.values()[keyboard.nextInt()];
			} catch (InputMismatchException e) {
				keyboard.next();
				
				System.out.println("Invalid Input, please enter an Integer.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Please enter an integer between 0 and " + (Difficulty.values().length - 1) + ".");
			} System.out.println();
		}
	}
	
	private static void displayDifficulties() {
		System.out.println("Choose the AI difficulty:");
		for (Difficulty sc : Difficulty.values())
			System.out.printf("\t%d: %s\n", sc.ordinal(), sc.name());
		System.out.print("Input a number: ");
	}
	
	private static enum Difficulty {
		EASY, HARD;
	}
	
	/**
	 * Words with Enemies fight method.
	 * @param player
	 * @param ai
	 * @return
	 */
	public static BattleResult fight(List<Character> player, List<Character> ai) {
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
		System.out.printf("Battle:\nplayer : %" + s_size + "s -> %s\nai     : %" + s_size + "s -> %s\n",
				temp_player, player, 
				temp_ai, ai
		);
		
		return player.size() == ai.size() ? BattleResult.TIE : player.size() > ai.size() ? BattleResult.WIN : BattleResult.LOSS;
	}
	
	private static enum BattleResult { WIN, LOSS, TIE; }
	
	/**
	 * Words with Enemies random hand generator (1/3 vowels, 2/3 consonants).
	 * @param size
	 * @return
	 */
	public static String generateHand(int size) {
		List<Character> characters = new ArrayList<Character>();
		
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
	
	private static final char[] vowels = "aeiou".toCharArray(), consonants = "bcdfghjklmnpqrstvwxzy".toCharArray();
	
	/**
	 * Retrieve word list from from file.
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static List<String> getLinesFromFile(String filename) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String line;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		} reader.close();
		
		return lines;
	}
}