package demo3;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;


public class TestHistory extends TestCase {

	HashMap<String, Country> testMap = new HashMap<String, Country>();
	ArrayList<Player> testPlayers = new ArrayList<Player>();
	int testIndex = 0;
	Deck testDeck = new Deck();
	String actionStatus = "attack action";
	
	@Test
	public void testgetActionStatus()
	{
		Player p1 = new Player("Chang", 1);
		testPlayers.add(p1);
		History test = new History(actionStatus, testPlayers, testMap, testIndex, testDeck);
		assertEquals("attack action", test.getActionStatus());
	}

	

}
