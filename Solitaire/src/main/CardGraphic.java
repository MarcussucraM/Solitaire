package main;
import java.awt.Color;

//This class contains all things necessary for
//the view to draw it to the screen
public class CardGraphic {
	public final int height = 75;
	public final int width = 50;
	public final int arc_height = 10;
	public final int arc_width = 10;
	public int posX;
	public int posY;
	private Card card;
	
	//public Image img;
	//public Color color;
	
	//this id cooresponds with the slot you're assigned to.
	private int id;
	
	//used for creating empty slots on the screen
	public CardGraphic(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	
	//used for creating cards on the screen
	public CardGraphic(int x, int y, Card c) {
		this.posX = x;
		this.posY = y;
		this.card = c;
	}
	
	//gets the card this cardgraphic is associated to
	public Card getCard() {
		return this.card;
	}

	//check to see if point(x,y) is inside of this cardgraphic
	public boolean contains(int x, int y) {
		if(x >= posX && x <= posX+width) {
			if(y >= posY && y <= posY+height) return true;
		}
		return false;
	}
	
	public String toString() {
		return card + new BoardPosition(posX,posY).toString();
	}

}
