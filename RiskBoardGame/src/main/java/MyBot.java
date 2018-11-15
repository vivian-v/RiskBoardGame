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
	int gameId;
	int test;
	//SendMessage message = new SendMessage();
	
	@Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String user_first_name = update.getMessage().getChat().getFirstName();
            
            String user_last_name = update.getMessage().getChat().getLastName();
            
            String user_username = update.getMessage().getChat().getUserName();
            
            long user_id = update.getMessage().getChat().getId();
            
            String message_text = update.getMessage().getText();
            
            //long chat_id = -271143153;
            long chat_id = update.getMessage().getChatId();
            //String answer = "why it doesn't work...";
            String answer = message_text;
            
            
            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(answer);
            
            
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);
           

//            try {
//                execute(message); // Sending our message object to user
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//            
//            
            
        }
    }
	   
	   public boolean log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
	        System.out.println("\n ----------------------------");
//	        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//	        Date date = new Date();
//	        System.out.println(dateFormat.format(date));
	        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
//	        System.out.println("Bot answer: \n Text - " + bot_answer);
	        
	        return true;
	    }
	   
	   public int DisplayGameID(int id)
	   {
		   this.gameId = id;
		   return this.gameId;
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
