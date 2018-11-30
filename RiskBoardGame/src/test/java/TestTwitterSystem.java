import org.junit.Test;
import junit.framework.TestCase;
import riskboardgame.TwitterSystem;


public class TestTwitterSystem extends TestCase {

	TwitterSystem tweet = new TwitterSystem();


    @Test
    public void testGetKeysNTokens() {
    	assertEquals("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", tweet.getKeysNTokens("secret_MeowCat.properties").get("oauth.consumerKey"));
    }

	

}
