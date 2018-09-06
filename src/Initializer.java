import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Initializer {
	private int numPlayers = 0;
	private int NumOfTroops = 0;
	private int[] turnOrder;
	private Player[] players = null;
	private Player[] tempPlayers = null;
	private Dice dice = new Dice();
	private static String[] nameList;
	private static ArrayList<Country> CountryList = new ArrayList<Country>(); 
	private static ArrayList<Card> Cards = new ArrayList<Card>();
	private static ArrayList<Player> tempPlayer = new ArrayList<Player>();

	public Player[] CreatePlayers()
	{	
		System.out.println("=^-^= WELCOME TO THE GAME OF RISK =^-^=");
		setupGame();
		this.players = new Player[numPlayers];
		setupPlayer(NumOfTroops);
		//return this.players;
		return determineTurns();
		//return this.players;
	}

	public void setupGame()
	{
		Scanner keyboard = new Scanner(System.in);	// To get user input
		do
		{		
			System.out.println("How many players are playing? 2~6 : ");
	    	try 
	    	{
	    		numPlayers = keyboard.nextInt();
	    	}
		    catch (Exception e)
		    {
		    	System.out.println("Invalid input. Please enter a whole number between 2 - 6");
		    	keyboard.next(); // Discard bad input
		    }
		}
		while(numPlayers > 6 || numPlayers < 2);

		NumOfTroops = 24 - ((numPlayers - 2) * 5);
		System.out.println("Each player gets " + NumOfTroops + " troops.\n");
		
	}
	public void setupPlayer(int numTroops) 
	{
		Scanner keyboard = new Scanner(System.in);
		String userInput;
		
		for (int i = 0; i < this.numPlayers; i++) 
		{
			System.out.print("Enter name for Player " + (i+1) + ": ");
			userInput = keyboard.nextLine();
			Player newPlayer = new Player(userInput, numTroops);
			players[i] = newPlayer;	
		}
		System.out.println();
	}
	public Player[] determineTurns() 
	{
		tempPlayers = new Player[numPlayers];
		
		int num = numPlayers;
		int currentHighest = 0;//initialize to 0, will be changed as dice being roll
		int diceRolled;
		int dirNum = 0;
		
		//players who got the highest roll
		ArrayList<Integer> highest = new ArrayList<Integer>();
		
		//clone of highest
		ArrayList<Integer> tie = new ArrayList<Integer>();
		
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
		return this.players;
	}
	 public static ArrayList<Country> CreateCountries() 
	 {

	        String fileName1 = "Countries.txt";
	        String fileName2 = "Connectivity.txt";
	        
	        String line = null;
	        int index = 0;
	        try 
	        {
	            FileReader fileReader = new FileReader(fileName1);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) 
	            {
	            	nameList = line.split(",");
	            }   
	            for (int i = 0; i < nameList.length; i++)
	            	CountryList.add(new Country(nameList[i].toString()));
	            
	            fileReader = new FileReader(fileName2);
	            bufferedReader = new BufferedReader(fileReader);
	            
	            while((line = bufferedReader.readLine()) != null) 
	            {
	            	nameList = line.split(",");
	            	CountryList.get(index).setCountryInfo(nameList);
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
			return CountryList;
	 }
	public static ArrayList<Card> CreateCards()
	{
		String fileName = "Cards.txt";
		String line = null;
		
		try 
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) 
            {
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
		return Cards;
	}
}
