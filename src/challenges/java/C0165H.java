/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/27h53e/662014_challenge_165_hard_simulated_ecology_the/
 */
package challenges.java;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class C0165H extends Canvas {
	class Forest {
		public final double P_BEARS = .02;
		public final double P_LUMBERJACKS = .1;
		public final double P_TREES = .5;
		
		public int width, height, months;
		public List<Entity> entities;
		public Entity[] tiles;
		public Forest(int width, int height) {
			this.width = width;
			this.height = height;
			this.months = 1;
			this.tiles = new Entity[width * height];
			
			for (int y = 0; y < height; ++y) {
				for (int x = 0; x < width; ++x) {
					this.tiles[x + y * width] = new Grass(x, y);
				}
			}
			
			init();
		}
		
		public void init() {
			for (int y = 0; y < height; ++y) {
				for (int x = 0; x < width; ++x) {
					this.tiles[x + y * width] = new Grass(x, y);
				}
			}
			
			int n_bears = (int) (tiles.length * P_BEARS);
			while (n_bears > 0) {
				int coordinate;
				int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
				if (tiles[coordinate = x + y * width] instanceof Grass) {
					tiles[coordinate] = new Bear(x, y);
					--n_bears;
				}
			}
			
			int n_lumberjacks = (int) (tiles.length * P_LUMBERJACKS);
			while (n_lumberjacks > 0) {
				int coordinate;
				int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
				if (tiles[coordinate = x + y * width] instanceof Grass) {
					tiles[coordinate] = new Lumberjack(x, y);
					--n_lumberjacks;
				}
			}
			
			int n_trees = (int) (tiles.length * P_TREES);
			while (n_trees > 0) {
				int coordinate;
				int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
				if (tiles[coordinate = x + y * width] instanceof Grass) {
					int r = (int) (Math.random() * tiles.length) % 4;
					TreeType type = (r == 0 ? TreeType.SAPLING : (r == 1 ? TreeType.ELDER : TreeType.TREE));
					
					tiles[coordinate] = new Tree(type, x, y);
					--n_trees;
				}
			}
		}
		
		public void place(Entity e, int x, int y) {
			tiles[x + y * width] = e;
			//System.out.println("place pos: " + x + ", " + y + ", " + (x + y * width));
		}
		
		public void swap(int x1, int y1, int x2, int y2) {
			Entity t1 = tiles[x1 + y1 * width], t2 = tiles[x2 + y2 * width];
			
			t1.x = x2;
			t1.y = y2;
			
			t2.x = x1;
			t2.y = y1;
			
			tiles[x1 + y1 * width] = t2;
			tiles[x2 + y2 * width] = t1;
		}
		
		public void update() {
			int l = 0, g = 0;
			for (Entity e : tiles) {
				if (e instanceof Lumberjack) ++l;
				if (e instanceof Grass) ++g;
				e.update(this);
			}
			System.out.println("Lumberjacks: " + l + ".");
			System.out.println("Grass: " + g + ".");
			++months;
		}
		
		public void render(Screen screen) {
			for (Entity e : tiles) {
				e.render(screen);
			}
		}
	}
	
	class Entity {
		public int color, x, y;
		public Entity(int color, int x, int y) {
			this.color = color;
			this.x = x;
			this.y = y;
		}
		public void update(Forest forest) {}
		public void render(Screen screen) {
			screen.render(x, y, color);
		}
		@Override public String toString() {
			return "Entity [color=" + color + ", x=" + x + ", y=" + y + "]";
		}
	}
	
	class Bear extends Entity {
		public Bear(int x, int y) {
			super(0x502000, x, y);
		}
	}
	
	class Grass extends Entity {
		public Grass(int x, int y) {
			super(0x00FF00, x, y);
		}
	}
	
	class Lumberjack extends Entity {
		public int lumber;
		public Lumberjack(int x, int y) {
			super(0xFF0000, x, y);
			this.lumber = 0;
		}
		
		public boolean move(Forest forest) {
			List<Point> points = new ArrayList<Point>();
			
			/* Add points around the lumberjack as possible locations (must be walkable [Grass]). */
			for (int yd = -1; yd <= 1; ++yd) {
				for (int xd = -1; xd <= 1; ++xd) {
					if (xd == 0 && yd == 0)
						continue;
					
					int nx = x + xd, ny = y + yd;
					if (nx < 0 || nx >= forest.width || ny < 0 || ny >= forest.height)
						continue;
					
					if (forest.tiles[nx + ny * forest.width] instanceof Grass) {
						points.add(new Point(nx, ny));
					}
				}
			}
			
			/* If there are no points available, the lumberjack cannot move. */
			if (points.size() == 0)
				return false;
			
			/* Otherwise, choose a random point. */
			Point point = points.get((int) (Math.random() * points.size()));
			
			/* TODO The placing / swapping is not working, lumberjacks get multiplied. */
			/* Move the lumberjack to the new spot and replace the old spot. */
			//forest.place(new Grass(x, y), x, y);
			//forest.place(this, point.x, point.y);
			forest.swap(x, y, point.x, point.y);
			
			return true;
		}
		
		public boolean scan(Forest forest) {
			for (int yd = -1; yd <= 1; ++yd) {
				for (int xd = -1; xd <= 1; ++xd) {
					if (xd == 0 && yd == 0)
						continue;
					
					int nx = x + xd, ny = y + yd;
					if (nx < 0 || nx >= forest.width || ny < 0 || ny >= forest.height)
						continue;
					
					Entity e;
					// If the entity is a Tree type.
					if ((e = forest.tiles[nx + ny * forest.width]) instanceof Tree) {
						switch (((Tree) e).type) {
						case TREE:
							forest.place(new Grass(nx, ny), nx, ny);
							/*forest.place(new Grass(x, y), x, y);
							forest.place(this, nx, ny);
							this.x = nx;
							this.y = ny;*/
							lumber += 1;
							return true;
							
						case ELDER:
							forest.place(new Grass(nx, ny), nx, ny);
							/*forest.place(new Grass(x, y), x, y);
							forest.place(this, nx, ny);
							this.x = nx;
							this.y = ny;*/
							lumber += 2;
							return true;
							
						default:
							break;
						}
					}
				}
			}
				
			return false;
		}
		
		public void update(Forest forest) {
			int moves = 1;
			while (moves > 0) {
				/* If a tree can be found, break out of the loop. */
				if (scan(forest)) break;
				/* If no tiles are open break out of the loop, otherwise loop. */
				if (!move(forest)) break; else --moves;
			}
		}
	}
	
	class Tree extends Entity {
		public TreeType type;
		public Tree(TreeType type, int x, int y) {
			super(type.color, x, y);
			this.type = type;
		}
		
		public void update(Forest forest) {
			switch (type) {
			case SAPLING:
				break;
				
			case TREE:
				break;
				
			case ELDER:
				break;
			}
		}
	}
	
	enum TreeType { 
		ELDER(0x005500), TREE(0x008800), SAPLING(0x00BB00);
		
		int color;
		TreeType(int color) {
			this.color = color;
		}
	}
	
	class Screen {
		public int width, height;
		public int[] pixels;
		public Screen(int width, int height) {
			this.width = width;
			this.height = height;
			this.pixels = new int[width * height];
		}
		public void clear() {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = 0;
			}
		}
		public void random() {
			for (int j = 0; j < pixels.length; j++) {
				pixels[j] = new Random().nextInt(0xFFFFFF);
			}
		}
		public void render(int x, int y, int pixel) {
			pixels[x + y * width] = pixel;
		}
	}
	
	/* MAIN WINDOW */
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH  = 100;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE  = 8;
	
	private final int TICKS_PER_SECOND = 1;
	private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	
	public static final String TITLE = "[C0165H] Forest Simulation";

	private JFrame frame;
	private Forest forest;
	private Screen screen;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public C0165H() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
        frame = new JFrame();
        forest = new Forest(WIDTH, HEIGHT);
        screen = new Screen(WIDTH, HEIGHT);
	}
	
	public void run() {
	    int loops;
		// double interpolation = 0;
		double next_game_tick = System.currentTimeMillis();

	    while (true) {
	        loops = 0;
	        
	        while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {
	            update();

	            next_game_tick += SKIP_TICKS;
	            ++loops;
	        }
	        
	        // interpolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick / (double) SKIP_TICKS);
	        render();
	    }
	}
	
	public void update() {
		screen.clear();
		forest.update();
	}
	
	public void render() {
        BufferStrategy bs;
		if ((bs = getBufferStrategy()) == null) {
			createBufferStrategy(3);
			return;
		}

		forest.render(screen);
		for (int i = 0; i < pixels.length; ++i) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		C0165H game = new C0165H();
		
		game.frame.setResizable(false);
		game.frame.setTitle(TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.run();
	}
}