package riskboardgame;

import java.util.ArrayList;
import java.util.Collections;

public class Deck  {
	private Card card;
	private ArrayList<Card> deck;
	
	public Deck()
	{
		deck = new ArrayList<Card>();
	}
	public Deck(Deck d) {
		// TODO Auto-generated constructor stub
	}
	public void putCards(ArrayList<Card> card){
		deck = card;
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
