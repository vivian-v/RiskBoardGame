import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private Card card;
	private ArrayList<Card> deck;
	
	public void putCards(ArrayList<Card> card){
		deck = card;
		shuffle();
	}
	public void shuffle(){
		Collections.shuffle(deck);
	}
	public void addCard(Card card){
		deck.add(card);
	}
	public Card draw(){
		card = deck.get(0);
		deck.remove(0);
		
		return card;
	}
}
