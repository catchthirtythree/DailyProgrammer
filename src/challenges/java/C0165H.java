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
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;

public class C0165H extends Canvas {
	class Forest {
		public int width, height, months;
		
		public List<Entity> entities;
		public Tile[] tiles;

		public final int P_BEARS = 20;
		public final int P_LUMBERJACKS = 1;
		public final int P_TREES = 5;
		public int n_bears, n_lumberjacks, n_trees;
		
		public Forest(int width, int height) {
			this.width = width;
			this.height = height;
			this.months = 1;
			
			this.entities = new CopyOnWriteArrayList<Entity>();
			this.tiles = new Tile[width * height];
			
			this.n_bears = tiles.length * P_BEARS / 100;
			this.n_lumberjacks = tiles.length * P_LUMBERJACKS / 100;
			this.n_trees = tiles.length * P_TREES / 100;
			
			init();
		}
		
		public void init() {
			/* Place tiles. */
			for (int y = 0; y < height; ++y) {
				for (int x = 0; x < width; ++x) {
					this.tiles[x + y * width] = Tile.GRASS;
				}
			}
	
			/* Place entities. */
			for (int i = 0; i < n_bears; ++i) {
				int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
				if (tiles[x + y * width].walkable) {
					entities.add(new Bear(x, y));
					//tiles[coordinate] = new Bear(x, y);
				}
			}
			
			for (int i = 0; i < n_lumberjacks; ++i) {
				int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
				if (tiles[x + y * width].walkable) {
					entities.add(new Lumberjack(x, y));
					//tiles[coordinate] = new Lumberjack(x, y);
				}
			}
			
			for (int i = 0; i < n_trees; ++i) {
				int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
				if (tiles[x + y * width].walkable) {
					int r = (int) (Math.random() * tiles.length) % 4;
					TreeType type = (r == 0 ? TreeType.SAPLING : (r == 1 ? TreeType.ELDER : TreeType.TREE));

					entities.add(new Tree(type, x, y));
					//tiles[coordinate] = new Tree(type, x, y);
				}
			}
		}
		
		public void update() {
			for (Entity e : entities) {
				e.update(this);
			}

			System.out.println("Bears: " + n_bears + ".");
			System.out.println("Lumberjacks: " + n_lumberjacks + ".");
			System.out.println("Trees: " + n_trees + ".");
			System.out.println();
			
			++months;
		}
		
		public void render(Screen screen) {
			/* Render tile map. */
			for (int y = 0; y < height; ++y) {
				for (int x = 0; x < width; ++x) {
					tiles[x + y * width].render(screen, x, y);
				}
			}
			
			/* Render entities. */
			for (Entity e : entities) {
				e.render(screen);
			}
		}
	}
	
	enum Tile {
		GRASS(0x00FF00, true);
		
		int color;
		boolean walkable;
		Tile(int color, boolean walkable) {
			this.color = color;
			this.walkable = walkable;
		}
		
		public void render(Screen screen, int x, int y) {
			screen.render(x, y, color);
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
					
					if (forest.tiles[nx + ny * forest.width] == Tile.GRASS) {
						points.add(new Point(nx, ny));
					}
				}
			}
			
			/* If there are no points available, the lumberjack cannot move. */
			if (points.size() == 0)
				return false;
			
			/* Otherwise, choose a random point. */
			Point point = points.get((int) (Math.random() * points.size()));
			
			this.x = point.x;
			this.y = point.y;
			
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
					
					for (Entity e : forest.entities) {
						if (e instanceof Tree && (e.x == nx && e.y == ny)) {
							switch (((Tree) e).type) {
							case TREE:
								forest.entities.remove(e);
								--forest.n_trees;
								lumber += 1;
								return true;
								
							case ELDER:
								forest.entities.remove(e);
								--forest.n_trees;
								lumber += 2;
								return true;
								
							default:
								break;
							}
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
	
	private final int TICKS_PER_SECOND = 15;
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