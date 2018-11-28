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

}
