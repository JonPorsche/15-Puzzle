package puzzle;

public class FifteenPuzzle{

	private GameBoard gameBoard = new GameBoard();


	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}

	public void start() {
		gameBoard.setUpGameBoard();
	}

}
