package riskboardgame;

import java.io.IOException;
import java.util.ArrayList;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.apache.http.HttpEntity;

public class Risk {

	public static void main(String[] args) {
		Setup setup = new Setup();

		ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Board(setup.LoadMap(), setup.LoadDeck()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		
		
//		new Board();

//		try {
//			new Board();
//
//			new Board(setup.LoadMap(), setup.setupPlayer(2));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}

	


}
