package puzzle;

import jserver.Board;
import jserver.XSendAdapterEN;

public class FifteenPuzzle{

	private GameBoard gameBoard = new GameBoard();
	public static final XSendAdapterEN XSEND = new XSendAdapterEN();
	public static final Board BOARD = XSEND.getBoard();

	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}

	public void start() {
		gameBoard.setUpGameBoard();
	}

}
