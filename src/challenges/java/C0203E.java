package challenges.java;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class C0203E extends JComponent {
	private static final long serialVersionUID = 1L;

	public static final String TITLE = "[C0203E] Squares";

	public int width, height;
	
	public C0203E(int width, int height) {
		this.width = width;
		this.height = height;
		
		setPreferredSize(new Dimension(width, height));
	}
	
	public void paint(Graphics g) {
		g.fillRect(width / 2 / 2, height / 2 / 2, width / 2, height / 2);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.add(new C0203E(400, 400));
		frame.pack();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}