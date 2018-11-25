package riskboardgame;

import java.util.ArrayList;
import java.util.HashMap;

public class History {
	
	private final String actionStatus;
	private final ArrayList<Player> prevPlayers;
	private final HashMap<String, Country> prevMap;
	private final int prevPlayerIndex;
	private final Deck prevDeck;
	private final int prevTradeSetIndex;
	
	public History(String action, ArrayList<Player> p, HashMap<String, Country> m, int index, Deck d, int setIndex)
	{
		this.actionStatus = action;
		this.prevPlayers = p;
		this.prevMap = m;
		this.prevPlayerIndex = index;
		this.prevDeck = d;
		this.prevTradeSetIndex = setIndex;
	}
	public String getActionStatus()
	{
		return this.actionStatus;
	}
	public ArrayList<Player> getPrevPlayers()
	{
		return this.prevPlayers;
	}
	public HashMap<String, Country> getPrevMap()
	{
		return this.prevMap;
	}
	public int getPrevPlayerIndex()
	{
		return this.prevPlayerIndex;
	}
	public Deck getPrevDeck()
	{
		return this.prevDeck;
	}
	public int getPrevTradeSetIndex()
	{
		return this.prevTradeSetIndex;
	}
}
