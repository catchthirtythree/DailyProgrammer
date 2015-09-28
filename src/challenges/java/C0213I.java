package challenges.java;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class C0213I {
	enum KeyboardLayout {
		AZERTY((
			"azertyuiop" +
			"qsdfghjklm" +
			"^wxcvbn  ^" +
			"   #####  "
			).toCharArray(), 10, 4
		),
		
		QWERTY((
			"qwertyuiop" +
			"asdfghjkl." +
			"^zxcvbnm.^" +
			"...     .."
			).toCharArray(), 10, 4
		);
		
		char[] keys;
		int width, height;
		KeyboardLayout(char[] layout, int width, int height) {
			this.keys = layout;
			this.width = width;
			this.height = height;
		}
	}
	
	static class Keyboard {
		private KeyboardLayout layout;
		
		public Keyboard(KeyboardLayout layout) {
			this.layout = layout;
		}
		
		public int effort(Point from, char to) {
			String keys = new String(layout.keys);

			int best = Integer.MAX_VALUE, effort = 0, position = 0;
			while ((position = keys.indexOf(to, ++position)) != -1) {
				if ((effort = nn(from.x, from.y, position % layout.width, position / layout.width)) < best) 
					best = effort;
			}
			
			return best;
		}
		
		public Point find(Point from, char to) {
			String keys = new String(layout.keys);
			
			Point point = new Point();
			int best = Integer.MAX_VALUE, effort = 0, position = 0;
			while ((position = keys.indexOf(to, ++position)) != -1) {
				int nx = position % layout.width, ny = position / layout.width;
				if ((effort = nn(from.x, from.y, position % layout.width, position / layout.width)) < best) { 
					best = effort;
					point.x = nx;
					point.y = ny;
				}
			}
			
			return point;
		}
		
		public int nn(int cx, int cy, int tx, int ty) {
			return Math.abs(cx - tx) +  Math.abs(cy - ty);
		}
	}
	
	static class Hand {
		private Keyboard keyboard;
		
		public String name;
		public Point position;
		public int totalEffort, lastEffort;
		
		public Map<Long, Point> actions;
		
		public Hand(Keyboard keyboard, String name) {
			this(keyboard, name, new Point());
		}
		
		public Hand(Keyboard keyboard, String name, Point position) {
			this.keyboard = keyboard;
			this.name = name;
			this.position = position;
			
			this.actions = new HashMap<Long, Point>();
		}
		
		public int effort(char to) {
			return keyboard.effort(position, to);	
		}
		
		public Point find(char to) {
			return keyboard.find(position, to);
		}
		
		public void move(char to) {
			this.lastEffort = effort(to);
			this.totalEffort += lastEffort;
			
			this.position = find(to);
			
			this.actions.put(System.nanoTime(), position);
		}
	}
	
	//private static final String fmt = "%c: Move %s from %c. (Effort: %i)\n";
	public static void main(String[] args) {
		String input = String.join(" ", args);
		
		Keyboard keyboard = new Keyboard(KeyboardLayout.QWERTY);
		Hand[] hands = {new Hand(keyboard, "Left Hand"), new Hand(keyboard, "Right Hand")};
		
		// TODO: Initial placement for hands.
		
		for (char c : input.toCharArray()) {
			if (Character.isUpperCase(c)) {
				char t = Character.toLowerCase(c);
				if ((hands[0].effort('^') + hands[1].effort(t)) < (hands[0].effort(t) + hands[1].effort('^'))) {
					hands[0].move('^');
					hands[1].move(t);
				} else {
					hands[0].move(t);
					hands[1].move('^');
				}
			} else {
				if (hands[0].effort(c) > hands[1].effort(c)) {
					hands[0].move(c);
				} else {
					hands[1].move(c);
				}
			}
		}
	}
}