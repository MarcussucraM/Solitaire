package user_interface;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import model.*;

//Our User Interface
//DrawPanel is in charge of drawing cardGraphics to screen
public class SolitaireFrame extends JFrame {
	private final int frame_height = 900;
	private final int frame_width = 1210;
	
	private DrawPanel canvas;
	private StatsPanel statsbar;

	private ArrayList<CardGraphic> cards;
	private ArrayList<CardGraphic> slots;
	private Solitaire game;

	public SolitaireFrame() {
		super.setTitle("Solitaire");
		super.setVisible(true);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setPreferredSize(new Dimension(frame_width,frame_height));
		super.setJMenuBar(new GameMenu());
		canvas = new DrawPanel();
		super.add(canvas, BorderLayout.CENTER);		
		statsbar = new StatsPanel();
		super.add(statsbar, BorderLayout.SOUTH);
		super.pack();
	}	

	//called when user presses the new game button or ctrl n on the keyboard
	//it initializes the game and grabs cardgraphics to draw to screen 
	//also it starts up our statsbar to start tracking time and moves made.
	void start() {
		game = new Solitaire();
		updateScreen();	
		statsbar.setTimer(true);
		statsbar.reset();
	}

	//called when user presses the undo move button or ctrl <-
	void undoMove() {
		if(game != null) {
			game.undoMove();
			updateScreen();
			statsbar.decrementMoveCount();
		}
	}

	//exit out of the application
	//called when quit button or ctrl x pressed
	void exit() {
		this.dispose();
	}

	//get an updated state of the game
	//usually called after a user event occured
	public void updateScreen() {
		game.updateBoard();
		cards = game.getCardGraphics();
		slots = game.getSlotGraphics();
		canvas.repaint();
	}

	// In charge of drawing everything in our game
	class DrawPanel extends JPanel {
		
		public DrawPanel() {
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					//if we have no instance of the game - there's
					//no point in going further.
					if(game == null) return;
					
					
					//was the click on a card on top of a slot?
					if (game.selectCard(e.getX(), e.getY())) {
						//was it the second selected card - and is it valid for a move?
						if (game.tryToMakeMove()) {
							updateScreen();
							statsbar.incrementMoveCount();
							//was that the game winning move?
							if (game.winCondition()) {
								//stop the timer so we can see what our final time was
								statsbar.setTimer(false);
							}
						}
					//if it wasn't a card we could click was it a slot?
					} else if (game.selectSlot(e.getX(), e.getY())) {
						if (game.tryToMakeMove()) {
							updateScreen();
							statsbar.incrementMoveCount();
							if (game.winCondition()) {
								//stop the timer so we can see what our final time was
								statsbar.setTimer(false);
							}
						}
					//we must have clicked empty space or something not allowed
					//reset any selected cards we have
					} else {
						game.resetSelectedCards();
						updateScreen();
					}

					canvas.repaint();
			}
			});
		}

		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			drawBackGround(g2d);
			
			if(game != null) {
				drawSlots(g2d);
				drawCards(g2d);
				drawWinScreen(g2d);
			}
		}
		
		//helper method to load an image file
		private BufferedImage loadImage(String filename) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return img;
		}

		public void drawBackGround(Graphics2D g) {			
			BufferedImage img = loadImage("background.jpg");
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
		}
		
		public void drawWinScreen(Graphics2D g) {
			if(game != null && game.winCondition()) {
				g.setColor(Color.BLACK);
				g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
				g.setColor(Color.WHITE);
				g.setFont(new Font("TimesRoman", Font.BOLD, 30));
				g.drawString("YOU WIN!!!", this.getX()+this.getWidth()/2-80, this.getY()+this.getHeight()/2);				
			}
		}

		public void drawCards(Graphics2D g) {
			BufferedImage img = loadImage("cardSprites.jpg");

			for (int i = 0; i < cards.size(); i++) {
				//card graphic dimensions
				int x = cards.get(i).posX;
				int y = cards.get(i).posY;
				int width = cards.get(i).width;
				int height = cards.get(i).height;
				int arcWidth = cards.get(i).arc_width;
				int arcHeight = cards.get(i).arc_height;

				//clip out the card we want from the sprite sheet
				int spriteHeight = img.getHeight() / 4;
				int spriteWidth = img.getWidth() / 13;
				// subtract 1 from rank because we use 1-13 for rank and 0-12 for img positions from spritesheet
				int cardRank = cards.get(i).getCard().rank - 1;
				int cardSuit = cards.get(i).getCard().suit;
				int spriteX = cardRank * spriteWidth;
				int spriteY = cardSuit * spriteHeight;

				//draw the actual card
				g.drawImage(img, x, y, x + width, y + height, spriteX, spriteY, spriteX + spriteWidth, spriteY + spriteHeight,
						null);
				
				//draw a bold outline around the cards
				//make them look a little cleaner
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(3));
				g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

				// draw red outlines around cards if they're currently selected
				if (cards.get(i).isHighLighted()) {
					g.setColor(Color.RED);
					g.setStroke(new BasicStroke(5));
					g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
					g.setStroke(new BasicStroke(1));
				}
			}
		}

		public void drawSlots(Graphics2D g) {
			for (int i = 0; i < slots.size(); i++) {
				g.setColor(Color.WHITE);
				int x = slots.get(i).posX;
				int y = slots.get(i).posY;
				int width = slots.get(i).width;
				int height = slots.get(i).height;
				int arcWidth = slots.get(i).arc_width;
				int arcHeight = slots.get(i).arc_height;

				g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

				if (slots.get(i).isHighLighted()) {
					g.setColor(Color.RED);
					g.setStroke(new BasicStroke(5));
					g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
					g.setStroke(new BasicStroke(1));
				}
			}
		}
	}
	
	class GameMenu extends JMenuBar implements ActionListener{
		JMenu menu;
		JMenuItem startMenuItem;
		JMenuItem undoMenuItem;
		JMenuItem exitMenuItem;
		
		public GameMenu() {
			menu = new JMenu("Game");
			startMenuItem = new JMenuItem("New Game", KeyEvent.VK_N);
			startMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
			undoMenuItem = new JMenuItem("Undo Move");
			undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_MASK));
			exitMenuItem = new JMenuItem("Quit");
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
			
			startMenuItem.addActionListener(this);
			undoMenuItem.addActionListener(this);
			exitMenuItem.addActionListener(this);

			menu.add(startMenuItem);
			menu.add(undoMenuItem);
			menu.add(exitMenuItem);

			super.add(menu);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startMenuItem) {
				start();
			}
			if (e.getSource() == undoMenuItem) {
				undoMove();
			}
			if (e.getSource() == exitMenuItem) {
				exit();
			}
		}
	}
}
