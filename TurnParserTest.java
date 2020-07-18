import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TurnParserTest{

	private static final String MY_TEAM_NAME = "My Team";
	private static final String MY_TEAM_NAME_PROPER = "MYTEAM";
	private static final String OTHER_TEAM_NAME = "Other Team";
	private static final String LETTER = "f";
	private static final String[] EMPTY_STRINGS = {"", " ", "\n", "\t"};
	private TurnParser turnParser;

	@BeforeClass
	public static void beforeClassFunction(){
	}

	@Before
	public void beforeFunction(){
		turnParser = new TurnParser(MY_TEAM_NAME);
	}

	@After
	public void afterFunction(){
	}

	@AfterClass
	public static void afterClassFunction(){
	}
	
	@Test
	public void getNextLineTese(){
		String properLine = MY_TEAM_NAME_PROPER+TurnParser.DELIMITER+LETTER;
				
		assertTrue(properLine.equalsIgnoreCase(turnParser.getNextLine(LETTER)));
		
		assertTrue(properLine.equalsIgnoreCase(turnParser.getNextLine(LETTER+" ")));
		
		assertTrue(properLine.equalsIgnoreCase(turnParser.getNextLine(LETTER+"\n")));
		
		assertTrue(properLine.equalsIgnoreCase(turnParser.getNextLine(LETTER+"\t")));
		
		String[] testCases = new String[] {
				LETTER+LETTER,
				OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN+"\t",
				OTHER_TEAM_NAME.toLowerCase()+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase(),
				OTHER_TEAM_NAME+"\n"+TurnParser.DELIMITER+TurnParser.TURN,
				" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+TurnParser.TURN,
				OTHER_TEAM_NAME+TurnParser.TURN,
				MY_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN,
				MY_TEAM_NAME+TurnParser.DELIMITER+LETTER,
				"jkdskdj",
				" "+MY_TEAM_NAME+" "+TurnParser.DELIMITER,
				" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+" ",
				" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+"\n",
				OTHER_TEAM_NAME+TurnParser.DELIMITER+LETTER+" ",
				OTHER_TEAM_NAME+TurnParser.DELIMITER+LETTER,
				OTHER_TEAM_NAME+TurnParser.DELIMITER+LETTER+"\n",
				OTHER_TEAM_NAME+TurnParser.DELIMITER+"\t"+LETTER
		};
		
		illegalArgumentTest(testCases, n ->  turnParser.getNextLine(n));
		
		emptyTest(n -> turnParser.getNextLine(n));
	}

	@Test
	public void otherLetterTurn(){
		assertTrue(LETTER.equalsIgnoreCase(turnParser.getOtherPlayersLetter(OTHER_TEAM_NAME+TurnParser.DELIMITER+LETTER)));

		assertTrue(LETTER.equalsIgnoreCase(turnParser.getOtherPlayersLetter(OTHER_TEAM_NAME+TurnParser.DELIMITER+LETTER+" ")));

		assertTrue(LETTER.equalsIgnoreCase(turnParser.getOtherPlayersLetter(OTHER_TEAM_NAME+TurnParser.DELIMITER+LETTER+"\n")));

		assertTrue(LETTER.equalsIgnoreCase(turnParser.getOtherPlayersLetter(OTHER_TEAM_NAME+TurnParser.DELIMITER+"\t"+LETTER)));
		
		String[] testCases = new String[] {
				OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase(),
				OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN+"\t",
				OTHER_TEAM_NAME.toLowerCase()+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase(),
				OTHER_TEAM_NAME+"\n"+TurnParser.DELIMITER+TurnParser.TURN,
				" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+TurnParser.TURN,
				OTHER_TEAM_NAME+TurnParser.TURN,
				MY_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN,
				MY_TEAM_NAME+TurnParser.DELIMITER+LETTER,
				"jkdskdj",
				" "+MY_TEAM_NAME+" "+TurnParser.DELIMITER,
				" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+" ",
				" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+"\n"
		};

		illegalArgumentTest(testCases, n ->  turnParser.getOtherPlayersLetter(n));
		
		emptyTest(n -> turnParser.getOtherPlayersLetter(n));
	}

	@Test
	public void otherFinishTurn(){

		assertTrue(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+"f"));

		assertTrue(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+" f"));

		assertTrue(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+"f\n"));

		assertFalse(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase()));

		assertFalse(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN+"\t"));

		assertFalse(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME.toLowerCase()+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase()));

		assertFalse(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+"\n"+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.didOtherPlayerFinishTurn(" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.didOtherPlayerFinishTurn(OTHER_TEAM_NAME+TurnParser.TURN));

		assertFalse(turnParser.didOtherPlayerFinishTurn(MY_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.didOtherPlayerFinishTurn("jkdskdj"));

		assertFalse(turnParser.didOtherPlayerFinishTurn(" "+MY_TEAM_NAME+" "+TurnParser.DELIMITER));

		assertFalse(turnParser.didOtherPlayerFinishTurn(" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+" "));

		assertFalse(turnParser.didOtherPlayerFinishTurn(" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+"\n"));

		emptyTest(n -> turnParser.didOtherPlayerFinishTurn(n));
	}

	@Test
	public void isOtherTurnTest(){

		assertTrue(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN));

		assertTrue(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+"kjrkehrhf"));

		assertTrue(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+" e"));

		assertTrue(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase()));

		assertTrue(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN+"\t"));

		assertTrue(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME.toLowerCase()+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase()));

		assertTrue(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME+"\n"+TurnParser.DELIMITER+TurnParser.TURN));

		assertTrue(turnParser.isOtherPlayersTurn(" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.isOtherPlayersTurn(OTHER_TEAM_NAME+TurnParser.TURN));

		assertFalse(turnParser.isOtherPlayersTurn(MY_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.isOtherPlayersTurn("jkdskdj"));

		assertFalse(turnParser.isOtherPlayersTurn(" "+MY_TEAM_NAME+" "+TurnParser.DELIMITER));

		assertFalse(turnParser.isOtherPlayersTurn(" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+" "));

		assertFalse(turnParser.isOtherPlayersTurn(" "+OTHER_TEAM_NAME+" "+TurnParser.DELIMITER+"\n"));

		emptyTest(n -> turnParser.isOtherPlayersTurn(n));
	}

	@Test
	public void isMyTurnTest(){

		assertTrue(turnParser.isMyTurn(MY_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN));

		assertTrue(turnParser.isMyTurn(MY_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase()));

		assertTrue(turnParser.isMyTurn(MY_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN+"\t"));

		assertTrue(turnParser.isMyTurn(MY_TEAM_NAME.toLowerCase()+TurnParser.DELIMITER+TurnParser.TURN.toLowerCase()));

		assertTrue(turnParser.isMyTurn(MY_TEAM_NAME+"\n"+TurnParser.DELIMITER+TurnParser.TURN));

		assertTrue(turnParser.isMyTurn(" "+MY_TEAM_NAME+" "+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.isMyTurn(MY_TEAM_NAME+TurnParser.TURN));

		assertFalse(turnParser.isMyTurn(MY_TEAM_NAME+TurnParser.DELIMITER+"kjrkehrhf"));

		assertFalse(turnParser.isMyTurn(OTHER_TEAM_NAME+TurnParser.DELIMITER+TurnParser.TURN));

		assertFalse(turnParser.isMyTurn("jkdskdj"));

		assertFalse(turnParser.isMyTurn(" "+MY_TEAM_NAME+" "+TurnParser.DELIMITER));

		assertFalse(turnParser.isMyTurn(" "+MY_TEAM_NAME+" "+TurnParser.DELIMITER+" "));

		emptyTest(n -> turnParser.isMyTurn(n));
	}

	@Test
	public void gameOverTest(){

		assertTrue(turnParser.isGameOver(TurnParser.GAME_OVER));

		assertTrue(turnParser.isGameOver(TurnParser.GAME_OVER+" difoewn"));

		assertTrue(turnParser.isGameOver(TurnParser.GAME_OVER+"difoewn"));

		assertTrue(turnParser.isGameOver(TurnParser.GAME_OVER.toLowerCase()));

		assertTrue(turnParser.isGameOver(TurnParser.GAME_OVER.toUpperCase()));

		assertFalse(turnParser.isGameOver("jkdskdj"));

		emptyTest(n -> new TurnParser(n));
	}

	@Test
	public void gameStartTest(){

		assertTrue(turnParser.isGameStarted(TurnParser.GAME_START));

		assertTrue(turnParser.isGameStarted(TurnParser.GAME_START+" difoewn"));

		assertTrue(turnParser.isGameStarted(TurnParser.GAME_START+"difoewn"));

		assertTrue(turnParser.isGameStarted(TurnParser.GAME_START.toLowerCase()));

		assertTrue(turnParser.isGameStarted(TurnParser.GAME_START.toUpperCase()));

		assertFalse(turnParser.isGameStarted("jkdskdj"));

		emptyTest(n -> new TurnParser(n));
	}

	@Test
	public void initializeTest(){
		turnParser = new TurnParser("Test");	

		emptyTest(n -> new TurnParser(n));
	}
	
	@Test
	public void setTeamTest(){
		turnParser.setTeamName(MY_TEAM_NAME);	

		emptyTest(n -> new TurnParser(n));
	}


	private void emptyTest(Consumer<String> consumer) {
		illegalArgumentTest(EMPTY_STRINGS, consumer);
	}

	private void illegalArgumentTest(String[] strings, Consumer<String> consumer) {
		for(String string : strings) {
			try {
				consumer.accept(string);	
			}catch (Exception e) {
				assertTrue(e instanceof IllegalArgumentException);
			}
		}
	}

}