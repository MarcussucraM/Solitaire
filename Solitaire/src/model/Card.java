package model;

public class Card {
	static final int spades = 0;
	static final int hearts = 1;
	static final int diamonds = 2;
	static final int clubs = 3;
	static final int jack = 11;
	static final int queen = 12;
	static final int king = 13;
	
	public static final String[] suitNames = {"Spades", "Hearts","Diamonds","Clubs"};
	
	public int rank;
	public int suit;
	
	public Card(int rank, int suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public boolean equals(Card other) {
		if(other == null) return false;
		return this.rank == other.rank && this.suit == other.suit;
	}
	
	public String toString() {
		return this.rank + " of " + suitNames[suit].charAt(0);
	}
	
	
}
