



import java.io.IOException;

import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Board;
import riskboardgame.Setup;


public class TestBoard extends TestCase {

	Setup setup = new Setup();
	String file1 = "Countries.txt";
	String file2 = "Connectivity.txt";
	  @Test
	  public void testReinforce(){
	  	Board bd  = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
	  	assertEquals("reinforce action", bd.reinforce(0));
	
	  }
	    @Test
	    public void testTradeInCard() throws IOException {
	    	Board bd  = new Board(setup.LoadMap(), setup.LoadDeck());
			  Board.testGenerator();
	    	assertEquals(4, bd.tradeInCard(0));
	    	assertEquals(0, bd.tradeInCard(1));
	
	    }
	    
	    @Test
	    public void testBotResponseToAll()
	    {
	    	Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
	    	bd.setBotStart(false);
	    	assertFalse(bd.getBotStart());
	    	assertFalse(bd.botResponseToAll("test"));
	    }
	  @Test
	  public void testFortify()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertEquals("You can't fortify",bd.fortify(0, "Alberta", "China", 1));

	  }
	  @Test
	  public void testShowAllCommands()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  assertNotNull(bd.showAllCommands().length());
	  }
	  @Test
	  public void testDetermineTurns()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  assertFalse(bd.determineTurns());

		  Board.testGenerator();
		  assertTrue(bd.determineTurns());
	  }
	  @Test
	  public void testAttack()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertTrue(bd.attack(0, "Alaska", "Alberta").contains("test1"));
		  assertEquals("You can't attack this country",bd.attack(0, "Alaska", "China"));
		  assertTrue(bd.attack(0, "Alberta", "Alaska").contains("Lost"));

	  }
	  
	  @Test
	  public void testAttack2()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  bd.setPlaceStart(true);
		  bd.setOpenGameNum(1);
		  bd.setGameStart(true);
		  System.out.println(bd.checkCategory("test1", 12345, "=doneaction"));
	  }
	  @Test
	  public void testkillPlayer()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertEquals(1, bd.killPlayer(0));

	  }
	  
	  @Test
	  public void testTransferOwnership()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertTrue(bd.transferOwnership("Alaska", 0, "Alberta", 1));
		  assertFalse(bd.transferOwnership("China", 0, "China", 1));
	  }
	  
	  @Test
	  public void testShowCountryValidation()
	  {
		  Board bd1 = new Board();
		  Board.testGenerator();
		  assertEquals("This country is not your country", bd1.showCountryValidation("test1", "China", 1));
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertTrue(bd.showCountryValidation("test1", "Alberta", 1).contains("Alaska"));
		  assertEquals("This country is not your country", bd.showCountryValidation("test2", "Alberta", 1));
		  assertEquals("Invalid Country Name", bd.showCountryValidation("test1", "test", 1));
		  assertTrue(bd.showCountryValidation("test1", "Alberta", 2).contains("lists"));

	  }
	  @Test
	  public void testCheckAttackableOrFortifiable() {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  
		  assertEquals(-1, bd.checkAttackableOrFortifiable("test1", "test"));
		  assertEquals(-2, bd.checkAttackableOrFortifiable("test1", "Alaska"));
		  assertEquals(1, bd.checkAttackableOrFortifiable("test1", "Alberta"));

	  }
	  
	  @Test
	  public void testGetPlayerIndex() {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();

		  assertEquals(0, bd.getPlayerIndex("test1"));

	  }
	  
	  @Test
	  public void testCheckCategory()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertEquals("Failed to setup number of players", bd.checkCategory("test1", 12345, "=creategame 1 123"));
		  assertEquals("Created a new game. ID : 333", bd.checkCategory("test1", 12345, "=creategame 2 333"));
		  assertEquals("Failed to create a game", bd.checkCategory("test1", 12345, "=creategame"));
		  assertTrue(bd.checkCategory("test1", 12345, "/help").length() == 337);
		  assertTrue(bd.checkCategory("test1", 12345, "=showgameid").contains("333"));
		  assertEquals("You are already in the game",bd.checkCategory("test2", 12345, "=join 333"));
		  
		  bd.setGameStart(true);
		  assertEquals("The game is full", bd.checkCategory("test3", 12345, "=join 333"));


		  assertEquals("Failed to join the game", bd.checkCategory("test1", 12345, "=join"));
		  assertEquals("Game ID is not valid", bd.checkCategory("test1", 12345, "=join 111"));
		  assertTrue(bd.checkCategory("test1", 12345, "=showallplayers").contains("test1"));
		  assertTrue(bd.checkCategory("test1", 12345, "=myinfo").contains("test1"));
		  bd.setGameStart(false);

		  assertTrue(bd.checkCategory("test1", 12345, "=mapinfo").contains("The action you just did : =mapinfo"));
		  
		  
		  assertEquals("Not correct action", bd.checkCategory("test", 12345, "=attack"));
		  bd.increaseCurrentActionIndex();
		  assertEquals("invalid input", bd.checkCategory("test", 12345, "=attack 2"));
		  
		  
		  
		  bd.setGameStart(false);
		  assertEquals("Game hasn't started yet", bd.checkCategory("test", 12345, "=attack Alberta China"));
		  bd.setGameStart(true);
		  assertTrue(bd.checkCategory("test22", 12345, "=attack Alberta China").contains("test1"));
		  assertTrue(bd.checkCategory("test", 12345, "=showattackable").contains("invalid"));
		  assertEquals("This country is not your country", bd.checkCategory("test1", 12345, "=showattackable Ural"));
		  bd.setPlaceStart(true);
		  assertTrue(bd.checkCategory("test", 12345, "=doneaction").contains("test1"));
		  bd.setOpenGameNum(0);
		  assertEquals("Game hasn't started yet", bd.checkCategory("test", 12345, "=doneaction"));

		  bd.setOpenGameNum(1);

		  assertTrue(bd.checkCategory("test1", 12345, "=doneaction").contains("fortify"));
		  assertTrue(bd.checkCategory("test1", 12345, "=doneaction").contains("=doneaction"));

		  assertTrue(bd.checkCategory("test", 12345, "=showturns").contains("test1"));
		  bd.setGameStart(false);
		  assertEquals("Game hasn't started yet", bd.checkCategory("test", 12345, "=showturns"));

		  assertEquals("Game hasn't started yet", bd.checkCategory("test", 12345, "=determineturns"));
		  bd.setGameStart(true);
		  assertTrue(bd.checkCategory("test", 12345, "=determineturns").contains("=determineturns"));

		  
		  


		  
		  

		  assertEquals("RiskGameBot", bd.getBotUsername());
		  assertEquals("718366234:AAHZ64pich1qeITo4J2S8CmauBCfPSqCkQY", bd.getBotToken());
		  
		  
	  }
	  
	  @Test
	  public void testCheckCategory2()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  
		  
		  bd.setGameStart(false);
		  assertEquals("Game hasn't started yet", bd.checkCategory("test1", 12345, "=myinfo"));
		  bd.setGameStart(true);
		  assertEquals("Failed to show info", bd.checkCategory("test1", 12345, "=myinfo"));
		  assertEquals("Created a new game. ID : 444", bd.checkCategory("test1", 12345, "=creategame 3 444"));
		  bd.setGameStart(false);
		  assertTrue(bd.checkCategory("test3", 12345, "=join 444").contains("join"));
		  Board.testGenerator();
		  assertTrue(bd.checkCategory("test1", 12345, "=placement").contains("=placement"));
		 
		  assertTrue(bd.checkCategory("test", 12345, "=reinforce").contains("test3")); 
		  bd.setGameStart(false);
		  assertEquals("Game hasn't started yet", bd.checkCategory("test3", 12345, "=reinforce")); 
		  bd.setGameStart(true);
		  assertEquals("reinforce action", bd.checkCategory("test3", 12345, "=reinforce")); 

		  bd.increaseCurrentActionIndex();
		  assertEquals("Not correct action", bd.checkCategory("test", 12345, "=reinforce")); 
		  bd.increaseCurrentActionIndex();
		  bd.increaseNumDeadPlayer();
	  }

	  @Test
	  public void testIsTradable()
	  {
		  String[] c1 = {"wild"};
		  String[] c2 = {"wild", "wild", "horse"};
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  
		  assertFalse(bd.isTradable(c1));
		  assertTrue(bd.isTradable(c2));


		  
	  }
	  @Test
	  public void testCheckCountryInCardsOwned() {
		  String[] c1 = {"China", "Alberta", "Ural"};
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertEquals(2, bd.checkCountryInCardsOwned(0, c1));
		  
	  }
	  @Test
	  public void testCheckCategory3()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  bd.setNumOfPlayers(2);
		  assertEquals(2, bd.getNumOfPlayers());
		  bd.setOpenGameNum(1);
		  assertEquals(1, bd.getOpenGameNum());
		  bd.addNumParticipants();
		  bd.setNumTroops(2);
		  assertEquals(2, bd.getNumTroops());
		  bd.increaseCurrentPlayerIndex();
		  assertEquals(1, bd.getCurrentPlayerIndex());
		  assertEquals(0, bd.getNumDeadPlayer());
		  assertEquals(0, bd.getCurrentActionIndex());
		  assertFalse(bd.getGameStart());
		  assertFalse(bd.getPlaceStart());
		  bd.setDetermineTurnsStart(true);
		  assertTrue(bd.getDetermineTurnsStart());
		  bd.setGameID("1234");
		  assertEquals("1234", bd.getGameID());
		  
	  }
	  @Test
	  public void testCheckCategory4()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertEquals("Not correct action", bd.checkCategory("test", 12345, "=fortify"));
		  bd.increaseCurrentActionIndex();
		  bd.increaseCurrentActionIndex();

		  assertEquals("invalid input", bd.checkCategory("test", 12345, "=fortify"));
		  assertEquals("Game hasn't started yet", bd.checkCategory("test", 12345, "=fortify China Alberta 2"));
		  bd.setGameStart(true);
		  bd.setOpenGameNum(1);
		  assertTrue(bd.checkCategory("test", 12345, "=fortify China Alberta 2").contains("test1"));
		  assertEquals("fortify action", bd.checkCategory("test1", 12345, "=fortify Kamchatka Alberta 2"));


	  }
	  @Test
	  public void testShowFortifiable()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertEquals("invalid input", bd.checkCategory("test", 12345, "=showfortifiable"));
		  assertTrue(bd.checkCategory("test1", 12345, "=showfortifiable Kamchatka").contains("lists"));

	  }
	  @Test
	  public void testArmyPlacement()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertTrue(bd.armyPlacement().contains("Done"));
		  bd.setPlaceStart(true);
		  assertEquals("Placement has done already", bd.checkCategory("test1", 12345, "=placement"));
	  
	  }
	  @Test
	  public void testShowCurrentAction()
	  {
		  Board bd = new Board(setup.LoadMap(), setup.LoadDeck());
		  Board.testGenerator();
		  assertTrue(bd.checkCategory("test1", 12345, "=showcurrentaction").contains("reinforce"));
		  bd.increaseCurrentActionIndex();
		  assertTrue(bd.checkCategory("test1", 12345, "=showcurrentaction").contains("attack"));
		  bd.increaseCurrentActionIndex();
		  assertTrue(bd.checkCategory("test1", 12345, "=showcurrentaction").contains("fortify"));

	  }

}
