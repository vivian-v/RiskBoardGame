package riskboardgame;

import java.io.IOException;
import java.util.ArrayList;

public class Risk {

	public static void main(String[] args) {
		
		Setup setup = new Setup();
		try {
			Board riskBoard = new Board(setup.LoadMap(), setup.setupPlayer(2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ReplayS3 replay = new ReplayS3();

		//replay.listBuckets();

	//	Twitter4J tw = new Twitter4J();
		
	}

	


}
