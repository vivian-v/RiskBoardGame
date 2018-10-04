package demo3;
import java.util.ArrayList;

public class Player  {
	
	private int numOfTroops;
    private int [] countriesOwnedByRegions;

	private String playerName;
	private String[] regionNames = {"Africa", "Asia", "Australia", "Europe", "North America", "South America"};;

	private ArrayList<Country> ownedCountries;
	private ArrayList<Card> cards;
	
	public Player(String s, int n)	{
		this.playerName = s;
		this.numOfTroops = n;
		this.ownedCountries = new ArrayList<Country>();
		this.cards = new ArrayList<Card>();
		this.countriesOwnedByRegions = new int[6];
	}
	public int getNumOfTroops()
	{
		return numOfTroops;
	}
	public boolean isPlayerDead()
	{
		return this.ownedCountries.size() < 1 ? true : false;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public int[] getCountriesOwnedByRegions()
	{
		return this.countriesOwnedByRegions;
	}
	
	public void setPlayerName(String n)	{
		this.playerName = n;
	}

	public void setNumOfTroops(int n) {
		this.numOfTroops += n;
	}
	
	public void takeCountry(Country c) {
		this.ownedCountries.add(c);
		updateRegionHeld(c, 1);
	}
	
	public void loseCountry(Country c) {
		this.ownedCountries.remove(c);
		updateRegionHeld(c, -1);

	}
	
	public void addCard(Card c) {
		this.cards.add(c);
	}
	
	public Card drawCard()
	{
		return this.cards.remove(0);
	}
	public ArrayList<Card> getAllCards()
	{
		return this.cards;
	}
	public ArrayList<Country> getOwnedCountries() {
		return this.ownedCountries;
	}
	
	public void updateRegionHeld(Country c, int n) {
		String tempStr = c.getContinentName();
		for (int i = 0; i < 6; i++)	{
			if (this.regionNames[i].equals(tempStr)) {
				this.countriesOwnedByRegions[i] += n;
				break;
			}
		}
	}

}

