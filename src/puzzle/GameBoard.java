package puzzle;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapterEN;

public class GameBoard implements BoardClickListener {

	private static final int BOARD_SIZE = 4;
	private GameElement[] elements = new GameElement[BOARD_SIZE * BOARD_SIZE];
	private int clickedPosition1D;
	private int holePosition1D;
	private GameLogic gameLogic = new GameLogic();
	WriteJSON write = new WriteJSON();
	ReadJSON read = new ReadJSON();
	private int[] tileNrs = new int[16];
	private Move move = new Move();
	private int movesCount = 0;
	private GameElement gameElement = new GameElement();
	private ButtonBar buttonBar = new ButtonBar();

	public GameBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setMovesCount(int movesCount) {
		this.movesCount = movesCount;
	}

	public GameElement[] getElements() {
		return elements;
	}

	public void setElements(GameElement[] elements) {
		this.elements = elements;
	}

	public void setElementsNrs(GameElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			this.elements[i].setNumber(elements[i].getNumber());
		}
	}

	private void getTileNrs() {
		for (int i = 0; i < elements.length; i++) {
			tileNrs[i] = elements[i].getNumber();
		}
	}

	public int getHolePosition1D() {
		return holePosition1D;
	}

	public int getClickedPosition1D() {
		return clickedPosition1D;
	}

	public void setUpGameBoard() {
		Renderer renderer = new Renderer();
		FifteenPuzzle.BOARD.addClickListener(this);
		movesCount = 0;
		createElements();

		int count = 0;

		do {
			gameLogic.shuffle(elements);
			getTileNrs();
			count++;
		} while (!gameLogic.isSolvable(tileNrs));

//		System.out.println("Shufled: " + count + " times.");

//		gameLogic.easyModus(elements);
		renderer.renderElements(elements);
	}

	public void createElements() {
		int index = 0;
		int tileNumber;
		for (int tileY = 3; tileY >= 0; tileY--) {
			for (int tileX = 0; tileX < 4; tileX++) {
				tileNumber = index + 1;
				if (tileNumber < 16) {
					elements[index] = new GameElement(tileX, tileY, tileNumber, XSendAdapterEN.WHITE,
							XSendAdapterEN.BLUE, XSendAdapterEN.LIGHTGRAY);
				} else {
					elements[index] = new GameElement(tileX, tileY, tileNumber, XSendAdapterEN.LIGHTGRAY,
							XSendAdapterEN.LIGHTGRAY, XSendAdapterEN.LIGHTGRAY);
				}
				index++;
			}
		}
	}

	@Override
	public void boardClick(BoardClickEvent click) {

		// Start the timer when the user clicks on the tiles
		if (!ButtonBar.timeControl.isStarted()) {
			ButtonBar.timeControl.setStarted(true);
			ButtonBar.timeControl.start();
			ButtonBar.btnStart.setText("Stop");
		}

		int clickedX = click.getX();
		int clickedY = click.getY();

		// Get hole position. In other words, get (x, y) coords from tile number 16.
		int[] hole = gameElement.getElement2DCoords(elements, 16);
		int holeX = hole[0];
		int holeY = hole[1];

		// Convert clicked (x, y) coords in 1D position
		clickedPosition1D = gameElement.convert2DCoordsTo1D(clickedX, clickedY);

		// Convert hole (x, y) coords in 1D position
		holePosition1D = gameElement.convert2DCoordsTo1D(holeX, holeY);

		// Search direction for multiple tile moves at once
		int direction = move.getMoveDirection(clickedX, clickedY, holeX, holeY);

		// Move tiles in the direction
		moveElements(direction);

		getTileNrs();

		if (gameLogic.isSolved(tileNrs)) {

			System.out.println("\nPuzzle is solved");

			buttonBar.resetBtns();
			ButtonBar.btnStart.setEnabled(false);

			int newTime = ButtonBar.timeControl.getElapsedTime();
			System.out.println("Elapsed time = " + newTime);

			/*
			 * If the file doesn't has time records or the new time is better than the saved
			 * time record: 1. Write the new time in the file 2. Update the best time label
			 * with new time
			 */
			
			boolean newBestTime = gameLogic.isBestTime(newTime);
			if (newBestTime) {
				JOptionPane.showMessageDialog(null, "Puzzle solved with new best time!", "Result",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else JOptionPane.showMessageDialog(null, "Puzzle solved!", "Result", JOptionPane.INFORMATION_MESSAGE);

			if (read.readResult() == 0 || gameLogic.isBestTime(newTime)) {
				System.out.println("Entered if");
				System.out.println("Is best time? " + newBestTime);
				write.writeResult(newTime);
				InfoPanel.updateBestTimeLabel(newTime);
			}

		}
	}

	public void moveElements(int direction) {

		Renderer renderer = new Renderer();

		if (direction != 0) {
			while (holePosition1D != clickedPosition1D) {
				int newHolePosition1D = holePosition1D + direction;
				int cachedTileNr = elements[newHolePosition1D].getNumber();
				elements[newHolePosition1D].setNumber(16);
				elements[holePosition1D].setNumber(cachedTileNr);
				renderer.renderElement(elements[newHolePosition1D]);
				renderer.renderElement(elements[holePosition1D]);
				holePosition1D = newHolePosition1D;
			}
			movesCount++;
			InfoPanel.movesLabel.setText("Moves: " + String.valueOf(movesCount));
		}
	}
}
