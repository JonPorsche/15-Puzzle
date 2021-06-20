package puzzle;

import java.awt.Font;
import javax.swing.*;

public class InfoPanel {

	public static JLabel timeLabel = new JLabel("Timer: 00:00");
	public static JLabel movesLabel = new JLabel("Moves: 0");
	
	public void setUpInfoPanel() {
		timeLabel.setBounds(500, 20, 100, 20);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		timeLabel.setOpaque(true);
		
		movesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		FifteenPuzzle.GRAPHIC.addEastComponent(timeLabel);
		FifteenPuzzle.GRAPHIC.addEastComponent(movesLabel);
	}
	

}
