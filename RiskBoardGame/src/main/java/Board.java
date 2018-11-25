package riskboardgame;

import javax.swing.undo.UndoManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends TelegramLongPollingBot{
	//private String[] regionNames = {"Africa", "Asia", "Australia", "Euroupe", "North America", "South America"};
	private String format = "%1$-5s | %2$-20s | %3$-24s | %4$-15s | %5$-11s | \n";
	private static HashMap<String, Country> Map = new HashMap<String, Country>();

	int numOfPlayers; //number of players that will play the game
	int openGameNum;
	int numParticipants;
	int numTroops;
	int currentPlayerIndex;
	int numDeadPlayers;
	int currentActionIndex;
	boolean gameStart;
	boolean placeStart;
	String gameID; //game id for a game
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<String> mapKeySets = new ArrayList<String>();
	
	Dice dice = new Dice();

	
	public Board(HashMap<String, Country> m)
	{
		System.out.println("Console Start");
		this.numOfPlayers = 0;
		this.gameID = "";
		this.openGameNum = 0;
		this.numParticipants = 0;
		this.numTroops = 0;
		this.gameStart = false;
		this.placeStart = false;
		this.currentPlayerIndex = 0;
		this.Map = m;
		this.numDeadPlayers = 0;
		this.currentActionIndex = 0;
		
		for (String value : Map.keySet())
		{
			mapKeySets.add(value);		
		}
		Collections.shuffle(mapKeySets);

	}

	public int setupNumOfPlayers()
	{
	
		return 0;
	}
	public String checkCategory(String userName, long user_id, String txt)
	{
		String delim = " ";
		String[] tokens = txt.split(delim);
		if (txt.contains("=creategame"))
		{
			if (tokens.length < 3 || openGameNum > 0)
				return "Failed to create a game";	
			
			if (Integer.parseInt(tokens[1]) < 2 || Integer.parseInt(tokens[1]) > 6)
				return "Failed to setup number of players";
			
			openGameNum = 1;
			this.numOfPlayers = Integer.parseInt(tokens[1]);
			this.numTroops = 40 - ((this.numOfPlayers - 2) * 5);
			this.gameID = tokens[2];
			
			return "Created a new game. ID : " + tokens[2];
		} else if (txt.equals("/help"))
		{
			return showAllCommands();
		} else if (txt.contains("=showgameid"))
		{
			return (this.gameID.length() == 0) ? "There is no game open" : "Game ID : " + this.gameID + "\nCurrent num participants : " + this.numParticipants;
		} else if (txt.contains("=join"))
		{
			if (tokens.length < 2)
			{
				return "Failed to join the game";
			} else 
			{		
				for (int i = 0; i < players.size(); i++)
				{
					if (!tokens[1].equals(this.gameID))
						return "Game ID is not valid";
					if (players.get(i).getPlayerName().equals(userName))
						return "You are already in the game";
				}
				if (!this.gameID.equals(tokens[1]))
					return "Invalid Game ID";
				else if (this.gameStart == true)
					return "The game is full";
		
				this.numParticipants++;
				Player newPlayer = new Player(userName, user_id, this.numTroops);
				players.add(newPlayer);
				
				if (this.numParticipants == this.numOfPlayers)
				{
					this.gameStart = true;
					botResponseToAll("Game Start\n" + "The Current Turn : " + players.get(0).getPlayerName());
    
				}
				return "Successfully join the game";
			}
		} else if (txt.equals("=showallplayers"))
		{
			String str = "";
			for (int i = 0; i < players.size(); i++)
			{
				str += (i+1) + " : " + players.get(i).getPlayerName() + "\n";
			}
			return str;
		} else if (txt.equals("=myinfo"))
		{
			if (!gameStart)
				return "Game hasn't started yet";
			
			for (int i = 0; i < players.size(); i++)
			{
				if (players.get(i).getPlayerName().equals(userName))
					return "My Name : " + players.get(i).getPlayerName() + "\nMy Number of Troops : " 
						+ players.get(i).getNumOfTroops() + "\nMy Number of Conquered Countries : " 
						+ players.get(i).getNumConquered() + "\nMy Number of Owned Countries : "
						+ players.get(i).getOwnedCountries().size();  
			}
			
			return "Failed to show info";
		} else if (txt.equals("=placement"))
		{
			if (placeStart)
				return "Placement has done already";
			
			placeStart = true;
			botResponseToAll(userName + " starts the placement\n");
			botResponseToAll(armyPlacement());
	
		} else if (txt.equals("=mapinfo"))
		{
			this.displayMapInfo();
		} else if (txt.contains("=attack") && this.currentActionIndex == 0)
		{
			if (tokens.length < 3 )
				return "invalid input";	
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
			if (currentPlayerIndex != getPlayerIndex(userName))
				return "Not your turn. Current Turn : " + players.get(currentPlayerIndex).getPlayerName();
			
			botResponseToAll(attack(currentPlayerIndex, tokens[1], tokens[2]));
		} else if (txt.contains("=showattackable"))
		{
			if (tokens.length < 2 )
				return "invalid input";
			
			return showAttackableCountry(userName, tokens[1]);
			
		} else if (txt.equals("=doneaction")) {
			if (!gameStart || openGameNum == 0)
				return "Game hasn't started yet";
			if (currentPlayerIndex != getPlayerIndex(userName))
				return "Not your turn. Current Turn : " + players.get(currentPlayerIndex).getPlayerName();
			
			if (this.currentActionIndex == 2)
			{
				this.currentActionIndex = 0;
				String cpname = players.get(currentPlayerIndex).getPlayerName();
				String npname = (currentPlayerIndex == this.numOfPlayers) ? players.get(0).getPlayerName() : players.get(currentPlayerIndex + 1).getPlayerName();
				botResponseToAll(cpname + "'s turn is over\n" + "Now, " + npname + "'s turn");
			} else
			{
				this.currentActionIndex++;
				if (this.currentActionIndex == 1)
					return "Now action : attack";
				else 
					return "Now action : fortify";
			}
			
		}
		
		return "fuck";
	}
	
	public boolean botResponseToAll(String str)
	{
		for (int i = 0; i < this.numOfPlayers; i++)
		{
			SendMessage message = new SendMessage() 
		            .setChatId(players.get(i).getUserId())
                    .setText(str);
			
			try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
                return false;
            }
		}
		
		return true;
	}
	
	
	public String showAllCommands()
	{
		String tempStr = "=help\n"
				+ "=creategame [number of Player] [gameid]\n"
				+ "=showgameid\n"
				+ "=join [gameid]\n"
				+ "=showallplayers\n"
				+ "=myinfo\n"
				+ "=placement\n"
				+ "=mapinfo\n"
				+ "=showattackable [countryName]"
				+ "=doneaction";

				
		return tempStr;
		
	}
	public String attack (int attackerIndex, String aCountry, String dCountry)
	{
		String warInfo = "";
		int[] attackerRolls;
		int[] defenderRolls;
		int numAttackerLose = 0;
		int numDefenderLose = 0;
		
		Country attackerCountry = getCountry(aCountry);
		Country defenderCountry = getCountry(dCountry);
		
		int defenderIndex = getPlayerIndexFromCountry(dCountry);
		
		if (attackerIndex == defenderIndex)
			return "You can't attack your country";

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

			Map.get(attackerCountry.getCountryName()).loseNumOfArmy(numAttackerLose);
			Map.get(defenderCountry.getCountryName()).loseNumOfArmy(numDefenderLose);
			
			if (Map.get(defenderCountry.getCountryName()).getNumOfArmy() < 1)
			{
				transferOwnership(attackerCountry.getCountryName(), attackerIndex,defenderCountry.getCountryName(), defenderIndex);

				
				if (players.get(defenderIndex).getOwnedCountries().size() == 0)
					killPlayer(defenderIndex);
				
			}
	
		} else
		{
			return "You can't attack this country";
		}
		
		warInfo = players.get(attackerIndex).getPlayerName()+" Lost : " + numAttackerLose + "\n" + players.get(defenderIndex).getPlayerName() + " Lost : " + numAttackerLose; 
		
		return warInfo;
		
	}
	public int killPlayer(int playerIndex)
	{
		players.get(playerIndex).killPlayer();
		this.numDeadPlayers++;
		return this.numDeadPlayers;
	}
	public boolean transferOwnership(String attackerCountry, int attackerIndex,String defenderCountry, int defenderIndex)
	{
		if (attackerCountry == defenderCountry)
			return false;
		Map.get(defenderCountry).setOwnerName((Map.get(attackerCountry).getOwnerName()));
		Map.get(defenderCountry).addNumOfArmy(1);
		Map.get(attackerCountry).loseNumOfArmy(1);
		
		players.get(attackerIndex).takeCountry(Map.get(defenderCountry));
		players.get(attackerIndex).increaseNumConquered();
		players.get(defenderIndex).loseCountry(Map.get(defenderCountry));
		
		//add tweets
		
		return true;
	}
	public String showAttackableCountry(String name, String country)
	{
		ArrayList<String> connection;
		String playerName = name;
		String countryLists = "Here is/are the lists\n";

		if (!Map.containsKey(country))
			return "Invalid Country Name";
		if (!Map.get(country).getOwnerName().equals(playerName))
			return "This country is not your country";
		if (Map.get(country).getNumOfArmy() < 2)
			return "Not enough army in the country";
		
		connection = Map.get(country).getAdjacency();
		
		if (connection.size() == 0)
			return "There is no country you can attack";
		
		for (int i = 0; i < connection.size(); i++)
		{
			if (!Map.get(connection.get(i)).getOwnerName().equals(playerName))
				countryLists += Map.get(connection.get(i)).getCountryName() + " : " + Map.get(connection.get(i)).getNumOfArmy() + "\n";
		}

		
		return countryLists;
		
	}
	public boolean isAttackable(Country c1, Country c2)
	{
		if (!c1.getAdjacency().contains(c2.getCountryName()) || c1.getOwnerName().equals(c2.getOwnerName()) || c1.getNumOfArmy() < 2)
		{
			return false;
		}

		return true;	
	}
	public Country getCountry(String s)
	{
		Country fc = Map.get(s);
		return fc;
	}
	public int getPlayerIndexFromCountry(String c)
	{
		String name = Map.get(c).getOwnerName();
		return getIndex(name);
	}
	public int getPlayerIndex(String name)
	{
		return getIndex(name);
	}
	public int getIndex(String name)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getPlayerName().equals(name))
				return i;
		}
		return -1;
	}
	public String armyPlacement()
	{
		boolean firstRound = false;
		int playerIndex = 0;
		String pickedCountry;
		int i = 0;
		while (players.get(this.numOfPlayers - 1).getNumOfTroops()> 0)
		{
			if (!firstRound)
			{
				pickedCountry = this.mapKeySets.get(i);
				Map.get(pickedCountry).setOwnerName(players.get(playerIndex).getPlayerName());
				Map.get(pickedCountry).addNumOfArmy(1);
				players.get(playerIndex).loseNumOfTroops(1);
				players.get(playerIndex).takeCountry(Map.get(pickedCountry));
				playerIndex++;
				if (playerIndex == this.numOfPlayers)
					playerIndex = 0;
				i++;
				if (i == this.mapKeySets.size())
				{	
					i = 0;
					firstRound = true;
				}
			} else
			{
				pickedCountry = this.mapKeySets.get(i);
				if (Map.get(pickedCountry).getOwnerName().equals(players.get(playerIndex).getPlayerName()))
				{
					Map.get(pickedCountry).addNumOfArmy(1);
					players.get(playerIndex).loseNumOfTroops(1);
					playerIndex++;
					if (playerIndex == this.numOfPlayers)
						playerIndex = 0;
					i++;
					if (i == this.mapKeySets.size())
					{	
						i = 0;
					}
				}
			}
		}

		return "Done army placement";
	}
	
	public void displayMapInfo()
	{
		System.out.format(format, "Index", "Region" ,"Country Name", "Land Owner", "Num of Army");
		int i = 0;
		for (String key : Map.keySet()) {
		   System.out.format(format, ++i, Map.get(key).getContinentName(),Map.get(key).getCountryName(), Map.get(key).getOwnerName(), Map.get(key).getNumOfArmy());
		}

	}
	
	
	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String first_name = update.getMessage().getChat().getFirstName();

			String answer = "";
            long user_id = update.getMessage().getChat().getId();
            long chat_id = update.getMessage().getChatId();

            String message_text = update.getMessage().getText();
            
            answer = checkCategory(first_name, user_id , message_text);
 
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

    @Override
    public String getBotUsername() {
        return "RiskGameBot";
    }

    @Override
    public String getBotToken() {
        return "718366234:AAHZ64pich1qeITo4J2S8CmauBCfPSqCkQY";
    }

	
}
