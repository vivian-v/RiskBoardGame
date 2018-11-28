



import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Country;
import riskboardgame.Deck;
import riskboardgame.History;
import riskboardgame.Player;


public class TestHistory extends TestCase {

	HashMap<String, Country> testMap = new HashMap<String, Country>();
	ArrayList<Player> testPlayers = new ArrayList<Player>();
	int testIndex = 0;
	Deck testDeck = new Deck();
	String actionStatus = "attack action";
	int testTradeSetIndex = 0;
	@Test
	public void testGetActionStatus()
	{
		Player p1 = new Player("Chang", 12345,1);
		testPlayers.add(p1);
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck, testTradeSetIndex);
		assertEquals("attack action", test.getActionStatus());
	}
	@Test
	public void testGetPrevPlayers()
	{
		Player p1 = new Player("Chang", 12345,1);
		testPlayers.add(p1);
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck, testTradeSetIndex);
		assertEquals("Chang", test.getPrevPlayers().get(0).getPlayerName());
	}

	@Test
	public void testGetPrevMap()
	{

		testMap.put("China", new Country("test"));
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck, testTradeSetIndex);
		assertTrue(test.getPrevMap().containsKey("China"));
	}
	@Test
	public void testGetPrevPlayerIndex()
	{
		Player p1 = new Player("Chang", 12345,1);
		testPlayers.add(p1);
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck, testTradeSetIndex);
		assertEquals(0, test.getPrevPlayerIndex());
	}
	@Test
	public void testGetPrevDeck()
	{
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck, testTradeSetIndex);
		assertNotNull(test.getPrevDeck());
	}
	@Test
	public void testGetPrevTradeSetIndex()
	{
		Player p1 = new Player("Chang", 12345,1);
		testPlayers.add(p1);
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck, testTradeSetIndex);
		assertEquals(0, test.getPrevTradeSetIndex());
	}



}