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

		String hand;
		int turn = 0, p_score = 0, c_score = 0;
		System.out.println("Welcome to Words against Enemies.");

		hand = generateHand(14);
		System.out.printf("\nTurn %d -- Points -> You: %d Computer: %d\n", turn, p_score, c_score);
		System.out.println("--------------------------------------");
		System.out.printf("Current letters: %s\n", hand.replaceAll(".(?=.)", "$0 "));
		
		/*
		 Welcome to Words with Enemies!
		 Select an AI difficulty:
		 1) easy
		 2) Hard
		 --> 1
		 You have selected Easy! - Let's begin!

		 Turn 1 -- Points You: 0 Computer: 0
		 -----------------------------------------
		 Your letters: a b c d e k l m o p t u
		 Your word-> rekt
		 I am sorry but you cannot spell rekt with your letters. Try again.
		 Your letters: a b c d e k l m o p t u
		 Your word-> top
		 Valid Word! Open Fire!!!!
		 AI selects "potluck"

		 top vs potluck -- Computer wins.
		 You had 0 letters left over - you score 0 points
		 AI had 4 letters left over - AI score 4 points

		 Turn 2 -- Points You: 0 Computer: 4
		 -----------------------------------------
		 Your letters: e i o k a l m q t u w y
		 */
		
		System.out.print("Choose a word: ");
		String line = keyboard.nextLine();
		
		List<String> poss = trie.findAll(line);
		System.out.printf("The word '%s' can be found in the dictionary: %s", line, poss.contains(line));
	}
	
	public static Difficulty chooseDifficulty(Scanner keyboard) {
		return null;
	}
	
	private static enum Difficulty {
		EASY, MEDIUM, HARD;
	}
	
	/**
	 * Words with Enemies fight method.
	 * @param w_player
	 * @param w_ai
	 * @return
	 */
	public static BattleResult fight(List<Character> w_player, List<Character> w_ai) {
		String t_player = w_player.toString(), t_ai = w_ai.toString();
		
		for (int i = 0; i < w_player.size(); ++i) {
			Character c = w_player.get(i);
			int index;
			if ((index = w_ai.indexOf(c)) >= 0) {
				w_player.remove(i--);
				w_ai.remove(index);
			}
		}
		
		int s_size = (t_player.length() > t_ai.length() ? t_player.length() : t_ai.length());
		System.out.printf("Battle:\nplayer : %" + s_size + "s -> %s\nai     : %" + s_size + "s -> %s\n",
				t_player, w_player, 
				t_ai, w_ai
		);
		
		return w_player.size() == w_ai.size() ? BattleResult.TIE : w_player.size() > w_ai.size() ? BattleResult.WIN : BattleResult.LOSS;
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