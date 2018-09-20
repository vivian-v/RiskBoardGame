import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerFuncTest {

	@Test
	public void isPlayerDeadTest() {
		Player a = new Player("a",0);
    	Country b = new Country("ASD");
		a.takeCountry(b);
		assert(!a.isPlayerDead());
		a.loseCountry(b);
		assert(a.isPlayerDead());
	}	
}
