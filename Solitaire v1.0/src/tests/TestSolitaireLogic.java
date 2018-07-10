package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.*;

class TestSolitaireLogic {

	
	@Test
	void test_Deck() {
		Deck d = new Deck();
		d.shuffleDeck();
		
		assertEquals(true, true);
	}
	
	@Test
	void test_drawKings() {
		//make sure the top 4 cards of an unshuffled deck are actually kings
		//we do this in the game first before we shuffle to grab the kings right
		//away and place them
		Deck d = new Deck();
		ArrayList<Card> kings = new ArrayList<Card>();
		for(int i = 0; i < 4; i++) {
			Card king = d.drawCard();
			assertEquals(king.rank == Card.king, true);
			kings.add(king);
		}
	}
	
	@Test
	void test_setUpTableauSlots() {
		//Basically make sure we're setting up correctly
		//make sure by the end of the set up we have 13 sets of
		//4 cards each in them with kings on the bottom
		SolitaireLogic s = new SolitaireLogic();
		ArrayList<Slot> t_slot = s.setUpTableauSlots();
		
		//make sure there are 13 slots
		assertEquals(t_slot.size() == 13, true);
		
		//make sure they're all size 4
		for(int i= 0; i < t_slot.size(); i++) {
			assertEquals(t_slot.get(i).size() == 4, true);
		}
		
		//make sure there are 4 kings - all at the bottom of the slot pos(0)
		int kingcount = 0;
		for(int i = 0; i < t_slot.size(); i++) {
			Card card_at_bottom = t_slot.get(i).getCards().get(0);
			if(card_at_bottom.rank == Card.king) kingcount++;
		}
		
		assertEquals(kingcount == 4, true);
		
	}
	
	@Test
	void test_setUpFoundationSlots() {
		SolitaireLogic s = new SolitaireLogic();
		ArrayList<Slot> f_slot = s.setUpFoundationSlots();
		
		//there should be 4 foundation slots
		assertEquals(f_slot.size() == 4, true);
		
		//they should all originally be empty
		for(int i = 0;i < f_slot.size(); i++) {
			assertEquals(f_slot.get(i).size() == 0, true);
		}
	}
	
	@Test
	void test_setUpSlots() {
		SolitaireLogic s = new SolitaireLogic();
		
		ArrayList<Slot> all_slots = s.setUpSlots();
		
		//make sure we've built an array consisting
		//of all slots from tableau and foundation
		//size of tableau = 13, size of foundation = 4
		assertEquals(all_slots.size() == 17, true);
		
		//go through the list and make sure they're of the right type
		// 0 - 12 are tableau slots
		// 13 - 16 are foundation slots
		// we already checked that they have the correct things
		// in them in previous tests so theres no need to test for that
		// again.
		for(int i = 0; i < all_slots.size(); i++) {
			if(i < 13) {
				assertEquals(all_slots.get(i).getType().equals(Slot.tableau), true);
			}
			else {
				assertEquals(all_slots.get(i).getType().equals(Slot.foundation), true);
			}
		}
	}
	
	@Test
	void test_canMoveToFoundation_validCard() {
		Slot to = Slot.createSlot(Slot.foundation);
		Slot from = Slot.createSlot(Slot.tableau);
		SolitaireLogic s = new SolitaireLogic();
		
		
		//try to move a 2 of clubs from tslot
		//onto an ace of clubs on fslot
		from.addCard(new Card(2,Card.clubs));
		to.addCard(new Card(Card.ace,Card.clubs));
		
		assertEquals(true, s.canMove(from, to));
	}
	
	@Test
	void test_putAceOntoEmptyFoundation() {
		Slot to = Slot.createSlot(Slot.foundation);
		Slot from = Slot.createSlot(Slot.tableau);
		SolitaireLogic s = new SolitaireLogic();
		
		//ACE OF DIAMONDS
		//try to move onto an empty foundation slot
		//should pass
		from.addCard(new Card(Card.ace,Card.diamonds));
		assertEquals(true, s.canMove(from,to));
		to.addCard(from.removeTopCard());
	}
	
	@Test
	void test_put10OntoEmptyFoundation() {
		Slot to = Slot.createSlot(Slot.foundation);
		Slot from = Slot.createSlot(Slot.tableau);
		SolitaireLogic s = new SolitaireLogic();
		
		//10 OF CLUBS
		//try to move onto an empty foundation slot
		//should fail
		from.addCard(new Card(10,Card.clubs));		
		assertEquals(false, s.canMove(from,to));
	}
	
	@Test
	void test_putEmptySlotOntoEmptySlot() {
		//A player can possibly select a slot that's empty 
		//and then select another slot - initiating the move
		//function - we don't want to allow this
		Slot to = Slot.createSlot(Slot.foundation);
		Slot from = Slot.createSlot(Slot.tableau);
		
		SolitaireLogic s = new SolitaireLogic();
		
		assertEquals(false, s.canMove(from, to));
	}
	
	@Test
	void test_canMoveToFoundation_invalidCard() {
		Slot to = Slot.createSlot(Slot.foundation);
		Slot from = Slot.createSlot(Slot.tableau);
		SolitaireLogic s = new SolitaireLogic();
		
		to.addCard(new Card(3,Card.diamonds));
		from.addCard(new Card(4,Card.clubs));
		
		//the rank is correct but not similar suits - should fail
		assertEquals(false,s.canMove(from, to));
		
		
		from.addCard(new Card(10,Card.clubs));
		//the suit is correct but not the correct rank
		assertEquals(false, s.canMove(from, to));
	}
	
	@Test
	void test_canMoveToTableau_validCard() {
		Slot to = Slot.createSlot("t");
		Slot from = Slot.createSlot("t");
		SolitaireLogic s = new SolitaireLogic();
		
		//see if a 9 can be moved onto a 10
		to.addCard(new Card(10,3));
		from.addCard(new Card(9,1));
		assertEquals(true,s.canMove(from,to));	
	}
	
	@Test
	void test_canMoveToTableau_invalidCard() {
		Slot to = Slot.createSlot("t");
		Slot from = Slot.createSlot("f");
		SolitaireLogic s = new SolitaireLogic();
		
		//see if a 11 from a foundation can be moved onto a 10
		//types of from slots shouldn't matter
		to.addCard(new Card(10,3));
		from.addCard(new Card(11,1));
		assertEquals(false,s.canMove(from,to));	
	}

	@Test
	void test_putCardOntoEmptyTableau() {
		Slot to = Slot.createSlot("t");
		Slot from = Slot.createSlot("f");
		SolitaireLogic s = new SolitaireLogic();
		
		//see if a 11 from a foundation can be moved onto a 10
		//types of from slots shouldn't matter
		from.addCard(new Card(11,1));
		assertEquals(false,s.canMove(from,to));	
	}
	
	@Test
	void test_getFoundationSlots() {
		Solitaire s = new Solitaire();
		ArrayList<Slot> fSlots = s.getFoundationSlots();
		
		assertEquals(fSlots.size() == 4, true);
	}
	
}
