import static org.junit.Assert.*;

import org.junit.Test;



import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.MyBot;

public class TestMyBot {

	MyBot mybot = new MyBot();
	
	
	@Test
	public void testLog() {

    	assertTrue(mybot.log("Chang", "Test", "12345", "test", "bot_answerTest"));

	
	
	}

	@Test
	public void testDisplayGameID() {

    	assertEquals(mybot.DisplayGameID(12345), 12345);

	
	
	}


}
