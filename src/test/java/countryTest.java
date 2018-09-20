import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import junit.framework.TestCase;


import org.junit.Test;

public class countryTest extends TestCase {

	protected Country C1 = new Country("China");
	protected String owner = "Vincent";
	protected int numOfArmy = 10;
	protected String[] connectedCountry = {"Japan", "Korea"};

	protected void setUp()
	{
		C1.AddNumOfArmy(numOfArmy);
		C1.setCountryInfo(connectedCountry);
		C1.setOwerName(owner);
	}
    @Test
    public void test() {
    	
    	assertTrue(C1.isConnected("Korea"));
   	
    	
    	assertEquals("China", C1.getCountryName());
        assertEquals(10, C1.getNumArmy());
        assertEquals("Vincent", C1.getOwnerName());


    }

	

}

