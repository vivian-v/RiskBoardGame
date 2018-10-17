


import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Dice;


public class TestDice extends TestCase {

	Dice test = new Dice();


    @Test
    public void testRoll() {
    	int[] testNum = test.roll(3);
    	
    	assertTrue(testNum[0] > 0 && testNum[0] < 7);
    	assertTrue(testNum[1] > 0 && testNum[1] < 7);
    	assertTrue(testNum[2] > 0 && testNum[2] < 7);

    	assertEquals(2, test.roll(2).length);
    	assertEquals(3, test.roll(3).length);
    	

    }
    @Test
    public void testForone() {
    	assertTrue(test.rollForOne() > 0 && test.rollForOne() < 7);
    }

	@Test
	public void testMaxNumDice()
	{
		assertEquals(3, test.maxNumDice("Attacker", 4));
		assertEquals(2, test.maxNumDice("Defender", 4));

	}

}