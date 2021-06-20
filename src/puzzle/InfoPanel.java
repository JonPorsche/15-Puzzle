package puzzle;

import javax.swing.JLabel;

public class InfoPanel {

	public static JLabel timeLabel = new JLabel("Timer: 00:00");	
	private JLabel movesLabel = new JLabel("Moves: ");
	
	public void setUpInfoPanel() {
		FifteenPuzzle.GRAPHIC.addEastComponent(timeLabel);
		FifteenPuzzle.GRAPHIC.addEastComponent(movesLabel);
	}
	

}
