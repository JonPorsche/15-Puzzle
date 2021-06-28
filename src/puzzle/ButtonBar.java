package puzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ButtonBar {

	public JButton btnNewGame = new JButton("New Game");
	public static JButton btnStart = new JButton("Start");
	public static TimeControl timeControl = new TimeControl();

	public ButtonBar() {
		super();
	}

	public void setUpButtonBar() {
		FifteenPuzzle.GRAPHIC.addSouthComponent(btnNewGame);
		FifteenPuzzle.GRAPHIC.addSouthComponent(btnStart);
		addBtnNewGame();
		addBtnStart();
	}

	private void addBtnNewGame() {
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("New game pressed.");
				timeControl.setStarted(false);
				btnStart.setText("Start");
				timeControl.reset();

			}
		});
	}

	public void addBtnStart() {
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!timeControl.isStarted()) {
					timeControl.setStarted(true);
					btnStart.setText("Stop");
					timeControl.start();
					InfoPanel.resetInfoPanel(timeControl.getElapsedTime());
				}
				else{
					timeControl.setStarted(false);
					btnStart.setText("Start");
					timeControl.stop();
				}
			}
		});
	}

	public void resetBtns() {
		timeControl.setStarted(false);
		btnStart.setText("Start");
		timeControl.stop();
	}
}