package demo3;
import java.io.IOException;
import java.util.ArrayList;

public class Risk {

	
	public static void main(String[] args) throws IOException {

		Setup setup = new Setup();

		
		
		Board newBoard = new Board(setup.LoadMap(), setup.setupPlayer(2));
		
		
		ArrayList<Player> p = new ArrayList<Player>();
		
	
		
//		p.add(setup.createPlayerObject("Chang", 1));
//		p.add(setup.createPlayerObject("Vincent", 1));
//		p.add(setup.createPlayerObject("Vivian",1));
		
//		for (int i = 0; i < p.size(); i++)
//		{
//			System.out.println(p.get(i).getPlayerName());
//		}
//
//		p = setup.DetermineTurn(p);
//		
//		for (int i = 0; i < p.size(); i++)
//		{
//			System.out.println(p.get(i).getPlayerName());
//		}
		
	}



	

}
