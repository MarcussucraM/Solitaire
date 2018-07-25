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
	private final BoardPosition[] slotStartPositions = { new BoardPosition(30,60) , new BoardPosition(30, 425),
												new BoardPosition(160, 60), new BoardPosition(160, 425),
												new BoardPosition(290,60), new BoardPosition(290,425),
												new BoardPosition(420,60),new BoardPosition(420,425),
												new BoardPosition(550,60), new BoardPosition(550,425),
												new BoardPosition(680,60), new BoardPosition(680, 425),
												new BoardPosition(810,60),
												new BoardPosition(940,60), new BoardPosition(940,425),
												new BoardPosition(1070,60), new BoardPosition(1070,425)};
	
	public Board(ArrayList<Slot> slots) {
		boardEntities = new ArrayList<CardGraphic>();
		slotEntities = new ArrayList<CardGraphic>();
		selectedCards = new ArrayList<CardGraphic>();
		
		generateCardEntities(slots);
		generateSlotEntities(slots);
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
	
	//this is really just here for the view to figure out where to draw the initial slots
	//it also gives it an id to associate it with a slot
	//fills the slotEntities array
	//called by the Solitaire class 
	private void generateSlotEntities(ArrayList<Slot> slots) {
		for(int i = 0; i < slots.size(); i++) {
			int x = slotStartPositions[i].getX();
			int y = slotStartPositions[i].getY();
			slotEntities.add(new CardGraphic(x,y,i));
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
	
	//Called by view to initialize it's its array of slot cardgraphics
	//to be drawn to the screen.
	public ArrayList<CardGraphic> getSlotEntities(){
		return slotEntities;
	}
	
	//Called by view to initialize it's its array of card cardgraphics
	//to be drawn to the screen.
	public ArrayList<CardGraphic> getCardEntities(){
		return boardEntities;
	}
}
