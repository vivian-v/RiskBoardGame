



import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.Card;
import riskboardgame.Deck;

import java.util.ArrayList;


public class TestDeck extends TestCase {

	Deck test = new Deck();
	Card c1 = new Card("test1", "test1");
	Card c2 = new Card("test2", "test2");
	Card c3 = new Card("test3", "test3");
	Card c4 = new Card("test4", "test4");

    @Test
    public void testPutCards() {
    	ArrayList<Card> cards = new ArrayList<Card>();
    	cards.add(c1);
    	cards.add(c2);
    	cards.add(c3);
    	cards.add(c4);
    	
    	test.putCards(cards);
    	assertEquals("test1", test.draw().getDetail());
    }
    @Test
    public void testShuffle() {
    	ArrayList<Card> cards = new ArrayList<Card>();
    	cards.add(c1);
    	cards.add(c2);
    	cards.add(c3);
    	cards.add(c4);
    	
    	test.putCards(cards);
    	test.shuffle();
    	assertNotNull(test);
    }
    @Test
    public void testAddCard() {
    	test.addCard(c1);
    	assertEquals("test1", test.draw().getDetail());
    }
    @Test 
    public void testDraw() {
    	test.addCard(c2);
    	assertEquals("test2", test.draw().getDetail());
    }

	

}