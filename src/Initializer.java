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
