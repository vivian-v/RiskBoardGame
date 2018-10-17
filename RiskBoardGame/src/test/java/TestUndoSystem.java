



import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Country;
import riskboardgame.Deck;
import riskboardgame.History;
import riskboardgame.Player;
import riskboardgame.UndoSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class TestUndoSystem extends TestCase {

	Stack<History> test = new Stack();


	Player p1 = new Player("Chang" , 20);
	Player p2 = new Player("Vincent", 20);
	
	Country c1 = new Country("China");
	Country c2 = new Country("Japan");
	Deck d1 = new Deck();
	int playerTurn = 0;
	int testTradeSetIndex = 0;
	HashMap<String, Country> m1 = new HashMap<String, Country>();
	ArrayList<Player> testPlist = new ArrayList<Player>();
	
	History test1;
	History test2;	
	
	UndoSystem actionController;
	
	
	
	
	
	
	
	
	
    @Test
    public void testUndo() throws IOException {
    	actionController = new UndoSystem();
    	
    	String actionStatus1 = "attack"; 
		test1 = new History(actionStatus1, testPlist, m1, playerTurn, d1, testTradeSetIndex);
		actionController.addActionRecord(test1);
    	
	  	String actionStatus2 = "reinforce";
		test1 = new History(actionStatus2, testPlist, m1, playerTurn, d1, testTradeSetIndex);
		actionController.addActionRecord(test1);
		
		
		assertEquals("reinforce", actionController.undo().getActionStatus());
		assertEquals("attack", actionController.undo().getActionStatus());
    	
    	
    }
    @Test
    public void addActionRecord() {
    	
    }

	

}