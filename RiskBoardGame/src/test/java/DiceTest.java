import static org.junit.Assert.*;

import org.junit.Test;

public class DiceTest {

	Dice a = new Dice();
	int i = a.rollForOne();
	@Test
	public void test() {
		assert(i >= 1);
		assert(i <= 6);
	}

}
