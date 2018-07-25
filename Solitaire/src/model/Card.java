package model;

public class Card {
	public static final int hearts = 0;
	public static final int spades = 1;
	public static final int diamonds = 2;
	public static final int clubs = 3;
	public static final int ace = 1;
	public static final int jack = 11;
	public static final int queen = 12;
	public static final int king = 13;
	
	//public static final String[] suitNames = {"Spades", "Hearts","Diamonds","Clubs"};
	public static final String[] suitNames = {"Hearts", "Spades","Diamonds","Clubs"};
	
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
