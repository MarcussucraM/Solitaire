package main;

import java.util.ArrayList;


//start here
//basic functions for controller to access from the model
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
	
	public SolitaireLogic getLogic() {
		return logic;
	}
	
	public ArrayList<Slot> getAllSlots() {
		return allSlots;
	}
	
	public Board getGameBoard() {
		return gameBoard;
	}
	
	public void updateBoard() {
		gameBoard.updateBoard(allSlots);
	}
	
	public ArrayList<CardGraphic> getCardGraphics(){
		return gameBoard.getCardEntities();
	}
	
	public ArrayList<CardGraphic> getSlotGraphics(){
		return gameBoard.getSlotEntities();
	}
	
	//Wierd side affect of this 
	//if you generate a random card
	//and then you click it again when theres another card of
	//the same rank/suit on a different stack
	//the different stack will add another card too
	public void selectCard(int x, int y) {
		//first see if we already have a selected entity
		//if so then try to move that entity over the selected card
		
		ArrayList<Card> cardCollisions = gameBoard.checkForCardCollisions(x, y);
		if(cardCollisions.size() == 0) return;
		for(int i = 0; i < allSlots.size(); i++) {
			for(int j = 0; j < cardCollisions.size(); j++) {
				if(cardCollisions.get(j).equals(allSlots.get(i).lookAtTopCard())) {
					Slot selectedSlot = allSlots.get(i);
					int randomRank = (int)(Math.random() * 13+1);
					int randomSuit = (int)(Math.random() * 4);
					selectedSlot.addCard(new Card(randomRank,randomSuit));
					
				}
			}
		}	
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
