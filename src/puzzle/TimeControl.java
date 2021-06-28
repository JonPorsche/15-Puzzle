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
			elapsedTime += 1000;
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

	public int getElapsedTime() {
		return elapsedTime;
	}

	public String getSeconds_string() {
		return seconds_string;
	}

	public String getMinutes_string() {
		return minutes_string;
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
		System.out.println("Elapsed time = " + elapsedTime);
	}

	public void reset() {
		timer.stop();
		elapsedTime = 0;
		seconds = 0;
		minutes = 0;
		seconds_string = String.format("%02d", seconds);
		minutes_string = String.format("%02d", minutes);
		gameBoard.setUpGameBoard();
	}

	public static String formatTime(int time) {
		int toSeconds = (time / 1000) % 60;
		int toMinutes = (time / 60000) % 60;
		String toSeconds_string = String.format("%02d", toSeconds);
		String toMinutes_string = String.format("%02d", toMinutes);
		String formatedTime = toMinutes_string + ":" + toSeconds_string;
		return formatedTime;
	}

}
