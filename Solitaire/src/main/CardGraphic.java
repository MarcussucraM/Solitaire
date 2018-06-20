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
	
	boolean highLight;
	//public Image img;
	//public Color color;
	
	//this id cooresponds with the slot you're assigned to.
	private int slot_id;
	
	//used for creating empty slots on the screen
	public CardGraphic(int x, int y, int slot_id) {
		this.posX = x;
		this.posY = y;
		this.slot_id = slot_id;
	}
	
	//used for creating cards on the screen
	public CardGraphic(int x, int y, Card c, int id) {
		this.posX = x;
		this.posY = y;
		this.card = c;
		this.slot_id = id;
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
	
	public void setHighLight(boolean b) {
		highLight = b;
	}
	
	public boolean isHighLighted() {
		return highLight;
	}
	
	public int getSlotId() {
		return slot_id;
	}
	
	public String toString() {
		return card + "\nPosition on Board" + new BoardPosition(posX,posY).toString() + "\nSlot " + slot_id;
	}

}
