package user_interface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.*;


//Our User Interface
//DrawPanel is in charge of drawing cardGraphics to screen
public class SolitaireFrame extends JFrame {
	private final int frame_height = 600;
	private final int frame_width = 750;
	private boolean isVisible = true;
	private String name = "Solitaire";
	private DrawPanel canvas;

	private ArrayList<CardGraphic> cards;
	private ArrayList<CardGraphic> slots;
	private Solitaire game;

	public SolitaireFrame(Solitaire game) {
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
			drawBackGround(g2d);
			drawSlots(g2d);
			drawCards(g2d);				
		}
		
		public void drawBackGround(Graphics2D g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		public void drawCards(Graphics2D g) {
			for (int i = 0; i < cards.size(); i++) {
				g.setColor(Color.BLUE);
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
				
				if(cards.get(i).isHighLighted()) {
					g.setColor(Color.RED);
					g.setStroke(new BasicStroke(5));
					g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
					g.setStroke(new BasicStroke(1));
				}
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

				g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
				
				if(slots.get(i).isHighLighted()) {
					g.setColor(Color.RED);
					g.setStroke(new BasicStroke(5));
					g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
					g.setStroke(new BasicStroke(1));
				}
			}
		}
	}
	
	//Checks for mouse events and updates board and tells the view what to do
	class SolitaireMouseListener implements MouseListener, MouseMotionListener{

		private void update() {
			game.updateBoard();
			cards = game.getCardGraphics();
			slots = game.getSlotGraphics();
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			//try to move card
			
			//if we are successful in selecting a card we try to make a move
			//and if that move is successful we grab an updated state from the model
			//and repaint
			if(game.selectCard(e.getX(), e.getY())) {
				if(game.tryToMakeMove()) {
					update();
				}
				//update();
			}
			else if(game.selectSlot(e.getX(), e.getY())) {
				if(game.tryToMakeMove()) {
					update();
				}
				//update();
			}
			else {
				game.resetSelectedCards();
				update();
			}
			//then 
			//game.moveCard()
			
			
			//update will reset the state of all cardgraphics
			//game.updateBoard();
			//cards = game.getCardGraphics();
			
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
