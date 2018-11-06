package riskboardgame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MyBot extends  TelegramLongPollingBot
{
	
	//SendMessage message = new SendMessage();
	
	@Override
    public void onUpdateReceived(Update update) {

	    }
	   
	   	
	    @Override
	    public String getBotUsername() {
	        return "RiskGameBot";
	    }
 
	    @Override
	    public String getBotToken() {
	        return "718366234:AAHZ64pich1qeITo4J2S8CmauBCfPSqCkQY";
	    }
	    
}
