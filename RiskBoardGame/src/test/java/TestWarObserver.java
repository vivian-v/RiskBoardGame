import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Player;
import riskboardgame.WarObserver;

public class TestWarObserver extends TestCase {

	WarObserver warObserver = new WarObserver();
	Player p1 = new Player("Vivian", 123, 1);
	Player p2 = new Player("Vincent", 123, 1);

    @Test
    public void testAddObserver() {
    	warObserver.addObserver(p1);
		warObserver.addObserver(p2);
		assertNotNull(warObserver);
//		warObserver.notifyWarObservers();
    }
    @Test
    public void testRemoveObserver() {
    	warObserver.addObserver(p1);
		warObserver.addObserver(p2);
		warObserver.removeObserver(p2);
    	assertEquals(1, warObserver.notifyWarObservers());
    }
    @Test
    public void testNotifyWarObservers() {
      	warObserver.addObserver(p1);
    	warObserver.addObserver(p2);
    	assertEquals(2, warObserver.notifyWarObservers());
    }

	

}
