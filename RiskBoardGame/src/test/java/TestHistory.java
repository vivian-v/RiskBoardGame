



import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
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
	public void testgetActionStatus()
	{
		Player p1 = new Player("Chang", 1);
		testPlayers.add(p1);
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck, testTradeSetIndex);
		assertEquals("attack action", test.getActionStatus());
	}

	

}