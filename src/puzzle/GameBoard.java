package puzzle;

import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapterEN;

public class GameBoard implements BoardClickListener{
	
	private static final int BOARD_SIZE = 4;
	private GameElement[] elements = new GameElement[BOARD_SIZE * BOARD_SIZE];
	private int clickedPosition1D;
	private int holePosition1D;
	private GameLogic gameLogic = new GameLogic();
	private int[] tileNrs = new int[16];
	private Move move = new Move();
	private int movesCount = 0;
	
	public void setMoves(int moves) {
		this.movesCount = moves;
	}

	private GameElement gameElement = new GameElement();

	public GameBoard() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public GameElement[] getElements() {
		return elements;
	}
	
	public void setElements(GameElement[] elements) {
		this.elements = elements;
	}
	
	public void setElementsNrs(GameElement[] elements) {
		for(int i = 0; i < elements.length; i++) {
			this.elements[i].setNumber(elements[i].getNumber());
		}
	}

	private void getTileNrs() {
		for(int i = 0; i < elements.length; i++) {
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
		
		createElements();
		int count = 0;
		
		do {
			gameLogic.shuffle(elements);
			getTileNrs();
			count++;
		} while(!gameLogic.isSolvable(tileNrs));
		
		System.out.println("Shufled: " + count + " times.");
		renderer.renderElements(elements);
	}
	
	public void createElements() {
		int index = 0;
		int tileNumber;
		for (int tileY = 3; tileY >= 0; tileY--) {
			for (int tileX = 0; tileX < 4; tileX++) {
				tileNumber = index + 1;
				if (tileNumber < 16) {
					elements[index] = new GameElement(tileX, tileY, tileNumber, XSendAdapterEN.WHITE, XSendAdapterEN.BLUE,
							XSendAdapterEN.LIGHTGRAY);
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
		if(!ButtonBar.timeControl.isStarted()) {
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
		gameLogic.isSolved(tileNrs);		
	}
	
	public void moveElements(int direction) {
		
		Renderer renderer = new Renderer();
		
		if(direction != 0) {
			while(holePosition1D != clickedPosition1D) {
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
