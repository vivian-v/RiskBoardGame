package demo3;

import java.util.ArrayList;
import java.util.Stack;

public class UndoSystem {
	Stack<History> stack = new Stack();
	
	public void undo()
	{
		History oldHistory = stack.pop();
		System.out.println("Player " + oldHistory.getPrevPlayers().get(oldHistory.getPrevPlayerIndex()).getPlayerName() + " undo " + oldHistory.getActionStatus() + " completed");
		
	}

	public void addActionRecord(History history)
	{
		stack.push(history);
	}
}
