package riskboardgame;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;


public class Twitter4J {
	
	Properties prop = new Properties();
	InputStream input = null;
	String[] credentialData = new String[4];
	Twitter twitter;
    User user;



	public String[] getKeysNTokens()
	{
		try {
    		input = new FileInputStream("secret_MeowCat.properties");
    		prop.load(input);

    		credentialData[0] = prop.getProperty("oauth.consumerKey");
    		credentialData[1] = prop.getProperty("oauth.consumerSecret");
    		credentialData[2] = prop.getProperty("oauth.accessToken");
    		credentialData[3] = prop.getProperty("oauth.accessTokenSecret");

    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

		return credentialData;
	}
	public void connectTwitter(String[] cre)
	{
		twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(cre[0], cre[1]);
		AccessToken accesstoken = new AccessToken(cre[2], cre[3]);
        twitter.setOAuthAccessToken(accesstoken);
        try {
			user = twitter.verifyCredentials();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
	public void postTweet(String str)
	{
	   try {
			Status status = twitter.updateStatus(str);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
