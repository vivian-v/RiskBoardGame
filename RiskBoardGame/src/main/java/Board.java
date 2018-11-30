package riskboardgame;

import javax.swing.undo.UndoManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends TelegramLongPollingBot{
	//private String[] regionNames = {"Africa", "Asia", "Australia", "Euroupe", "North America", "South America"};
	private String format = "%1$-5s | %2$-20s | %3$-24s | %4$-15s | %5$-11s | \n";
	private static HashMap<String, Country> Map = new HashMap<String, Country>();
	
	int numOfPlayers; //number of players that will play the game
	int openGameNum;
	int numParticipants;
	int numTroops;
	int currentPlayerIndex;
	int numDeadPlayers;
	int currentActionIndex;
	boolean botStart;
	boolean gameStart;
	boolean placeStart;
	boolean determineTurnsStart;
	String lastAction;
	String gameID; //game id for a game
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<String> mapKeySets = new ArrayList<String>();
	ArrayList<Integer> tradeNumLists = new ArrayList<Integer>();
	Dice dice = new Dice();
	private static Deck deck;
	
	public Board(HashMap<String, Country> m, Deck d)	{
		System.out.println("=^-^= Risk Board Game");
		this.numOfPlayers = 0;
		this.gameID = "";
		this.openGameNum = 0;
		this.numParticipants = 0;
		this.numTroops = 0;
		this.gameStart = false;
		this.placeStart = false;
		this.determineTurnsStart = false;
		this.currentPlayerIndex = 0;
		this.numDeadPlayers = 0;
		this.currentActionIndex = 0;
		Board.Map = m;
		this.botStart = true;
		Board.deck = d;
		this.lastAction = "";
		tradeNumLists.add(3);
		tradeNumLists.add(30);
		tradeNumLists.add(300);
		tradeNumLists.add(111);
		tradeNumLists.add(1011);
		tradeNumLists.add(1101);
		tradeNumLists.add(1110);
		tradeNumLists.add(2001);
		tradeNumLists.add(2010);
		tradeNumLists.add(2100);
		
		for (String value : Map.keySet())
		{
			mapKeySets.add(value);		
		}
		Collections.shuffle(mapKeySets);

	}
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public int testGenerator()	{
		Player np1 = new Player("test1", 1234, 50);
		Player np2 = new Player("test2", 1234, 50);
		Country cn1 = new Country("China");
		Country cn2 = new Country("Alberta");
		Country cn3 = new Country("Ural");
		Country cn4 = new Country("Kamchatka");
		Card c1 = new Card("test", "solider");
		Card c2 = new Card("test", "horse");
		Card c3 = new Card("test", "cannon");
		Card c4 = new Card("test", "wild");
		Map.get("Alberta").setOwnerName("test1");
		Map.get("Alberta").addNumOfArmy(5);
		Map.get("China").setCountryName("test1");
		Map.get("China").addNumOfArmy(1);
		Map.get("Alaska").setOwnerName("test2");
		Map.get("Alaska").addNumOfArmy(2);
		Map.get("Kamchatka").setOwnerName("test1");
		Map.get("Kamchatka").addNumOfArmy(2);
		np1.addCard(c1);
		np1.addCard(c2);
		np1.addCard(c3);
		np1.addCard(c4);
		np1.takeCountry(cn1);
		np1.takeCountry(cn2);
		np1.takeCountry(cn3);
		players.add(np1);
		players.add(np2);
		this.numOfPlayers = 2;
		this.gameStart = true;
		this.botStart = false;
		return players.size();
	}
	public void setNumOfPlayers(int n)
	{
		this.numOfPlayers = n;
	}
	public int getNumOfPlayers()
	{
		return this.numOfPlayers;
	}
	public void setOpenGameNum(int n)
	{
		this.openGameNum = n;
	}
	public int getOpenGameNum()
	{
		return this.openGameNum;
	}
	public void addNumParticipants()
	{
		this.numParticipants++;
	}
	public void setNumTroops(int n)
	{
		this.numTroops = n;
	}
	public int getNumTroops()
	{
		return this.numTroops;
	}
	public void increaseCurrentPlayerIndex()
	{
		this.currentPlayerIndex++;
	}
	public int getCurrentPlayerIndex()
	{
		return this.currentPlayerIndex;
	}
	public void increaseNumDeadPlayer()
	{
		this.numDeadPlayers++;
	}
	public int getNumDeadPlayer()
	{
		return this.numDeadPlayers;
	}
	public void increaseCurrentActionIndex()
	{
		if (this.currentActionIndex == 2)
			this.currentActionIndex = 0;
		else
			this.currentActionIndex++;
	}
	public int getCurrentActionIndex() {
		return this.currentActionIndex;
	}
	public void setGameStart(boolean b)
	{
		this.gameStart = b;
	}
	public boolean getGameStart()
	{
		return this.gameStart;
	}
	public void setPlaceStart(boolean b)
	{
		this.placeStart = b;
	}
	public boolean getPlaceStart()
	{
		return this.placeStart;
	}
	public void setDetermineTurnsStart(boolean b)
	{
		this.determineTurnsStart = b;
	}
	public boolean getDetermineTurnsStart()
	{
		return this.determineTurnsStart;
	}
	public void setGameID(String id)
	{
		this.gameID = id;
	}
	public String getGameID()
	{
		return this.gameID;
	}
	public void setBotStart(boolean b)
	{
		this.botStart = b;
	}
	public boolean getBotStart()
	{
		return this.botStart;
	}
	public String reinforce(int playerIndex)
	{
		int totalTroops = 0;
		int troopsByTerritory = 0;
		int troopsByRegion = 0;

		int[] fullRegion = {6, 12, 4, 7, 9, 4};
		int[] bonusByRegion = {3, 7, 4, 5, 5, 2};
		int[] countriesByRegions = players.get(playerIndex).getCountriesOwnedByRegions();
		
		troopsByTerritory = players.get(playerIndex).getOwnedCountries().size() / 3;

		for (int i = 0; i < fullRegion.length; i++)
		{
			if (countriesByRegions[i] == fullRegion[i])
			{
				troopsByRegion += bonusByRegion[i];
			}
		}
	
		totalTroops = troopsByTerritory + troopsByRegion;
		
		
		players.get(playerIndex).addNumOfTroops(totalTroops);
		return "reinforce action";
	}
	public int tradeInCard(int playerIndex)
	{
		int idx = 0;
		int[] bonusNumber = {0,4,6,8,10,12,15,20,25};

		ArrayList<Card> cardHeld = players.get(playerIndex).getAllCards();
		
		if (cardHeld.size() == 0)
			return 0;
		else if (cardHeld.size() > 2)
		{
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
			
			if (isTradable(pickedCards))
			{
				int bonusByCountry = checkCountryInCardsOwned(playerIndex,pickedCountry);
				idx = players.get(playerIndex).getTradeSetIndex();
				players.get(playerIndex).setCards(afterTrade(cardHeld, pickedIndex));

				if (idx < 9)
					return bonusNumber[idx] + bonusByCountry;
				else 
					return 25 + ((idx - 8) * 5) + bonusByCountry;	
			}
			
		}
		

		return 0;
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
		ArrayList<Country> OwnedCountry = players.get(playerIndex).getOwnedCountries();
		
		for (int i = 0; i < OwnedCountry.size(); i++)
		{
			countryLists.add(OwnedCountry.get(i).getCountryName());
		}
		
		if (countryLists.contains(pickedCountry[0]) || countryLists.contains(pickedCountry[1]) || countryLists.contains(pickedCountry[2]))
			return 2;
		else 
			return 0;
		
	}
	public String fortify(int playerIndex, String c1, String c2, int numTransferArmy)
	{
		Country fromCountry = getCountry(c1);
		Country toCountry = getCountry(c2);
		
		if (players.get(playerIndex).getNumOfTroops() < numTransferArmy)
			return "You don't have enough army";
		
		
		if (isFortifiable(fromCountry, toCountry))
		{
			Map.get(fromCountry.getCountryName()).loseNumOfArmy(numTransferArmy);
			Map.get(toCountry.getCountryName()).addNumOfArmy(numTransferArmy);
		} else
		{
			return "You can't fortify";
		}
		

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

	public boolean botResponseToAll(String str)
	{
		if (this.botStart == true) {
		for (int i = 0; i < this.numOfPlayers; i++)
		{
			SendMessage message = new SendMessage() 
		            .setChatId(players.get(i).getUserId())
                    .setText(str);
			
			try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
                return false;
            }
		}
		}
		return false;
	}
	
	
	public String showAllCommands()
	{
		String tempStr = "Lists of Commands\n"
				+ "=creategame [number of Player] [gameid]\n"
				+ "=showgameid\n"
				+ "=join [gameid]\n"
				+ "=determineturns\n"
				+ "=showturns"
				+ "=showallplayers\n"
				+ "=myinfo\n"
				+ "=placement\n"
				+ "=mapinfo\n"
				+ "=showcurrentaction\n"
				+ "=showattackable [countryName]\n"
				+ "=showfortifiable [countryName]\n"
				+ "=reinforce\n"
				+ "=attack [fromCountry] [toCountry]\n"
				+ "=fortify [fromCountry] [toCountry] [numArmy]\n"
				+ "=doneaction\n";
		
				
				
		return tempStr;
		
	}
	public boolean determineTurns()
	{
		if (players.size() == 0)
			return false;
		Collections.shuffle(players);
		return true;
	}
	public String attack (int attackerIndex, String aCountry, String dCountry)
	{
		String warInfo = "";
		int[] attackerRolls;
		int[] defenderRolls;
		int numAttackerLose = 0;
		int numDefenderLose = 0;
		
		Country attackerCountry = getCountry(aCountry);
		Country defenderCountry = getCountry(dCountry);
		
		int defenderIndex = getPlayerIndexFromCountry(dCountry);
		
//		if (attackerIndex == defenderIndex)
//			return "You can't attack your country";

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

			Map.get(attackerCountry.getCountryName()).loseNumOfArmy(numAttackerLose);
			Map.get(defenderCountry.getCountryName()).loseNumOfArmy(numDefenderLose);
			
			if (Map.get(defenderCountry.getCountryName()).getNumOfArmy() < 1)
			{
				transferOwnership(attackerCountry.getCountryName(), attackerIndex,defenderCountry.getCountryName(), defenderIndex);

				
				if (players.get(defenderIndex).getOwnedCountries().size() == 0)
					killPlayer(defenderIndex);
				
			}
	
		} else
		{
			return "You can't attack this country";
		}
		
		warInfo = players.get(attackerIndex).getPlayerName()+" Lost : " + numAttackerLose + "\n" + players.get(defenderIndex).getPlayerName() + " Lost : " + numAttackerLose; 
		
		return warInfo;
		
	}
	public int killPlayer(int playerIndex)
	{
		players.get(playerIndex).killPlayer();
		this.numDeadPlayers++;
		return this.numDeadPlayers;
	}
	public boolean transferOwnership(String attackerCountry, int attackerIndex,String defenderCountry, int defenderIndex)
	{
		if (attackerCountry == defenderCountry)
			return false;
		Map.get(defenderCountry).setOwnerName((Map.get(attackerCountry).getOwnerName()));
		Map.get(defenderCountry).addNumOfArmy(1);
		Map.get(attackerCountry).loseNumOfArmy(1);
		
		players.get(attackerIndex).takeCountry(Map.get(defenderCountry));
		players.get(attackerIndex).increaseNumConquered();
		players.get(defenderIndex).loseCountry(Map.get(defenderCountry));

		return true;
	}
	public String showCountryValidation(String name, String country, int dir)
	{
		ArrayList<String> connection;
		String countryLists = "Here is/are the lists\n";

		int x = checkAttackableOrFortifiable(name, country);
		
		if (x == -1)
			return "Invalid Country Name";
		if (x == -2)
			return "This country is not your country";
		if (x == -3)
			return "Not enough army in the country";
		
		connection = Map.get(country).getAdjacency();
		
		if (connection.size() == 0)
			return "There is no country you can attack";
		
		for (int i = 0; i < connection.size(); i++)
		{
			if (dir == 1) {
				if (!Map.get(connection.get(i)).getOwnerName().equals(name))
					countryLists += Map.get(connection.get(i)).getCountryName() + " : " + Map.get(connection.get(i)).getNumOfArmy() + "\n";
			} else if (dir == 2)
			{
				if (Map.get(connection.get(i)).getOwnerName().equals(name))
					countryLists += Map.get(connection.get(i)).getCountryName() + " : " + Map.get(connection.get(i)).getNumOfArmy() + "\n";
			}
		}

		return countryLists;
	}

	public int checkAttackableOrFortifiable(String name, String country)
	{
		if (!Map.containsKey(country))
			return -1;
		if (!Map.get(country).getOwnerName().equals(name))
			return -2;
		if (Map.get(country).getNumOfArmy() < 2)
			return -3;
		
		
		return 1;
	}
	public boolean isAttackable(Country c1, Country c2)
	{
		if (!c1.getAdjacency().contains(c2.getCountryName()) || c1.getOwnerName().equals(c2.getOwnerName()) || c1.getNumOfArmy() < 2)
		{
			return false;
		}

		return true;	
	}
	public Country getCountry(String s)
	{
		Country fc = Map.get(s);
		return fc;
	}
	public int getPlayerIndexFromCountry(String c)
	{
		String name = Map.get(c).getOwnerName();
		return getIndex(name);
	}
	public int getPlayerIndex(String name)
	{
		return getIndex(name);
	}
	public int getIndex(String name)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getPlayerName().equals(name))
				return i;
		}
		return -1;
	}
	public String armyPlacement()
	{
		boolean firstRound = false;
		int playerIndex = 0;
		String pickedCountry;
		int i = 0;
		while (players.get(this.numOfPlayers - 1).getNumOfTroops()> 0)
		{
			if (!firstRound)
			{
				pickedCountry = this.mapKeySets.get(i);
				Map.get(pickedCountry).setOwnerName(players.get(playerIndex).getPlayerName());
				Map.get(pickedCountry).addNumOfArmy(1);
				players.get(playerIndex).loseNumOfTroops(1);
				players.get(playerIndex).takeCountry(Map.get(pickedCountry));
				playerIndex++;
				if (playerIndex == this.numOfPlayers)
					playerIndex = 0;
				i++;
				if (i == this.mapKeySets.size())
				{	
					i = 0;
					firstRound = true;
				}
			} else
			{
				pickedCountry = this.mapKeySets.get(i);
				if (Map.get(pickedCountry).getOwnerName().equals(players.get(playerIndex).getPlayerName()))
				{
					Map.get(pickedCountry).addNumOfArmy(1);
					players.get(playerIndex).loseNumOfTroops(1);
					playerIndex++;
					if (playerIndex == this.numOfPlayers)
						playerIndex = 0;
					i++;
					if (i == this.mapKeySets.size())
					{	
						i = 0;
					}
				}
			}
		}

		
		return "Done army placement";
	}
	
	public void displayMapInfo()
	{
		System.out.format(format, "Index", "Region" ,"Country Name", "Land Owner", "Num of Army");
		int i = 0;
		for (String key : Map.keySet()) {
		   System.out.format(format, ++i, Map.get(key).getContinentName(),Map.get(key).getCountryName(), Map.get(key).getOwnerName(), Map.get(key).getNumOfArmy());
		}

	}
	public String checkCategory(String userName, long user_id, String txt)
	{
		String delim = " ";
		String[] tokens = txt.split(delim);
		String tempStr = "";
		if (txt.contains("=creategame"))
		{
			if (tokens.length < 3 || openGameNum > 0)
				return "Failed to create a game";	
			
			if (Integer.parseInt(tokens[1]) < 2 || Integer.parseInt(tokens[1]) > 6)
				return "Failed to setup number of players";
			
			openGameNum = 1;
			this.numOfPlayers = Integer.parseInt(tokens[1]);
			this.numTroops = 40 - ((this.numOfPlayers - 2) * 5);
			this.gameID = tokens[2];
			
			return "Created a new game. ID : " + tokens[2];
		} else if (txt.equals("/help"))
		{
			return showAllCommands();
		} else if (txt.contains("=showgameid"))
		{
			return (this.gameID.length() == 0) ? "There is no game open" : "Game ID : " + this.gameID + "\nCurrent num participants : " + this.numParticipants
					+ "\nExpected num participants : " + this.numOfPlayers;
		} else if (txt.contains("=join"))
		{
			if (tokens.length < 2)
			{
				return "Failed to join the game";
			} else 
			{		
				for (int i = 0; i < players.size(); i++)
				{
					if (!tokens[1].equals(this.gameID))
						return "Game ID is not valid";
					if (players.get(i).getPlayerName().equals(userName))
						return "You are already in the game";
				}
				if (!this.gameID.equals(tokens[1]))
					return "Invalid Game ID";
				else if (this.gameStart == true)
					return "The game is full";
		
				this.numParticipants++;
				Player newPlayer = new Player(userName, user_id, this.numTroops);
				players.add(newPlayer);
				
				if (this.numParticipants == this.numOfPlayers)
				{
					this.gameStart = true;
					botResponseToAll("Game Start\n" + "The Current Turn : " + players.get(0).getPlayerName());
    
				}
				return "Successfully join the game";
			}
		} else if (txt.equals("=showallplayers"))
		{
			String str = "";
			for (int i = 0; i < players.size(); i++)
			{
				str += (i+1) + " : " + players.get(i).getPlayerName() + "\n";
			}
			return str;
		} else if (txt.equals("=myinfo"))
		{
			if (!gameStart)
				return "Game hasn't started yet";
			
			for (int i = 0; i < players.size(); i++)
			{
				if (players.get(i).getPlayerName().equals(userName))
					return "My Name : " + players.get(i).getPlayerName() + "\nMy Number of Troops : " 
						+ players.get(i).getNumOfTroops() + "\nMy Number of Conquered Countries : " 
						+ players.get(i).getNumConquered() + "\nMy Number of Owned Countries : "
						+ players.get(i).getOwnedCountries().size();  
			}
			
			return "Failed to show info";
		} else if (txt.equals("=placement"))
		{
			if (placeStart)
				return "Placement has done already";
			
			placeStart = true;
			botResponseToAll(userName + " starts the placement\n");
			botResponseToAll(armyPlacement());
	
		} else if (txt.equals("=mapinfo"))
		{
			this.displayMapInfo();
		} else if (txt.contains("=attack"))
		{
			if (!(this.currentActionIndex == 1))
				return "Not correct action";
			if (tokens.length < 3 )
				return "invalid input";	
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
			if (currentPlayerIndex != getPlayerIndex(userName))
				return "Not your turn. Current Turn : " + players.get(currentPlayerIndex).getPlayerName();
			
			botResponseToAll(attack(currentPlayerIndex, tokens[1], tokens[2]));
		} else if (txt.contains("=showattackable"))
		{
			if (tokens.length < 2 )
				return "invalid input";
			
			return showCountryValidation(userName, tokens[1], 1);
			
		} else if (txt.equals("=doneaction") && placeStart) {
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
			if (currentPlayerIndex != getPlayerIndex(userName))
				return "Not your turn. Current Turn : " + players.get(currentPlayerIndex).getPlayerName();
			
			if (this.currentActionIndex == 2)
			{
				this.currentActionIndex = 0;
				this.lastAction = "";
				String cpname = players.get(currentPlayerIndex).getPlayerName();
				String npname = (currentPlayerIndex == this.numOfPlayers - 1) ? players.get(0).getPlayerName() : players.get(currentPlayerIndex + 1).getPlayerName();
				this.currentPlayerIndex = (this.currentPlayerIndex == this.numOfPlayers - 1) ? 0 : (this.currentPlayerIndex + 1);
				botResponseToAll(cpname + "'s turn is over\n" + "Now, " + npname + "'s turn");
			} else
			{
				if (this.currentActionIndex == 0)
				{
					this.currentActionIndex++;
					return "Now action : attack";
				} else if (this.currentActionIndex == 1)
				{
					this.currentActionIndex++;
					return "Now action : fortify";
				}
				
			}
		} else if (txt.equals("=showturns"))
		{
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
			tempStr = " > ";
			for (int i = 0; i < this.numOfPlayers; i++)
			{
				tempStr += players.get(i).getPlayerName() + " > ";
			}
			tempStr += "\nCurrent Player : " + players.get(this.currentPlayerIndex).getPlayerName();


			
			return tempStr;
		} else if (txt.equals("=determineturns"))
		{
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
//			if (this.determineTurnsStart)
//				return "Placement has done already";
			this.determineTurns();
		} else if (txt.equals("=reinforce"))
		{
			if (!(this.currentActionIndex == 0))
				return "Not correct action";
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
			if (currentPlayerIndex != getPlayerIndex(userName))
				return "Not your turn. Current Turn : " + players.get(currentPlayerIndex).getPlayerName();
			if (this.lastAction.equals("reinforce"))
				return "You reinforced already";
			
			this.lastAction = "reinforce";
			return reinforce(this.currentPlayerIndex);
			
		} else if (txt.contains("=fortify") )
		{
			if (!(this.currentActionIndex == 2))
				return "Not correct action";
			if (tokens.length < 4)
				return "invalid input";	
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
			if (currentPlayerIndex != getPlayerIndex(userName))
				return "Not your turn. Current Turn : " + players.get(currentPlayerIndex).getPlayerName();
			if (this.lastAction.equals("fortify"))
				return "You fortified already";
			
			this.lastAction = "fortify";
			return fortify(this.currentPlayerIndex, tokens[1], tokens[2], Integer.parseInt(tokens[3]));
			
		} else if (txt.contains("=showfortifiable"))
		{
			
			if (tokens.length < 2 )
				return "invalid input";
			return showCountryValidation(userName, tokens[1], 2);

		} else if (txt.equals("=showcurrentaction"))
		{
			if (this.currentActionIndex == 0)
				return "Current Action : reinforce";
			else if (this.currentActionIndex == 1)
				return "Current Action : attack";
			else if (this.currentActionIndex == 2)
				return "Current Action : fortify";
		}
		
		
		return "The action you just did : " + txt;
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String first_name = update.getMessage().getChat().getFirstName();

			String answer = "";
            long user_id = update.getMessage().getChat().getId();
            long chat_id = update.getMessage().getChatId();

            String message_text = update.getMessage().getText();
            
            answer = checkCategory(first_name, user_id , message_text);
 
            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(answer);
           
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            
        }
		
	}

    @Override
    public String getBotUsername() {
        return "RiskGameBot";
    }

    @Override
    public String getBotToken() {
        return "718366234:AAHZ64pich1qeITo4J2S8CmauBCfPSqCkQY";
    }

	
}
