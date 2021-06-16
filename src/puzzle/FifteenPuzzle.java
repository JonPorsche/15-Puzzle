package puzzle;

import java.util.Arrays;
import java.util.Random;
import jserver.Board;
import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapter;
import jserver.XSendAdapterEN;

public class FifteenPuzzle implements BoardClickListener {

	private XSendAdapterEN xsend = new XSendAdapterEN(); // > GameBoard
	private static int boardSize = 4; // > GameBoard
	public GameElement[] elements = new GameElement[boardSize * boardSize]; // > GameBoard
	private int[] element2DCoords = new int[2]; // > GameElement
	private int clickedPosition1D; // > GameBoard
	private int holePosition1D; // > GameBoard
	private Board board = xsend.getBoard(); // > GameBoard
	private static final Random RANDOM = new Random(); // > GameLogic
	private GameLogic gameLogic = new GameLogic();
	private int[] tileNrs = new int[16];

	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}

	public void start() {
		setUpBoard();
	}

	public GameElement[] getElements() {
		return elements;
	}
	
	private void getTileNrs() {
		for(int i = 0; i < elements.length; i++) {
			System.out.print(elements[i].getNumber() + ", ");
			tileNrs[i] = elements[i].getNumber();
		}
		System.out.println(Arrays.toString(tileNrs));
	}

	// > GameBoard
	private void setUpBoard() {
		xsend.size(boardSize, boardSize);
		xsend.symbolSizes(GameElement.TILE_SIZE);
		xsend.forms(GameElement.FORM);
		board.addClickListener(this);
		createElements();
		shuffle();
		getTileNrs();
		System.out.println(gameLogic.isSolvable(tileNrs));
		renderElements();
	}

	// > GameBoard
	public void createElements() {
		int index = 0;
		int tileNumber;
		for (int tileY = 3; tileY >= 0; tileY--) {
			for (int tileX = 0; tileX < 4; tileX++) {
				tileNumber = index + 1;
				if (tileNumber < 16) {
					elements[index] = new GameElement(tileX, tileY, tileNumber, XSendAdapter.WHITE, XSendAdapterEN.BLUE,
							XSendAdapter.LIGHTGRAY);
				} else {
					elements[index] = new GameElement(tileX, tileY, tileNumber, XSendAdapter.LIGHTGRAY,
							XSendAdapterEN.LIGHTGRAY, XSendAdapter.LIGHTGRAY);
				}
				index++;
			}
		}
	}

	// > GameBoard
	@Override
	public void boardClick(BoardClickEvent click) {

		int clickedX = click.getX();
		int clickedY = click.getY();

		// Get hole position. In other words, get (x, y) coords from tile number 16.
		getElement2DCoords(16);
		int holeX = element2DCoords[0];
		int holeY = element2DCoords[1];

		// Convert clicked (x, y) coords in 1D position
		clickedPosition1D = convert2DCoordsTo1D(clickedX, clickedY);

		// Convert hole (x, y) coords in 1D position
		holePosition1D = convert2DCoordsTo1D(holeX, holeY);

		// Search direction for multiple tile moves at once
		int direction = getMoveDirection(clickedX, clickedY, holeX, holeY);

		// Move tiles in the direction
		moveElements(direction, clickedX, clickedY, holeX, holeY);
	}
	
	public void moveElements(int direction, int clickedX, int clickedY, int holeX, int holeY) {
		if(direction != 0) {
			while(holePosition1D != clickedPosition1D) {
				int newHolePosition1D = holePosition1D + direction;
				int cachedTileNr = elements[newHolePosition1D].getNumber();
				elements[newHolePosition1D].setNumber(16);
				elements[holePosition1D].setNumber(cachedTileNr);
				renderElements();
				holePosition1D = newHolePosition1D;
			}
		}
	}

	private int getMoveDirection(int clickedX, int clickedY, int holeX, int holeY) {
		int direction = 0;
		if (clickedX == holeX) { // Same x?
			if (clickedY - holeY > 0) { // Is the hole above or under the clicked tile
				return direction = -4; // Is under -> Hole moves back 4 positions
			} else {
				return direction = 4; // Is above -> Hole moves forward 4 positions
			}
		} else if (clickedY == holeY) { // Same y?
			if (clickedX - holeX > 0) { // Is the hole before or after the clicked tile?
				return direction = 1; // Is before -> Hole moves forward 1 position
			} else {
				return direction = -1; // Is after -> Hole moves back 1 position
			}
		}
		return direction;
	}

	private void renderElements() {
		for (int i = 0; i < elements.length; i++) {
			renderElement(elements[i]);
		}
	}
	
	private void renderElement(GameElement tile) {
		xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
		if (tile.getNumber() == 16) {
			xsend.textColor2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
			xsend.color2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
			xsend.background2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
		} else {
			xsend.textColor2(tile.getX(), tile.getY(), XSendAdapter.WHITE);
			xsend.color2(tile.getX(), tile.getY(), XSendAdapterEN.BLUE);
			xsend.background2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
		}
	}

	private void shuffle() {
			int[] baseArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
			for (int i = 0; i < baseArray.length-1; i++) {
				int randomIndexToSwap = RANDOM.nextInt(baseArray.length-1);
				int temp = baseArray[randomIndexToSwap];
				baseArray[randomIndexToSwap] = baseArray[i];
				baseArray[i] = temp;
			}
			
			for(int i = 0; i < elements.length; i++) {
				elements[i].setNumber(baseArray[i]);
			}

	}

	// > GameElement
	private void getElement2DCoords(int tileNr) {
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].getNumber() == tileNr) {
				element2DCoords[0] = elements[i].getX();
				element2DCoords[1] = elements[i].getY();
			}
		}
	}

	// > GameElement
	private int convert2DCoordsTo1D(int x, int y) {
		int position1D = x - 4 * y + 12;
		return position1D;
	}
}
