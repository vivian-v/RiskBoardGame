package riskboardgame;


public class Card {
	private String detail;
	private String type;


	public Card(String detail, String t){
		this.detail = detail;
		this.type = t;
	}

	public String getDetail(){
		return this.detail;
	}
	public String getType(){
		return this.type;
	}
}
