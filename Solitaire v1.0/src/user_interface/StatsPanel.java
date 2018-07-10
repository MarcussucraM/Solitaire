package user_interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

//class that deals with the stats of the game
//holds the state of the stats
//and also displays them
class StatsPanel extends JPanel implements ActionListener{
	JLabel movecountText;
	JLabel timerText;
	Timer timer;
	int currentTime;
	int moveCount;
	
	public StatsPanel() {
		movecountText = new JLabel("Moves Made: 0");
		super.add(movecountText,BorderLayout.CENTER);
		
		timerText = new JLabel("Time Played: 0");
		super.add(timerText);
		
		int currentTime = 0;
		int moveCount = 0;
		
		timer = new Timer(1000, this);
	}
	
	//Turns the timer on or off -
	//it just tracks how long you've been playing
	//false is off true is on
	public void setTimer(boolean state) {
		if(state == true && !timer.isRunning()) {
			timer.start();
		}
		if(state == false && timer.isRunning()) {
			timer.stop();
		}
	}
	
	//resets state back to 0 and updates textfields accordingly
	//called whenever a new game has been started
	public void reset() {
		currentTime = 0;
		moveCount = 0;
		
		movecountText.setText("Moves Made: " + moveCount);
		timerText.setText("Time Played: " + currentTime);	
	}
	
	//for results screen 
	public int getCurrentTime() {
		return currentTime;
	}
	
	//for results screen
	public int getMoveCount() {
		return moveCount;
	}
	
	public void incrementMoveCount() {
		movecountText.setText("Moves Made: " + ++moveCount);
	}
	
	public void decrementMoveCount() {
		if(moveCount > 0)
		movecountText.setText("Moves Made: " + --moveCount);
	}
	
	//activates whenever the timer goes off
	//updates how long we've been playing
	public void actionPerformed(ActionEvent e) {
		timerText.setText("Time Played: " + ++currentTime);		
	}
}
