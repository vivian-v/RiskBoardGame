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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;


public class TwitterSystem {
	
	Properties prop = new Properties();
	InputStream input = null;
	String[] credentialData = new String[4];
	Twitter twitter;
    User user;
    Dictionary<String, String> credentialKeys = new Hashtable<String, String>();


	public Dictionary<String, String> getKeysNTokens(String propName)
	{
		try {
    		input = new FileInputStream(propName);
    		prop.load(input);

    		credentialKeys.put("oauth.consumerKey", prop.getProperty("oauth.consumerKey"));
    		credentialKeys.put("oauth.consumerSecret", prop.getProperty("oauth.consumerSecret"));
    		credentialKeys.put("oauth.accessToken", prop.getProperty("oauth.accessToken"));
    		credentialKeys.put("oauth.accessTokenSecret", prop.getProperty("oauth.accessTokenSecret"));

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

		return credentialKeys;
	}
	public void connectTwitter(Dictionary<String, String> cre)
	{
		twitter = TwitterFactory.getSingleton();
		
        twitter.setOAuthConsumer(cre.get("oauth.consumerKey"), cre.get("oauth.consumerSecret"));
		AccessToken accesstoken = new AccessToken(cre.get("oauth.accessToken"), cre.get("oauth.accessTokenSecret"));
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
			twitter.updateStatus(str);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
