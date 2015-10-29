package challenges.java.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
	public static final String WORDLIST = "ext/wordlist.txt";
	
	/**
	 * Retrieve line from file, return list.
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static List<String> getLinesFromFile(String filename) throws IOException {
		return Files.lines(Paths.get(filename)).collect(Collectors.toList());
	}
}