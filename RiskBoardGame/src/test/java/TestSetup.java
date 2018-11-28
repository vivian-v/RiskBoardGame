



import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Deck;
import riskboardgame.Setup;


public class TestSetup extends TestCase {


    @Test
    public void testLoadDeck() {
    	Setup setup = new Setup();
    	Deck testDeck = new Deck();
    	testDeck  =setup.LoadDeck();
    	assertEquals("Alaska", testDeck.draw().getDetail());
    	assertNotNull(setup.LoadDeck());
    }
 

	

}