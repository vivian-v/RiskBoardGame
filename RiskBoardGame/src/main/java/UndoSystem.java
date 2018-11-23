package riskboardgame;

import java.util.Stack;

public class UndoSystem {
	Stack<History> stack = new Stack<History>();
	
	public History undo()
	{
		History oldHistory = stack.pop();
		return oldHistory;
		
	}

	public void addActionRecord(History history)
	{
		stack.push(history);
	}
}
