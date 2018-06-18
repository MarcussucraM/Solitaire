package main;

import java.util.ArrayList;

//Is in control of positioning our game entities
//The view will query the board in order to draw
//our user interface
public class Board {
	public static final int cardSpacing = 15;
	private ArrayList<CardGraphic> boardEntities;
	private ArrayList<CardGraphic> slotEntities;
	
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
	}
	
	public BoardPosition[] getStartPositions() {
		return slotStartPositions;
	}
	
	//we could possibly have cards overlapping eachother
	//where we clicked so we need to return an array
	public ArrayList<Card> checkForCardCollisions(int x, int y) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int i = 0; i < boardEntities.size(); i++) {
			if(boardEntities.get(i).contains(x, y)) {
				Card c = boardEntities.get(i).getCard();
				cards.add(c);
			}
		}
		return cards;
	}
	
	public void checkForSlotCollisions(int x, int y) {
		for(int i = 0; i < slotEntities.size(); i++) {
			if(slotEntities.get(i).contains(x, y)) {
				System.out.println("Slot");
			}
		}
	}
	
	//this is really just here for the view to figure out where to draw the initial slots.
	private void generateSlotEntities(ArrayList<Slot> slots) {
		for(int i = 0; i < slots.size(); i++) {
			int x = slotStartPositions[i].getX();
			int y = slotStartPositions[i].getY();
			slotEntities.add(new CardGraphic(x,y));
		}
	}
	
	//Creates a bunch of card entity objects 
	private void generateCardEntities(ArrayList<Slot> slots) {
		for(int i = 0; i < slots.size(); i++) {
			int currentX = slotStartPositions[i].getX();
			int currentY = slotStartPositions[i].getY();
			
			for(int j = 0; j < slots.get(i).size(); j++) {
				Card next = slots.get(i).getCards().get(j);
				
				//only apply the spacing on the cards after the first.
				if(j!=0) currentY += cardSpacing;
				
				CardGraphic entity = new CardGraphic(currentX, currentY, next);	
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
		generateCardEntities(slots);
	}
	
	public ArrayList<CardGraphic> getSlotEntities(){
		return slotEntities;
	}
	
	public ArrayList<CardGraphic> getCardEntities(){
		return boardEntities;
	}
	
	public void printBoardPositions() {
		for(int i = 0; i < slotStartPositions.length; i++) {
			System.out.println(slotStartPositions[i]);
		}
	}
	
	public void printCardGraphics() {
		for(int i = 0; i < boardEntities.size(); i++) {
			System.out.println(boardEntities.get(i));
		}
	}
	
}
