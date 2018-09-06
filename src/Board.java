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
	public void GameStart()
	{
		Boolean end = false;
		show.gameStart();	
		
		displayMapInfo();
		ArmyPlacement();
		int idx = 0;
		for (int i = 0; i < 1; i++)
		{
//			Fortify(players[idx].getPlayerID());
			displayMapInfo();
			displayPlayerInfo(players[idx].getPlayerID());
			Reinforce(players[idx].getPlayerID());
			Attack(players[idx].getPlayerID());
			 
		
		}
		
		//System.out.println(countries.get(getCountryIndex("China")).getOwnerName());
		
		displayMapInfo();
//		displayPlayerInfo(0);
		
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

//	public void Reinforce(int n) //player index is being passed into parameter
//    {
//        // Troops based on the number of territories you occupy divided by 3 and truncated
//        int troopsByTerritory = players[n].getNumTerritoriesOwned() / 3;
//        System.out.println("You [" + players[n].getPlayerName() + "] own " + players[n].getNumTerritoriesOwned() + 
//                "territories. You get " + troopsByTerritory + " bonus troops.");
//
//        // Troops based on the value of the continents you control.
//        int troopsByRegion = 0;
//        
//        int[] x = {9, 4, 7, 6, 12, 4};
//        
//        
//        // print out which region they own 
//        System.out.println("You gain " + troopsByRegion + " total troops for the regions you own.");
//
//        // -200 IQ
//
//        // the value of the matched sets of RISK cards you trade in.
//    }

	public void displayPlayerInfo(int n)
	{
		show.spaceFormat();

		System.out.println(players[n].getPlayerName() + "'s Information\n");
		System.out.format(format, "Index", "Region" ,"Country Name", "Land Owner", "Num of Army");
		playerCountries = players[n].showMyCountries();

		for (int i = 0; i < playerCountries.size(); i++)
		{
			   System.out.format(format, i+1, playerCountries.get(i).getContinent(),playerCountries.get(i).getCountryName(), playerCountries.get(i).getOwnerName(), playerCountries.get(i).getNumOfArmy());
		}
		
		System.out.println("\n=Statistic=");
		System.out.println("Number of Troops : " + players[n].getPlayerTroops());
		
		System.out.println("1. North America : " + players[n].getNumNorthAmerica());
		System.out.println("2. South America : " + players[n].getNumSouthAmerica());
		System.out.println("3. Europe        : " + players[n].getNumEurope());
		System.out.println("4. Africa        : " + players[n].getNumAfrica());
		System.out.println("5. Asia          : " + players[n].getNumAsia());
		System.out.println("6. Australia     : " + players[n].getNumAustralia());

		
		
	}
	public void displayMenu()
	{
		
	}
	public void ArmyPlacement()
	{
		ArrayList<Integer> checkDuplicatedNum = new ArrayList<Integer>();
		int TotalCountry = 42;
		boolean check = false;
		int idx = 0;
		int k = 0;
		Scanner keyboard = new Scanner(System.in);	// To get user input
		
		int testindex1 = 1;
		int testindex2 = 1;
		
		while (players[numOfPlayers - 1].getPlayerTroops() != 0)
		{
			System.out.println(players[k].getPlayerName() + "'s turn. Please, select a country ");
			for (int i = 0; i< countries.size(); i++)
			{
				if ((!countries.get(i).isOccupied()))
				{
					System.out.println(i + 1 + " : " + countries.get(i).getCountryName());	
					
				}else if ((countries.get(i).getOwnerName() == players[k].getPlayerName()) && check)
				{
					System.out.println(i + 1 + " : " + countries.get(i).getCountryName());	
				}
			}			

			if (!check)
			{
				do
				{
					idx = testindex1++;
					//idx = keyboard.nextInt();
					if (!check)
					{
						if (idx < 1 || idx > 42 || checkDuplicatedNum.contains(idx))
							System.out.println("invalid input, please do it again");
					} 
				} while (idx < 1 || idx > 42 || checkDuplicatedNum.contains(idx));
			} else
			{
				do
				{
					idx = testindex2++;
					//idx = keyboard.nextInt();
					if (!check)
					{
						if (idx < 1 || idx > 42)
							System.out.println("invalid input, please do it again");
					} 
				} while (idx < 1 || idx > 42);
				
			}

			checkDuplicatedNum.add(idx);

			countries.get(idx - 1).setOwerName(players[k].getPlayerName());
			countries.get(idx - 1).AddNumOfArmy(1);
			players[k].putOneAmry();
			if (!check)
			players[k].takeCountry(countries.get(idx - 1));

			if (k == numOfPlayers - 1)
				k = 0;
			else 
				k++;
			
			TotalCountry--;
			if (TotalCountry == 0)
				check = true;
		}
		
		show.placementEnd();
	}

	public void displayMapInfo()
	{
		System.out.format(format, "Index", "Region" ,"Country Name", "Land Owner", "Num of Army");
		for (int i = 0; i < countries.size(); i++)
		{
			   System.out.format(format, i+1, countries.get(i).getContinent(),countries.get(i).getCountryName(), countries.get(i).getOwnerName(), countries.get(i).getNumOfArmy());
		}
	
	}
}
