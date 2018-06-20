package main;

import java.util.ArrayList;


//start here
//basic functions for controller to access
public class Solitaire {
	private SolitaireLogic logic;
	private Board gameBoard;
	private ArrayList<Slot> allSlots;
	
	//start game
	public Solitaire() {
		logic = new SolitaireLogic();
		allSlots = logic.setUpSlots();
		gameBoard = new Board();
		gameBoard.initBoard(allSlots);
	}
	
	//move a card from a slot to another slot
	//return true if the move was sucessful - false otherwise
	public boolean moveCard(Slot from, Slot to) {
		if(logic.canMove(from, to)) {
			to.addCard(from.removeTopCard());
			return true;
		}
		return false;
	}
	
	//this is used in the command line version
	public ArrayList<Slot> getAllSlots() {
		return allSlots;
	}
	
	//refreshes the board
	public void updateBoard() {
		gameBoard.updateBoard(allSlots);
	}
	
	public ArrayList<CardGraphic> getCardGraphics(){
		return gameBoard.getCardEntities();
	}
	
	public ArrayList<CardGraphic> getSlotGraphics(){
		return gameBoard.getSlotEntities();
	}
	
	public void resetSelectedCards() {
		gameBoard.resetSelectedCards();
	}
	
	//todo add some nicer comments
	public boolean tryToMakeMove() {
		ArrayList<CardGraphic> selectedCards = gameBoard.getSelectedCards();
		boolean res = false;
		
		if(selectedCards.size() == 2)
		{
			Slot slot1 = allSlots.get(selectedCards.get(0).getSlotId());
			Slot slot2 = allSlots.get(selectedCards.get(1).getSlotId());
			if(moveCard(slot1, slot2)) {
				res = true;
			}
			gameBoard.resetSelectedCards();
		}
		
		return res;
	}
	
	
	
	/*How to Select
	 * A card/slot collision occurs if our mouse click at point (x,y)
	 * is within one our CardGraphics bounding area.
	 * if one of those CardGraphics Card value is equal to the top Card of the 
	 * Slot its associated to - defined by its slot_id field, then its
	 * deemed valid and we select it
	 * 
	 * As for slots - we check for these after card collisions because
	 * some slot positions are behind other cards
	 * Selecting a slot is easier because we can only select it if it's empty
	 * 
	 *  returns true if it was successful in selecting a card
	 *  false otherwise
	 */
	public boolean selectCard(int x, int y) {
		//our card and slotCollsions - if any
		ArrayList<CardGraphic> cardCollisions = gameBoard.checkForCardCollisions(x, y);
		ArrayList<CardGraphic> slotCollisions = gameBoard.checkForSlotCollision(x, y);
		
		for(int i = 0; i < cardCollisions.size(); i++) {
			int slot_id = cardCollisions.get(i).getSlotId();
			Card cardTopCard = allSlots.get(slot_id).lookAtTopCard();
			Card cardClickedOn = cardCollisions.get(i).getCard();
			
			//Check for Card Collisions
			//the card we picked on the top of the slot! - card selected
			if(cardTopCard.equals(cardClickedOn)) {
				gameBoard.addSelectedCard(cardCollisions.get(i));
				//gameBoard.setSelectedCardGraphic(cardCollisions.get(i));
				cardCollisions.get(i).setHighLight(true);
				return true;
			}
		}
		return false;
		
	}
	
	//Try to select an empty slot at point(x,y)
	public boolean selectSlot(int x, int y) {
		ArrayList<CardGraphic> slotCollisions = gameBoard.checkForSlotCollision(x, y);
		for(int i = 0; i < slotCollisions.size(); i++) {
			//we can't select a slot thats being covered by cards!
			Slot currentSlot = allSlots.get(slotCollisions.get(i).getSlotId());
			if(currentSlot.size() != 0) return false;
			
			//if we pass that check then add it to the the selected cards array
			gameBoard.addSelectedCard(slotCollisions.get(i));
			slotCollisions.get(i).setHighLight(true);
			return true;
		}
		return false;
	}
	

	//get these so we can check if they're all full
	//in which case we've won the game.
	public ArrayList<Slot> getFoundationSlots(){
		ArrayList<Slot> foundationSlots = new ArrayList<Slot>();
		//int start_pos = 12;
		for(int i = 0, start_pos = 12; i < 4; i++) {
			foundationSlots.add(allSlots.get(start_pos + i));
		}
		
		return foundationSlots;
		
	}
}
