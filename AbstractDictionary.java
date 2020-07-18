import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * A class that holds a collection of words read from a text file. The collection of words is used in the methods of this class
 * methods provided in this class.
 * @author Harris Williams
 * @since Sep 28, 2018
 */
public abstract class AbstractDictionary {
	
	private FileTextReader<Set<String>> fileReader;
	private Set<String> allWords;
	
	/**
	 * Creates an abstract dictionary of words using the text file at the specified path. 
	 * @param path a path to a text file containing a dictionary of words
	 * @param dictionaryFileReader the FileReader used to read from the text file at the specified path
	 * @throws IOException thrown if there is a problem reading the file at the path
	 */
	public AbstractDictionary(String path, FileTextReader<Set<String>> dictionaryFileReader) throws IOException {
		Objects.requireNonNull(dictionaryFileReader, "The File reader can not be null");
		
		setDictionaryFilePath(path);
		
		allWords = dictionaryFileReader.getAllLines(getDictionaryFilePath());
	}
	
	/**
	 * Returns the file reader specified when this class was constructed
	 * @return a file read used to read this dictionary's text file
	 */
	public FileTextReader<Set<String>> getFileReader(){
		return fileReader;
	}
	
	/**
	 * Returns a set of all the words contained in the dictionary text file.
	 * @return a file read used to read this dictionary's text file
	 */
	public Set<String> getAllWords(){
		return allWords;
	}

	/**
	 * Sets a path to a text file, the text file should contain a list of words that will be used by the other methods in this class
	 * @param path a path to a text file containing a lot of words
	 * @throws IllegalArgumentException if the specified string is null or empty (Meaning contains no characters or only white space)
	 */
	public abstract void setDictionaryFilePath(String path) throws IllegalArgumentException;
	
	/**
	 * Returns a path to a text file containing a dictionary of words
	 * @return the path to the dictionary text file
	 */
	public abstract String getDictionaryFilePath();

	/**
	 * Counts the number of words in this Dictionary that start with the specified prefix and contain the same number of 
	 * characters of as the specified size. If size the specified size is less than 1 then word size is not taken into account. 
	 * @param prefix the prefix to be found
	 * @param size the number of characters that the words must contain. If a value less than 1 is specified, all words regardless of their
	 * characters size should be considered. In other words if the size parameter is < 1 word size is disregarded in the calculations. 
	 * @param ignoreCase if true this will ignore case differences when matching the strings. If false this considers 
	 * case differences when matching the strings
	 * @return The number of words that start with the specified prefix
	 * @throws IllegalArgumentException if the specified string is null or empty (Meaning contains no characters or only white space)
	 */
	public abstract int countWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException;

	/**
	 * Tests if this Dictionary contains at least one word of the specified size that starts with the specified prefix.
	 * If size the specified size is less than 1 then word size is not taken into account. 
	 * @param prefix the prefix to be found
	 * @param size the number of characters that the words must contain. If a value less than 1 is specified, all words regardless of their
	 * characters size should be considered. In other words if the size parameter is < 1 word size is disregarded in the calculations. 
	 * @param ignoreCase if true this will ignore case differences when matching the strings. If false this considers 
	 * case differences when matching the strings
	 * @return The number of words that start with the specified prefix
	 * @throws IllegalArgumentException if the specified string is null or empty (Meaning contains no characters or only white space)
	 */
	public abstract boolean containsWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException;

	/**
	 * Returns a set of all the words within in this Dictionary that start with the specified prefix and have the same amount of charters as the specified
	 * size. If size the specified size is less than 1 then word size is not taken into account. 
	 * @param prefix the prefix to be found
	 * @param size the number of characters that the words must contain. If a value less than 1 is specified, all words regardless of their
	 * characters size should be considered. In other words if the size parameter is < 1 word size is disregarded in the calculations. 
	 * @param ignoreCase if true this will ignore case differences when matching the strings. If false this considers 
	 * case differences when matching the strings
	 * @return A list of all strings that start with the specified prefix
	 * @throws IllegalArgumentException if the specified string is null or empty (Meaning contains no characters or only white space)
	 */
	public abstract Set<String> getWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException;
	
}
