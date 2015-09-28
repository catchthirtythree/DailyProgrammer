/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/378h44/20150525_challenge_216_easy_texas_hold_em_1_of_3/
 */
package challenges.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class C0216E {
	enum Value {
		Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;
	}

	enum Suit {
		Clubs, Diamonds, Spades, Hearts;
	}

	static class Card {
		public Value value;
		public Suit suit;

		public Card(Value value, Suit suit) {
			this.value = value;
			this.suit = suit;
		}
		
		public String toString() {
			return value + " of " + suit;
		}
	}

	static class Deck {
		public Stack<Card> cards = new Stack<Card>();
		
		public Deck() {
			for (Value value : Value.values())
				for (Suit suit : Suit.values())
					cards.push(new Card(value, suit));
		}
		
		public Card[] deal(int n) {
			Card[] cards = new Card[n];
			
			for (int i = 0; i < n; ++i)
				cards[i] = draw();
			
			return cards;
		}
		
		public Card draw() {
			return cards.pop();
		}

		public void shuffle() {
			Collections.shuffle(cards);
		}
		
		public String toString() {
			return cards.toString();
		}
	}
	
	@SuppressWarnings("resource") public static void main(String[] args) {
		/* Intialize deck and scanner */
		Deck deck = new Deck();
		Scanner keyboard = new Scanner(System.in);
		
		/* Get number of hands */
		System.out.print("How many players? ");
		int players = keyboard.nextInt();
		Card[][] hands = new Card[players][];
		
		/* Shuffle */
		deck.shuffle();
		
		/* Set river, turn and flop */
		Card[] river = deck.deal(3), turn = deck.deal(1), flop = deck.deal(1);
		
		/* Create player hands */
		for (int i = 0; i < hands.length; ++i) {
			hands[i] = deck.deal(2);
		}
		
		/* Display players, river, turn and flop */
		int index = 0;
		for (Card[] hand : hands)
			System.out.printf("Player %d: %s\n", ++index, Arrays.toString(hand));
		
		System.out.println("River: " + Arrays.toString(river));
		System.out.println("Turn: " + Arrays.toString(turn));
		System.out.println("Flop: " + Arrays.toString(flop));
	}
}