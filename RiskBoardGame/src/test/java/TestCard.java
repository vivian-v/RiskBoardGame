



import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Card;


public class TestCard extends TestCase {

	Card test = new Card("testDetail", "testType");

    @Test
    public void testGetDetail() {
    	assertEquals("testDetail", test.getDetail());
    }
    @Test
    public void testShuffle() {
    	assertEquals("testType", test.getType());
    }

	

}