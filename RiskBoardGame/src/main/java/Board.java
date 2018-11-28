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
	String gameID; //game id for a game
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<String> mapKeySets = new ArrayList<String>();
	ArrayList<Integer> tradeNumLists = new ArrayList<Integer>();
	Dice dice = new Dice();
	Deck deck;
	
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
		this.Map = m;
		this.botStart = true;
		this.deck = d;
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
