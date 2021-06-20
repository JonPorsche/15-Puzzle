package puzzle;

import jserver.Board;
import jserver.XSendAdapterEN;
import plotter.Graphic;

public class FifteenPuzzle {

	private GameBoard gameBoard = new GameBoard();
	private ButtonBar buttonBar = new ButtonBar();
	private InfoPanel infoPanel = new InfoPanel();
	public static final XSendAdapterEN XSEND = new XSendAdapterEN();
	public static final Board BOARD = XSEND.getBoard();
	public static final Graphic GRAPHIC = BOARD.getGraphic();

	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}

	public void start() {
		BOARD.setSize(590, 500);
		GRAPHIC.removeAllMenus();
		gameBoard.setUpGameBoard();
		buttonBar.setUpButtonBar();
		infoPanel.setUpInfoPanel();
		XSEND.statusText("Order the tiles from 1 to 15.");
		XSEND.border(XSendAdapterEN.LIGHTGRAY);

	}

}
