/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/3a64hq/20150617_challenge_219_intermediate_todo_list/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class C0219I {
	static class TodoList {
		public Map<String, List<String>> list;
		
		public TodoList() {
			list = new HashMap<String, List<String>>();
		}
		
		public void addItem(String item, String... categories) {
			for (String category : categories) {
				List<String> items;
				
				if ((items = list.get(category)) == null) {
					items = new ArrayList<String>();
				} items.add(item);
				
				list.put(category, items);
			}
		}
		
		public void updateItem(String item, String newItem) {
			for (Entry<String, List<String>> entry : list.entrySet()) {
				List<String> items;
				
				if ((items = entry.getValue()) != null) {
					if (items.contains(item))
						items.set(items.indexOf(item), newItem);
				}
			}
		}
		
		public void viewList(String... categories) {
			boolean first = true;
			List<String> retained = new ArrayList<String>();
			
			for (String category : categories) {
				List<String> items; 
				
				if ((items = list.get(category)) != null) {
					if (first) {
						retained.addAll(items);
						first = false;
					} else {
						retained.retainAll(items);
					}
				} else {
					retained.clear();
					break;
				}
			}
			
			System.out.println(String.join(" & ", categories));
			retained.forEach(r -> System.out.println("> " + r));
		}
	}
	
	public static void main(String[] args) {
		TodoList list = new TodoList();
		
		list.addItem("Go to work", "Programming");
		list.addItem("Create Sine Waves in C", "Music", "Programming");

		list.viewList("Music");
		list.viewList("Programming");
		list.viewList("Music", "Programming");
		
		list.updateItem("Create Sine Waves in C", "Create Sine Waves in Python");
		
		list.viewList("Music", "Programming");
	}
}