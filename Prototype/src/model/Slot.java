package model;

import java.util.ArrayList;

public class Slot {
	//types = foundation or tableau
	private String type;
	private ArrayList<Card> cards;
	
	private Slot(String type) {
		this.type = type;
		cards = new ArrayList<Card>();
	}
	
	public static Slot createSlot(String type) {
		if(type == "f" || type == "t") 
			return new Slot(type);
		else return null;		
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	//returns top card of the stack -
	//if empty returns null
	public Card lookAtTopCard() {
		if(cards.size() == 0) {
			return null;
		}
		else return cards.get(cards.size()-1);
	}
	
	//remove a card from the top of the stack
	public Card removeTopCard() {
		if(cards.size() == 0) {
			return null;
		}
		else return cards.remove(cards.size()-1);
	}
	
	//Add a card to the top of the stack
	public void addCard(Card c) {
		cards.add(c);
	}
	
	//get the number of cards in the slot - for logic purposes
	public int size() {
		return cards.size();
	}
	
	public String getType() {
		return this.type;
	}
	
	public String toString() {
		String res = "";
		for(int i = 0; i < cards.size(); i++) {
			res += cards.get(i).rank + " " + Card.suitNames[cards.get(i).suit] + " ";
		}
		return res;
	}
	
}
