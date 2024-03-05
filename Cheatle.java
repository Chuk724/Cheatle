//49/49 code - DO NOT CHANGE
import java.io.*;
import java.util.*;


public class Cheatle {

	//dictionarySet contains all the entries from dictionary and solutions
	private HashSet<String> dictionarySet;
	
	//just contains all the entries from solutions
	private HashSet<String> solutionSet;

	//contains all possible words left
	private TreeSet<String> wordsLeft;

	//all the correctLetters
	private TreeSet<String> correctLocation;
	
	//all the correct letters but incorrect places
	private TreeSet<String> correctLetters;

	//all the incorrect letters
	private TreeSet<String> incorrectLetters;

	//all the unguessed letters
	private TreeSet<Character> unguessedLetters;

	private TreeSet<Character> alphabet;
	 
	private HashMap<String, String> guesses;
	private int wordLength;

	//Reads the dictionaryFile and puts all the allowed guesses into a data structure,
	//and also reads the solutionFile and puts all the possible solutions into a data structure,
	//also adding all the possible solutions to the allowed guesses.
	//Throws a BadDictionaryException if not every word in the dictionary & solutions are of the same length 
	public Cheatle(String dictionaryFile, String solutionFile) throws FileNotFoundException, BadDictionaryException{
		//All of data structure reading should take place in constructor
		dictionarySet = new HashSet<String>();
		solutionSet = new HashSet<String>();
		Scanner dictionaryScanner = new Scanner (new FileReader(dictionaryFile));
		Scanner solutionScanner = new Scanner (new FileReader(solutionFile));
		wordLength = 0;
		int initWordLength = 0;
		String word = "";
		if (dictionaryScanner.hasNextLine()) {
			word = dictionaryScanner.nextLine();
			initWordLength = word.length();
			wordLength = word.length();
		}
		while (dictionaryScanner.hasNextLine()) {
			word = dictionaryScanner.nextLine();
			wordLength = word.length();
			if (initWordLength!=wordLength) {
				throw new BadDictionaryException();
			}
			dictionarySet.add(word);
		}
		while (solutionScanner.hasNextLine()) {
			word = solutionScanner.nextLine();
			wordLength = word.length();
			if (initWordLength!=wordLength) {
				throw new BadDictionaryException();
			}
			dictionarySet.add(word);
			solutionSet.add(word);
		}
		alphabet = new TreeSet<Character>();
		for (String solution : solutionSet) {
			for (int i = 0; i < solution.length(); i++) {
				char character = solution.charAt(i);
				if (!alphabet.contains(character)) {
					alphabet.add(character);
				}
			}
		}
 	}

	//Returns the length of the words in the list of words
	public int getWordLength() {
		return wordLength;
	}

	//Returns the complete alphabet of chars that are used in any word in the solution list,
	//in order as a String - in alphabetical order
	public String getAlphabet() {
		String alphabetStrings = "";
		for (Character c : alphabet) {
			alphabetStrings += c + "";
		}
		return alphabetStrings;
	}


	//Begins a game of Cheatle, initializing any private instance variables necessary for
	// a single game.
	public void beginGame() {
		guesses = new HashMap<String, String>();
		wordsLeft = new TreeSet<String>();
		wordsLeft.addAll(solutionSet);
		correctLocation = new TreeSet<String>();
		correctLetters = new TreeSet<String>();
		incorrectLetters = new TreeSet<String>();
		unguessedLetters = new TreeSet<Character>();
		unguessedLetters.clear();
		unguessedLetters.addAll(alphabet);

	}

	//Checks to see if the guess is in the dictionary of words.
	//Does NOT check to see whether the guess is one of the REMAINING words.
	public boolean isAllowable(String guess) {
		return dictionarySet.contains(guess);
	}

	//Given a guess, returns a String of '*', '?', and '!'
	//that gives feedback about the corresponding letters in that guess:
	// * means that letter is not in the word
	// ? means that letter is in the word, but not in that place
	// ! means that letter is in that exact place in the word
	// Because this is CHEATLE, not Wordle, you are to return the feedback
	// that leaves the LARGEST possible number of words remaining!!!
	//makeGuess should also UPDATE the list of remaining words
	// and update the list of letters where we KNOW where they are,
	// the list of letters that are definitely in the word but we don't know where they are,
	// the list of letters that are not in the word,
	// and the list of letters that are still possibilities
	public String makeGuess(String guess) {
		HashMap<String, ArrayList<String>> dictionary = new HashMap<String, ArrayList<String>>();
		if (!dictionarySet.contains(guess) || guess.length() != getWordLength()) {
			return "";
		}
		String worstFeedback = "";
		int maxFeedback = 0;
		for (String word : wordsLeft) {
			String feedback = compareWords(guess, word);
			if (dictionary.containsKey(feedback)) {
				ArrayList<String> solutions = dictionary.get(feedback);
				solutions.add(word);
				if (solutions.size() > maxFeedback) {
					maxFeedback = solutions.size();
					worstFeedback = feedback;
				}
			} else {
				ArrayList<String> solutions = new ArrayList<String>();
				solutions.add(word);
				dictionary.putIfAbsent(feedback, solutions);
				if (solutions.size() > maxFeedback) {
					maxFeedback = solutions.size();
					worstFeedback = feedback;
				}
			}
		}
		for (int i = 0; i < worstFeedback.length(); i++) {
			String letter = worstFeedback.charAt(i) + "";
			String guessLetter = guess.charAt(i) + "";
			char character = letter.charAt(0);
			char guessChar = guessLetter.charAt(0);
			if (letter.equals("!")) {
				correctLocation.add(guessLetter + "");
				if (correctLetters.contains(guessLetter)) {
					correctLetters.remove(guessLetter);
				}
				if (unguessedLetters.contains(guessChar)) {
					unguessedLetters.remove(guessChar);
				}
				if (incorrectLetters.contains(guessLetter)) {
					incorrectLetters.remove(guessLetter);
				}
			} else if (letter.equals("?")) {
				correctLetters.add(guessLetter + "");
				if (unguessedLetters.contains(guessChar)) {
					unguessedLetters.remove(guessChar);
				}
				if (incorrectLetters.contains(guessLetter)) {
					incorrectLetters.remove(guessLetter);
				}
			} else {
				incorrectLetters.add(guessLetter + "");
				if (unguessedLetters.contains(guessChar)) {
					unguessedLetters.remove(guessChar);
				}
			}
		}
		wordsLeft.clear();
		wordsLeft.addAll(dictionary.get(worstFeedback));
		guesses.putIfAbsent(guess, worstFeedback);
		return worstFeedback;
	}

	//returns feedback of comparison
	public String compareWords (String guess, String dictionary) {
		String output = "";
		ArrayList<String> outputList = new ArrayList<String>();
		ArrayList<String> guessList = new ArrayList<String>();;
		ArrayList<String> dictionaryList = new ArrayList<String>();
		for (int i = 0; i < guess.length(); i++) {
			guessList.add(guess.charAt(i) + "");
		}
		for (int i = 0; i < dictionary.length(); i++) {
			dictionaryList.add(dictionary.charAt(i) + "");
			outputList.add("*");
		}
		for (int i = 0; i < guessList.size(); i++) {
			if (guessList.get(i).equals(dictionaryList.get(i))) {
				outputList.set(i, "!");
				guessList.set(i, null);
				dictionaryList.set(i, null);
			}
		}
		for (int i = 0; i < guessList.size(); i++) {
			String guessCharacter = guessList.get(i);
			String outputCharacter = outputList.get(i);
			if (!outputCharacter.equals("!") && dictionaryList.contains(guessCharacter)) {
				int index = dictionaryList.indexOf(guessCharacter);
				dictionaryList.set(index, null);
				outputList.set(i, "?");
			}
		}
		for (int i = 0; i < outputList.size(); i++) {
			output+= outputList.get(i);
		}
		return output;
	}

	//Returns a String of all letters that have received a ! feedback
	public String correctPlaceLetters() {
		String output = "";
		for (String letter : correctLocation) {
			output += letter;
		}
		return output;
	}

	//Returns a String of all letters that have received a ? feedback
	public String wrongPlaceLetters() {
		String output = "";
		for (String letter : correctLetters) {
			output += letter;
		}
		return output;
	}

	//Returns a String of all letters that have received a * feedback
	public String eliminatedLetters() {
		String output = "";
		for (String letter : incorrectLetters) {
			output += letter;
		}
		return output;
	}

	//Returns a String of all unguessed letters
	public String unguessedLetters() {
		String output = "";
		for (Character c : unguessedLetters) {
			output+= c + "";
		}
		return output;
	}


	//Returns true if the feedback string is the winning one,
	//i.e. if it is all !s
	public boolean isWinningFeedback(String feedback) {
		for (int i = 0; i < feedback.length(); i++) {
			if (feedback.charAt(i) != '!') {
				return false;
			}
		}
		return true;
	}

	//Returns a String of all the remaining possible words, with one word per line
	public String getWordsRemaining() {
		String output = "";
		for (String word : wordsLeft) {
			output+= word + "\n";
		}
		//output = output.substring(output.length()-1);
		return output;
	}
	
	//Returns the number of possible words remaining
	public int getNumRemaining() {
		return wordsLeft.size();
	}

	//Returns the number of guesses made in this game
	public int numberOfGuessesMade() {
		return guesses.size();
	}

	//Ends the current game and starts a new game.
	public void restart() {
		beginGame();
	}

	public static void main (String[]args) throws FileNotFoundException, BadDictionaryException {
		Cheatle cheaty = new Cheatle ("WordleDictionary.txt", "WordleSolutionList.txt");
		cheaty.beginGame();
		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Guess: blast");
		System.out.println("Feedback: " + cheaty.makeGuess("blast"));

		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Unguessed Letters: " + cheaty.unguessedLetters());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Words Remaining: \n" + cheaty.getWordsRemaining());

		System.out.println("Guess: vogue");
		System.out.println("Feedback: " + cheaty.makeGuess("vogue"));
		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Unguessed Letters: " + cheaty.unguessedLetters());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Guess: check");
		System.out.println("Feedback: " + cheaty.makeGuess("check"));
		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Unguessed Letters: " + cheaty.unguessedLetters());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Guess: grope");
		System.out.println("Feedback: " + cheaty.makeGuess("grope"));
		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Unguessed Letters: " + cheaty.unguessedLetters());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Guess: eerie");
		System.out.println("Feedback: " + cheaty.makeGuess("eerie"));
		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Unguessed Letters: " + cheaty.unguessedLetters());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Words Remaining: \n" + cheaty.getWordsRemaining());

		System.out.println("Guess: diner");
		System.out.println("Feedback: " + cheaty.makeGuess("diner"));
		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Unguessed Letters: " + cheaty.unguessedLetters());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Words Remaining: \n" + cheaty.getWordsRemaining());

		System.out.println("Guess: finer");
		System.out.println("Feedback: " + cheaty.makeGuess("finer"));
		System.out.println("Alphabet: " + cheaty.getAlphabet());
		System.out.println("Unguessed Letters: " + cheaty.unguessedLetters());
		System.out.println("Num Remaining: " + cheaty.getNumRemaining());

		System.out.println("Words Remaining: \n" + cheaty.getWordsRemaining());
		//System.out.println(cheaty.compareWords("float","aooms"));
		//**!?*
		//System.out.println(cheaty.compareWords("there", "cater"));
		//?*??*
		//System.out.println(cheaty.compareWords("AAAXX","BBABA"));
		//?*!**
	}

}
