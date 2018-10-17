

import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Card;
import riskboardgame.Country;
import riskboardgame.Player;



public class TestPlayer extends TestCase {
	Player test = new Player("Vivian", 40);
	Country testCountry = new Country("China");
	Card testCard = new Card("testCard", "cannon");
	
	
	
    @Test
    public void testGetNumOfTroops() {
    	assertEquals(40, test.getNumOfTroops());
    }
    @Test
    public void testLoadMap() {
    	assertTrue(test.isPlayerDead());
    }
    @Test
    public void testGetPlayerName()
    {
    	assertEquals("Vivian", test.getPlayerName());
    }
    @Test
    public void testGetCountriesOwnedByRegions()
    {
    	assertNotNull(test.getCountriesOwnedByRegions());
    }
    @Test
    public void testSetupPlayer()
    {
    	test.setPlayerName("Vincent");
    	assertEquals("Vincent", test.getPlayerName());
    }
	@Test
	public void testSetNumOfTroops()
	{
		test.addNumOfTroops(20);
		assertEquals(60, test.getNumOfTroops());
	}
	@Test
	public void testTakeCountry()
	{
		test.takeCountry(testCountry);
		assertEquals("China", test.getOwnedCountries().get(0).getCountryName());
		assertEquals(1, test.getOwnedCountries().size());
	}
	@Test
	public void testLoseCountry()
	{
		test.loseCountry(testCountry);
		assertEquals(0, test.getOwnedCountries().size());
	}
	@Test
	public void testAddCard()
	{
		test.addCard(testCard);
		assertEquals("testCard", test.drawCard().getDetail());
	}
	@Test
	public void testDrawCard()
	{
		test.addCard(testCard);
		test.drawCard();
		assertEquals(0, test.getAllCards().size());
	}
	@Test
	public void testGetAllCard()
	{
		test.addCard(testCard);
		assertEquals(1, test.getAllCards().size());
		assertNotNull(test.getAllCards());
	}
	@Test
	public void testGetOwnedCountries()
	{
		Country testCountry2 = new Country("Japan");
		test.takeCountry(testCountry2);
		assertEquals(1, test.getOwnedCountries().size());
		assertEquals("Japan", test.getOwnedCountries().get(0).getCountryName());
	}
	@Test
	public void testUpdateRegionHead()
	{
		Country testCountry3 = new Country("test");
		String testString[] = {"Africa", "test1", "test2"};
		testCountry3.setCountryInfo(testString);
		int testRegions[] = test.getCountriesOwnedByRegions();

		test.updateRegionHeld(testCountry3, 1);
		assertEquals(1, testRegions[0]);
		
		test.updateRegionHeld(testCountry3, -1);
		assertEquals(0, testRegions[0]);

	}
	
}