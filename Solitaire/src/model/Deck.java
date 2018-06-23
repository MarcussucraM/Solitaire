package model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	ArrayList<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		for(int i = 1; i < 14; i++) {
			for(int j = 0; j < 4; j++) {
				cards.add(new Card(i,j));
			}
		}
	}
	
	
	public void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
	public String toString() {
		String res = "";
		
		for(int i = 0; i < cards.size(); i++) {
			res += Card.suitNames[cards.get(i).suit];
			res += " " + cards.get(i).rank + "\n";
		}
		
		return res;
	}
	
	public Card drawCard() {
		return cards.remove(cards.size()-1);
	}
	
	
}
