import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestCheatle {

	static Cheatle cheat;
	static CheatleSolution cheatsol;

	@BeforeAll
	public static void setUpGames() throws IOException, FileNotFoundException, BadDictionaryException {
		cheat = new Cheatle("WordleDictionary.txt", "WordleSolutionList.txt");
		cheatsol = new CheatleSolution("WordleDictionary.txt", "WordleSolutionList.txt");

	}

	@Test
	@DisplayName("[5] Test if your wordLength method works correctly")
	public void testWordLength() {
		assertEquals(cheatsol.getWordLength(), cheat.getWordLength());
	}

	@Test
	@DisplayName("[5] Test if your getAlphabet method works correctly")
	public void testAlphabet() {
		assertEquals(cheatsol.getAlphabet(), cheat.getAlphabet());
	}

	@Test
	@DisplayName("[5] Test if your isAllowable method works correctly")
	public void testIsAllowable() {
		String s = "vomit";
		assertEquals(cheatsol.isAllowable(s), cheat.isAllowable(s));
		s = "plans";
		assertEquals(cheatsol.isAllowable(s), cheat.isAllowable(s));
		s = "gurps";
		assertEquals(cheatsol.isAllowable(s), cheat.isAllowable(s));
	}

	@Test
	@DisplayName("[5] Test if your makeGuess method works correctly")
	public void testMakeGuess() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		assertEquals(cheatsol.makeGuess(s), cheat.makeGuess(s));
		s = "round";
		assertEquals(cheatsol.makeGuess(s), cheat.makeGuess(s));
		s = "juicy";
		assertEquals(cheatsol.makeGuess(s), cheat.makeGuess(s));
		s = "guppy";
		assertEquals(cheatsol.makeGuess(s), cheat.makeGuess(s));
	}

	@Test
	@DisplayName("[5] Test if your correctPlaceLetters method works correctly")
	public void testCorrectPlace() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.correctPlaceLetters(), cheat.correctPlaceLetters());
		s = "round";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.correctPlaceLetters(), cheat.correctPlaceLetters());
		s = "juicy";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.correctPlaceLetters(), cheat.correctPlaceLetters());
	}

	@Test
	@DisplayName("[5] Test if your wrongPlaceLetters method works correctly")
	public void testWrongPlace() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.wrongPlaceLetters(), cheat.wrongPlaceLetters());
		s = "round";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.wrongPlaceLetters(), cheat.wrongPlaceLetters());
		s = "juicy";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.wrongPlaceLetters(), cheat.wrongPlaceLetters());
	}

	@Test
	@DisplayName("[5] Test if your eliminatedLetters method works correctly")
	public void testEliminated() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.eliminatedLetters(), cheat.eliminatedLetters());
		s = "round";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.eliminatedLetters(), cheat.eliminatedLetters());
		s = "juicy";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.eliminatedLetters(), cheat.eliminatedLetters());
	}

	@Test
	@DisplayName("[5] Test if your unguessedLetters method works correctly")
	public void testUnguessed() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.unguessedLetters(), cheat.unguessedLetters());
		s = "round";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.unguessedLetters(), cheat.unguessedLetters());
		s = "juicy";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.unguessedLetters(), cheat.unguessedLetters());
	}

	@Test
	@DisplayName("[5] Test if your getWordsRemaining method works correctly")
	public void testWordsRemaining() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.getWordsRemaining(), cheat.getWordsRemaining());
		s = "round";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.getWordsRemaining(), cheat.getWordsRemaining());
		s = "juicy";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.getWordsRemaining(), cheat.getWordsRemaining());
	}

	@Test
	@DisplayName("[5] Test if your getNumRemaining method works correctly")
	public void testNumRemaining() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.getNumRemaining(), cheat.getNumRemaining());
		s = "round";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.getNumRemaining(), cheat.getNumRemaining());
		s = "juicy";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.getNumRemaining(), cheat.getNumRemaining());
	}


	@Test
	@DisplayName("[5] Test if your numberOfGuessesMade method works correctly")
	public void testNumGuesses() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.numberOfGuessesMade(), cheat.numberOfGuessesMade());
		s = "round";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.numberOfGuessesMade(), cheat.numberOfGuessesMade());
		s = "juicy";
		cheat.makeGuess(s);
		cheatsol.makeGuess(s);
		assertEquals(cheatsol.numberOfGuessesMade(), cheat.numberOfGuessesMade());
	}

	@Test
	@DisplayName("[5] Test if your restart method works correctly")
	public void testRestart() {
		cheat.beginGame();
		cheatsol.beginGame();
		String s = "slate";
		assertEquals(cheat.makeGuess(s), cheatsol.makeGuess(s));
		s = "round";
		assertEquals(cheat.makeGuess(s), cheatsol.makeGuess(s));

		cheat.restart();
		cheatsol.restart();
		s = "round";
		assertEquals(cheat.makeGuess(s), cheatsol.makeGuess(s));
		s = "slate";
		assertEquals(cheat.makeGuess(s), cheatsol.makeGuess(s));

	}

}
