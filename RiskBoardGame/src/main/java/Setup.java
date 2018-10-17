package riskboardgame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Setup {

	private static ArrayList<Country> countries = new ArrayList<Country>();
	private static Deck deck;

	
	

	

	public Deck LoadDeck() //JUnit is done
	{
		int secretMission = 0;

	    String[] nameList = null;
		String fileName = "Cards.txt";
		String line = null;
		ArrayList<Card> Cards = new ArrayList<Card>();
		deck = new Deck();
		
		try 
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for (int i = 0; i < (44 + secretMission); i++)
            {
            	line = bufferedReader.readLine();
            	nameList = line.split(",");
            	Cards.add(new Card(nameList[0], nameList[1]));
            }

              
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Unable to open file ' '");                
        }
        catch(IOException ex) 
        {
            System.out.println("Error reading file ''");                  

        }
		deck.putCards(Cards);
		return deck;
	}
	public ArrayList<Player> DetermineTurn(ArrayList<Player> players)	{
		int currentHighest = 0; 
		int diceRolled;
		int dirNum = 0;
		
		ArrayList<Player> tempPlayers = new ArrayList<Player>();
		ArrayList<Integer> highest = new ArrayList<Integer>();
		ArrayList<Integer> tie = new ArrayList<Integer>();
		
		Dice dice = new Dice();
		
		for(int i = 0; i < players.size(); i++)	{
			highest.add(i + 1);
		}
		
		while(highest.size() != 1){
			//System.out.println("There is a tie");
			
			currentHighest = 0;//reset highest for reroll
			//cloned who got a tie
			tie.clear();
			for (int i = 0; i < highest.size(); i++)
				tie.add(highest.get(i));
			highest.clear();//clean highest
			
			for(int i = 0; i < tie.size();i++)
			{
				diceRolled = dice.rollForOne();
				//System.out.println(players[i].getPlayerName()  + " rolled..... : " + diceRolled + "!" );
				if(diceRolled > currentHighest)
				{					
					currentHighest = diceRolled;
					highest.clear();
					highest.add(tie.get(i));
				}
				else if (diceRolled == currentHighest)
					highest.add(tie.get(i));
			}			
		}
		
		dirNum = highest.get(0) - 1;

		for (int i = 0; i < players.size(); i++){
			tempPlayers.add(i, players.get(dirNum));
			if (dirNum == players.size() - 1)
				dirNum = 0;
			else if (dirNum < players.size() - 1)
				dirNum++;
		}
		return tempPlayers;
	}
	public HashMap<String, Country> LoadMap() 
	{
        int index = 0;

	    String[] nameList = null;
	    String fileName1 = "Countries.txt";
	    String fileName2 = "Connectivity.txt";
	    ArrayList<Country> CountryList = new ArrayList<Country>(); 
	    HashMap<String, Country> Map = new HashMap<String, Country>();
	      
	    String line = null;
	    try 
	    {
	         FileReader fileReader = new FileReader(fileName1);
	         BufferedReader bufferedReader = new BufferedReader(fileReader);

	         while((line = bufferedReader.readLine()) != null) 
	         {
	          	nameList = line.split(",");
	         }   
	         for (int i = 0; i < nameList.length; i++)
	         {
	         	CountryList.add(new Country(nameList[i].toString()));
	         }  
	         fileReader = new FileReader(fileName2);
	         bufferedReader = new BufferedReader(fileReader);
	            
	         while((line = bufferedReader.readLine()) != null) 
	         {
	          	nameList = line.split(",");
	           	CountryList.get(index).setCountryInfo(nameList);
	           	Map.put(CountryList.get(index).getCountryName(), CountryList.get(index));
	           	index++;
	         }   
	                        
	         bufferedReader.close();         
	     }
	     catch(FileNotFoundException ex)
	     {
	         System.out.println("Unable to open file ' '");                
	     }
	     catch(IOException ex) 
	     {
	         System.out.println("Error reading file ''");                  
	     }
	    return Map;
	 }
	public int setupNumOfPlayers()
	{
		//Scanner keyboard = new Scanner(System.in);
		int numPlayers = 4;
//		do{		
//			System.out.println("How many players are playing? 2~6 : ");
//	    	try	{
//	    		numPlayers = keyboard.nextInt();
//	    	}
//		    catch (Exception e){
//		    	System.out.println("Invalid input. Please enter a whole number between 2 - 6");
//		    	keyboard.next(); // Discard bad input
//		    }
//		}
//		while(numPlayers > 6 || numPlayers < 2);
		
		return numPlayers;
		//this.players = new Player[numPlayers];
	}
	public ArrayList<Player> setupPlayer(int numPlayers)
	{
		
		ArrayList<Player> players = new ArrayList<Player>();
		Scanner keyboard = new Scanner(System.in);
		String userInput;
		int NumTroops = 40 - ((numPlayers - 2) * 5);
		String[] str  = {"Chang", "Vincent"};
		for (int i = 0; i < numPlayers; i++) 
		{
			System.out.print("Enter name for Player " + (i+1) + ": ");
			//userInput = keyboard.nextLine();
			userInput = str[i];
			Player newPlayer = new Player(userInput, NumTroops);
			players.add(newPlayer);
		}
		
		System.out.println();
		return players;
	}
}
