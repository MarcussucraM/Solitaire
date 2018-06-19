package user_interface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.*;


//Our User Interface
//DrawPanel is in charge of drawing cardGraphics to screen
public class Basic_Frame extends JFrame {
	private final int frame_height = 600;
	private final int frame_width = 750;
	private boolean isVisible = true;
	private String name = "Solitaire";
	private DrawPanel canvas;

	private ArrayList<CardGraphic> cards;
	private ArrayList<CardGraphic> slots;
	private Solitaire game;

	public Basic_Frame(Solitaire game) {
		//set up the frame
		super.setTitle(name);
		super.setSize(frame_width, frame_height);
		super.setVisible(isVisible);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		canvas = new DrawPanel();
		super.add(canvas);
		
		//add listeners
		SolitaireMouseListener listener = new SolitaireMouseListener();
		canvas.addMouseListener(listener);

		//get our model and ask for a reference to its cardGraphics
		this.game = game;
		cards = game.getCardGraphics();
		slots = game.getSlotGraphics();
		
		
	}

	//In charge of drawing cardGraphics to screen
	class DrawPanel extends JPanel{
		
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			drawSlots(g2d);
			drawCards(g2d);				
		}

		public void drawCards(Graphics2D g) {
			for (int i = 0; i < cards.size(); i++) {
				g.setColor(Color.BLACK);
				int x = cards.get(i).posX;
				int y = cards.get(i).posY;
				int width = cards.get(i).width;
				int height = cards.get(i).height;
				int arcWidth = cards.get(i).arc_width;
				int arcHeight = cards.get(i).arc_height;

				g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

				g.setColor(Color.WHITE);
				g.drawString(cards.get(i).getCard().toString(), x, y+15);
				g.setColor(Color.RED);
				g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
			}
		}

		public void drawSlots(Graphics2D g) {
			for (int i = 0; i < slots.size(); i++) {
				g.setColor(Color.BLUE);
				int x = slots.get(i).posX;
				int y = slots.get(i).posY;
				int width = slots.get(i).width;
				int height = slots.get(i).height;
				int arcWidth = slots.get(i).arc_width;
				int arcHeight = slots.get(i).arc_height;

				g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
			}
		}
	}
	
	//Checks for mouse events and updates board and tells the view what to do
	class SolitaireMouseListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			//try to move card
			game.selectCard(e.getX(), e.getY());
			
			game.updateBoard();
			cards = game.getCardGraphics();
			canvas.repaint();
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//game.getGameBoard().checkForCardCollisions(e.getX(),e.getY());
			//game.getGameBoard().checkForSlotCollisions(e.getX(),e.getY());
			
		}
		
	}

}
