package main;
import java.awt.Color;

public class CardGraphic {
	public final int height = 75;
	public final int width = 50;
	public final int arc_height = 10;
	public final int arc_width = 10;
	public int posX;
	public int posY;
	private Card card;
	public Color color;
	
	//this id cooresponds with the slot you're assigned to.
	private int id;
	
	
	public CardGraphic(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	
	public CardGraphic(int x, int y, Card c) {
		this.posX = x;
		this.posY = y;
		this.card = c;
	}
	
	public Card getCard() {
		return this.card;
	}

	public String toString() {
		return card + new BoardPosition(posX,posY).toString();
	}
	
	//check to see if point(x,y) is inside of the bounding rectangle
	public boolean contains(int x, int y) {
		if(x >= posX && x <= posX+width) {
			if(y >= posY && y <= posY+height) return true;
		}
		return false;
	}

}
