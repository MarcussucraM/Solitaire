package controller;
import main.*;
import user_interface.*;

public class SolitaireCommandLineController {
	public Solitaire game;
	public GameView view;
	//public Basic_Frame view;
	
	
	public SolitaireCommandLineController() {
		this.game = new Solitaire();
		//this.view = new Basic_Frame(game);
		this.view = new GameView(game);
	}
	
	
	public void playGame() {
		while(true) {
			try {
				
				//view functions
				view.printSlots();
				Slot a = view.selectFirstSlot();
				if(a.size() == 0) {
					view.printErrorMsg("Card could not be selected");
					continue;
				}
				
				Slot b = view.selectSecondSlot();
				//----
	
				if(!game.moveCard(a,b)) {
					view.printErrorMsg("Card could not be moved");
				}
				
			}
			catch(IndexOutOfBoundsException e) {
				view.printErrorMsg("Slot index out of bounds error");
			}
			catch(NumberFormatException e) {
				view.printErrorMsg("Expected int");
			}
		}
	}
	
}
