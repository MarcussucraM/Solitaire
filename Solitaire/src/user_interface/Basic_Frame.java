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
public class Basic_Frame extends JFrame implements Runnable {
	static int frame_title_height = 25;
	private final int frame_height = 600;
	private final int frame_width = 750;
	private boolean isVisible = true;
	private String name = "Test";
	private DrawPanel canvas;

	private ArrayList<CardGraphic> cards;
	private ArrayList<CardGraphic> slots;
	private Solitaire game;

	public Basic_Frame(Solitaire game) {
		super.setTitle(name);
		super.setSize(frame_width, frame_height);
		super.setVisible(isVisible);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game = game;
		
		SolitaireMouseListener listener = new SolitaireMouseListener();
		super.addMouseListener(listener);
		super.addMouseMotionListener(listener);
		
		canvas = new DrawPanel();
		super.add(canvas);

		cards = game.getCardGraphics();
		slots = game.getSlotGraphics();
		
	}

	//draws our card graphics to the screen
	/*public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		
		
		drawSlots(g2d);
		drawCards(g2d);		
		//repaint();
			
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
	}*/

	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class DrawPanel extends JPanel{
		//draws our card graphics to the screen
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			
			
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
	
	class SolitaireMouseListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			//game.getGameBoard().checkForCardCollisions(e.getX(),e.getY()-frame_title_height);
			game.getGameBoard().checkForSlotCollisions(e.getX(),e.getY()-frame_title_height);
			//check if that point collided with any of our (top cards) or an empty stack
			//System.out.println(e.getX()+ " "+ (e.getY()-frame_title_height));
			
			//try to move card
			game.selectCard(e.getX(), e.getY()-frame_title_height);
			
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
