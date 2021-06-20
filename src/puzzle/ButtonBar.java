package puzzle;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

public class ButtonBar {

	private JButton btnPause = new JButton("Pause");
	private JButton btnStart = new JButton("Start");
	private Timer timer;
	private int seconds;
	private int minutes;

	public void setUpButtonBar() {
		FifteenPuzzle.GRAPHIC.addSouthComponent(btnPause);
		FifteenPuzzle.GRAPHIC.addSouthComponent(btnStart);
		addBtnPause();
		addBtnStart();
	}

	private void addBtnPause() {
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					timer.stop();
				} catch(Exception f) {
					System.out.println("Error: couldn't stop the timer.");
				}
			}
		});
	}

	public void addBtnStart() {

		InfoPanel.timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						seconds++;
						if (seconds < 10)
							InfoPanel.timeLabel.setText("Timer: 00:0" + String.valueOf(seconds));
						else
							InfoPanel.timeLabel.setText("Timer: 00:" + String.valueOf(seconds));
					}
				});
				timer.start();
			}
		});
	}
}