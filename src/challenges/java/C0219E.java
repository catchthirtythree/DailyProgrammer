/**
 * @author Michael
 *
 * http://www.reddit.com/r/dailyprogrammer/comments/39ws1x/20150615_challenge_218_easy_todo_list_part_1/
 */
package challenges.java;

import java.util.ArrayList;
import java.util.List;

public class C0219E {
	static class TodoList {
		public List<String> items;
		
		public TodoList() {
			items = new ArrayList<String>();
		}
		
		public void addItem(String item) {
			items.add(item);
		}
		
		public boolean deleteItem(String item) {
			return items.remove(item);
		}
		
		public void viewList() {
			items.forEach(System.out::println);
		}
	}
	
	public static void main(String[] args) {
		TodoList list = new TodoList();
		
		list.addItem("Take a shower");
		list.addItem("Go to work");
		list.viewList();
		
		list.addItem("Buy a new phone");
		list.deleteItem("Go to work");
		list.viewList();
	}
}