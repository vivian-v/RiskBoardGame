import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	private String playerName;
	private int numTroops;
	private int playerID;
    private int [] countriesOwnedByRegions;
	private ArrayList<Country> ownedCountries;
	private String[] regionNames = {"Africa", "Asia", "Australia", "Europe", "North America", "South America"};
	private ArrayList<Card> cards;
	public Player (String name, int num)
	{
		this.playerName = name;
		this.numTroops = num;
		this.ownedCountries = new ArrayList<Country>();
		this.countriesOwnedByRegions = new int[6];
		this.cards = new ArrayList<Card>();
	}
	public void addCard(Card card)
	{
		this.cards.add(card);
	}
	
	
	public void takeCountry(Country country)
	{
		ownedCountries.add(country);
		AddRegionHeld(country);
	}
	public void loseCountry(Country country)
	{
		ownedCountries.remove(country);
		MinusRegionHeld(country);

	}
	public String getPlayerName()
	{
		return this.playerName;
	}
	public void setPlayerID(int id)
	{
		this.playerID = id;
	}
	public int getPlayerTroops()
	{
		return this.numTroops;
	}
	public void putOneAmry()
	{
		this.numTroops -= 1;
	}
	public int getTotalCountriesOwned()
	{
		return this.ownedCountries.size();
	}
	public ArrayList<Country> getOwnedCountries()
	{
		return this.ownedCountries;
	}
	public void MinusRegionHeld(Country country)
	{
		String tempStr = country.getContinentName();
		
		for (int i = 0; i < 6; i++)
		{
			if (regionNames[i].equals(tempStr))
			{
				this.countriesOwnedByRegions[i]--;
				break;
			}
		}

	}
	public boolean isPlayerDead()
	{
		return ownedCountries.size() < 1 ? true : false;
	}
	public void AddRegionHeld(Country country)
	{
		String tempStr = country.getContinentName();
		//System.out.println(tempStr);
		for (int i = 0; i < 6; i++)
		{
			if (regionNames[i].equals(tempStr))
			{
				this.countriesOwnedByRegions[i]++;
				break;
			}
		}
		
	}//end AddRegionHeld
	public int[] getCountriesOwnedByRegions()
	{
		return this.countriesOwnedByRegions;
	}
	public void addTroops(int n)
	{
		this.numTroops += n;
	}
	public void showRegions()
	{
		for (int i = 0 ; i < countriesOwnedByRegions.length; i++)
		{
			System.out.println(countriesOwnedByRegions[i]);
		}
	}
}
