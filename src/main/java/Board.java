import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Board {
	private int currentTurn;
	private int totalCountries;
	private int numOfRegions;
	private int numOfPlayers; 
	private Player[] players;
	private Dice dice;
	private Deck deck;
	private ArrayList<String> index;
	private HashMap<String, Country> Map;
	private String[] regionNames = {"Africa", "Asia", "Australia", "Euroupe", "North America", "South America"};
	private String format = "%1$-5s | %2$-20s | %3$-24s | %4$-15s | %5$-11s | \n";
	
	public Board(Player[] p, ArrayList<String> ci, HashMap<String, Country> m, Deck dk, Dice d){
		this.players = p;
		this.index = ci;
		this.Map = m;
		this.deck = dk;
		this.dice = d;
		this.currentTurn = 0;
		this.numOfRegions = 6;
		this.numOfPlayers = p.length;
		this.totalCountries = m.size();
	}
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public void GameStart(){
		ArmyPlacement();
		Reinforce(0);
		Attack();
		Fortify();
		//Check();
		
	}
	public void Check()
	{
		
		displayMapInfo();
		
		//for (players[0].)
		
	}
	public void ArmyPlacement()
	{
		ArrayList<Integer> checkDuplicatedNum = new ArrayList<Integer>();
		Scanner keyboard = new Scanner(System.in);	// To get user input

		boolean check = false;
		int idx = 0;
		int k = 0;
		int numTotal = 42;

		int testindex1 = 1;
		int testindex2 = 1;
		
		while (players[numOfPlayers - 1].getPlayerTroops() != 0)
		{
			System.out.println(players[k].getPlayerName() + "'s turn. Please, select a country ");
			for (int i = 0; i < totalCountries; i++)
			{
				if (Map.get(index.get(i)).getOwnerName() == "Unknown")
				{
					System.out.println(i + 1 + " : " + index.get(i));	

					
				} else if (Map.get(index.get(i)).getOwnerName().equals(players[k].getPlayerName())  && check)
				{
					System.out.println(i + 1 + " : " + index.get(i));	
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

			Map.get(index.get(idx - 1)).setOwerName(players[k].getPlayerName());
			Map.get(index.get(idx - 1)).AddNumOfArmy(1);
			players[k].putOneAmry();
		
			if (!check)
				players[k].takeCountry(Map.get(index.get(idx - 1)));

			if (k == numOfPlayers - 1)
				k = 0;
			else 
				k++;
			
			numTotal--;
			if (numTotal == 0)
				check = true;
		}
		
	}
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
		int[] fullRegion = {6, 12, 4, 7, 9, 4};
		int[] bonusByRegion = {3, 7, 4, 5, 5, 2};
		int[] countriesByRegions = players[index].getCountriesOwnedByRegions();
		Scanner keyboard = new Scanner(System.in);	// To get user input
		ArrayList<Integer> checkDuplicatedNum = new ArrayList<Integer>();

		// Troops based on the number of territories you occupy divided by 3 and truncated
		troopsByTerritory = players[index].getTotalCountriesOwned() / 3;
		System.out.println("You [" + players[index].getPlayerName() + "] own " + players[index].getTotalCountriesOwned()+ 
             " territories. You get " + troopsByTerritory + " bonus troops.");
		
				
		//System.out.println(countriesByRegions.length+ "xxxx");
		
		
		
		//retrieve the info about how many countries per region a player has conquered
		
		for (int i = 0; i < countriesByRegions.length;i++)
		{
			System.out.println(countriesByRegions[i]);
		}
		
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
		//troopsByCards = troopsByTradeInCards(index);
		
		
		//total troops earned
		totalTroops = troopsByTerritory + troopsByRegion + troopsByCards;
		
		//add it into total troops of a player
		//players[index].addTroops(totalTroops);
		
		//Print out msg of how many bonus troops a player gets
		System.out.println("You've gotten " + totalTroops + " bonus troops. It's time to reinforce your territories.");
			
		
		//Placement of bonus troops into territories.
		System.out.println ("Choose countries you want to put extra armies in.");
		ArrayList <Country> playerCountries = players[index].getOwnedCountries();

		//Display list of countries a player owns, s/he will pick countries to put troops in
		for (int i = 0; i < playerCountries.size(); i++)
		{
			 System.out.format(format, i+1, playerCountries.get(i).getContinentName(),playerCountries.get(i).getCountryName(), 
			   			playerCountries.get(i).getOwnerName(), playerCountries.get(i).getNumArmy());
			 checkDuplicatedNum.add(i);
		}
		
			
		int maxIndex = playerCountries.size(); //the last index of countries a player owns
		int countryPick; // index of country to put troops in
		int numTroops; // number of troops to put in
		
		System.out.println("You currently have " + totalTroops + " troops. Select a country to put troops in.");

		do {
			countryPick = keyboard.nextInt() - 1;
		} while(!checkDuplicatedNum.contains(countryPick));
		
		String PickedCountry = playerCountries.get(countryPick).getCountryName();
		
		while (totalTroops > 0)
		{
			System.out.println("How many troops do you want to put in " + playerCountries.get(countryPick).getCountryName() + "? ");
			System.out.println("\n1 to " + totalTroops);
			countryPick = keyboard.nextInt();

			Map.get(PickedCountry).AddNumOfArmy(countryPick);
			totalTroops = 0;			
		}
		
		
		
		
		//Place bonus army into player's choice of territories
//		while (totalTroops > 0)
//		{
//			//ask what country to put troops in
//			System.out.println("You currently have " + totalTroops + " troops. Select a country to put troops in.");
//			countryPick = getIntInput(1, maxIndex) - 1; // because we start at 0
//			
//			
//			//ask how many troops to put in that country
//			System.out.println("How many troops do you want to put in " + playerCountries.get(countryPick).getCountryName() + "? ");
//			//numTroops = getIntInput(1, totalTroops);
//			numTroops = 1;
//
//			totalTroops -= numTroops;
//						
//			
//			playerCountries.get(countryPick).AddNumOfArmy(numTroops);
//			
//			
//		}// end while
//		
//		
		
		
		
	}
	
	public void Attack()
	{
		displayMapInfo();
		int dirNum = 1;
		int index = 0;
		int attackerDice;
		int defenderDice;
		int numAttackerLose = 0;
		int numDefenderLose = 0;
		int defenderIdx = 0;
		int[] attackerRolls;
		int[] defenderRolls;
		String attackerName = players[currentTurn].getPlayerName();
		String fromCountry;
		String toCountry;
		ArrayList<Country> attackerCountry = players[currentTurn].getOwnedCountries();
		ArrayList<Integer> CheckDuplicatedNum = new ArrayList<Integer>();
		ArrayList<String> connectedCountry;
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Select one of your countries to start a war");
		for (int i = 0; i < attackerCountry.size(); i++)
		{
			if (Map.get(attackerCountry.get(i).getCountryName()).getNumArmy() > 1)
			{
				System.out.println((i + 1) + " : " + attackerCountry.get(i).getCountryName() + " : Num of Army " + Map.get(attackerCountry.get(i).getCountryName()).getNumArmy());
				CheckDuplicatedNum.add(i+1);

			}
		}
		
		
		do
		{
			index = keyboard.nextInt() - 1;
			if (!CheckDuplicatedNum.contains(index + 1))
				System.out.println("Invalid Input");
		} while (!CheckDuplicatedNum.contains(index + 1));
		
		fromCountry = attackerCountry.get(index).getCountryName();
		
		connectedCountry = Map.get(fromCountry).getAdjacency();
		index = 0;
		CheckDuplicatedNum.clear();
		CheckDuplicatedNum = new ArrayList<Integer>();
		System.out.println("Here is the list of countries that you can attack");
		
		for (int i = 0; i < connectedCountry.size(); i++)
		{
			if (!Map.get(connectedCountry.get(i)).getOwnerName().equals(attackerName))
			{
				System.out.println((index++ + 1) + " : " + Map.get(connectedCountry.get(i)).getCountryName()+ " Num Army : " + Map.get(connectedCountry.get(i)).getNumArmy());
				CheckDuplicatedNum.add(index);
			}
		}
		
		do
		{
			index = keyboard.nextInt() - 1;
			if (!CheckDuplicatedNum.contains(index + 1))
				System.out.println("Invalid Input");
		} while (!CheckDuplicatedNum.contains(index + 1));
		
		
		toCountry = connectedCountry.get(index);
		defenderIdx = getPlayerIndex(Map.get(toCountry).getOwnerName());
		while (dirNum != 2)
		{
			
			diceMenu(Map.get(fromCountry).getOwnerName(), "Attacker", Map.get(fromCountry).getNumArmy());
			
			attackerDice = keyboard.nextInt();
			attackerRolls = dice.roll(10);
			
			diceMenu(Map.get(toCountry).getOwnerName(), "Defender", Map.get(toCountry).getNumArmy());

			defenderDice = keyboard.nextInt();
			defenderRolls = dice.roll(defenderDice);
			
			System.out.println("Attacker's highest number : " + attackerRolls[attackerRolls.length - 1]);
			System.out.println("Defender's highest number : " + defenderRolls[defenderRolls.length - 1]);
			
			if (attackerRolls[attackerRolls.length - 1] > defenderRolls[defenderRolls.length - 1])
			{
				System.out.println("Attacker won");
				numDefenderLose++;
			} else if (attackerRolls[attackerRolls.length - 1] <= defenderRolls[defenderRolls.length - 1])
			{
				System.out.println("Defender won");
	
				numAttackerLose++;
			}
			
			if (attackerDice > 1 && defenderDice > 1)
			{
				System.out.println("Attacker's Second Highest Number : " + attackerRolls[attackerRolls.length - 2]);
				System.out.println("Defender's Second Highest number : " + defenderRolls[defenderRolls.length - 2]);
				
				if (attackerRolls[attackerRolls.length - 2] > defenderRolls[defenderRolls.length - 2])
				{
					System.out.println("Attacker won");
	
					numDefenderLose++;
				} else if (attackerRolls[attackerRolls.length - 2] <= defenderRolls[defenderRolls.length - 2])
				{
					System.out.println("Defender won");
	
					numAttackerLose++;
				}
			}
			
			Map.get(fromCountry).loseNumArmy(numAttackerLose);
			Map.get(toCountry).loseNumArmy(numDefenderLose);
			
			if (Map.get(fromCountry).getNumArmy() < 2)
			{
				System.out.println("You Don't Have Enough Army to Attack");
				break;
			} else if (Map.get(toCountry).getNumArmy() < 1)
			{
				index = 3;
				break;
			} 
			
			System.out.println("Do you want to keep attacking?\n1: Yes\n2: No");
			do {
				
				index = keyboard.nextInt();
			} while (index != 1 && index != 2);
	
			
			numDefenderLose = 0;
			numAttackerLose = 0;

		}

		
		if (index == 3)
		{
			//defenderIndex = getPlayerIndex(countries.get(defenderIndex).getOwnerName());
			Map.get(toCountry).setOwerName(Map.get(fromCountry).getOwnerName());

			players[currentTurn].takeCountry(Map.get(toCountry));
			players[defenderIdx].loseCountry(Map.get(toCountry));
			
			if (players[defenderIdx].isPlayerDead())
			{
				System.out.println("you Loser");
			}
			
			Map.get(toCountry).AddNumOfArmy(1);
			Map.get(fromCountry).loseNumArmy(1);
			
			if (Map.get(fromCountry).getNumArmy() > 1)
			{
				System.out.println("Do you want to put more army?");
				System.out.println("Type 0 to" + (Map.get(fromCountry).getNumArmy() - 1));
			}

			

//			displayPlayerInfo(n);
//			displayPlayerInfo(defenderIndex);
		} else
		{
//			displayPlayerInfo(n);
//			displayPlayerInfo(defenderIndex);
		}
//		
//		
		displayMapInfo();

		
	}
	public int getPlayerIndex(String pName)
	{
		int idx = 0;
		
		for (int i = 0; i < numOfPlayers; i++)
		{
			if (players[i].getPlayerName().equals(pName))
			{
				return i;
			}
		}
		return 0;
	}
	public void afterCombatMenu()
	{
		System.out.println("Do you want to keep attacking the country?");
		System.out.println("1. YES");
		System.out.println("2. No");
		
	}
	public int diceMenu(String s1, String s2, int n)
	{
		int num = 0;
		int maxDice = 0;

		System.out.println(s1 + ", pick how many dice you want to use");
		
		if (!s2.equals("Attacker"))
			num = -1;
	
		
		if (n == (2 + num))
			maxDice = 1;
		else if (n == (3 + num))
			maxDice = 2;
		else if (n > (3 + num))
			maxDice = 3 + num;
		
		System.out.println("Pick One in the List" );
		for (int i = 0; i < maxDice; i++)
		{
			System.out.println((i+1) + " : " + (i + 1) + " dice");
		}
		return maxDice;
	}
	
	// end function checkIntInput
//	public int troopsByTradeInCards(int index)
//	{
//		
//		String input;// to get input if a user wants to trade card or not
//		Boolean toTrade = false;
//		
//		//total troops
//		int totalTroops = 0;
//		
//		ArrayList<Card> cardHeld = players[currentTurn].getCards();
//		//get total cards a player is holding
//		int totalCards = cardHeld.size();
//		
//		//to get user input
//		Scanner keyboard = new Scanner(System.in);
//		
//		//ask if a player wanna trade card when s/he has >= 3 cards
//		
//		if (totalCards >= 5)
//		{
//			System.out.println("You have more than 5 cards. You have to trade in at least one set of cards (3 cards).");
//			toTrade = true;
//		}
//		
//		else if (totalCards >= 3)
//		{
//			//prompt to ask if player wanna trade in cards
//			System.out.print("Do you want to trade in cards for troops? Enter y or Y for yes, any key for No >>  ");
//			input = keyboard.nextLine();
//			while (input != "y" && input != "n")
//			{
//				System.out.println("Invalid input. Enter y or Y for yes, any key for No >>  ");
//				input = keyboard.nextLine();
//			}// end while
//			
//			if (input == "y" || input == "Y")
//				toTrade = true;
//				
//		}// end if
//		
//		
//		keyboard.close();
//		
//		//store indexes of cards picked
//		int[] cardIndex = new int[3];	
//		
//		//Card array to store 3 cards picked
//		Card[] cardPicked = new Card[3];
//				
//	
//		//if a player wants/ has to trade in cards
//		if (toTrade == true)
//		{
//			//Print out rules for trade in cards
//			System.out.println("\t****RULES FOR TRADE IN CARDS***");
//			System.out.println("Choose any set of 3 cards to trade in, either: ");
//			System.out.println("\t- 3 cards of the same types.");
//			System.out.println("\t- 3 cards of different types.");
//			System.out.println("\t- Any 2 cards and a \"wild card\" (if any).");
//			System.out.println("\t- BONUS 2 troops on any territory that has name matching with one of the cards you're trading in.");
//			
//			System.out.printf("%s -13s: %s","Card#", "Card Type", "Country");
//			for (int i = 0; i < totalCards; i++)
//			{
//				System.out.printf("%d. %-13s: %s", i+1 , cardHeld.get(i).getType(), cardHeld.get(i).getName());
//			}
//			
//			
//			
//			//bool to check if 3 cards is valid to trade
//			boolean valid = false;
//			
//						
//			while (valid == false)
//			{
//				System.out.println("\nPick the cards to trade in. Enter Card#  ");
//				
//				//get indexes of 3 cards picked
//				cardIndex = pickedCardIndexArr(totalCards);
//
//				
//				//store 3 valid cards picked into a Card array
//				for (int i = 0; i < 3; i++)
//					cardPicked[i] = cardHeld.get(cardIndex[i]);
//				
//				
//				//to be used later to see if any card has the name that 
//				//matches with country a player owns
//				
//				
//				//If 3 cards are same types
//				if (cardPicked[0].getType() == cardPicked[1].getType() && cardPicked[1].getType() == cardPicked[2].getType())
//					valid = true;
//				
//				//else if 3 card has different types
//				else if ( (cardPicked[0].getType() != cardPicked[1].getType()) &&
//						  (cardPicked[0].getType() != cardPicked[2].getType()) &&
//						  (cardPicked[2].getType() != cardPicked[1].getType())
//						)
//					valid = true;
//				
//				//else if there is one wild card among 3
//				else if (cardPicked[0].getType() == "wild" || cardPicked[1].getType() == "wild" || cardPicked[2].getType() == "wild")
//						valid = true;
//				
//			}// end while loop -checking for valid cards
//			
//			
//			
//			//after trading in, remove the cards traded in
//			if (valid == true)
//			{
//				
//				for (int i = 0 ; i < 3; i++)
//					players[index].removeCard(cardIndex[i]);
//			}// end removing cards
//			
//			
//			//update set of cards traded 
//			setOfCardTraded++;
//			
//				
//		}// end trading cards
//		
//		/*
//		 * # of troops awarded calculated as below:
//		 * 		- 1st set = 4 armies
//		 * 		- 2nd	  = 6
//		 * 		- 3rd     = 8
//		 * 		- 4th     = 10
//		 * 		- 5th 	  = 12
//		 * 		- 6th 	  = 15
//		 * 		From 7th on is 20, 25, 30....
//		 */
//		
//		if (setOfCardTraded < 6)
//		{
//			totalTroops = setOfCardTraded*2 + 2;
//		}
//		else if (setOfCardTraded == 6)
//			totalTroops = setOfCardTraded*2 + 3;
//		else 
//		{
//			int start = 15;
//			totalTroops = start + (setOfCardTraded-6)*5;
//			
//		}
//		
//		
//		//array of countries owned by player, to check if any card has name matching with owned country's name
//		ArrayList <Country> playerCountries = players[index].showMyCountries();
//								
//		for (int i = 0; i < 3; i++)
//		{
//			//check for each countries to see if there's any matched result
//			for (int j = 0; j < playerCountries.size(); j++)
//				{
//				if (cardPicked[i].getName() == playerCountries.get(j).getCountryName())
//					{
//						// print out msg then add 2 troop to that country
//						System.out.println("Good Pick! You've picked a card that matches its name with a country you own."
//								+ " You've added 2 troops into " + playerCountries.get(j).getCountryName());
//						
//						playerCountries.get(j).AddNumOfArmy(2);
//						
//						break; // because you can only add 2 troops with matched card ONCE.
//					}
//						
//				} 
//		}//end for
//		
//		
//		//return total troops traded by cards.
//		return totalTroops;
//		
//		
//		
//	}//end troopsByTradeInCards function
	public void Fortify()
	{
		boolean check = false;
		ArrayList<Country> countryList = players[currentTurn].getOwnedCountries();
		ArrayList<Integer> checkDuplicatedNum = new ArrayList<Integer>();
		ArrayList<String> connectedCountry = new ArrayList<String>();
		String fromCountry;
		String toCountry;
		int idx = 0;
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Lists of your Countries that you can fortify");
		
		for (int i = 0; i < countryList.size(); i++)
		{
			if (Map.get(countryList.get(i).getCountryName()).getNumArmy() > 1)
			{
				System.out.println((i + 1) + " : " + countryList.get(i).getCountryName() + "  | Num of Army : " + Map.get(countryList.get(i).getCountryName()).getNumArmy());
				checkDuplicatedNum.add(i);
			}
		}
		
		System.out.println("\nSelect a country");
	
		do {
			idx = keyboard.nextInt() - 1;
		} while(!checkDuplicatedNum.contains(idx));
		checkDuplicatedNum.clear();
		//System.out.println(countryList.get(idx).getCountryName());
		fromCountry = countryList.get(idx).getCountryName();
		
		connectedCountry = Map.get(countryList.get(idx).getCountryName()).getAdjacency();
		
		System.out.print("Here is the List of Countries that you can fortify\n");

		
		for (int i =0; i < connectedCountry.size(); i++)
		{
			if (players[currentTurn].getPlayerName().equals(Map.get(connectedCountry.get(i)).getOwnerName()))
			{
				System.out.println((i+1) + " : " + connectedCountry.get(i));
				checkDuplicatedNum.add(i);
				check = true;
			}
		}
			
		if (check == false)
		{
			System.out.println("You don't have any country owned by you from your selected country");
		} else
		{
		
			System.out.println("Select a country?");
			do {
				idx = keyboard.nextInt() - 1;
			} while(!checkDuplicatedNum.contains(idx));
			toCountry = connectedCountry.get(idx);

			System.out.println("How many armies to move");
			System.out.println("0 to " + (Map.get(fromCountry).getNumArmy() - 1));
			do {
				idx = keyboard.nextInt();
			} while(idx < 1 || (idx) > Map.get(fromCountry).getNumArmy() - 1);
			
			
			Map.get(fromCountry).loseNumArmy(idx);
			Map.get(toCountry).AddNumOfArmy(idx);
		}
		
	
	
		displayMapInfo();
		players[currentTurn].addCard(deck.draw());
	
	
		
	
	
	
	
	
	
	
	}
	public void displayMapInfo()
	{
		System.out.format(format, "Index", "Region" ,"Country Name", "Land Owner", "Num of Army");
		for (int i = 0; i < totalCountries; i++)
		{
			   System.out.format(format, i+1, Map.get(index.get(i)).getContinentName(),Map.get(index.get(i)).getCountryName(), Map.get(index.get(i)).getOwnerName(), Map.get(index.get(i)).getNumArmy());
		}
	
	}
}
