package riskboardgame;



import java.io.ByteArrayInputStream;
import javax.swing.undo.UndoManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;




public class Board {

	Dice dice = new Dice();
	UndoManager dd = new UndoManager();
	private HashMap<String, Country> Map = new HashMap<String, Country>();
	ArrayList<Player> Players = new ArrayList<Player>();
	Deck deck = new Deck();
	UndoSystem actionController = new UndoSystem();
	History history;
	ReplayS3 replay = new ReplayS3();
	ArrayList<String> s = new ArrayList<String>();
	File file;
	WarObserver warObserver = new WarObserver();
	boolean checkGameEnd = false;
	String[] playerNameList;
	int[] playerConquerList;
	int numDeadPlayers = 0;
	//Twitter4J tweet = new Twitter4J();
	public Board(HashMap<String, Country> m, ArrayList<Player> p) throws IOException
	{
	
		
		int playerTurn = 0;
		String actionStatus;
		Map = m;
		Players = p;
		
//		tweet.connectTwitter(tweet.getKeysNTokens());
//		loadGameInfo();
//		
//	
//
//		armyPlacement(0);
//		attack(0,1);
//		
//		updateGameInfo();
//		postGameInfo();
////		
//		
		
		warObserver.addObserver(Players.get(0));
		warObserver.addObserver(Players.get(1));
		warObserver.notifyWarObservers();
		
		Transaction player = new Proxy(20);
	
		System.out.println(player.getCredit());

		System.out.println(player.buyCards());
		System.out.println(player.buyUndoActions());
		System.out.println(player.transferCredit());
		System.out.println(player.getCredit());

		
	}


	public Board() {
		// TODO Auto-generated constructor stub
	}

	public void loadGameInfo()
	{
		this.playerNameList = new String[Players.size()];
		this.playerConquerList = new int[Players.size()];
		
		for (int i = 0; i < this.playerNameList.length; i ++)
		{
			this.playerNameList[i] = Players.get(i).getPlayerName();
			this.playerConquerList[i] = 0;
		}
		
		
	}
	public String attack (int attackerIndex, int defenderIndex)
	{
		s.add(Players.get(attackerIndex).getPlayerName() + " starts to attack " + Players.get(defenderIndex).getPlayerName()+ "\n");

		int[] attackerRolls;
		int[] defenderRolls;
		int numAttackerLose = 0;
		int numDefenderLose = 0;
		
		Country attackerCountry = fromCountry("Alberta");
		Country defenderCountry = toCountry("Alaska");
		
		attackerRolls = dice.roll(dice.maxNumDice("Attacker", attackerCountry.getNumOfArmy()));
		defenderRolls = dice.roll(dice.maxNumDice("Defender", defenderCountry.getNumOfArmy()));
		
		

		if (isAttackable(attackerCountry, defenderCountry))
		{
			if (attackerRolls[attackerRolls.length - 1] > defenderRolls[defenderRolls.length - 1])
			{
				numDefenderLose++;
				
			} else if (attackerRolls[attackerRolls.length - 1] <= defenderRolls[defenderRolls.length - 1])
			{
				numAttackerLose++;
			}
		
			if (attackerRolls.length > 1 && defenderRolls.length > 1)
			{
				if (attackerRolls[attackerRolls.length - 2] > defenderRolls[defenderRolls.length - 2])
				{
					numDefenderLose++;
				} else if (attackerRolls[attackerRolls.length - 2] <= defenderRolls[defenderRolls.length - 2])
				{	
					numAttackerLose++;
				}
			}
			
			s.add(Players.get(attackerIndex).getPlayerName() + " lost " + numAttackerLose + "\n");
			s.add(Players.get(defenderIndex).getPlayerName() + " lost " + numDefenderLose + "\n");

			Map.get(attackerCountry.getCountryName()).loseNumOfArmy(numAttackerLose);
			Map.get(defenderCountry.getCountryName()).loseNumOfArmy(numDefenderLose);
			
			if (Map.get(defenderCountry.getCountryName()).getNumOfArmy() < 1)
			{
				transferOwnership(attackerCountry.getCountryName(), attackerIndex,defenderCountry.getCountryName(), defenderIndex);

				
				if (Players.get(defenderIndex).getOwnedCountries().size() == 0)
					killPlayer(defenderIndex);
				
				if (gameOver())
				{
					checkGameEnd = true;
					
					

				} 
			}
	
		} else
		{
			s.add(Players.get(attackerIndex).getPlayerName() + " can't attack due to following reasons\n");
			s.add("# Attacker might not have enough army to attack\n");
			s.add("# Attacker can't attack his country\n");
			s.add("# attacker's country and defender's country is not connected\n");
		}

		
		return "attack action";
		
	}
	
	public boolean gameOver()
	{
		if ((this.numDeadPlayers + 1) == Players.size())
			return true;
		else 
			return false;
	}
	public void updateGameInfo()
	{
		for (int i = 0; i < this.playerNameList.length; i++)
		{
			this.playerConquerList[i] = Players.get(i).getNumConquered();
		}
	}
	public void postGameInfo()
	{
		ArrayList<String> str = new ArrayList<String>();
		String lines = "";

		if (checkGameEnd)
		{
			str.add("Game Over : Conquered Country Numbers by Players\n");

		} else
		{
			str.add("Game Info : Conquered Country Numbers by Players\n");
		}
		
		for (int i = 0; i < this.playerNameList.length; i++)
		{
			str.add(this.playerNameList[i] + " : " + this.playerConquerList[i] + "\n");
		}
		for (int i = 0; i < str.size(); i++)
		{
			lines += str.get(i);
		}
		//tweet.postTweet(lines);
	}
	public boolean isAttackable(Country c1, Country c2)
	{
		if (!c1.getAdjacency().contains(c2.getCountryName()) || c1.getOwnerName().equals(c2.getOwnerName()) || c1.getNumOfArmy() < 2)
		{
			return false;
		}

		return true;	
	}
	public boolean transferOwnership(String attackerCountry, int attackerIndex,String defenderCountry, int defenderIndex)
	{
		if (attackerCountry == defenderCountry)
			return false;
		Map.get(defenderCountry).setOwnerName((Map.get(attackerCountry).getOwnerName()));
		Map.get(defenderCountry).addNumOfArmy(1);
		Map.get(attackerCountry).loseNumOfArmy(1);
		
		Players.get(attackerIndex).takeCountry(Map.get(defenderCountry));
		Players.get(attackerIndex).increaseNumConquered();
		Players.get(defenderIndex).loseCountry(Map.get(defenderCountry));
		
		//add tweets
		
		return true;
	}
	public int killPlayer(int playerIndex)
	{
		Players.get(playerIndex).killPlayer();
		this.numDeadPlayers++;
		return this.numDeadPlayers;
	}
	public String armyPlacement(int playerIndex)
	{
		s.add(Players.get(playerIndex).getPlayerName() + " starts to army placement\n");

		int index = 0;
		int num = 2;
		String pickedCountry = "Alberta";
		
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).addNumOfArmy(10);
		Players.get(playerIndex).loseNumOfTroops(10);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		s.add(Players.get(playerIndex).getPlayerName() + " take " + pickedCountry+ " with " + num + " army" + "\n");

		
		index++;
		num = 1;
		pickedCountry = "Alaska";
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).addNumOfArmy(num);
		Players.get(playerIndex).loseNumOfTroops(num);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		s.add(Players.get(playerIndex).getPlayerName() + " take " + pickedCountry+ " with " + num + " army" + "\n");
//
//		pickedCountry = "Central America";
//		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
//		Map.get(pickedCountry).addNumOfArmy(num);
//		Players.get(playerIndex).loseNumOfTroops(num);
//		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
//		s.add(Players.get(playerIndex).getPlayerName() + " take " + pickedCountry+ " with " + num + " army" + "\n");

		return "army placement action";
	}

	public Country fromCountry(String s)
	{
		Country fc = Map.get(s);
		return fc;
	}
	public Country toCountry(String s)
	{
		Country tc = Map.get(s);
		return tc;
	}

	public String reinforce(int playerIndex)
	{
		s.add(Players.get(playerIndex).getPlayerName() + " starts to reinforce\n");

		int totalTroops = 0;
		int troopsByTerritory = 0;
		int troopsByRegion = 0;
		int troopsByCards = 0;

		int[] fullRegion = {6, 12, 4, 7, 9, 4};
		int[] bonusByRegion = {3, 7, 4, 5, 5, 2};
		int[] countriesByRegions = Players.get(playerIndex).getCountriesOwnedByRegions();
		
		troopsByTerritory = Players.get(playerIndex).getOwnedCountries().size() / 3;
//		System.out.println(Players.get(playerIndex).getOwnedCountries().size());

		for (int i = 0; i < fullRegion.length; i++)
		{
			if (countriesByRegions[i] == fullRegion[i])
			{
				troopsByRegion += bonusByRegion[i];
			}
		}
		
		totalTroops = troopsByTerritory + troopsByRegion;
		
		
		Players.get(playerIndex).addNumOfTroops(totalTroops);
		s.add(Players.get(playerIndex).getPlayerName() + " earn " + totalTroops + " from reinforcement\n");

		//System.out.println(troopsByTerritory + troopsByRegion);
		
		return "reinforce action";
	}
	public int tradeInCard(int playerIndex)
	{
		int idx = 0;
		int[] bonusNumber = {0,4,6,8,10,12,15,20,25};

		ArrayList<Card> cardHeld = Players.get(playerIndex).getAllCards();
		String[] pickedCards = new String[3];
		String[] pickedCountry = new String[3];
		int[] pickedIndex = new int[3];
		
		pickedCards[0] = cardHeld.get(0).getType();
		pickedCards[1] =  cardHeld.get(1).getType();
		pickedCards[2] =  cardHeld.get(2).getType();
		
		pickedCountry[0] = cardHeld.get(0).getDetail();
		pickedCountry[1] = cardHeld.get(1).getDetail();
		pickedCountry[2] = cardHeld.get(2).getDetail();

		
		pickedIndex[0] = 0;
		pickedIndex[1] = 1;
		pickedIndex[2] = 2;

		if (cardHeld.size() > 2)
		{
			if (isTradable(pickedCards))
			{
				int bonusByCountry = checkCountryInCardsOwned(playerIndex,pickedCountry);
				idx = Players.get(playerIndex).getTradeSetIndex();
				Players.get(playerIndex).setCards(afterTrade(cardHeld, pickedIndex));

				if (idx < 9)
					return bonusNumber[idx] + bonusByCountry;
				else 
					return 25 + ((idx - 8) * 5) + bonusByCountry;	
			}
		}
		else
		{
			return 0;	
		}
		

		return 0;
	}
	public ArrayList<Card> afterTrade(ArrayList<Card> c, int[] pickedIndex)
	{
		ArrayList<Card> tempCardHeld = new ArrayList<Card>();
		
		for (int i = 0; i < c.size(); i++)
		{
			if (i != pickedIndex[0] && i != pickedIndex[1] && i != pickedIndex[2])
			tempCardHeld.add(new Card(c.get(i).getDetail(), c.get(i).getType()));
		}
					
		return tempCardHeld; 
	}


	public int checkCountryInCardsOwned(int playerIndex,String[] pickedCountry)
	{
		
		ArrayList<String> countryLists = new ArrayList<String>();
		ArrayList<Country> OwnedCountry = Players.get(playerIndex).getOwnedCountries();
		
		for (int i = 0; i < OwnedCountry.size(); i++)
		{
			countryLists.add(OwnedCountry.get(i).getCountryName());
		}
		
		if (countryLists.contains(pickedCountry[0]) || countryLists.contains(pickedCountry[1]) || countryLists.contains(pickedCountry[2]))
			return 2;
		else 
			return 0;
		
	}
	public void displayNumConquered()
	{
		
	}
	public boolean isTradable(String[] c)
	{
		ArrayList<Integer> numLists = new ArrayList<Integer>();
		int total = 0;
		
		numLists.add(3);
		numLists.add(30);
		numLists.add(300);
		numLists.add(111);
		numLists.add(1011);
		numLists.add(1101);
		numLists.add(1110);
		numLists.add(2001);
		numLists.add(2010);
		numLists.add(2100);

		for (int i = 0; i < c.length; i++)
		{
			if (c[i].equals("solider"))
				total += 1;
			else if (c[i].equals("horse"))
				total += 10;
			else if (c[i].equals("cannon"))
				total += 100;
			else if (c[i].equals("wild"))
				total += 1000;
		}
		
		if (numLists.contains(total))
		{
			return true;
		}
		else 
			return false;

	}
	public String fortify(int playerIndex)
	{
		s.add(Players.get(playerIndex).getPlayerName() + " starts to fortify his/her country\n");

		int numTrasferArmy = 2;
		Country fromCountry = fromCountry("Alberta");
		Country toCountry = toCountry("Alaska");
		
		if (isFortifiable(fromCountry, toCountry))
		{
			Map.get(fromCountry.getCountryName()).loseNumOfArmy(numTrasferArmy);
			Map.get(toCountry.getCountryName()).addNumOfArmy(numTrasferArmy);
		}
		
		s.add(Players.get(playerIndex).getPlayerName() + " fortify from" + fromCountry.getCountryName()+ " to " + toCountry.getCountryName() + "\n");

		return "fortify action";
	}
	public boolean isFortifiable(Country c1, Country c2)
	{
		if (!c1.getOwnerName().equals(c2.getOwnerName()) || c1.getNumOfArmy() < 2 && !c1.getAdjacency().contains(c2.getCountryName()))
		{
			return false;
		}
		return true;
	}




}
