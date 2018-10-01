package demo3;

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

	//private static 
	int playerTurn = 0;
	Country c1 = new Country("China");
	Country c2 = new Country("Japan");
	Country c3 = new Country("Korea");
	Country c4 = new Country("Alberta");
	Stack<HashMap<String, Country>> ss = new Stack();
	Stacksys stack = new Stacksys();
	ArrayList<ArrayList<Player>> aa = new ArrayList<ArrayList<Player>>();
	public Board(HashMap<String, Country> m, ArrayList<Player> p)
	{
		Map = m;
		Players = p;
		
		
		armyPlacement(0);
		reinforce(0);
		attack(0,1);
		fortify(0);
//		System.out.println(Map.get("Alberta").getOwnerName());
//		System.out.println(Map.get("Alaska").getOwnerName());
//
//		System.out.println(Map.get("Alberta").getNumOfArmy());
	}

	public void attack (int attackerIndex, int defenderIndex)
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



}
