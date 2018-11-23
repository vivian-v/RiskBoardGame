package riskboardgame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MyBot extends  TelegramLongPollingBot
{
//-281194447
	
	 @Override
	    public void onUpdateReceived(Update update) {

	        // We check if the update has a message and the message has text
	        if (update.hasMessage() && update.getMessage().hasText()) {
	            // Set variables
	            String message_text = update.getMessage().getText();
	            long chat_id = update.getMessage().getChatId();
	            //chat_id = -281194447;
	            
	            System.out.println(update.getChannelPost());

	            SendMessage message = new SendMessage() // Create a message object object
	                .setChatId(chat_id)
	                .setText(message_text);
	            
	            
	            try {
	                execute(message); // Sending our message object to user
	            } catch (TelegramApiException e) {
	                e.printStackTrace();
	            }
	            
	            chat_id = -281194447;

	            message = new SendMessage() // Create a message object object
	                .setChatId(chat_id)
	                .setText(message_text);
	            
	            
	            try {
	                execute(message); // Sending our message object to user
	            } catch (TelegramApiException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	   
	   
	   @Override
	    public String getBotUsername() {
	        return "MeowChatBot";
	    }

	    @Override
	    public String getBotToken() {
	        //return "718366234:AAHZ64pich1qeITo4J2S8CmauBCfPSqCkQY";
	        return "704843018:AAGMDCsXfqVmOF_umFGSdZgJNAMiFyVr0d4";
	    }
	    
}
