package demo3;
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
	//ReplayS3 replay = new ReplayS3();
	ArrayList<String> s = new ArrayList<String>();
	File file;
	
	public Board(HashMap<String, Country> m, ArrayList<Player> p) throws IOException
	{
		int playerTurn = 0;
		String actionStatus;
		Map = m;
		Players = p;
		//replay.createBucket();
		//replay.listBuckets();
		
		
		actionStatus = armyPlacement(playerTurn);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		
		
		actionStatus = reinforce(playerTurn);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);

		
		actionStatus = attack(playerTurn,1);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);
		//s.add(this.actionController.undo());

		
		actionStatus = fortify(playerTurn);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);

		
		playerTurn++;
		actionStatus = armyPlacement(1);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);

		actionStatus = reinforce(1);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);

		//s.add(this.actionController.undo());

		actionStatus = attack(1,0);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);

		
		
		actionStatus = fortify(playerTurn);
		history = new History(actionStatus, Players, Map, playerTurn, deck);
		actionController.addActionRecord(history);

		
		//file = replay.createFile(s);
		//replay.putObject(file);
		//replay.downloadObject();
		
		this.actionController.undo();
//		this.actionController.undo();
//		this.actionController.undo();
//		this.actionController.undo();
//		this.actionController.undo();
//		this.actionController.undo();
//		this.actionController.undo();
//		this.actionController.undo();

		
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

			Map.get(attackerCountry.getCountryName()).setNumOfArmy(numAttackerLose * -1);
			Map.get(defenderCountry.getCountryName()).setNumOfArmy(numDefenderLose * -1);
			
			if (Map.get(defenderCountry.getCountryName()).getNumOfArmy() < 1)
			{
				transferOwnership(attackerCountry.getCountryName(), defenderCountry.getCountryName());
				killPlayer(defenderIndex);
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
	public boolean isAttackable(Country c1, Country c2)
	{
		if (!c1.getAdjacency().contains(c2.getCountryName()) || c1.getOwnerName().equals(c2.getOwnerName()) || c1.getNumOfArmy() < 2)
		{
			return false;
		}
		
		return true;	
	}
	public boolean transferOwnership(String attackerCountry, String defenderCountry)
	{
		if (attackerCountry == defenderCountry)
			return false;
		Map.get(defenderCountry).setOwnerName((Map.get(attackerCountry).getOwnerName()));
		Map.get(defenderCountry).setNumOfArmy(1);
		Map.get(attackerCountry).setNumOfArmy(-1);
		return true;
	}
	public int killPlayer(int playerIndex)
	{
		Players.remove(playerIndex);
		return playerIndex;
	}
	public String armyPlacement(int playerIndex)
	{
		s.add(Players.get(playerIndex).getPlayerName() + " starts to army placement\n");

		int index = 0;
		int num = 4;
		String pickedCountry = "Alberta";
		
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).setNumOfArmy(num);
		Players.get(playerIndex).setNumOfTroops(num * -1);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		s.add(Players.get(playerIndex).getPlayerName() + " take " + pickedCountry+ " with " + num + " army" + "\n");

		
		//index++;
		
		pickedCountry = "Alaska";
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).setNumOfArmy(num);
		Players.get(playerIndex).setNumOfTroops(num * -1);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		s.add(Players.get(playerIndex).getPlayerName() + " take " + pickedCountry+ " with " + num + " army" + "\n");

		pickedCountry = "Central America";
		Map.get(pickedCountry).setOwnerName(Players.get(index).getPlayerName());
		Map.get(pickedCountry).setNumOfArmy(num);
		Players.get(playerIndex).setNumOfTroops(num * -1);
		Players.get(playerIndex).takeCountry(Map.get(pickedCountry));
		s.add(Players.get(playerIndex).getPlayerName() + " take " + pickedCountry+ " with " + num + " army" + "\n");

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
		Players.get(playerIndex).setNumOfTroops(totalTroops);
		s.add(Players.get(playerIndex).getPlayerName() + " earn " + totalTroops + " from reinforcement\n");

		//System.out.println(troopsByTerritory + troopsByRegion);
		
		return "reinforce action";
	}

	public String fortify(int playerIndex)
	{
		s.add(Players.get(playerIndex).getPlayerName() + " starts to fortify his/her country\n");

		int numTrasferArmy = 2;
		Country fromCountry = fromCountry("Alberta");
		Country toCountry = toCountry("Alaska");
		
		if (isFortifiable(fromCountry, toCountry))
		{
			Map.get(fromCountry.getCountryName()).setNumOfArmy(numTrasferArmy * -1);
			Map.get(toCountry.getCountryName()).setNumOfArmy(numTrasferArmy);
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
