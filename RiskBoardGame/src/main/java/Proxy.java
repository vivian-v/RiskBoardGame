package riskboardgame;

public class Proxy implements Transaction {

	private Player player;
	private int credit;
	
	public Proxy(int n)
	{
		this.credit = n;
	}
	@Override
	public int getCredit()
	{
		if (player == null)
		{
			player = new Player(credit);
		}
		return player.getCredit();
	}
	@Override
	public int buyCards() {
		if (player == null)
		{
			player = new Player(credit);
		}
		
		return player.buyCards();
	}

	@Override
	public int buyUndoActions() {
		if (player == null)
		{
			player = new Player(credit);
		}
		return player.buyUndoActions();
	}

	@Override
	public int transferCredit() {
		if (player == null)
		{
			player = new Player(credit);
		}
		return player.transferCredit();	
	}
	
}
