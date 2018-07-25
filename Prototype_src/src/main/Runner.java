package main;

import model.Solitaire;
import user_interface.SolitaireFrame;


//Entry Point for the Application

public class Runner {
	public static void main(String[] args) {
		Solitaire game = new Solitaire();
		SolitaireFrame view = new SolitaireFrame(game);
	}

}
