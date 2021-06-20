package puzzle;

import java.awt.Font;

import javax.swing.*;

public class InfoPanel {

	public static JLabel timeLabel = new JLabel("Timer: 00:00");	
	private JLabel movesLabel = new JLabel("Moves: ");
	
	public void setUpInfoPanel() {
		timeLabel.setBounds(500, 20, 100, 20);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		timeLabel.setOpaque(true);
		
		FifteenPuzzle.GRAPHIC.addEastComponent(timeLabel);
		FifteenPuzzle.GRAPHIC.addEastComponent(movesLabel);
	}
	

}
