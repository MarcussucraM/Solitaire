package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Slot;
import model.Solitaire;

class TestSolitaire {
	
	@Test
	void test_getFoundationSlots() {
		Solitaire s = new Solitaire();
		ArrayList<Slot> f_slots = s.getFoundationSlots();
		
		assertEquals(f_slots.size() == 4, true);
		for(int i = 0; i < f_slots.size(); i++) {
			assertEquals(f_slots.get(i).getType().equals("f"), true);
		}
	}

}
