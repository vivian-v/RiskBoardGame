package riskboardgame;

import java.util.ArrayList;
import java.util.Stack;

public class UndoSystem {
	Stack<History> stack = new Stack();
	
	public History undo()
	{
		History oldHistory = stack.pop();
		//("Player " + oldHistory.getPrevPlayers().get(oldHistory.getPrevPlayerIndex()).getPlayerName() + " undo " + oldHistory.getActionStatus() + "\n");
		return oldHistory;
		
	}

	public void addActionRecord(History history)
	{
		stack.push(history);
	}
}
