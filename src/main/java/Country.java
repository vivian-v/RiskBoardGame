import java.util.ArrayList;

public class Country {
	private int numOfArmy;

	private String OwnerName;
	private String ContinentName;
	private String CountryName;

	private ArrayList<String> ConnectedCountry;
		
	public Country(String x){
		this.OwnerName = "Unknown";
		this.ContinentName = null;
		this.CountryName = x;
		this.numOfArmy = 0;
		this.ConnectedCountry = new ArrayList<String>();
	}
	public void setCountryInfo(String[] n){
		this.ContinentName = n[0];
		for (int i = 1; i < n.length; i++)
			this.ConnectedCountry.add(n[i]);
	}
	public void loseNumArmy(int n)
	{
		this.numOfArmy -= n;
	}
	public int getNumArmy(){
		return this.numOfArmy;
	}
	public String getOwnerName(){
		return this.OwnerName;
	}
	public String getContinentName(){
		return this.ContinentName;
	}
	public String getCountryName(){
		return this.CountryName;
	}
	public boolean isConnected(String nearCountry){
		if (this.ConnectedCountry.contains(nearCountry))
			return true;
		else
			return false;	
	}
	public ArrayList<String> getAdjacency(){
		return this.ConnectedCountry;
	}
	public void setOwerName(String name)
	{
		this.OwnerName = name;
	}
	public void AddNumOfArmy(int num)
	{
		this.numOfArmy += num;
	}
}
