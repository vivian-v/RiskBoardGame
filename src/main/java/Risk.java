import java.util.ArrayList;
import java.util.HashMap;

public class Risk {

	public static void main(String[] args) {

		Initializer setup = new Initializer();

		Board RiskBoard = new Board(setup.loadPlayers(), setup.loadCountryIndex() ,setup.loadMap(), setup.loadDeck(), new Dice());
		RiskBoard.GameStart();	
	}

}
