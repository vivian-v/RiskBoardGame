



import java.io.IOException;

import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Board;
import riskboardgame.Country;
import riskboardgame.Setup;


public class TestBoard extends TestCase {

	Setup setup = new Setup();

    @Test
    public void testAttack() throws IOException {
    	Board bd  = new Board(setup.LoadMap(), setup.setupPlayer(2));
    	
    	assertEquals("attack action", bd.attack(0, 1));

    }
    @Test
    public void testIsAttackable() throws IOException {
    	Board bd  = new Board(setup.LoadMap(), setup.setupPlayer(2));

    	String[] list1 = {"t1", "t2", "t3"};
    	Country c1 = new Country("test");
    	c1.setCountryInfo(list1);
    	
    	String[] list2 = {"t1", "t2", "t3"};
    	Country c2 = new Country("test");
    	c1.setCountryInfo(list2);
    	
    	assertFalse(bd.isAttackable(c1, c2));
    }
    @Test
    public void testTransferOwnership() throws IOException
    {
    	Board bd  = new Board(setup.LoadMap(), setup.setupPlayer(2));
    	assertTrue(bd.transferOwnership("China", 0,"Alberta", 1));
    }

	@Test
	public void testKillPlayer() throws IOException
	{
    	Board bd  = new Board(setup.LoadMap(), setup.setupPlayer(2));
    	assertEquals(1, bd.killPlayer(0));
	}
	@Test
	public void testArmyPlacement() throws IOException {
	    Board bd  = new Board(setup.LoadMap(), setup.setupPlayer(2));
	    	
	    assertEquals("army placement action", bd.armyPlacement(0));

	}
	@Test
	public void testfortify() throws IOException {
	    Board bd  = new Board(setup.LoadMap(), setup.setupPlayer(2));
	    	
	    assertEquals("fortify action", bd.fortify(0));

	}
	@Test
	public void testReinforcement() throws IOException {
	    Board bd  = new Board(setup.LoadMap(), setup.setupPlayer(2));
	    	
	    assertEquals("reinforce action", bd.reinforce(0));

	}
}