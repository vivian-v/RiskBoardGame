import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	int NorthAmericaHeld;
	int SouthAmericaHeld;
	int EuropeHeld;
	int AfricaHeld;
	int Asiaheld;
	int AustraliaHeld;

	private String playerName;
	private int numTroops;
    int playerID;
    
	public Player (String name, int num)
	{
		this.playerID = 0;
		this.playerName = name;
		this.numTroops = num;
		this.NorthAmericaHeld = 0;
		this.SouthAmericaHeld = 0;
		this.EuropeHeld = 0;
		this.AfricaHeld = 0;
		this.Asiaheld = 0;
		this.AustraliaHeld = 0;
	}
	
	public void setPlayerID(int x)
	{
		this.playerID = x;
	}
	
	public int getPlayerID()
	{
		return this.playerID;
	}
	
	public String getPlayerName()
	{
		return this.playerName;
	}
	
	public int getPlayerTroops()
	{
		return numTroops;
	}	
}