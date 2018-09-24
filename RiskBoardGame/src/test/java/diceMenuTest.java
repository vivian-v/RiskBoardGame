import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import junit.framework.TestCase;


import org.junit.Test;

public class diceMenuTest extends TestCase {

    String Name = "Player1";
    String attacker = "Attacker";
    int numTroops = 4;
    Board tester = new Board();

    @Test
    public void test() {

    	assertEquals(3, tester.diceMenu(Name, attacker, numTroops));

        //fail("Not yet implemented");
    }



}