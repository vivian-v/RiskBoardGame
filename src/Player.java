import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	int NorthAmericaHeld;
	int SouthAmericaHeld;
	int EuropeHeld;
	int AfricaHeld;
	int Asiaheld;
	int AustraliaHeld;

	
	private String playerName;
	private int numTroops;
    private HashMap<String, Country> ownedCountries;
    private ArrayList<Card> cardHeld;
    int playerID;
    
	public Player (String name, int num)
	{
		this.playerID = 0;
		this.playerName = name;
		this.numTroops = num;
		this.NorthAmericaHeld = 0;
		this.SouthAmericaHeld = 0;
		this.EuropeHeld = 0;
		this.AfricaHeld = 0;
		this.Asiaheld = 0;
		this.AustraliaHeld = 0;
		cardHeld = new ArrayList<Card>();
		ownedCountries = new HashMap<String, Country>();
		

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
		if (tempStr.equals("North America"))
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
	}
	public void AddRegionHeld(Country country)
	{
		String tempStr = country.getContinent();
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
	}
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
	public void addCard(Card card)
	{
		cardHeld.add(card);
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
	
}
