package puzzle;

import java.awt.event.*;
import javax.swing.*;

public class TimeControl {

	private int elapsedTime = 0;
	private int seconds = 0;
	private int minutes = 0;
	private boolean started = false;
	private String seconds_string = String.format("%02d", seconds);
	private String minutes_string = String.format("%02d", minutes);
	private GameBoard gameBoard = new GameBoard();

	
	private Timer timer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			elapsedTime+=1000;
			seconds = (elapsedTime / 1000) % 60;
			minutes = (elapsedTime / 60000) % 60;
			seconds_string = String.format("%02d", seconds);
			minutes_string = String.format("%02d", minutes);
			InfoPanel.timeLabel.setText("Time: " + minutes_string + ":" + seconds_string);
		}
	});
	
	public TimeControl() {
		super();
	}
	
	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void start() {
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}
	
	public void reset() {
		timer.stop();
		elapsedTime = 0;
		seconds = 0;
		minutes = 0;
		seconds_string = String.format("%02d", seconds);
		minutes_string = String.format("%02d", minutes);
		InfoPanel.timeLabel.setText("Time: " + minutes_string + ":" + seconds_string);
		gameBoard.setUpGameBoard();
		gameBoard.setMoves(0);
		InfoPanel.movesLabel.setText("Moves: 0");
	}

}
