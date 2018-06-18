package controller;
import main.*;
import user_interface.*;

public class SolitaireController {
	public Solitaire game;
	//public GameView view;
	public Basic_Frame view;
	
	
	public SolitaireController() {
		this.game = new Solitaire();
		this.view = new Basic_Frame(game);
		//this.view = new GameView(game);
	}
	
	
	/* for command prompt version
	public void playGame() {
		while(true) {
			try {
				
				//view functions
				view.printSlots();
				Slot a = view.selectFirstSlot();
				if(!game.getLogic().canSelect(a)) {
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
	}*/
	
}
