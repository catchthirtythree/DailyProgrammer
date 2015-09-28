/**
 * @author Michael
 * 
 * http://www.reddit.com/r/dailyprogrammer/comments/2qmz12/20141228_challenge_195_easy_symbolic_link/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C0195I {
	static class Die {
		private int sides;
		private int last;
		public Die(int sides) {
			this.sides = sides;
		}
		
		public int roll() {
			return (last = (int) (Math.random() * sides) + 1);
		}
		
		public int getLast() {
			return last;
		}
	}
	
	public static void main(String[] args) {
		String input = "1d12 5d6";
		
		// Split string into target and scoring dice.
		
		String[] split = input.split(" ");
		String target = split[0];
		String scoring = split[1];
		
		// Create the target die and roll it.
		
		Die target_die = new Die(Integer.parseInt(target.split("d")[1]));
		int target_roll = target_die.roll();
		
		// Create the scoring dice and roll them.
		
		String[] split_scoring = scoring.split("d");
		Die[] scoring_dice = new Die[Integer.parseInt(split_scoring[0])];
		int[] scoring_rolls = new int[scoring_dice.length];
		
		for (int i = 0; i < scoring_dice.length; ++i) {
			scoring_dice[i] = new Die(Integer.parseInt(split_scoring[1]));
			scoring_rolls[i] = scoring_dice[i].roll();
		}
		
		// Display some stuff.
		
		System.out.println("Target roll:   " + target_roll);
		System.out.println("Scoring rolls: " + Arrays.toString(scoring_rolls));
		
		// Find all the combinations that add up to the target roll.
		
		combinations(target_roll, scoring_rolls);
	}

	public static void combinations(int target, int[] scoring_rolls) {
		// Start a list for each number in the list.
		
		for (int i = 0; i < scoring_rolls.length; ++i) {
			List<Integer> set = new ArrayList<Integer>();
			set.add(scoring_rolls[i]);
			
			combinations(target, scoring_rolls, i + 1, set);
		}
	}
	
	public static void combinations(int target, int[] scoring_rolls, int start, List<Integer> set) {
		// Break conditions.
		
		int sum = calculate_sum(set);
		if (sum == target) {
			// Return set?
			System.out.println("Winning combination: " + Arrays.toString(set.toArray()));
		} else if (sum > target) {
			return;
		}
		
		// Create combinations with new sets.
		
		for (int i = start; i < scoring_rolls.length; ++i) {
			List<Integer> new_set = new ArrayList<Integer>();
			for (int k = 0; k < set.size(); ++k) {
				new_set.add(set.get(k));
			} new_set.add(scoring_rolls[i]);
			
			combinations(target, scoring_rolls, i + 1, new_set);
		}
	}
	
	public static int calculate_sum(List<Integer> set) {
		int sum = 0;
		for (int i : set) {
			sum += i;
		} return sum;
	}
}