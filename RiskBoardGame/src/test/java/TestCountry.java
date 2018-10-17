



import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Country;


public class TestCountry extends TestCase {

	Country test = new Country("China");
//	@Before
//	public void setup()
//	{
//		test.setContinentName("Asia");
//	}
    @Test
    public void testSetCountryInfo()
    {
    	String[] testStr = {"test1", "test2", "test3"};
    	test.setCountryInfo(testStr);
    	
    	assertEquals("test1", test.getContinentName());
    	assertEquals("test2", test.getAdjacency().get(0));
    	assertEquals("test3", test.getAdjacency().get(1));

    }
    @Test
    public void testGetContinentName()
    {
    	String[] testStr = {"Asia", "test1", "test2"};
    	test.setCountryInfo(testStr);
 	
    	assertEquals("Asia", test.getContinentName());
    }
    @Test
    public void testGetAdjacency()
    {
    	String[] testStr = {"Asia", "test1", "test2"};
    	test.setCountryInfo(testStr);
    	
    	assertEquals(2, test.getAdjacency().size());
    	assertEquals("test1", test.getAdjacency().get(0));
    	assertEquals("test2", test.getAdjacency().get(1));
    }
    @Test
    public void testSetOwnerName()
    {
    	test.setOwnerName("Chang");
    	assertEquals("Chang", test.getOwnerName());
    }

    @Test
    public void testGetOwnerName()
    {
    	test.setOwnerName("test");
    	assertEquals("test", test.getOwnerName());
    }
    @Test
    public void testGetNumOfArmy() {
    	test.addNumOfArmy(1);
    	assertEquals(1, test.getNumOfArmy());
    	
    }
    @Test
    public void testAddNumOfArmy() {
     	test.addNumOfArmy(5);
    	assertEquals(5, test.getNumOfArmy());
    }


	

}