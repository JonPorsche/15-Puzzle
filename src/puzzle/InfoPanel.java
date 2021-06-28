package puzzle;

import java.awt.Font;
import javax.swing.*;

public class InfoPanel {

	public static JLabel timeLabel = new JLabel("Time: 00:00");
	public static JLabel movesLabel = new JLabel("Moves: 0");
	private static final String BEST_TIME_LABEL_TITLE = "Best time: ";
	public static String bestTime = "--:--";
	public static JLabel bestTimeLabel = new JLabel(BEST_TIME_LABEL_TITLE + bestTime);
	private static TimeControl timeControl = new TimeControl();

	public InfoPanel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setBestTime(String record) {
		InfoPanel.bestTime = record;
	}

	public void setUpInfoPanel() {

		setupTimeLabel();
		setupMovesLabel();
		setupBestTimeLabel();
	}

	private void setupTimeLabel() {
		timeLabel.setBounds(500, 20, 100, 20);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		timeLabel.setOpaque(true);
		FifteenPuzzle.GRAPHIC.addEastComponent(timeLabel);
	}

	private void setupMovesLabel() {
		movesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		FifteenPuzzle.GRAPHIC.addEastComponent(movesLabel);
	}

	private void setupBestTimeLabel() {
		ReadJSON readJson = new ReadJSON();
		int recordedTime = readJson.readResult();

		if (recordedTime != 0) {
			updateBestTimeLabel(recordedTime);
		}
		bestTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		FifteenPuzzle.GRAPHIC.addEastComponent(bestTimeLabel);
	}

	public static void updateBestTimeLabel(int time) {
		String timeString = TimeControl.formatTime(time);
		bestTimeLabel.setText(BEST_TIME_LABEL_TITLE + timeString);

	}

	/*
	 * public static void resetInfoPanel(int newTime) {
	 * timeLabel.setText("Time: 00:00"); movesLabel.setText("Moves: 0");
	 * updateBestTimeLabel(newTime); }
	 */

	public static void resetInfoPanel() {
		timeLabel.setText("Time: 00:00");
		movesLabel.setText("Moves: 0");
	}

}
