package model;

import java.util.ArrayList;

//basic functions for controller to access
//which allow it to update the state of the model
public class Solitaire {
	private SolitaireLogic logic;
	private Board gameBoard;
	private ArrayList<Slot> allSlots;
	
	//history of all moves - used for undomove
	private ArrayList<ArrayList<Slot>> gameStateHistory;


	public Solitaire() {
		logic = new SolitaireLogic();
		allSlots = logic.setUpSlots();
		
		//add original game state
		gameStateHistory = new ArrayList<ArrayList<Slot>>();
		gameStateHistory.add(copyGameState(allSlots));
	}
		

	
	public void undoMove() {
		if(gameStateHistory.size() > 1) {
			//remove most recent state
			gameStateHistory.remove(gameStateHistory.size()-1);
			
			allSlots = copyGameState(gameStateHistory.get(gameStateHistory.size()-1));
		}
	}
	
	public ArrayList<Slot> copyGameState(ArrayList<Slot> from) {
		ArrayList<Slot> currentState = new ArrayList<Slot>();
		for(int i = 0; i < from.size(); i++) {
			String type = from.get(i).getType();
			ArrayList<Card> cards = from.get(i).getCards();
			Slot s = Slot.createSlot(type);
			for(int j = 0; j < cards.size(); j++) {
				s.addCard(cards.get(j));
			}
			
			currentState.add(s);
		}
		return currentState;
	}
	

	// this is used in the command line version
	public ArrayList<Slot> getAllSlots() {
		return allSlots;
	}

	// get a new instance of the board
	// refreshes the board with updated slots
	public void updateBoard() {
		gameBoard = new Board(allSlots);
	}

	//allows the view to grab card entities
	public ArrayList<CardGraphic> getCardGraphics() {
		return gameBoard.getCardEntities();
	}

	public ArrayList<CardGraphic> getSlotGraphics() {
		return gameBoard.getSlotEntities();
	}

	//reset the cards that have been selected
	//this will be called if an invalid selection/move occurs
	//or if a move was successful and we need to 
	//reset it for a new pair of selected cards.
	public void resetSelectedCards() {
		gameBoard.resetSelectedCards();
	}

	/* Called by SolitaireMouseListener when a selection was successful
	 * if we have two selected cards in memory try to move the first one 
	 * onto the second one
	 * 
	 * return if it was successful or not
	 */
	public boolean tryToMakeMove() {
		ArrayList<CardGraphic> selectedCards = gameBoard.getSelectedCards();
		boolean res = false;

		if (selectedCards.size() == 2) {
			Slot slot1 = allSlots.get(selectedCards.get(0).getSlotId());
			Slot slot2 = allSlots.get(selectedCards.get(1).getSlotId());
			
			//add the move to the gameStateHistory if we successfully move a card
			//some how every gamestate is the exact same
			if(logic.moveCard(slot1, slot2)) {
				//addGameState();
				gameStateHistory.add(copyGameState(allSlots));
				res = true;
			}
			
			resetSelectedCards();
		}

		return res;
	}

	/*
	 * How to Select A card/slot collision occurs if our mouse click at point (x,y)
	 * is within one our CardGraphics bounding area. if one of those CardGraphics
	 * Card value is equal to the top Card of the Slot its associated to - defined
	 * by its slot_id field, then its deemed valid and we select it
	 * 
	 * The GameBoard holds the selected cardgraphic in memory until another
	 * cardgraphic has been selected - then we try to move the first card selected
	 * onto the second one
	 * 
	 * returns true if it was successful in selecting a card false otherwise
	 */
	public boolean selectCard(int x, int y) {
		// get collisions - if any exist
		ArrayList<CardGraphic> cardCollisions = gameBoard.checkForCardCollisions(x, y);

		for (int i = 0; i < cardCollisions.size(); i++) {
			int slot_id = cardCollisions.get(i).getSlotId();
			Card cardTopCard = allSlots.get(slot_id).lookAtTopCard();
			Card cardClickedOn = cardCollisions.get(i).getCard();

			// Check for Card Collisions
			// the card we picked on the top of the slot! - card selected
			if (cardTopCard.equals(cardClickedOn)) {
				gameBoard.addSelectedCard(cardCollisions.get(i));
				cardCollisions.get(i).setHighLight(true);
				return true;
			}
		}
		return false;

	}

	// Try to select an empty slot at point(x,y)
	public boolean selectSlot(int x, int y) {
		ArrayList<CardGraphic> slotCollisions = gameBoard.checkForSlotCollision(x, y);
		
		//We don't want to be able to select an empty slot as the first
		//selected thing - because you can't move an empty slot onto
		//another card or onto another slot.
		if(gameBoard.getSelectedCards().size() < 1) return false;
		
		for (int i = 0; i < slotCollisions.size(); i++) {
			// we can't select a slot thats being covered by cards!
			Slot currentSlot = allSlots.get(slotCollisions.get(i).getSlotId());
			if (currentSlot.size() != 0)
				return false;

			// if we pass that check then add it to the the selected cards array
			gameBoard.addSelectedCard(slotCollisions.get(i));
			slotCollisions.get(i).setHighLight(true);
			return true;
		}
		return false;
	}

	// We win if all the foundation slots have
	// 13 cards in them (they're full)
	// called by SolitaireMouseListener every time a card has been
	// successfully moved
	public boolean winCondition() {
		ArrayList<Slot> f_slots = getFoundationSlots();
		int full_slot_count = 0;

		for (int i = 0; i < f_slots.size(); i++) {
			if (f_slots.get(i).size() == 13) {
				full_slot_count++;
			}
		}

		return full_slot_count == 4;
	}

	// get these so we can check if they're all full
	// in which case we've won the game.
	public ArrayList<Slot> getFoundationSlots() {
		ArrayList<Slot> foundationSlots = new ArrayList<Slot>();
		for (int i = 0, start_pos = 13; i < 4; i++) {
			foundationSlots.add(allSlots.get(start_pos + i));
		}

		return foundationSlots;

	}
}
