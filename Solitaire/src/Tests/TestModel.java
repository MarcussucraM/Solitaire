package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import main.*;

import org.junit.jupiter.api.Test;

class TestModel {

	
	@Test
	void test() {
		Deck d = new Deck();
		d.shuffleDeck();
		assertEquals(true, true);
	}
	
	@Test
	void testDrawKings() {
		Deck d = new Deck();
		ArrayList<Card> kings = new ArrayList<Card>();
		for(int i = 0; i < 4; i++) {
			Card king = d.drawCard();
			assertEquals(king.rank == 13, true);
			kings.add(king);
		}
	}
	
	@Test
	void testSetUpTableauSlots() {
		//Basically make sure we're setting up correctly
		//make sure by the end of the set up we have 13 sets of
		//4 cards each in them with kings on the bottom
		SolitaireLogic s = new SolitaireLogic();
		
		ArrayList<Slot> t_slot = s.setUpTableauSlots();
		assertEquals(t_slot.size() == 13, true);
		
		for(int i= 0; i < t_slot.size(); i++) {
			assertEquals(t_slot.get(i).size() == 4, true);
		}
	}
	
	@Test
	void test_canMoveToFoundation_validCard() {
		Slot to = Slot.createSlot("f");
		Slot from = Slot.createSlot("t");
		SolitaireLogic s = new SolitaireLogic();
		
		
		//try to move a 2 of clubs from tslot
		//onto an ace of clubs on fslot
		from.addCard(new Card(2,3));
		to.addCard(new Card(1,3));
		
		assertEquals(true, s.canMove(from, to));
	}
	
	@Test
	void test_putAceOntoEmptyFoundation() {
		Slot to = Slot.createSlot("f");
		Slot from = Slot.createSlot("t");
		SolitaireLogic s = new SolitaireLogic();
		
		//ACE OF DIAMONDS
		//try to move onto an empty foundation slot
		//should pass
		from.addCard(new Card(1,3));
		assertEquals(true, s.canMove(from,to));
		to.addCard(from.removeTopCard());
	}
	
	@Test
	void test_put10OntoEmptyFoundation() {
		Slot to = Slot.createSlot("f");
		Slot from = Slot.createSlot("t");
		SolitaireLogic s = new SolitaireLogic();
		
		//10 OF CLUBS
		//try to move onto an empty foundation slot
		//should fail
		from.addCard(new Card(10,3));		
		assertEquals(false, s.canMove(from,to));
	}
	
	@Test
	void test_canMoveToFoundation_invalidCard() {
		Slot to = Slot.createSlot("f");
		Slot from = Slot.createSlot("t");
		SolitaireLogic s = new SolitaireLogic();
		
		to.addCard(new Card(3,3));
		from.addCard(new Card(4,2));
		
		//the rank is correct but not similar suits - should fail
		assertEquals(false,s.canMove(from, to));
		
		
		from.addCard(new Card(10,3));
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
