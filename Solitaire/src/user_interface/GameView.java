package user_interface;
import java.util.ArrayList;
import java.util.Scanner;

import main.*;

public class GameView {
	public Solitaire game;
	public static Scanner in = new Scanner(System.in);
	
	public GameView(Solitaire game) {
		this.game = game;
	}
	
	public void printErrorMsg(String msg) {
		System.out.println(msg);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printSlots() {
		ArrayList<Slot> slots = game.getAllSlots();
		for(int i = 0; i < slots.size(); i++) {
			Slot cur = slots.get(i);
			
			System.out.print("Slot [" + i + "] ");
			if(cur.size() != 0) {
				System.out.println(slots.get(i));
			}
			else {
				System.out.println();
			}
		}
	}
	
	public Slot selectFirstSlot() throws NumberFormatException, IndexOutOfBoundsException {
		System.out.println("Select a slot to move a card from");
		String selection = in.nextLine();
		int index = Integer.parseInt(selection);
		return game.getAllSlots().get(index);
	}
	
	public Slot selectSecondSlot() throws NumberFormatException, IndexOutOfBoundsException {
		System.out.println("Select a slot to move a card to");
		String selection = in.nextLine();
		int index = Integer.parseInt(selection);
		return game.getAllSlots().get(index);
	}
	
}
