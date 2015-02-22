/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/27h53e/662014_challenge_165_hard_simulated_ecology_the/
 */
package challenges.java;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.swing.JFrame;

public class C0165H extends Canvas {
	private static final boolean DEBUG = false;
	
	class Forest {
		private static final int BEAR_MOVES = 1;
		private static final int LUMBERJACK_MOVES = 1;
		
		public int width, height;
		public int months = 1;
		
		public Map<Point, List<Entity>> entities;
		public Tile[] tiles;

		public final int P_BEARS = 2;
		public final int P_LUMBERJACKS = 10;
		public final int P_TREES = 50;
		public int n_bears, n_lumberjacks, n_trees;
		
		public int lumber, maws;
		
		public Forest(int width, int height) {
			this.width = width;
			this.height = height;
			
			this.entities = new ConcurrentHashMap<Point, List<Entity>>();
			this.tiles = new Tile[width * height];
			
			this.n_bears = (tiles.length * P_BEARS) / 100;
			this.n_lumberjacks = (tiles.length * P_LUMBERJACKS) / 100;
			this.n_trees = (tiles.length * P_TREES) / 100;
			
			init();
		}
		
		public void addEntity(Entity e, int x, int y) {
			Point point = new Point(x, y);
			
			/* Change the entity's position. */
			e.x = x;
			e.y = y;
			
			List<Entity> list;
			if ((list = entities.get(point)) == null) {
				list = new CopyOnWriteArrayList<Entity>();
			} list.add(e);
			
			entities.put(point, list);
		}
		
		public void removeEntity(Entity e, int x, int y) {
			Point point = new Point(x, y);
			
			List<Entity> list;
			if ((list = entities.get(point)) == null) {
				list = new CopyOnWriteArrayList<Entity>();
			} list.remove(e);
			
			entities.put(point, list);
		}
		
		private void init() {
			/* Place tiles. */
			for (int y = 0; y < height; ++y) {
				for (int x = 0; x < width; ++x) {
					this.tiles[x + y * width] = Tile.GRASS;
				}
			}
	
			/* Place entities. */
			for (int i = 0; i < n_bears; ++i) {
				boolean placed = false;
				while (!placed) {
					int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
					
					if (tiles[x + y * width].walkable) {
						if (entities.get(new Point(x, y)) == null) {
							addEntity(new Bear(x, y), x, y);
							placed = true;
						}
					}
				}
			}
			
			for (int i = 0; i < n_lumberjacks; ++i) {
				boolean placed = false;
				while (!placed) {
					int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
					
					if (tiles[x + y * width].walkable) {
						if (entities.get(new Point(x, y)) == null) {
							addEntity(new Lumberjack(x, y), x, y);
							placed = true;
						}
					}
				}
			}
			
			for (int i = 0; i < n_trees; ++i) {
				boolean placed = false;
				while (!placed) {
					int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
					
					if (tiles[x + y * width].walkable) {
						int r = (int) (Math.random() * tiles.length) % 4;
						TreeType type = (r == 0 ? TreeType.SAPLING : (r == 1 ? TreeType.ELDER : TreeType.TREE));

						if (entities.get(new Point(x, y)) == null) {
							addEntity(new Tree(type, x, y), x, y);
							placed = true;
						}
					}
				}
			}
		}
		
		private void harvest() {
			/* Get all instances of lumberjacks in list of entities. */
			List<Lumberjack> lumberjacks = new ArrayList<Lumberjack>();
			for (List<Entity> ents : entities.values()) {
				List<Lumberjack> t = ents.stream()
					   .filter(e -> e instanceof Lumberjack)
					   .map(e -> (Lumberjack) e).collect(Collectors.toList());
				
				lumberjacks.addAll(t);
			}
			
			/* If lumber is equal to or greater than number or lumberjacks, create lumberjacks. */
			if (lumber >= n_lumberjacks) {
				if (C0165H.DEBUG) {
					System.out.println("Creating a lumberjack.\n");
				}

				/* Create (lumber / 10) number of lumberjacks. */
				int new_lumberjacks = lumber / 10;
				while (--new_lumberjacks >= 0) {
					boolean placed = false;
					while (!placed) {
						int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
						
						List<Entity> list = forest.entities.get(new Point(x, y));
						if (list == null || list.size() == 0) {
							if (tiles[x + y * width].walkable) {
								addEntity(new Lumberjack(x, y), x, y);
								placed = true;
								++n_lumberjacks;
							}
						}
					}
				}
				
			/* Otherwise, remove a lumberjack. */
			} else {
				if (C0165H.DEBUG) {
					System.out.println("Removing a lumberjack.\n");
				}
				
				Lumberjack lumberjack = lumberjacks.get((int) (Math.random() * lumberjacks.size()));
				removeEntity(lumberjack, lumberjack.x, lumberjack.y);
				--n_lumberjacks;
			}
		}
		
		private void mawing() {
			/* Get all instances of lumberjacks in list of entities. */
			List<Bear> bears = new ArrayList<Bear>();
			for (List<Entity> ents : entities.values()) {
				List<Bear> t = ents.stream()
					   .filter(e -> e instanceof Bear)
					   .map(e -> (Bear) e).collect(Collectors.toList());
				
				bears.addAll(t);
			}
			
			/* If there are no maws, create a bear. */
			if (maws == 0) {
				if (C0165H.DEBUG) {
					System.out.println("Creating a bear.\n");
				}
				
				boolean placed = false;
				while (!placed) {
					int x = (int) (Math.random() * width), y = (int) (Math.random() * height);
					
					List<Entity> list = forest.entities.get(new Point(x, y));
					if (list == null || list.size() == 0) {
						if (tiles[x + y * width].walkable) {
							addEntity(new Bear(x, y), x, y);
							placed = true;
							++n_bears;
						}
					}
				}
			/* Otherwise, remove a bear. */
			} else {
				if (C0165H.DEBUG) {
					System.out.println("Removing a bear.\n");
				}
				
				Bear bear = bears.get((int) (Math.random() * bears.size()));
				removeEntity(bear, bear.x, bear.y);
				--n_bears;
			}
		}
		
		public void update() {
			if (C0165H.DEBUG) {
				System.out.printf(
						"Bears: %d\nLumberjacks: %d\nTrees: %d\n\n", 
						n_bears,
						n_lumberjacks,
						n_trees
				);
			}
			
			/* If a year has passed, do yearly things. */
			if (months % 12 == 0) {
				harvest();
				mawing();
				
				/* Reset lumber harvested and maw accidents. */ 
				lumber = 0;
				maws = 0;
			}
			
			/* Update all entities. */
			for (List<Entity> list : entities.values()) {
				for (Entity e : list) {
					e.update(this);
				}
			}
			
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
			for (List<Entity> list : entities.values()) {
				for (Entity e : list) {
					e.render(screen);
				}
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
	}
	
	class Bear extends Entity {
		public Bear(int x, int y) {
			super(0x502000, x, y);
		}
		
		private boolean maw(Lumberjack lumberjack) {
			/* Remove the lumberjack from entities and from the counter. */
			forest.removeEntity(lumberjack, lumberjack.x, lumberjack.y);
			--forest.n_lumberjacks;
			++forest.maws;
			
			/* Move bear to lumberjack coordinates. */
			forest.removeEntity(this, x, y);
			/*this.x = lumberjack.x;
			this.y = lumberjack.y;*/
			forest.addEntity(this, x, y);
			
			return true;
		}
		
		private boolean move(Forest forest) {
			List<Point> points = new ArrayList<Point>();
			
			/* Add points around the lumberjack as possible locations (must be walkable [Grass]). */
			for (int yd = -1; yd <= 1; ++yd) {
				for (int xd = -1; xd <= 1; ++xd) {
					if (xd == 0 && yd == 0)
						continue;
					
					int nx = x + xd, ny = y + yd;
					if (nx < 0 || nx >= forest.width || ny < 0 || ny >= forest.height)
						continue;
					
					//List<Entity> list = forest.entities.get(new Point(nx, ny));
					//if (list == null || list.size() == 0) {
						if (forest.tiles[nx + ny * forest.width].walkable) {
							points.add(new Point(nx, ny));
						}
					//}
				}
			}
			
			/* If there are no lumberjacks available, the bear cannot maw. */
			if (points.size() == 0)
				return false;
			
			/* Otherwise, choose a random point. */
			Point point = points.get((int) (Math.random() * points.size()));
			
			forest.removeEntity(this, x, y);
			/*this.x = point.x;
			this.y = point.y;*/
			forest.addEntity(this, point.x, point.y);
			
			return true;
		}

		private boolean scan(Forest forest) {
			List<Lumberjack> lumberjacks = new ArrayList<Lumberjack>();
			
			/* Scan for possible lumberjacks to maw around the bear. */
			for (int yd = -1; yd <= 1; ++yd) {
				for (int xd = -1; xd <= 1; ++xd) {
					if (xd == 0 && yd == 0)
						continue;
					
					int nx = x + xd, ny = y + yd;
					if (nx < 0 || nx >= forest.width || ny < 0 || ny >= forest.height)
						continue;
					
					List<Entity> list = forest.entities.get(new Point(nx, ny));
					if (list != null && list.size() > 0) {
						for (Entity e : list) {
							if (e instanceof Lumberjack) {
								lumberjacks.add((Lumberjack) e);
							}
						}
					}
				}
			}
			
			/* If there are no lumberjacks available, leave. */
			if (lumberjacks.size() == 0)
				return false;
				
			return maw(lumberjacks.get((int) (Math.random() * lumberjacks.size())));
		}
				
		public void update(Forest forest) {
			int moves = Forest.BEAR_MOVES;
			while (moves > 0) {
				/* If a lumberjack can be found, break out of the loop. */
				if (scan(forest)) break;
				/* If no tiles are open break out of the loop, otherwise loop. */
				if (!move(forest)) break; else --moves;
			}
		}
	}
	
	class Lumberjack extends Entity {
		public Lumberjack(int x, int y) {
			super(0xFF0000, x, y);
		}
		
		private boolean chop(Tree tree) {
			if (tree.type == TreeType.SAPLING)
				return false;
			
			/* Remove the tree from entities and from the counter. */
			forest.removeEntity(tree, tree.x, tree.y);
			--forest.n_trees;
			
			/* 1 lumber for trees, 2 lumber for elders. */
			forest.lumber += tree.type == TreeType.TREE ? 1 : 2;
			
			/* Move lumberjack to tree's coordinates. */
			forest.removeEntity(this, x, y);
			/*this.x = tree.x;
			this.y = tree.y;*/
			forest.addEntity(this, x, y);
			
			return true;
		}
		
		private boolean move(Forest forest) {
			List<Point> points = new ArrayList<Point>();
			
			/* Add points around the lumberjack as possible locations (must be walkable [Grass]). */
			for (int yd = -1; yd <= 1; ++yd) {
				for (int xd = -1; xd <= 1; ++xd) {
					if (xd == 0 && yd == 0)
						continue;
					
					int nx = x + xd, ny = y + yd;
					if (nx < 0 || nx >= forest.width || ny < 0 || ny >= forest.height)
						continue;
					
					List<Entity> list = forest.entities.get(new Point(nx, ny));
					if (list == null || list.size() == 0) {
						if (forest.tiles[nx + ny * forest.width].walkable) {
							points.add(new Point(nx, ny));
						}
					}
				}
			}
			
			/* If there are no lumberjacks available, the bear cannot maw. */
			if (points.size() == 0)
				return false;
			
			/* Otherwise, choose a random point. */
			Point point = points.get((int) (Math.random() * points.size()));
			
			forest.removeEntity(this, x, y);
			/*this.x = point.x;
			this.y = point.y;*/
			forest.addEntity(this, point.x, point.y);
			
			return true;
		}
		
		private boolean scan(Forest forest) {
			List<Tree> trees = new ArrayList<Tree>();
			
			/* Scan for possible trees to cut around the lumberjack. */
			for (int yd = -1; yd <= 1; ++yd) {
				for (int xd = -1; xd <= 1; ++xd) {
					if (xd == 0 && yd == 0)
						continue;
					
					int nx = x + xd, ny = y + yd;
					if (nx < 0 || nx >= forest.width || ny < 0 || ny >= forest.height)
						continue;
					
					List<Entity> list = forest.entities.get(new Point(nx, ny));
					if (list != null && list.size() > 0) {
						for (Entity e : list) {
							if (e instanceof Tree && forest.tiles[nx + ny * forest.width].walkable) {
								trees.add((Tree) e);
							}
						}
					}
				}
			}

			/* Remove all saplings. */
			trees = trees.stream().filter(t -> t.type != TreeType.SAPLING).collect(Collectors.toList());
			
			/* If there are no trees available, the lumberjack cannot chop. */
			if (trees.size() == 0)
				return false;
			
			/* Otherwise, chop a random tree in the list. */
			return chop(trees.get((int) (Math.random() * trees.size())));
		}
		
		public void update(Forest forest) {
			int moves = Forest.LUMBERJACK_MOVES;
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
		public int months = 1;
		public Tree(TreeType type, int x, int y) {
			super(type.color, x, y);
			this.type = type;
		}
		
		private void change(TreeType type) {
			this.type = type;
			this.color = type.color;
		}

		private void plant(Forest forest) {
			/* If tree is a sapling, cannot plant. */
			if (type == TreeType.SAPLING)
				return;
			
			List<Point> points = new ArrayList<Point>();
			
			/* Add points around the lumberjack as possible locations (must be walkable [Grass]). */
			for (int yd = -1; yd <= 1; ++yd) {
				for (int xd = -1; xd <= 1; ++xd) {
					if (xd == 0 && yd == 0)
						continue;
					
					int nx = x + xd, ny = y + yd;
					if (nx < 0 || nx >= forest.width || ny < 0 || ny >= forest.height)
						continue;
					
					boolean found = true;
					List<Entity> list = forest.entities.get(new Point(nx, ny));
					if (list == null || list.size() == 0) {
						if (found && forest.tiles[nx + ny * forest.width].walkable) {
							points.add(new Point(nx, ny));
						}
					}
				}
			}
			
			/* If there are no points available, the tree cannot plant a sapling. */
			if (points.size() == 0)
				return;
			
			/* Trees have a 10% chance of planting saplings, Elders have a 20% chance. */
			int chance = type == TreeType.TREE ? 100 : 50;
			
			if ((int) (Math.random() * chance) % 10 == 0) {
				/* Choose a random point to plant the sapling. */
				Point point = points.get((int) (Math.random() * points.size()));
				
				forest.addEntity(new Tree(TreeType.SAPLING, point.x, point.y), point.x, point.y);
				
				++forest.n_trees;
			}
		}
		
		public void update(Forest forest) {
			/* Upgrade the tree type. */
			upgrade();
			/* Plant saplings. */
			plant(forest);
			
			++months;
		}
		
		private void upgrade() {
			switch (type) {
			case SAPLING:
				if (months == 12)
					change(TreeType.TREE);
				break;
				
			case TREE:
				if (months == 120)
					change(TreeType.ELDER);
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
		
		public void render(int x, int y, int pixel) {
			pixels[x + y * width] = pixel;
		}
	}
	
	/* MAIN WINDOW */
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH  = 100;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE  = 8;
	
	private final int TICKS_PER_SECOND = 5;
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
		
		/* Draw entity / resource numbers in the top right. */
		if (!DEBUG) {
			g.setColor(Color.BLACK);
			g.fillRect(4, 4, 185, 27);
			g.setColor(Color.GREEN);
			g.fillRect(3, 3, 184, 26);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Ubuntu", 0, 12));
			g.drawString("Entities  - B: " + forest.n_bears + ", L: " + forest.n_lumberjacks + ", T: " + forest.n_trees, 4, 13);
			g.drawString("Resources - L: " + forest.lumber + ", M: " + forest.maws, 4, 26);
		}
		
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