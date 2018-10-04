package demo3;

import org.junit.Test;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

public class TestSetup extends TestCase {

	Setup test = new Setup();


    @Test
    public void testLoadDeck() {
    	Deck testDeck = test.LoadDeck();
    	assertEquals("Alaska", testDeck.draw().getDetail());
    }
    @Test
    public void testLoadMap() {
    	HashMap<String, Country> Map = test.LoadMap();
    	assertEquals(42, Map.size());
    }
    @Test
    public void testDetermineTurn()
    {
    	ArrayList<Player> players = new ArrayList<Player>();
    	players.add(new Player("Chang", 20));
    	players.add(new Player("Vincent", 20));
    	players.add(new Player("Vivian", 20));
    	players.add(new Player("Duong", 20));
    	assertNotNull(test.DetermineTurn(players));
    	assertEquals(4, test.DetermineTurn(players).size());
    }
    @Test
    public void testSetupNumOfPlayers()
    {
    	int numPlayers = test.setupNumOfPlayers();
        //int numPlayers = 2;
        assertTrue(numPlayers > 1 && numPlayers < 7);
    }
    @Test
    public void testSetupPlayer()
    {
    	ArrayList<Player> players = test.setupPlayer(2);
    	assertEquals(40, players.get(0).getNumOfTroops());

    }
	

}
