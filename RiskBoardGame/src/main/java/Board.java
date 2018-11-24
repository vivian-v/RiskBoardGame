package riskboardgame;



import javax.swing.undo.UndoManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;





public class Board extends TelegramLongPollingBot{
	private String[] regionNames = {"Africa", "Asia", "Australia", "Euroupe", "North America", "South America"};
	private String format = "%1$-5s | %2$-20s | %3$-24s | %4$-15s | %5$-11s | \n";
	boolean gameReady;
	int totalNumParticipants;
	private static HashMap<String, Country> Map = new HashMap<String, Country>();
	private static ArrayList<Player> Players = new ArrayList<Player>();
    
	int currentPlayerIndex;
	int currentActionIndex;
	
	
	
	
	
	
	int nextplayerIndex;
    boolean dir;
    
    
    
    
    
	Dice dice = new Dice();
	UndoManager dd = new UndoManager();

	Deck deck = new Deck();
	ArrayList<String> s = new ArrayList<String>();
	boolean checkGameEnd = false;
	String[] playerNameList;
	int[] playerConquerList;
	int numDeadPlayers = 0;
	int gameStatte;
	
    MyBot mybot = new MyBot();
   
    
	public Board(HashMap<String, Country> m, ArrayList<Player> p) throws IOException
	{
		gameReady = false;
		totalNumParticipants = 0;
		currentPlayerIndex = 0;
		currentActionIndex = 0;
		
		int playerTurn = 0;
		String actionStatus;
		Map = m;
		Players = p;

		displayMapInfo();

		


	}

	class Task extends TimerTask
	{
	    private String m_name;
	    boolean check = false;

	    public Task(String name)
	    {
	        this.m_name = name;
	    }

	    public void run()
	    {
	    	if (check == true)
	    	{
	            System.out.println("Time is up");
	            nextplayerIndex++;

	            check = false;
	            cancel();
	    	}else {
	            System.out.println(this.m_name);
	            check = true;
	    	}
	    }
	}


	public Board() {
		// TODO Auto-generated constructor stub
	}

	public void loadGameInfo()
	{
		this.playerNameList = new String[Players.size()];
		this.playerConquerList = new int[Players.size()];
		
		for (int i = 0; i < this.playerNameList.length; i ++)
		{
			this.playerNameList[i] = Players.get(i).getPlayerName();
			this.playerConquerList[i] = 0;
		}
		
		
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
			
			s.add(Players.get(attackerIndex).getPlayerName() + " lost " + numAttackerLose + "\n");
			s.add(Players.get(defenderIndex).getPlayerName() + " lost " + numDefenderLose + "\n");

			Map.get(attackerCountry.getCountryName()).loseNumOfArmy(numAttackerLose);
			Map.get(defenderCountry.getCountryName()).loseNumOfArmy(numDefenderLose);
			
			if (Map.get(defenderCountry.getCountryName()).getNumOfArmy() < 1)
			{
				transferOwnership(attackerCountry.getCountryName(), attackerIndex,defenderCountry.getCountryName(), defenderIndex);

				
				if (Players.get(defenderIndex).getOwnedCountries().size() == 0)
					killPlayer(defenderIndex);
				
				if (gameOver())
				{
					checkGameEnd = true;
					updateGameInfo();
					postGameInfo();

				} else 
				{
					updateGameInfo();
					postGameInfo();
				}
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
	
	public boolean gameOver()
	{
		if ((this.numDeadPlayers + 1) == Players.size())
			return true;
		else 
			return false;
	}
	public void updateGameInfo()
	{
		for (int i = 0; i < this.playerNameList.length; i++)
		{
			this.playerConquerList[i] = Players.get(i).getNumConquered();
		}
	}
	public void postGameInfo()
	{
		ArrayList<String> str = new ArrayList<String>();
		String lines = "";

		if (checkGameEnd)
		{
			str.add("Game Over : Conquered Country Numbers by Players\n");

		} else
		{
			str.add("Game Info : Conquered Country Numbers by Players\n");
		}
		
		for (int i = 0; i < this.playerNameList.length; i++)
		{
			str.add(this.playerNameList[i] + " : " + this.playerConquerList[i] + "\n");
		}
		for (int i = 0; i < str.size(); i++)
		{
			lines += str.get(i);
		}
		//tweet.postTweet(lines);
	}
	public boolean isAttackable(Country c1, Country c2)
	{
		if (!c1.getAdjacency().contains(c2.getCountryName()) || c1.getOwnerName().equals(c2.getOwnerName()) || c1.getNumOfArmy() < 2)
		{
			return false;
		}

		return true;	
	}
	public boolean transferOwnership(String attackerCountry, int attackerIndex,String defenderCountry, int defenderIndex)
	{
		if (attackerCountry == defenderCountry)
			return false;
		Map.get(defenderCountry).setOwnerName((Map.get(attackerCountry).getOwnerName()));
		Map.get(defenderCountry).addNumOfArmy(1);
		Map.get(attackerCountry).loseNumOfArmy(1);
		
		Players.get(attackerIndex).takeCountry(Map.get(defenderCountry));
		Players.get(attackerIndex).increaseNumConquered();
		Players.get(defenderIndex).loseCountry(Map.get(defenderCountry));

		//add tweets
		
		return true;
	}
	public int killPlayer(int playerIndex)
	{
		Players.get(playerIndex).killPlayer();
		this.numDeadPlayers++;
		return this.numDeadPlayers;
	}
	public String armyPlacement(int playerIndex, String pickedCountry, int num)
	{
		s.add(Players.get(playerIndex).getPlayerName() + " starts to army placement\n");
		System.out.println("start placing armies");
	//	botresponse("start placing armies");


		
		Map.get(pickedCountry).setOwnerName(Players.get(playerIndex).getPlayerName());
		Map.get(pickedCountry).addNumOfArmy(num);
		Players.get(playerIndex).loseNumOfTroops(num);
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
		//System.out.println("start reinforcing");
		//botresponse("start reinforcing");
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
		
		
		Players.get(playerIndex).addNumOfTroops(totalTroops);
		s.add(Players.get(playerIndex).getPlayerName() + " earn " + totalTroops + " from reinforcement\n");

		//System.out.println(troopsByTerritory + troopsByRegion);
		
		return "reinforce action";
	}
	public int tradeInCard(int playerIndex)
	{
		int idx = 0;
		int[] bonusNumber = {0,4,6,8,10,12,15,20,25};

		ArrayList<Card> cardHeld = Players.get(playerIndex).getAllCards();
		String[] pickedCards = new String[3];
		String[] pickedCountry = new String[3];
		int[] pickedIndex = new int[3];
		
		pickedCards[0] = cardHeld.get(0).getType();
		pickedCards[1] =  cardHeld.get(1).getType();
		pickedCards[2] =  cardHeld.get(2).getType();
		
		pickedCountry[0] = cardHeld.get(0).getDetail();
		pickedCountry[1] = cardHeld.get(1).getDetail();
		pickedCountry[2] = cardHeld.get(2).getDetail();

		
		pickedIndex[0] = 0;
		pickedIndex[1] = 1;
		pickedIndex[2] = 2;

		if (cardHeld.size() > 2)
		{
			if (isTradable(pickedCards))
			{
				int bonusByCountry = checkCountryInCardsOwned(playerIndex,pickedCountry);
				idx = Players.get(playerIndex).getTradeSetIndex();
				Players.get(playerIndex).setCards(afterTrade(cardHeld, pickedIndex));

				if (idx < 9)
					return bonusNumber[idx] + bonusByCountry;
				else 
					return 25 + ((idx - 8) * 5) + bonusByCountry;	
			}
		}
		else
		{
			return 0;	
		}
		

		return 0;
	}
	public ArrayList<Card> afterTrade(ArrayList<Card> c, int[] pickedIndex)
	{
		ArrayList<Card> tempCardHeld = new ArrayList<Card>();
		
		for (int i = 0; i < c.size(); i++)
		{
			if (i != pickedIndex[0] && i != pickedIndex[1] && i != pickedIndex[2])
			tempCardHeld.add(new Card(c.get(i).getDetail(), c.get(i).getType()));
		}
					
		return tempCardHeld; 
	}


	public int checkCountryInCardsOwned(int playerIndex,String[] pickedCountry)
	{
		
		ArrayList<String> countryLists = new ArrayList<String>();
		ArrayList<Country> OwnedCountry = Players.get(playerIndex).getOwnedCountries();
		
		for (int i = 0; i < OwnedCountry.size(); i++)
		{
			countryLists.add(OwnedCountry.get(i).getCountryName());
		}
		
		if (countryLists.contains(pickedCountry[0]) || countryLists.contains(pickedCountry[1]) || countryLists.contains(pickedCountry[2]))
			return 2;
		else 
			return 0;
		
	}
	public void displayNumConquered()
	{
		
	}
	public boolean isTradable(String[] c)
	{
		ArrayList<Integer> numLists = new ArrayList<Integer>();
		int total = 0;
		
		numLists.add(3);
		numLists.add(30);
		numLists.add(300);
		numLists.add(111);
		numLists.add(1011);
		numLists.add(1101);
		numLists.add(1110);
		numLists.add(2001);
		numLists.add(2010);
		numLists.add(2100);

		for (int i = 0; i < c.length; i++)
		{
			if (c[i].equals("solider"))
				total += 1;
			else if (c[i].equals("horse"))
				total += 10;
			else if (c[i].equals("cannon"))
				total += 100;
			else if (c[i].equals("wild"))
				total += 1000;
		}
		
		if (numLists.contains(total))
		{
			return true;
		}
		else 
			return false;

	}
	public String fortify(int playerIndex)
	{
		s.add(Players.get(playerIndex).getPlayerName() + " starts to fortify his/her country\n");
		
		
		//System.out.println("start fortifying");
		//botresponse("start fortifying");

		
		
		int numTrasferArmy = 2;
		Country fromCountry = fromCountry("Alberta");
		Country toCountry = toCountry("Alaska");
		
		if (isFortifiable(fromCountry, toCountry))
		{
			Map.get(fromCountry.getCountryName()).loseNumOfArmy(numTrasferArmy);
			Map.get(toCountry.getCountryName()).addNumOfArmy(numTrasferArmy);
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

	public void displayMapInfo()
	{
		System.out.format(format, "Index", "Region" ,"Country Name", "Land Owner", "Num of Army");
		int i = 0;
		System.out.println(Map.size());
		for (String key : Map.keySet()) {
		   System.out.format(format, ++i, Map.get(key).getContinentName(),Map.get(key).getCountryName(), Map.get(key).getOwnerName(), Map.get(key).getNumOfArmy());

		}

	
	}
	@Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String user_first_name = update.getMessage().getChat().getFirstName();     
            String user_last_name = update.getMessage().getChat().getLastName();
            String message_text = update.getMessage().getText();
            String answer = null;
            long chat_id = update.getMessage().getChatId();
            boolean validAction = false;

            int x = log(user_first_name, user_last_name, message_text);
           
            System.out.println(Players.get(0).getNumOfTroops());
            if (gameReady)
            {
            	if (x == currentActionIndex && Players.get(currentPlayerIndex).getPlayerName().equals(user_first_name))
            	{
            		armyPlacement(this.currentPlayerIndex, "Alberta", 2);
            		currentActionIndex++;
            		answer = "Done placing army";
            		validAction = true;
            	}
            	else if (x == currentActionIndex && Players.get(currentPlayerIndex).getPlayerName().equals(user_first_name))
            	{
            		reinforce(this.currentPlayerIndex);
            		currentActionIndex++;
            		answer = "Done reinforcing";
            		validAction = true;


            	}
            	else if (x == currentActionIndex && Players.get(currentPlayerIndex).getPlayerName().equals(user_first_name))
            	{
            		int nextPlayerIndex = (this.currentPlayerIndex == 1) ? 0 : 1;
            		attack(currentPlayerIndex, nextPlayerIndex);

            		currentActionIndex++;
            		answer = "Done attacking";
            		validAction = true;


            	}else if (x == currentActionIndex && Players.get(currentPlayerIndex).getPlayerName().equals(user_first_name))
            	{
            		fortify(currentPlayerIndex);
            		currentPlayerIndex = (currentPlayerIndex == 0 ) ? 1 : 0;
            		currentActionIndex = 0;
            		answer = "Done fortifying";
            		validAction = true;


            	} else if (x == 4)
            	{
    	        	displayMapInfo();
    	        	answer = "Display Current Map info";
            		validAction = true;

            	}
            	 else if (x == 5)
            	{
            		answer = "Game Start";
            		validAction = true;
            	}
            } else
            {
            	answer = "Not enough players";
        		validAction = true;
            }

            if (!validAction)
            {
            	answer = "Not your turn or step for the action is not right";
            	
            } 
            
            
            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(answer);

	        
	        try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            
            
            
        }
    }

	   private int log(String first_name, String last_name, String txt) {
	        System.out.println("\n ----------------------------");
	        System.out.println("Message from " + first_name + " " + last_name + "\n Text - " + txt);


	        
	        if (txt.contains("/placearmy"))
	        {
	        	return 0;
	        }	
	        else if (txt.equals("/reinforce"))
	        {
	        	return 1;
	        } else if (txt.equals("/attack"))
	        {
	        	return 2;
	        }
	        else if (txt.equals("/fortify"))
	        {
	        	return 3;
	        } else if (txt.equals("/map"))
	        {
	        	return 4;
	        } else if (txt.equals("/join") && gameReady != true)
	        {
	        	Players.get(totalNumParticipants++).setPlayerName(first_name);

	        	if (totalNumParticipants == 2)
	        	{
	        		gameReady = true;
	        	}
	        	return 5;
	        } else if (txt.equals("/myinfo") && gameReady == true)
	        {
	        	System.out.println(Players.get(0).getPlayerName());
	        }

	   
	        return -1;
	   }
	   

	   
	   
	   
	   @Override
	    public String getBotUsername() {
	        return "MeowChatBot";
	    }

	    @Override
	    public String getBotToken() {
	        //return "718366234:AAHZ64pich1qeITo4J2S8CmauBCfPSqCkQY";
	        return "704843018:AAGMDCsXfqVmOF_umFGSdZgJNAMiFyVr0d4";
	    }


}
