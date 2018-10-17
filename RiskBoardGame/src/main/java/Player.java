package riskboardgame;

import java.util.ArrayList;



public class Player implements Observer, Transaction {
	private ArrayList<Observer> warObservers = new ArrayList<Observer>();
	
	private int numOfTroops;
    private int [] countriesOwnedByRegions;

	private String playerName;
	private String[] regionNames = {"Africa", "Asia", "Australia", "Europe", "North America", "South America"};;

	private ArrayList<Country> ownedCountries;
	private ArrayList<Card> cards;
	private int tradeSetIndex;
	private int credit;
	private Transaction account;
	private int numConquered;
	private boolean dead;
	
	public Player(String s, int n)	{
		this.playerName = s;
		this.numOfTroops = n;
		this.ownedCountries = new ArrayList<Country>();
		this.cards = new ArrayList<Card>();
		this.countriesOwnedByRegions = new int[6];
		this.tradeSetIndex = 1;
		this.account = new Proxy(20);
		this.credit = 20;
		this.numConquered = 0;
		this.dead = false;
	}
	public Player(int credit2) {
		this.credit = credit2;
	}
	public void killPlayer()
	{
		this.dead = true;
	}
	public int getNumConquered()
	{
		return this.numConquered;
	}
	public void increaseNumConquered()
	{
		this.numConquered++;
	}
	public int getTradeSetIndex()
	{
		return this.tradeSetIndex;
	}
	public int getNumOfTroops()
	{
		return numOfTroops;
	}
	public boolean isPlayerDead()
	{
		return this.ownedCountries.size() < 1 ? true : false;
	}
	public int buyccc()
	{
		account.buyCards();
		return this.credit;
	}
	public String getPlayerName() {
		return this.playerName;
	}
	
	public int[] getCountriesOwnedByRegions()
	{
		return this.countriesOwnedByRegions;
	}
	
	public void setPlayerName(String n)	{
		this.playerName = n;
	}

	public void addNumOfTroops(int n) {
		this.numOfTroops += n;
	}
	public void loseNumOfTroops(int n) {
		this.numOfTroops -= n;
	}
	
	public void takeCountry(Country c) {
		this.ownedCountries.add(c);
		updateRegionHeld(c, 1);
	}
	
	public void loseCountry(Country c) {
		this.ownedCountries.remove(c);
		updateRegionHeld(c, -1);

	}
	
	public void addCard(Card c) {
		this.cards.add(c);
	}
	public void setCards(ArrayList<Card> c)
	{
		this.tradeSetIndex++;
		this.cards = c;
	}
	public Card drawCard()
	{
		return this.cards.remove(0);
	}
	public ArrayList<Card> getAllCards()
	{
		return this.cards;
	}
	public ArrayList<Country> getOwnedCountries() {
		return this.ownedCountries;
	}
	
	public void updateRegionHeld(Country c, int n) {
		String tempStr = c.getContinentName();
		for (int i = 0; i < 6; i++)	{
			if (this.regionNames[i].equals(tempStr)) {
				this.countriesOwnedByRegions[i] += n;
				break;
			}
		}
	}
//	public void setCredit(int n)
//	{
//		this.credit = n;
//	}
	@Override
	public void update() {
		System.out.println(this.playerName + " being attacked");
		
	}
	@Override
	public int buyCards() {
		System.out.print("Remaining Credit After Buy a Card : ");
		return this.credit-= 1;
	}
	@Override
	public int buyUndoActions() {
		System.out.print("Remaining Credit After Buy an Undo Action : ");
		return this.credit-= 5;
	}
	@Override
	public int transferCredit() {
		System.out.print("Transferable Credit : ");
		return this.credit;
		
	}
	
	@Override
	public int getCredit()
	{
		return this.credit;
	}

}
