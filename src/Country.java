import java.util.ArrayList;


public class Country {

	String Continent;
	String name;
	String owner;
	boolean checkOccupied;
	int numOfArmy;
	
    private ArrayList<String> ConnectedCountry = new ArrayList<String>();

	public Country()
	{
	
	}
	
	public Country(String n)
	{
		this.owner = "Unknown";
		this.Continent = null;
		this.name = n;
		this.numOfArmy = 0;
		this.checkOccupied = false;
	}
	public void addAdjacencies(ArrayList<String> adjacencies) {
			
			this.ConnectedCountry = adjacencies;
	}
	public String getOwnerName()
	{
		return owner;
	}
	public String getContinent()
	{
		return this.Continent;
	}
	public void setOwerName(String name)
	{
		this.owner = name;
	}
	public String getCountryName()
	{
		return this.name;
	}
	public int getNumOfArmy()
	{
		return this.numOfArmy;
	}
	public void AddNumOfArmy(int num)
	{
		this.numOfArmy += num;
	}
	public void MinusNumOfArmy(int num)
	{
		this.numOfArmy -= num;
	}
	public boolean isOccupied()
	{
		if (this.owner != "Unknown")
			return true;
		else
			return false;
	}
	public boolean isConnected(String nearCountry)
	{
		if (ConnectedCountry.contains(nearCountry))
			return true;
		else
			return false;	
	}
	public ArrayList<String> ShowAdjacency()
	{
//		ArrayList<String> temp = ConnectedCountry;
//		temp.remove(index)
		return ConnectedCountry;
	}
	
	public String showAttackableCountry()
	{
		return Continent;
		
	}
	public void setCountryInfo(String[] n)
	{
		this.Continent = n[0];
		for (int i = 1; i < n.length; i++)
			ConnectedCountry.add(n[i]);
	}


}
