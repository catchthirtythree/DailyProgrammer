/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/2m48nn/20141112_challenge_188_intermediate_box_plot/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class C0188I {
	static class BoxPlot {
		public List<Integer> data;
		public int Q1, Q2, Q3, IQR, min, max;
		public List<Integer> l_outliers, u_outliers;
		
		public BoxPlot(List<Integer> data) {
			this.data = data;
			
			this.Q1 = data.get(data.size() / 4);
			this.Q2 = data.get(2 * data.size() / 4);
			this.Q3 = data.get(3 * data.size() / 4);
			this.IQR = Q3 - Q1;
			
			this.min = data.get(0);
			this.max = data.get(data.size() - 1);
			
			this.l_outliers = data.stream().filter(x -> x < (Q1 - 1.5 * IQR)).collect(Collectors.toList());
			this.u_outliers = data.stream().filter(x -> x > (Q3 + 1.5 * IQR)).collect(Collectors.toList());
		}
		
		public void draw() {
			// Get position for min, q1, q2, q3, max. Draw l_outliers first, and u_outliers last.
			
			int l_q1 = (int) (Q1 / (double) max * SCALE);
			int l_q2 = (int) (Q2 / (double) max * SCALE);
			int l_q3 = (int) (Q3 / (double) max * SCALE);
			
			int l_start = 0;
			for (int i = 0; i < data.size(); ++i) {
				if (!l_outliers.contains(data.get(i))) {
					l_start = (int) (data.get(i) / (double) (max) * SCALE);
					break;
				}
			}
			
			int l_end = 0;
			for (int i = data.size() - 1; i >= 0; --i) {
				if (!u_outliers.contains(data.get(i))) {
					l_end = (int) (data.get(i) / (double) max * SCALE);
					break;
				}
			}
			
			// Top.
			
			for (int i = 0; i <= SCALE; ++i) {
				if (i < l_start) {
					for (int j = 0; j < l_outliers.size(); j++) {
						System.out.print(" ");
					}
				} else if (i == l_start) {
					System.out.print(" ");
				} else if (i == l_q1) {
					System.out.print("â”Œ");
				} else if (i == l_q2) {
					System.out.print("â”¬");
				} else if (i == l_q3) {
					System.out.print("â”�");
				} else if (i == l_end) {
					System.out.print(" ");
				} else if (i > l_q1 && i < l_q3) {
					System.out.print("â”€");
				} else if (i > l_end) {
					for (int j = 0; j < u_outliers.size(); j++) {
						System.out.print(" ");
					}
				} else {
					System.out.print(" ");
				}
			} System.out.println();
			
			// Middle.
			
			for (int i = 0; i <= SCALE; ++i) {
				if (i < l_start) {
					for (Integer l_out : l_outliers) {
						if (i == (int) (l_out / (double) max * SCALE)) {
							System.out.print("X");
						} else {
							System.out.print(" ");
						}
					}
				} else if (i == l_start) {
					System.out.print("â”œ");
				} else if (i == l_q1) {
					System.out.print("â”¼");
				} else if (i == l_q2) {
					System.out.print("â”¼");
				} else if (i == l_q3) {
					System.out.print("â”¼");
				} else if (i == l_end) {
					System.out.print("â”¤");
				} else if (i > l_end) {
					for (Integer u_out : u_outliers) {
						if (i == (int) (u_out / (double) max * SCALE)) {
							System.out.print("X");
						} else {
							System.out.print(" ");
						}
					}
				} else {
					System.out.print("â”€");
				}
			} System.out.println();
			
			// Bottom.
			
			for (int i = 0; i <= SCALE; ++i) {
				if (i < l_start) {
					for (int j = 0; j < l_outliers.size(); j++) {
						System.out.print(" ");
					}
				} else if (i == l_start) {
					System.out.print(" ");
				} else if (i == l_q1) {
					System.out.print("â””");
				} else if (i == l_q2) {
					System.out.print("â”´");
				} else if (i == l_q3) {
					System.out.print("â”˜");
				} else if (i == l_end) {
					System.out.print(" ");
				} else if (i > l_q1 && i < l_q3) {
					System.out.print("â”€");
				} else if (i > l_end) {
					for (int j = 0; j < u_outliers.size(); j++) {
						System.out.print(" ");
					}
				} else {
					System.out.print(" ");
				}
			}
		}
		
		private static final int SCALE = 100;
	}
	
	public static void main(String[] args) {
		// Create integer list.
		
		List<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i < args.length; ++i) {
			data.add(Integer.parseInt(args[i]));
		}

		// Sort data list.
		
		Collections.sort(data);
		
		// Create the box plot object.
		
		BoxPlot bp = new BoxPlot(data);
		
		// Draw box plot.
		
		bp.draw();
	}
}