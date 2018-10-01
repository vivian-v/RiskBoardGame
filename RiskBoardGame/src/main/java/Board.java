import java.io.ByteArrayInputStream;
import javax.swing.undo.UndoManager;
import java.io.ByteArrayOutputStream;
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
	
	
	
	public Board(HashMap<String, Country> m, ArrayList<Player> p)
	{
		int playerTurn = 0;
		String actionStatus;
		Map = m;
		Players = p;
		
		actionStatus = armyPlacement(0);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		
		actionStatus = reinforce(0);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		
		actionStatus = attack(0,1);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		
		
		playerTurn++;
		actionStatus = armyPlacement(1);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		
		actionStatus = reinforce(1);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		
		actionStatus = attack(0,1);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		
		this.actionController.undo();
		this.actionController.undo();
		this.actionController.undo();
		this.actionController.undo();
		this.actionController.undo();
		this.actionController.undo();

		
//		reinforce(0);
//		attack(0,1);
//		fortify(0);
//		System.out.println(Map.get("Alberta").getOwnerName());
//		System.out.println(Map.get("Alaska").getOwnerName());
//
//		System.out.println(Map.get("Alberta").getNumOfArmy());
	}

	public String attack (int attackerIndex, int defenderIndex)
	{
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
			
			Map.get(attackerCountry.getCountryName()).setNumOfArmy(numAttackerLose * -1);
			Map.get(defenderCountry.getCountryName()).setNumOfArmy(numDefenderLose * -1);
			
			if (Map.get(defenderCountry.getCountryName()).getNumOfArmy() < 1)
			{
				transferOwnership(attackerCountry.getCountryName(), defenderCountry.getCountryName());
				killPlayer(defenderIndex);
			}
	
		}
		
		System.out.println(numAttackerLose);
		System.out.println(numDefenderLose);
		
		return "attack action";
		
	}
	public boolean isAttackable(Country c1, Country c2)
	{
		if (!c1.getAdjacency().contains(c2.getCountryName()) || c1.getOwnerName().equals(c2.getOwnerName()) || c1.getNumOfArmy() < 2)
		{
			return false;
		}
		
		return true;	
	}
	public void transferOwnership(String attackerCountry, String defenderCountry)
	{
		Map.get(defenderCountry).setOwnerName((Map.get(attackerCountry).getOwnerName()));
		Map.get(defenderCountry).setNumOfArmy(1);
		Map.get(attackerCountry).setNumOfArmy(-1);

	}
	public void killPlayer(int playerIndex)
	{
		Players.remove(playerIndex);
	}
	public String armyPlacement(int playerIndex)
	{
		
		int index = 0;
		int num = 4;
		String pickedCountry = "Alberta";
		
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).setNumOfArmy(num);
		Players.get(playerIndex).setNumOfTroops(num * -1);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		
		//index++;
		
		pickedCountry = "Alaska";
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).setNumOfArmy(num);
		Players.get(playerIndex).setNumOfTroops(num * -1);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		
		pickedCountry = "Central America";
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).setNumOfArmy(num);
		Players.get(playerIndex).setNumOfTroops(num * -1);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		
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
		
		int totalTroops = 0;
		int troopsByTerritory = 0;
		int troopsByRegion = 0;
		int troopsByCards = 0;

		int[] fullRegion = {6, 12, 4, 7, 9, 4};
		int[] bonusByRegion = {3, 7, 4, 5, 5, 2};
		int[] countriesByRegions = Players.get(playerIndex).getCountriesOwnedByRegions();
		
		troopsByTerritory = Players.get(playerIndex).getOwnedCountries().size() / 3;
		System.out.println(Players.get(playerIndex).getOwnedCountries().size());

		for (int i = 0; i < fullRegion.length; i++)
		{
			if (countriesByRegions[i] == fullRegion[i])
			{
				troopsByRegion += bonusByRegion[i];
			}
		}
		
		System.out.println(troopsByTerritory + troopsByRegion);
		
		return "reinforce action";
	}

	public String fortify(int playerIndex)
	{
		int numTrasferArmy = 2;
		Country fromCountry = fromCountry("Alberta");
		Country toCountry = toCountry("Alaska");
		
		if (isFortifiable(fromCountry, toCountry))
		{
			Map.get(fromCountry.getCountryName()).setNumOfArmy(numTrasferArmy * -1);
			Map.get(toCountry.getCountryName()).setNumOfArmy(numTrasferArmy);
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
}
