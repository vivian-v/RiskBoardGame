package riskboardgame;

import java.util.ArrayList;

public class WarObserver implements Observable {
	private ArrayList<Observer> warObservers = new ArrayList<Observer>();
	
	@Override
	public void addObserver(Observer o) {
		warObservers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		warObservers.remove(o);
	}

	@Override
	public void notifyWarObservers() {
		for (Observer ob : warObservers) {
            ob.update();
     }


	}

}
