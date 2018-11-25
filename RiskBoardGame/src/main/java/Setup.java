package riskboardgame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Setup {
	//private static Deck deck;

	public Deck LoadDeck() 
	{
		int secretMission = 0;

	    String[] nameList = null;
		String fileName = "Cards.txt";
		String line = null;
		ArrayList<Card> Cards = new ArrayList<Card>();
		Deck deck = new Deck();
		
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


}
