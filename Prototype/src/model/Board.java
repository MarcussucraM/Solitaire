package model;

import java.util.ArrayList;

//Is in control of positioning our game entities
//The view will query the board in order to draw
//our user interface
public class Board {
	public static final int cardSpacing = 15;
	private ArrayList<CardGraphic> boardEntities;
	private ArrayList<CardGraphic> slotEntities;	
	private ArrayList<CardGraphic> selectedCards; 
	
	//All of our initial starting positions for slots
	//This makes our board very static (maybe change down the road?)
	
	//the basic layout of the board - t(tableau slots) f(foundation slots)
	
	/*  t	t	t	t	t	t	t		f	f
	 * 
	 *  t	t	t	t	t	t			f	f
	 */
	private final BoardPosition[] slotStartPositions = { new BoardPosition(30,60) , new BoardPosition(30, 330),
												new BoardPosition(100, 60), new BoardPosition(100, 330),
												new BoardPosition(170,60), new BoardPosition(170,330),
												new BoardPosition(240,60),new BoardPosition(240,330),
												new BoardPosition(310,60), new BoardPosition(310,330),
												new BoardPosition(380,60), new BoardPosition(380, 330),
												new BoardPosition(450,60),
												new BoardPosition(600,60), new BoardPosition(600,330),
												new BoardPosition(670,60), new BoardPosition(670,330)};
	
	public Board() {
		boardEntities = new ArrayList<CardGraphic>();
		slotEntities = new ArrayList<CardGraphic>();
		selectedCards = new ArrayList<CardGraphic>();
	}
	
	public void addSelectedCard(CardGraphic c) {
		if(selectedCards.size() == 2) {
			//error
			return;
		}
		selectedCards.add(c);		
	}
	
	public ArrayList<CardGraphic> getSelectedCards(){
		return selectedCards;
	}
	
	public void resetSelectedCards() {
		for(int i = 0; i < selectedCards.size(); i++) {
			selectedCards.get(i).setHighLight(false);
		}
		selectedCards.clear();
	}
	
	public ArrayList<CardGraphic> checkForCardCollisions(int x, int y){
		ArrayList<CardGraphic> cards = new ArrayList<CardGraphic>();
		for(int i = 0; i < boardEntities.size(); i++) {
			if(boardEntities.get(i).contains(x, y)) {
				cards.add(boardEntities.get(i));
			}
		}
		return cards;
	}
	
	
	public ArrayList<CardGraphic> checkForSlotCollision(int x, int y) {
		ArrayList<CardGraphic> slots = new ArrayList<CardGraphic>();
		for(int i = 0; i < slotEntities.size(); i++) {
			if(slotEntities.get(i).contains(x, y)) {
				slots.add(slotEntities.get(i));
			}
		}
		return slots;
	}
	
	//todo - figure out what slot has been clicked
	//we don't have a reference to the slots - the game does
	//potentially give the cardgraphics an id which maps to the slot
	//it is in
	public void checkForSlotCollisions(int x, int y) {
		for(int i = 0; i < slotEntities.size(); i++) {
			if(slotEntities.get(i).contains(x, y)) {
				System.out.println("Slot");
			}
		}
	}
	
	//this is really just here for the view to figure out where to draw the initial slots
	//it also gives it an id to associate it with a slot
	//fills the slotEntities array
	//called by the Solitaire class 
	private void generateSlotEntities(ArrayList<Slot> slots) {
		for(int i = 0; i < slots.size(); i++) {
			int x = slotStartPositions[i].getX();
			int y = slotStartPositions[i].getY();
			slotEntities.add(new CardGraphic(x,y,i));
			//cardGraphic.setID(i) //This is the index of the slot that the cardgraphic is in
		}
	}
	
	//Initializes a bunch of cardGraphics
	//The first card in the slot will be put at slotStartPositions[i]
	//The cards afterward will slightly overlap the one before it downwards
	//
	//in order to achieve this we keep track of a currentY position
	//which will start at slotStartPositions[i].getY() and increment by our cardSpacing amount
	//for every card thats in the slot 
	//fills the boardEntities array
	private void generateCardEntities(ArrayList<Slot> slots) {
		for(int i = 0; i < slots.size(); i++) {
			int currentX = slotStartPositions[i].getX();
			int currentY = slotStartPositions[i].getY();
			
			for(int j = 0; j < slots.get(i).size(); j++) {
				Card next = slots.get(i).getCards().get(j);
				
				//only apply the spacing on the cards after the first.
				if(j!=0) currentY += cardSpacing;
				
				CardGraphic entity = new CardGraphic(currentX, currentY, next, i);	
				boardEntities.add(entity);
			}
		}
	}
	
	//This just fills the board with cards -
	//the slots are - namely the foundation slots.
	public void initBoard(ArrayList<Slot> slots) {		
		generateSlotEntities(slots);
		generateCardEntities(slots);
	}
	
	//reposition the cards
	//this is called when a change is made
	//such as a card move to a different slot
	//or a selection
	public void updateBoard(ArrayList<Slot> slots) {
		//first remove the previous setup
		boardEntities = new ArrayList<CardGraphic>();
		slotEntities = new ArrayList<CardGraphic>();
		generateCardEntities(slots);
		generateSlotEntities(slots);
	}
	
	public ArrayList<CardGraphic> getSlotEntities(){
		return slotEntities;
	}
	
	public ArrayList<CardGraphic> getCardEntities(){
		return boardEntities;
	}
	
	//used for testing - remove eventually
	public void printBoardPositions() {
		for(int i = 0; i < slotStartPositions.length; i++) {
			System.out.println(slotStartPositions[i]);
		}
	}
	
	//used for testing - remove eventually
	public void printCardGraphics() {
		for(int i = 0; i < boardEntities.size(); i++) {
			System.out.println(boardEntities.get(i));
		}
	}
	
}
