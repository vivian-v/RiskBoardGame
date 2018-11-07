package riskboardgame;

import java.io.IOException;
import java.util.ArrayList;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.apache.http.HttpEntity;

public class Risk {

	public static void main(String[] args) {
		
		ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Board());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		
        
        
        
        
        
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
