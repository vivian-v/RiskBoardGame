import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.io.InputStreamReader;

public class Board {
	
	//info about regions
	int numOfRegions = 6;
	public String[] regionNames = {"Africa", "Asia", "Australia", "Euroupe", "North America", "South America"};
	static int setOfCardTraded = 0;
	
	
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
	public Board(Player[] p, ArrayList<Country> c, Dice d)
	{
		this.countryIndex = 0;
		this.players = p;
		this.countries = c;
		//this.deck = dk;
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
	
	
	public void Attack(int n)
	{
		ArrayList<String> TempCountryList;
		ArrayList<Integer> CheckDuplicatedNum = new ArrayList<Integer>();
		int AttackerDice;
		int DefenderDice;
		int[] AttackerRolls;
		int[] DefenderRolls;
		int num;
		int attackerIndex = 0;
		int defenderIndex = 0;
		int numAttackerLose = 0;
		int numDefenderLose = 0;
		String AttackerCountry;
		String DefenderCountry;
				
		Country C = new Country();
		
		show.spaceFormat();
		
		Scanner keyboard = new Scanner(System.in);
		playerCountries = players[n].showMyCountries();
		System.out.println("Choose one of your country to attack other country");
		
		for (int i = 0; i < playerCountries.size(); i++)
		{
			if (playerCountries.get(i).getNumOfArmy() > 1)
			{
				System.out.println((i + 1) + " : " + playerCountries.get(i).getCountryName() + " : Num of Army " + playerCountries.get(i).getNumOfArmy() );
				//CheckDuplicatedNum.add(i+1);
			}
		}
		
		do
		{
			countryIndex = keyboard.nextInt() - 1;
			if (!CheckDuplicatedNum.contains(countryIndex + 1))
				System.out.println("Invalid Input");
		}while (!CheckDuplicatedNum.contains(countryIndex + 1));
		
		AttackerCountry = playerCountries.get(countryIndex).getCountryName();
		
		show.spaceFormat();
			
		System.out.println("Here is the List that you can attack from " + AttackerCountry + " to :");
		C = players[n].getCountry(AttackerCountry);
		TempCountryList = C.ShowAdjacency();
		
		int x = 0;
		int idx = 0;
		for (int i = 0; i < TempCountryList.size(); i++)
		{
			x = getCountryIndex(TempCountryList.get(i));
			if (players[n].getPlayerName() != countries.get(x).getOwnerName())
			{
				System.out.println((idx+1)+ " : " + TempCountryList.get(i) + " : Num of Army " +countries.get(getCountryIndex(TempCountryList.get(i))).getNumOfArmy());
				idx++;
			}
		}

		countryIndex = keyboard.nextInt() - 1;
		DefenderCountry = TempCountryList.get(countryIndex);
				
		defenderIndex = getCountryIndex(DefenderCountry);
		attackerIndex = getCountryIndex(AttackerCountry);
		num = 1;
		while (num != 2)
		{
			show.spaceFormat();
			show.announceAttack(players[n].getPlayerName(), AttackerCountry, countries.get(defenderIndex).getOwnerName(), DefenderCountry);
					
			show.diceMenu(players[n].getPlayerName(), "Attacker", countries.get(getCountryIndex(AttackerCountry)).getNumOfArmy());
		
			AttackerDice = keyboard.nextInt();
			//AttackerRolls = dice.roll(AttackerDice);
			AttackerRolls = dice.roll(10);

			show.diceMenu(players[defenderIndex].getPlayerName(), "Defender", countries.get(getCountryIndex(DefenderCountry)).getNumOfArmy());
			DefenderDice = keyboard.nextInt();
			DefenderRolls = dice.roll(DefenderDice);
			
			System.out.println("Attacker's highest number : " + AttackerRolls[AttackerRolls.length - 1]);
			System.out.println("Defender's highest number : " + DefenderRolls[DefenderRolls.length - 1]);
	
			if (AttackerRolls[AttackerRolls.length - 1] > DefenderRolls[DefenderRolls.length - 1])
			{
				System.out.println("Attacker won");
				numDefenderLose++;
			} else if (AttackerRolls[AttackerRolls.length - 1] <= DefenderRolls[DefenderRolls.length - 1])
			{
				System.out.println("Defender won");
	
				numAttackerLose++;
			}
			
			if (AttackerDice > 1 && DefenderDice > 1)
			{
				System.out.println("Attacker's Second Highest Number : " + AttackerRolls[AttackerRolls.length - 2]);
				System.out.println("Defender's Second Highest number : " + DefenderRolls[DefenderRolls.length - 2]);
				
				if (AttackerRolls[AttackerRolls.length - 2] > DefenderRolls[DefenderRolls.length - 2])
				{
					System.out.println("Attacker won");
	
					numDefenderLose++;
				} else if (AttackerRolls[AttackerRolls.length - 2] <= DefenderRolls[DefenderRolls.length - 2])
				{
					System.out.println("Defender won");
	
					numAttackerLose++;
				}
			}
			
			show.spaceFormat();
			countries.get(attackerIndex).MinusNumOfArmy(numAttackerLose);
			countries.get(defenderIndex).MinusNumOfArmy(numDefenderLose);
			
			if (countries.get(attackerIndex).getNumOfArmy() < 2)
			{
				System.out.println("You Don't Have Enough Army to Attack");
				break;
			} else if (countries.get(defenderIndex).getNumOfArmy() < 1)
			{
				num = 3;
				break;
			} 
			
			System.out.println("Attacker's Army left : " + countries.get(attackerIndex).getNumOfArmy());
			System.out.println("Defender's Army left : " + countries.get(defenderIndex).getNumOfArmy());
			
			show.afterCombatMenu();
		
			do {
				num = keyboard.nextInt();
			} while (num != 1 && num != 2);
			
			numDefenderLose = 0;
			numAttackerLose = 0;
	
		}
		
		if (num == 3)
		{
			defenderIndex = getPlayerIndex(countries.get(defenderIndex).getOwnerName());
			countries.get(defenderIndex).setOwerName(countries.get(attackerIndex).getOwnerName());

			players[n].takeCountry(countries.get(defenderIndex));
			players[defenderIndex].loseCountry(countries.get(defenderIndex));

			displayPlayerInfo(n);
			displayPlayerInfo(defenderIndex);
		} else
		{
			displayPlayerInfo(n);
			displayPlayerInfo(defenderIndex);
		}
	}
	
	
	public void Fortify(int n)
	{
		int idx = 0;
		ArrayList<Integer> countryListIndex = new ArrayList<Integer>();
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Lists of your Countries that you can fortify");
		
		for (int i = 0; i < countries.size(); i++)
		{
			if (players[n].getPlayerName() == countries.get(i).getOwnerName() && countries.get(i).getNumOfArmy() > 1)
			{
				System.out.println((i + 1) + " : " + countries.get(i).getCountryName() + "  | Num of Army : " + countries.get(i).getNumOfArmy());
				countryListIndex.add(i);
				idx++;
			}
		}
		
		System.out.println("\nSelect a country");
		do {
			idx = keyboard.nextInt() - 1;
		} while(!countryListIndex.contains(idx));
		
		System.out.println(countries.get(idx).getCountryName());

	}
	
	
	
	/*
	 * Function to reinforce the troops
	 * @param n is the player index
	 * 
	 * @return number of bonus troops
	 * 
	 * 
	 */
	public void Reinforce (int index)
	{
		/* totalTroop = total of troops that would be awared based on:
		 * 		- troopsByTerritory = troops by number of territories you occupy
		 * 		- troopsByRegion = troops by regions if a player owns a whole region (decided by bonusByRegion)
		 * 		- troopsByCards = troops by value of matched sets of RISK cards
		 * 		- 2 added troops for a territory if a card's name matched with a player's owned territory
		 * 
		 */
		
		int totalTroops = 0;
		int troopsByTerritory = 0;
		int troopsByRegion = 0;
		int troopsByCards = 0;
		
		
		// Troops based on the number of territories you occupy divided by 3 and truncated
		troopsByTerritory = players[index].getTotalCountriesOwned() / 3;
		System.out.println("You [" + players[index].getPlayerName() + "] own " + players[index].getTotalCountriesOwned()+ 
             "territories. You get " + troopsByTerritory + " bonus troops.");
		
				
		
		//total number of countries in each region
		int[] fullRegion = {6, 12, 4, 7, 9, 4};
		
		//number of bonus troops a player will get if s/he owns a whole region
		int[] bonusByRegion = {3, 7, 4, 5, 5, 2};
		
		
		//retrieve the info about how many countries per region a player has conquered
		int[] countriesByRegions = players[index].getCountriesOwnedByRegions();
		
		
		//check for each region
		for (int i = 0; i < numOfRegions; i++)
		{
			//if find a region fully owned
			if (countriesByRegions[i] == fullRegion[i])
			{
				System.out.printf("You own region: " + regionNames[i] + " | + " + bonusByRegion[i] + " troops.\n");
				troopsByRegion += bonusByRegion[i];
				//then add bonus troops based on bonusByRegion
			}
		}
		
		//print out which region they own 
		System.out.println("You've gained " + troopsByRegion + " total troops for the regions you own.\n");
		
		
		//get troops by trading in cards
		troopsByCards = troopsByTradeInCards(index);
		
		
		//total troops earned
		totalTroops = troopsByTerritory + troopsByRegion + troopsByCards;
		
		//add it into total troops of a player
		players[index].addTroops(totalTroops);
		
		//Print out msg of how many bonus troops a player gets
		System.out.println("You've gotten " + totalTroops + " bonus troops. It's time to reinforce your territories.");
			
		
		//Placement of bonus troops into territories.
		System.out.println ("Choose countries you want to put extra armies in.");
		ArrayList <Country> playerCountries = players[index].showMyCountries();

		
		//Display list of countries a player owns, s/he will pick countries to put troops in
		for (int i = 0; i < playerCountries.size(); i++)
		{
			   System.out.format(format, i+1, playerCountries.get(i).getContinent(),playerCountries.get(i).getCountryName(), 
					   			playerCountries.get(i).getOwnerName(), playerCountries.get(i).getNumOfArmy());
		}
		
			
		int maxIndex = playerCountries.size(); //the last index of countries a player owns
		int countryPick; // index of country to put troops in
		int numTroops; // number of troops to put in
		
		
		//Place bonus army into player's choice of territories
		while (totalTroops > 0)
		{
			//ask what country to put troops in
			System.out.println("You currently have " + totalTroops + " troops. Select a country to put troops in.");
			countryPick = getIntInput(1, maxIndex) - 1; // because we start at 0
			
			
			//ask how many troops to put in that country
			System.out.println("How many troops do you want to put in " + playerCountries.get(countryPick).getCountryName() + "? ");
			numTroops = getIntInput(0, totalTroops);
			totalTroops -= numTroops;
						
			
			playerCountries.get(countryPick).AddNumOfArmy(numTroops);
			
			
		}// end while
		
		
		
		
		
		
	}
	
	
	/*
	 * CHECK valid INTEGER input
	 * only take input between @param minIndex and @param maxIndex (inclusive)
	 * 
	 * @return the valid integer input
	 */
	
	private int getIntInput (int minIndex, int maxIndex)
	{
		
		Scanner keyboard = new Scanner (System.in);
		
		//store valid integer input
		int numInput;
	
		do 
		{
			System.out.print("Valid number is from 1 to " + maxIndex + " >>   ");
			keyboard = new Scanner(System.in);
		} while (!keyboard.hasNextInt());
		
		 numInput = keyboard.nextInt();
		 
		 while (numInput < minIndex || numInput > maxIndex)
		 { 
			
			 do 
				{
					System.out.print("Invalid Input. Valid number is from " + minIndex + " to " + maxIndex + " >>  ");
					keyboard = new Scanner(System.in);
				} while (!keyboard.hasNextInt());
				
			numInput = keyboard.nextInt();
						 
		 }
		
		
		return numInput;
	}// end function checkIntInput
	
	
	
	
	/*
	 * function to decide trade in cards
	 * return troops earned by trading in cards
	 */
	private int troopsByTradeInCards(int index)
	{
		
		String input;// to get input if a user wants to trade card or not
		Boolean toTrade = false;
		
		//total troops
		int totalTroops = 0;
		
		ArrayList<Card> cardHeld = players[index].getCards();
		//get total cards a player is holding
		int totalCards = cardHeld.size();
		
		//to get user input
		Scanner keyboard = new Scanner(System.in);
		
		//ask if a player wanna trade card when s/he has >= 3 cards
		
		if (totalCards >= 5)
		{
			System.out.println("You have more than 5 cards. You have to trade in at least one set of cards (3 cards).");
			toTrade = true;
		}
		
		else if (totalCards >= 3)
		{
			//prompt to ask if player wanna trade in cards
			System.out.print("Do you want to trade in cards for troops? Enter y or Y for yes, any key for No >>  ");
			input = keyboard.nextLine();
			while (input != "y" && input != "n")
			{
				System.out.println("Invalid input. Enter y or Y for yes, any key for No >>  ");
				input = keyboard.nextLine();
			}// end while
			
			if (input == "y" || input == "Y")
				toTrade = true;
				
		}// end if
		
		
				
		//store indexes of cards picked
		int[] cardIndex = new int[3];	
		
		//Card array to store 3 cards picked
		Card[] cardPicked = new Card[3];
				
	
		//if a player wants/ has to trade in cards
		if (toTrade == true)
		{
			//Print out rules for trade in cards
			System.out.println("\t****RULES FOR TRADE IN CARDS***");
			System.out.println("Choose any set of 3 cards to trade in, either: ");
			System.out.println("\t- 3 cards of the same types.");
			System.out.println("\t- 3 cards of different types.");
			System.out.println("\t- Any 2 cards and a \"wild card\" (if any).");
			System.out.println("\t- BONUS 2 troops on any territory that has name matching with one of the cards you're trading in.");
			
			System.out.printf("%s -13s: %s","Card#", "Card Type", "Country");
			for (int i = 0; i < totalCards; i++)
			{
				System.out.printf("%d. %-13s: %s", i+1 , cardHeld.get(i).getType(), cardHeld.get(i).getName());
			}
			
			
			
			//bool to check if 3 cards is valid to trade
			boolean valid = false;
			
						
			while (valid == false)
			{
				System.out.println("\nPick the cards to trade in. Enter Card#  ");
				
				//get indexes of 3 cards picked
				cardIndex = pickedCardIndexArr(totalCards);

				
				//store 3 valid cards picked into a Card array
				for (int i = 0; i < 3; i++)
					cardPicked[i] = cardHeld.get(cardIndex[i]);
				
				
				//to be used later to see if any card has the name that 
				//matches with country a player owns
				
				
				//If 3 cards are same types
				if (cardPicked[0].getType() == cardPicked[1].getType() && cardPicked[1].getType() == cardPicked[2].getType())
					valid = true;
				
				//else if 3 card has different types
				else if ( (cardPicked[0].getType() != cardPicked[1].getType()) &&
						  (cardPicked[0].getType() != cardPicked[2].getType()) &&
						  (cardPicked[2].getType() != cardPicked[1].getType())
						)
					valid = true;
				
				//else if there is one wild card among 3
				else if (cardPicked[0].getType() == "wild" || cardPicked[1].getType() == "wild" || cardPicked[2].getType() == "wild")
						valid = true;
				
			}// end while loop -checking for valid cards
			
			
			
			//after trading in, remove the cards traded in
			if (valid == true)
			{
				
				for (int i = 0 ; i < 3; i++)
					players[index].removeCard(cardIndex[i]);
			}// end removing cards
			
			
			//update set of cards traded 
			setOfCardTraded++;
			
			/*
			 * # of troops awarded calculated as below:
			 * 		- 1st set = 4 armies
			 * 		- 2nd	  = 6
			 * 		- 3rd     = 8
			 * 		- 4th     = 10
			 * 		- 5th 	  = 12
			 * 		- 6th 	  = 15
			 * 		From 7th on is 20, 25, 30....
			 */
			
			if (setOfCardTraded < 6)
			{
				totalTroops = setOfCardTraded*2 + 2;
			}
			else if (setOfCardTraded == 6)
				totalTroops = setOfCardTraded*2 + 3;
			else 
			{
				int start = 15;
				totalTroops = start + (setOfCardTraded-6)*5;
				
			}
			
			
			//array of countries owned by player, to check if any card has name matching with owned country's name
			ArrayList <Country> playerCountries = players[index].showMyCountries();
									
			for (int i = 0; i < 3; i++)
			{
				//check for each countries to see if there's any matched result
				for (int j = 0; j < playerCountries.size(); j++)
					{
					if (cardPicked[i].getName() == playerCountries.get(j).getCountryName())
						{
							// print out msg then add 2 troop to that country
							System.out.println("Good Pick! You've picked a card that matches its name with a country you own."
									+ " You've added 2 troops into " + playerCountries.get(j).getCountryName());
							
							playerCountries.get(j).AddNumOfArmy(2);
							
							break; // because you can only add 2 troops with matched card ONCE.
						}
							
					} 
			}//end for		
			
		}// end trading cards
		
		
		
		//return total troops traded by cards.
		return totalTroops;
		
		
		
	}//end troopsByTradeInCards function
	
	
	
	
	/*
	 * a function to pick Cards, return indexes of cards picked
	 * Check:
	 * 	-	If they input a negative number and or a string that is not a number
	 * 	-	If they input out of range number
	 * 
	 * @return an array of indexes of picked card
	 */
	private int[] pickedCardIndexArr (int maxIndex)
	{
		
		int [] cards = new int [3];
		int numInput;
		
		for (int i = 0; i < 3; i++)
		{
			System.out.println("Enter card # ");
			numInput = getIntInput(1, maxIndex);
			
			 			 
			 //check if a player picked duplicate card
			 for (int j =0; j < i; j++)
			 {
				 while (numInput == cards[j])
				 {
					 
						System.out.print("Duplicate card. Pick another Card >>  ");
						numInput = getIntInput(1, maxIndex);
						 
				 }
				 
			 }
			 
			 //because we start from 0
			 cards[i] = numInput - 1;
					 
		}// end for
		
		
		//return array of indexes of cards
		return cards;
	}// end pickedCardIndexArr
	
	
	
	
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
//       // int[] x = {9, 4, 7, 6, 12, 4};
//        
//        public String[] regionNames = {3, 7, 4, 5, 5, 2};
//        if (players[n].getNumNorthAmerica() == 9)
//        {
//            troopsByRegion += 5;
//            System.out.println("You own region: North America | +5 troops.");
//        }
//        if (players[n].getNumSouthAmerica() == 4)
//        {
//            troopsByRegion += 2;
//            System.out.println("You own region: South America | +2 troops.");
//        }
//        if (players[n].getNumEurope() == 7)
//        {
//            troopsByRegion += 5;
//            System.out.println("You own region: Europe | +3 troops.");
//        }
//        if (players[n].getNumAfrica() == 6)
//        {
//            troopsByRegion += 3;
//            System.out.println("You own Region: Africa | +3 troops");
//        }
//        if (players[n].getNumAsia() == 12)
//        {
//            troopsByRegion += 7;
//            System.out.println("You own Region: Asia | +7 troops");
//        }
//        if (players[n].getNumAustralia() == 4)
//        {
//            troopsByRegion += 2;
//            System.out.println("You own Region: Australia | +2 troops");
//        }
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
		
		//nOCH = num of countries held
		int[] nOCH = players[n].getCountriesOwnedByRegions();
		
		for (int i = 0; i < 6; i++)
		{
			System.out.printf("%2d. %-13s: %2d\n", i+1, regionNames[i], nOCH[i]);
		}
		
		
	/*	
		System.out.println("1. North America : " + players[n].getNumNorthAmerica());
		System.out.println("2. South America : " + players[n].getNumSouthAmerica());
		System.out.println("3. Europe        : " + players[n].getNumEurope());
		System.out.println("4. Africa        : " + players[n].getNumAfrica());
		System.out.println("5. Asia          : " + players[n].getNumAsia());
		System.out.println("6. Australia     : " + players[n].getNumAustralia());
	*/
		
		
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
		//Scanner keyboard = new Scanner(System.in);	// To get user input
		
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
			if (!check)/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
