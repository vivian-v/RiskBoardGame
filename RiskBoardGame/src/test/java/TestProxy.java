



import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Proxy;
import riskboardgame.Transaction;

import java.util.ArrayList;


public class TestProxy extends TestCase {

	Transaction player = new Proxy(20);


    @Test
    public void testBuyCards() {
 	   	assertEquals(19, player.buyCards());
    }
    @Test
    public void testBuyUndoActions() {
    	assertEquals(15, player.buyUndoActions());

    }
    @Test
    public void testTransferCredit() {
    	assertEquals(20, player.transferCredit());

    }
    @Test 
    public void testGetCredit() {
    	assertEquals(20, player.getCredit());
    }

    @Test
    public void testProxy()
    {
    	Proxy test = new Proxy(20);
    	test.getCredit();
    	test.buyCards();
    	test.buyUndoActions();
    	test.transferCredit();
    }
	

}
