import java.util.ArrayList;
import java.util.HashMap;


public class Player {
/*	int NorthAmericaHeld;
	int SouthAmericaHeld;
	int EuropeHeld;
	int AfricaHeld;
	int Asiaheld;
	int AustraliaHeld;
*/
	
	//name of regions, this order to be used throughout the whole program (alphabetically ordered)
	private String[] regionNames = {"Africa", "Asia", "Australia", "Euroupe", "North America", "South America"};

	
	private String playerName;
	private int numTroops;
    private HashMap<String, Country> ownedCountries;
    private ArrayList<Card> cardHeld;
    int playerID;
    
    //number of countries owned by regions, there are 6 regions
    private int [] countriesOwnedByRegions;
    
	public Player (String name, int num)
	{
		this.playerID = 0;
		this.playerName = name;
		this.numTroops = num;
				
		//initiallize the number of countries owned by regions
		//initially = 0. initilization is guaranteed by Java that 0's are made.
		countriesOwnedByRegions = new int[6];
		
		
		cardHeld = new ArrayList<Card>();
		ownedCountries = new HashMap<String, Country>();
		
		
	/*	
		this.NorthAmericaHeld = 0;
		this.SouthAmericaHeld = 0;
		this.EuropeHeld = 0;
		this.AfricaHeld = 0;
		this.Asiaheld = 0;
		this.AustraliaHeld = 0;
		
	*/
		

	}
	
	public void setPlayerID(int x)
	{
		this.playerID = x;
	}
	public int getTotalNumCountries()
	{
		return ownedCountries.size();
	}
	public int getPlayerID()
	{
		return this.playerID;
	}
	public String getPlayerName()
	{
		return this.playerName;
	}
	public int getPlayerTroops()
	{
		return numTroops;
	}
	
	public void addTroops(int x)
	{
		numTroops += x;
	}
	
	public void addTroopsToCountry (Country country, int num)
	{
		country.AddNumOfArmy(num);
	}
	
	
	
	public void takeCountry(Country country)
	{		
		ownedCountries.put(country.getCountryName(), country);
		AddRegionHeld(country);
	}
	
	
	public void loseCountry(Country country)
	{
		ownedCountries.remove(country.getCountryName());
		MinusRegionHeld(country);
	}
	public void MinusRegionHeld(Country country)
	{
		String tempStr = country.getContinent();
		
		for (int i = 0; i < 6; i++)
		{
			if (regionNames[i] == tempStr)
			{
				this.countriesOwnedByRegions[i]--;
				break;
			}
		}
	
/*		if (tempStr.equals("North America"))
			this.NorthAmericaHeld--;
		else if (tempStr.equals("South America"))
			this.SouthAmericaHeld--;
		else if (tempStr.equals("Europe"))
			this.EuropeHeld--;
		else if (tempStr.equals("Africa"))
			this.AfricaHeld--;
		else if (tempStr.equals("Asia"))
			this.Asiaheld--;
		else if (tempStr.equals("Australia"))
			this.AustraliaHeld--;
*/
	}
	
	
	//increment by 1 the country held in a specified region
	public void AddRegionHeld(Country country)
	{
		String tempStr = country.getContinent();
		for (int i = 0; i < 6; i++)
		{
			if (regionNames[i] == tempStr)
			{
				this.countriesOwnedByRegions[i]++;
				break;
			}
		}
		
/*
		if (tempStr.equals("North America"))
			this.NorthAmericaHeld++;
		else if (tempStr.equals("South America"))
			this.SouthAmericaHeld++;
		else if (tempStr.equals("Europe"))
			this.EuropeHeld++;
		else if (tempStr.equals("Africa"))
			this.AfricaHeld++;
		else if (tempStr.equals("Asia"))
			this.Asiaheld++;
		else if (tempStr.equals("Australia"))
			this.AustraliaHeld++;
			
*/
	}//end AddRegionHeld
	
	
	/*
	 * <p>this will return {@code countriesOwnedByregions} a array of number of countries held based on regions</p>
	 */
	public int[] getCountriesOwnedByRegions()
	{
		return this.countriesOwnedByRegions;
	}

	/*
	public int getNumNorthAmerica()
	{
		return this.NorthAmericaHeld;
	}
	public int getNumSouthAmerica()
	{
		return this.SouthAmericaHeld;
	}
	public int getNumEurope()
	{
		return this.EuropeHeld;
	}
	public int getNumAfrica()
	{
		return this.AfricaHeld;
	}
	public int getNumAsia()
	{
		return this.Asiaheld;
	}
	public int getNumAustralia()
	{
		return this.AustraliaHeld;
	}
	*/
	
	//add a @param card into a player's card
	public void addCard(Card card)
	{
		cardHeld.add(card);
	}
	
	//remove a card by index
	public void removeCard (int index)
	{
		cardHeld.remove(index);
	}
	
	
	/*
	 * return total of cards held
	 */
	public ArrayList<Card> getCards ()
	{
		return this.cardHeld;
	}
	
	public void putOneAmry()
	{
		numTroops -= 1;
	}
	
	public ArrayList<Country> showMyCountries()
	{
		return new ArrayList<Country>(ownedCountries.values());
		
	}
	public Country getCountry(String s)
	{
		return ownedCountries.get(s);
	}
	
	
	//this function is to return total countries owned
	public int getTotalCountriesOwned()
	{
		//sum all number of countries owned by regions
		int total = 0;
		
		for (int i = 0; i < 6; i++)
		{
			total += this.countriesOwnedByRegions[i];
		}
	
		return total;
	}// end getTotalCountriesOwned
	
	/*
	 * Show all the cards the player has
	 */
	
	
	
}
