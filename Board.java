import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	int countryIndex;
	int dir= 0;
	private int numOfPlayers; 
	private Player[] players;
	private ArrayList<Country> countries;
	private ArrayList<Country> playerCountries;
	private ArrayList<String> countriesIdx;
	
	private Dice dice;
	private Deck deck;
	private Displayer show;
	int step = 0;
	String format = "%1$-5s | %2$-20s | %3$-24s | %4$-15s | %5$-11s | \n";
	
	public Board(Player[] p, ArrayList<Country> c, Deck dk, Dice d)
	{
		this.countryIndex = 0;
		this.players = p;
		this.countries = c;
		this.deck = dk;
		this.dice = d;
		this.show = new Displayer(c);
		numOfPlayers = p.length;
		setupCountryIndex();
		
		
	}
	public void setupCountryIndex()
	{
		countriesIdx = new ArrayList<String>();
		
		for (int i = 0; i < countries.size(); i++)
		{
			countriesIdx.add(countries.get(i).getCountryName());
		}
	}


	public int getCountryIndex(String c)
	{
		if (countriesIdx.contains(c))
		{
			countryIndex = countriesIdx.indexOf(c);
			return countryIndex;
		}
		return -1;
	}
	public int getPlayerIndex(String c)
	{
		for (int i = 0; i < players.length; i++)
		{
			if (players[i].getPlayerName().equals(c))
				return i;
		}
		return -1;
	}
	

}
