package riskboardgame;

import java.util.ArrayList;

public class Country   {
	private String OwnerName;
	private String CountryName;
	private int numOfArmy;
	private String continentName;
	private ArrayList<String> ConnectedCountry;

	public Country(String x){
		this.OwnerName = "Unknown";
		this.continentName = "Unknown";
		this.CountryName = x;
		this.numOfArmy = 0;
		this.ConnectedCountry = new ArrayList<String>();
	}
	public void setCountryInfo(String[] n){
		this.continentName = n[0];
		for (int i = 1; i < n.length; i++)
			this.ConnectedCountry.add(n[i]);
	}
	public ArrayList<String> getAdjacency()
	{
		return this.ConnectedCountry;
	}

	public String getOwnerName() { 
		return this.OwnerName; 
	}
	
	public String getCountryName() { 
		return this.CountryName; 
	}
	
	public int getNumOfArmy() { 
		return this.numOfArmy; 
	}
	
	public String getContinentName()
	{
		return this.continentName;
	}

	public void setOwnerName(String n) { 
		this.OwnerName = n; 
	}
	
	public void setCountryName(String n) {
		this.CountryName = n;
	}
	
	public void addNumOfArmy(int n) {
		this.numOfArmy += n;
	}
	public void loseNumOfArmy(int n)
	{
		this.numOfArmy -= n;
	}

}
