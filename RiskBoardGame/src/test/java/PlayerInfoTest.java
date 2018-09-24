import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;


	
	public class PlayerInfoTest extends TestCase {

	    protected Player p1 = new Player("test", 22);

	    protected void setUp()
	    {
	        Country country1 = new Country("Ural");
	        Country country2 = new Country("China");
	        Country country3 = new Country("Japan");
	        p1.takeCountry(country1);
	        p1.takeCountry(country2);
	        p1.takeCountry(country3);
	     
	    }
	    
	    @Test
	    public void test() {
	        assertEquals("test", p1.getPlayerName());
	        assertEquals(22, p1.getPlayerTroops());
	        assertEquals("Ural", p1.getOwnedCountries().get(0).getCountryName());
	        assertEquals("China", p1.getOwnedCountries().get(1).getCountryName());
	        assertEquals("Japan", p1.getOwnedCountries().get(2).getCountryName());

	    }
	}

