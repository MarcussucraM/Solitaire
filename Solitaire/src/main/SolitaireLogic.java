package main;

import java.util.ArrayList;

public class SolitaireLogic {
	static final int tableauSlotAmount = 13;
	static final int foundationSlotAmount = 13;

	// incase we want to go back one turn.
	// we can hold the state of all slots in one var
	// ArrayList<Slot> prevSlotStates

	public SolitaireLogic() {

	}

	ArrayList<Slot> initTabSlots() {
		ArrayList<Slot> res = new ArrayList<Slot>();
		for (int i = 0; i < 13; i++) {
			res.add(Slot.createSlot("t"));
		}

		return res;
	}
	
	//set up foundation - 
	//should just be four slots
	public ArrayList<Slot> setUpFoundationSlots() {
		ArrayList<Slot> fSlots = new ArrayList<Slot>();
		for (int i = 0; i < 4; i++) {
			fSlots.add(Slot.createSlot("f"));
		}

		return fSlots;
	}
	
	public ArrayList<Slot> setUpSlots(){
		ArrayList<Slot> tableauSlots = setUpTableauSlots();
		ArrayList<Slot> foundationSlots = setUpFoundationSlots();
		ArrayList<Slot> allSlots = new ArrayList<Slot>();
		

		allSlots.addAll(tableauSlots);
		allSlots.addAll(foundationSlots);
		
		return allSlots;		
	}

	public ArrayList<Slot> setUpTableauSlots() {
		Deck deck = new Deck();
		ArrayList<Slot> tabSlots = initTabSlots();

		// first we put the 4 kings down on random slots
		// do this with an unshuffled deck - theyre all on top.
		for (int i = 0; i < 4; i++) {
			int randomSlot = (int) (Math.random() * tabSlots.size());

			// if the slot isn't empty we need to pick another random slot
			// before we draw a card - so reset the loop
			if (tabSlots.get(randomSlot).size() != 0) {
				i--;
				continue;
			}

			Card king = deck.drawCard();
			tabSlots.get(randomSlot).addCard(king);
		}

		// now lets shuffle the deck and place the next 9 cards
		deck.shuffleDeck();
		for (int i = 0; i < tabSlots.size(); i++) {
			if (tabSlots.get(i).size() == 0) {

				Card next = deck.drawCard();
				tabSlots.get(i).addCard(next);
			}
		}

		// now we draw the rest of the cards and put them
		// down on slots till we're out of cards.
		while (deck.cards.size() > 0) {
			for (int i = 0; i < 13; i++) {
				if (deck.cards.size() == 0)
					break;
				Card next = deck.drawCard();
				tabSlots.get(i).addCard(next);

			}
		}

		return tabSlots;

	}
	
	//this will basically only be used
	//when selecting the first card to be moved
	public boolean canSelect(Slot s) {
		if(s.size() == 0) return false;
		return true;
	}

	//utility method for canMove(from,to)
	//provides the logic necessary to see if you can move
	//a card from the top of one slot(regardless of type)
	//to the top of a foundation slot.
	private boolean canMoveToFoundation(Slot from, Slot to) {
		Card top_from = from.lookAtTopCard();
		Card top_to = to.lookAtTopCard();

		//if we're trying to put a card onto an empty foundation slot
		//then it has to be an ace - otherwise it's not valid
		if (to.size() == 0)
			if(top_from.rank == 1) return true;
			else return false;
		
		//if the slot isn't empty then we want ascending order of rank
		//aka the rank of the top card is one higher than the one before it.
		else if (top_to.suit == top_from.suit && top_to.rank + 1 == top_from.rank)
			return true;
		return false;
	}

	//utility method for canMove(from,to)
	//provides the logic necessary to see if you can move
	//a card from the top of one slot(regardless of type)
	//to the top of a tableau slot.
	private boolean canMoveToTableau(Slot from, Slot to) {
		Card top_from = from.lookAtTopCard();
		Card top_to = to.lookAtTopCard();

		// we can't put cards into an empty tableau slot
		if (to.size() == 0)
			return false;
		//rank of the card from the from slot has to be 1 less
		//than the rank of the card on top of the to stack
		else if ((top_to.rank) == top_from.rank+1)
			return true;
		return false;
	}

	//handles logic for moving the top card from
	//one slot (slot from) onto the top of another slot (slot to)
	//Slots are broke into 2 different types foundation and tableau
	//each of those have a different set of rules to whether or not
	//we can put a card onto it.
	public boolean canMove(Slot from, Slot to) {
		// we're trying to move a card into a foundation slot.
		if (to.getType() == "f") {
			return canMoveToFoundation(from, to);
		}
		// trying to move a card into a tableau slot
		else if (to.getType() == "t") {
			return canMoveToTableau(from, to);
		}

		return false;
	}

	

}
