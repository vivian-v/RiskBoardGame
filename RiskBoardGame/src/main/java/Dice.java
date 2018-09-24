import java.util.Random;
import java.util.Arrays;


public class Dice {
	private int roll;
	private int[] dice;
	private Random RNum;
	
	public int[] roll (int n){
		dice = new int[n];
		for (int i = 0; i < n; i++){
			RNum = new Random();
			roll = RNum.nextInt(6) + 1;
			dice[i] = roll;
		}	
		Arrays.sort(dice);
	
		return dice;
	}
	public int rollForOne(){
		RNum = new Random();
		roll = RNum.nextInt(6) + 1;
		return roll;
	}
	
}
