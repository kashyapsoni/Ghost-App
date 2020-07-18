import java.io.File;
import java.io.IOException;
import java.util.*;

public class GhostSkeleton {

	private static String sharedFilePath;
	private static TurnParser turnParser;
	private static StringBuffer wordFragment;
	private static FileTextReader<Set<String>> reader;
	private static FileTextWriter writer;
    private static FileMonitor fileMonitor;
    private static AbstractDictionary abstractDictionary;

	public static void main(String[] args) throws Exception {

		//The location of the shared game file will be provide to you at runtime
		
		//sharedFilePath = args[0];

		//sharedFilePath = "demo.txt";
		sharedFilePath = File.separator+args[0];
		
		System.out.println("HERRE " +sharedFilePath);
		//sharedFilePath = System.getProperty("user.dir")+sharedFilePath;

		/*
		 * Create all of your necessary classes. 
		 * Keep in mind that my constructors may look different than yours.
		 */

		turnParser = new TurnParser("kashyap");

	    wordFragment = new StringBuffer();

		reader = new FileReader();

	    writer = new FileTextWrite();

		fileMonitor = new FileMonitor(sharedFilePath);

		//Don't forget to create your dictionary!

		abstractDictionary = new Dictionary("ARBITOR_DICTIONARY.txt", reader);

		//Start your game loop
		while(true) {

			//Update the file monitor with the most recent infromation
			fileMonitor.update();

			//A change to the shared file as occurred!
			if(fileMonitor.hasChanged()) {

				//I get the most recent line write to the shared file
				String lastLine = reader.getLastLine(sharedFilePath);

				System.out.println("reading lastline to check out turn "+ lastLine);
				//Check if its my turn
				if (turnParser.isMyTurn(lastLine)) {

					/*
					 * This is where you would do your more complex logic for letter selection.
					 *
					 * You would us the wordFragment in combination with your the dictionary to
					 * find possible words that start with the given word fragment.
					 *
					 * For example purposes I am simply selecting a random letter from a - z.
					 */

					if (wordFragment.length()==0) {

						Random rnd = new Random();

						char c = (char) (rnd.nextInt(26) + 'a');

						String nextLetter = Character.toString(c);

						//Figure out if you want even or odd length words
						//System.out.println("I WANT EVEN WORDS: " + doIWantEvenLengthWords());

						// check the first letter in the dictionary
						// if it has more odd letter than even then change it
						// or if the letter has
						
						//Add their letter to my word fragment
						wordFragment.append(nextLetter);

						//Create the next line to be written to the shared file
						String nextLine = turnParser.getNextLine(nextLetter);

						//Write the next line to the shared file
						writer.writeToFile(nextLine, sharedFilePath);

					}
					else {

						String firstLetterOfWordFragment = String.valueOf(wordFragment.charAt(0));

						int wf = wordFragment.length();

						Set<String> firstLetterWordDictionary = abstractDictionary.getWordsThatStartWith(firstLetterOfWordFragment.toLowerCase(), 1, true);
						
						String word = wordFragment.toString();
						
						System.out.println("aaa  "+firstLetterWordDictionary);

						Set<String> wordFragmentToCreateDictionary = abstractDictionary.getWordsThatStartWith(word.toLowerCase(), wf+1, true);

						Set<String> oddWordsDict = new HashSet<>();

                        iterateOddWords(wordFragmentToCreateDictionary, oddWordsDict);

                        Set<String> evenWordsDict = new HashSet<>();

                        iterateEvenWords(wordFragmentToCreateDictionary, evenWordsDict);

						if (wordFragment.length() % 2 == 0){ //even words (You played first)
						
							String bestWordToGetChar = "";
							
							bestWordToGetChar = bestEvenWordToWin(evenWordsDict);

							char getTheNextChar = bestWordToGetChar.charAt(wordFragment.length());

							String madeSureForBestWord = checkForEvenSubString(wordFragment, firstLetterWordDictionary, evenWordsDict, getTheNextChar, bestWordToGetChar, oddWordsDict);

							char getTheNextChar1 = madeSureForBestWord.charAt(wordFragment.length());

							appendAndWriteToFile(getTheNextChar1);

						}
					    else { //odd words in the fragment (You have second turn)

							String bestWordToGetChar = "";

							bestWordToGetChar = bestOddWordToWin(oddWordsDict);

							char getTheNextChar = bestWordToGetChar.charAt(wordFragment.length());

							String madeSureForBestWord = checkForOddSubString(wordFragment, firstLetterWordDictionary, oddWordsDict, getTheNextChar, bestWordToGetChar, evenWordsDict);

							char getTheNextChar1 = madeSureForBestWord.charAt(wordFragment.length());

							appendAndWriteToFile(getTheNextChar1);
						}
					}
				}

				//Check if the other play finished their turn
				else if(turnParser.didOtherPlayerFinishTurn(lastLine)){
					
					wordFragment.append(turnParser.getOtherPlayersLetter(lastLine));

				//The game is over and its time for me to quit
				}else if(turnParser.isGameOver(lastLine)) {
					
					System.exit(0);
				}
			}
		}
	}

	public static String bestOddWordToWin (Set<String> oddEvenWords){
		
		String getOnlyOddWords = "";
		
		for (Iterator<String> s = oddEvenWords.iterator(); s.hasNext(); ) {
		
			getOnlyOddWords = s.next();
			
			if (getOnlyOddWords.length() % 2 != 0) { 
			
				return getOnlyOddWords;
			}
		}
		return " ";
	}

	public static String bestEvenWordToWin (Set<String> oddEvenWords){
		
		for (Iterator<String> s = oddEvenWords.iterator(); s.hasNext(); ) {
		
			String getOnlyOddWords = s.next();
			
			if (getOnlyOddWords.length() % 2 == 0) {
			
				return getOnlyOddWords;
			}
		}
		return " ";
	}

    public static boolean getUniqueString(String bestWOrdToCheck, Set<String> firstLetterWordDictionary) {
        
    	for (String str: firstLetterWordDictionary) {
        
    		if (str.equalsIgnoreCase(bestWOrdToCheck)){
            
    			return false;
            }
        }
        return true;
    }

	public static String checkForOddSubString(StringBuffer wordFragment, Set<String> firstLetterWordDictionary, Set<String> oddWords , char ch, String bestWord, Set<String> evenWordsDict){
		
		StringBuffer checkSub1 = wordFragment;
		
		String checkSub = checkSub1.toString().toLowerCase();
		
		checkSub = checkSub + ch;
		
		String newWord = bestWord;
        
		if ((getUniqueString(checkSub, firstLetterWordDictionary)) == true && magicOdd(checkSub, evenWordsDict) == true) {
        	
				return newWord;
		}
        else if ((getUniqueString(checkSub, firstLetterWordDictionary)) == true && magicOdd(checkSub, evenWordsDict) == false){
        	
            return newWord;
        }
        else {
        	
            for (String str1 : oddWords){
            	
                if (str1 != checkSub && ch != str1.charAt(wordFragment.length() - 1)){
                    
                	newWord = str1;
					
                    char newChar = newWord.charAt(wordFragment.length());
                  
					String newWord1 = checkSub1.toString().toLowerCase();
                  
                    newWord1 = newWord1 + newChar;

                    if (getUniqueString(newWord1, firstLetterWordDictionary) == true && magicOdd(newWord1, evenWordsDict) == true) {
                    
                    	return newWord;
                    
                    } else if ((getUniqueString(newWord1, firstLetterWordDictionary)) == true && magicOdd(newWord1, evenWordsDict) == false){
						
                        return newWord;
                    }
                    else {}
                }
            }
        }
		return newWord;
	}

	public static String checkForEvenSubString(StringBuffer wordFragment, Set<String> firstLetterWordDictionary, Set<String> evenWords , char ch, String bestWord, Set<String> oddWordsDict){

		StringBuffer checkSub1 = wordFragment;
		
		String checkSub = checkSub1.toString().toLowerCase();
		
		checkSub = checkSub + ch;
		
		String newWord = bestWord;

		if ((getUniqueString(checkSub, firstLetterWordDictionary)) == true && magicEven(checkSub,oddWordsDict) == true){
		
			return newWord;
			
		}
        else if ((getUniqueString(checkSub, firstLetterWordDictionary)) == true && magicEven(checkSub, oddWordsDict) == false){
        
        	return newWord;
        	
        }		
		else {
         
			for (String str1 : evenWords){
            
				if ((!(str1.equalsIgnoreCase(checkSub))) && ch != str1.charAt(wordFragment.length() - 1)){
                
					newWord = str1;
                    
					char newChar = newWord.charAt(wordFragment.length());
                    
					String newWord1 = checkSub1.toString().toLowerCase();
                    
					newWord1 = newWord1 + newChar;
                    
                    if (getUniqueString(newWord1, firstLetterWordDictionary) == true && magicEven(newWord1, oddWordsDict) == true) {
                    
                    	return newWord;
                    	
                    }else if ((getUniqueString(newWord1, firstLetterWordDictionary)) == true && magicEven(newWord1, oddWordsDict) == false){
                    	
                        return newWord;
                    }
                    else {}
                }
            }
        }
		return newWord;
	}

    public static boolean magicEven(String subString, Set<String> oddDict){
        for (String str : oddDict) {
        	
            if (str.contains(subString)){
            
            	return false;
            }
        }
        
        return true;
    }

    public static boolean magicOdd(String subString, Set<String> evenDict){
        for (String str : evenDict) {
        	
            if (str.contains(subString)){
            
            	return false;
            }
        }
        
        return true;
    }

	public static void appendAndWriteToFile(char ch) throws IOException {
		wordFragment.append(ch);
		
		String nextLine = turnParser.getNextLine(String.valueOf(ch));
		
		writer.writeToFile(nextLine, sharedFilePath);
	}

	public static void iterateOddWords(Set<String> IterateTo, Set<String> saveTo){
		
		for (Iterator<String> s = IterateTo.iterator(); s.hasNext(); ) {
			
			String getOnlyOddWords = s.next();
			
			if (getOnlyOddWords.length() % 2 != 0) {
			
				saveTo.add(getOnlyOddWords);
			}
		}
	}

	public static void iterateEvenWords(Set<String> IterateTo, Set<String> saveTo){
		
		for (Iterator<String> s = IterateTo.iterator(); s.hasNext(); ) {
		
			String getOnlyOddWords = s.next();
			
			if (getOnlyOddWords.length() % 2 == 0) {
			
				saveTo.add(getOnlyOddWords);
			}
		}
	}

	/*
	 * Only call this before adding your letter to the word fragment and it will tell you
	 * if your looking for even or odd length words
	 */
	public static boolean doIWantEvenLengthWords() {
		return (wordFragment.length() & 1) == 0;
	}
}
