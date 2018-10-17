package riskboardgame;

public interface Transaction {
	public int buyCards();
	public int buyUndoActions();
	public int transferCredit();
	public int getCredit();
}
