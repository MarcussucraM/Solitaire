package main;

import controller.SolitaireCommandLineController;
import model.Solitaire;
import user_interface.SolitaireFrame;


//Entry Point for the Application
//If you want to use the command line version then uncomment the
//first two lines in the main method
//otherwise use the last two lines
public class Runner {
	public static void main(String[] args) {
		//if(args[0].equals("cmd"))
		//SolitaireCommandLineController a = new SolitaireCommandLineController();
		//a.playGame();
		
		Solitaire game = new Solitaire();
		SolitaireFrame view = new SolitaireFrame(game);
	}

}
