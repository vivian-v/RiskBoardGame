import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.omg.CORBA.portable.InputStream;

public class Initializer {
	private int numPlayers, NumTroops;
	private int secretMission;
	private Player[] players;
	private static HashMap<String, Country> Map;
	private static Deck deck;
	private static ArrayList<String> CountryIndex;
	
	Initializer(){
		System.out.println("=^-^= WELCOME TO THE GAME OF RISK =^-^=");
		setupSecretMission();
		setupNumOfPlayers();
		setupPlayerTroops();
		setupPlayerName();
		setupPlayerTurns();
		setupCountries();
		setupDeck();
	}
	
	Initializer(java.io.InputStream tempIn){
        System.out.println("=^-^= WELCOME TO THE GAME OF RISK =^-^=");
        setupNumOfPlayers();
    }
	public Player[] loadPlayers(){
		return this.players;
	}
	
	public ArrayList<String> loadCountryIndex()
	{
		return this.CountryIndex;
	}
	
	public HashMap<String, Country> loadMap()
	{
		return this.Map;
	}
	
	public Deck loadDeck()
	{
		return this.deck;
	}
	
	public void setupSecretMission()
	{
		Scanner keyboard = new Scanner(System.in);
		int num = 0;
		do{		
			System.out.println("Secret Mission?\n1: Yes\n2: No");
	    	try	{
	    		num = keyboard.nextInt();
	    		secretMission = (num == 1) ? 12 : 0;
	    	}
		    catch (Exception e){
		    	System.out.println("Invalid input.");
		    	keyboard.next(); // Discard bad input
		    }
		}
		while(num > 2 || num < 1);
		
	}
	public void setupNumOfPlayers()
	{
		Scanner keyboard = new Scanner(System.in);
		
		do{		
			System.out.println("How many players are playing? 2~6 : ");
	    	try	{
	    		numPlayers = keyboard.nextInt();
	    	}
		    catch (Exception e){
		    	System.out.println("Invalid input. Please enter a whole number between 2 - 6");
		    	keyboard.next(); // Discard bad input
		    }
		}
		while(numPlayers > 6 || numPlayers < 2);
		
		this.players = new Player[numPlayers];
	}
	public void setupPlayerTroops()	
	{
		NumTroops = 40 - ((numPlayers - 2) * 5);
		System.out.println("Each player gets " + NumTroops + " troops.\n");
	}
	public void setupPlayerName()
	{
		Scanner keyboard = new Scanner(System.in);
		String userInput;
		
		for (int i = 0; i < this.numPlayers; i++) 
		{
			System.out.print("Enter name for Player " + (i+1) + ": ");
			userInput = keyboard.nextLine();
			Player newPlayer = new Player(userInput, NumTroops);
			players[i] = newPlayer;	
		}
		
		System.out.println();
	}
	public void setupPlayerTurns()
	{
		Player[] tempPlayers = new Player[numPlayers];
		
		int currentHighest = 0; 
		int diceRolled;
		int dirNum = 0;
		
		ArrayList<Integer> highest = new ArrayList<Integer>();
		ArrayList<Integer> tie = new ArrayList<Integer>();
		Dice dice = new Dice();
		
		for(int i = 0; i < numPlayers; i++)	
		{
			highest.add(i + 1);
		}
		
		while(highest.size() != 1)
		{
			System.out.println("There is a tie");
			
			currentHighest = 0;//reset highest for reroll
			//cloned who got a tie
			tie.clear();
			for (int i = 0; i < highest.size(); i++)
			{
				tie.add(highest.get(i));
			}
			highest.clear();//clean highest
			
			for(int i = 0; i < tie.size();i++)
			{
				diceRolled = dice.rollForOne();
				System.out.println(players[i].getPlayerName()  + " rolled..... : " + diceRolled + "!" );
				if(diceRolled > currentHighest)
				{					
					currentHighest = diceRolled;
					highest.clear();
					highest.add(tie.get(i));
				}
				else if (diceRolled == currentHighest)
				{
					highest.add(tie.get(i));
				}
			}			
		}
		
		dirNum = highest.get(0) - 1;

		for (int i = 0; i < numPlayers; i++)
		{
			tempPlayers[i] = players[dirNum];
			if (dirNum == numPlayers - 1)
				dirNum = 0;
			else if (dirNum < numPlayers - 1)
				dirNum++;
		}

		this.players = tempPlayers;
		System.out.println("Player's turn in order");

		for (int i = 0; i < numPlayers; i ++)
		{
			System.out.println((i+1) + " : "+ players[i].getPlayerName());
			this.players[i].setPlayerID(i);
		}
		
	}
	
	public void setupCountries() 
	{
        int index = 0;

	    String[] nameList = null;
	    String fileName1 = "Countries.txt";
	    String fileName2 = "Connectivity.txt";
	    ArrayList<Country> CountryList = new ArrayList<Country>(); 
	    CountryIndex = new ArrayList<String>();
	    Map = new HashMap<String, Country>();
	      
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
	         	CountryIndex.add(nameList[i].toString());	
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

	 }
	public void setupDeck()
	{
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
//            while((line = bufferedReader.readLine()) != null) 
//            {
//            	nameList = line.split(",");
//            	Cards.add(new Card(nameList[0], nameList[1]));
//            }   
              
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
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}