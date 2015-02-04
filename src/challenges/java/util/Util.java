package challenges.java.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
	/**
	 * Retrieve line from file, return list.
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static List<String> getLinesFromFile(String filename) throws IOException {
		List<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String line;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		} reader.close();
		
		return lines;
	}
}