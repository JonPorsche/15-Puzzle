package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jserver.Board;
import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapter;
import jserver.XSendAdapterEN;

public class FifteenPuzzle implements BoardClickListener {

	private XSendAdapterEN xsend = new XSendAdapterEN();
	private int boardSize = 4;
	private GameElement[] elements = new GameElement[boardSize * boardSize];
	private int[] element2DCoords = new int[2];
	private int clickedPosition1D;
	private int holePosition1D;
	private Board board = xsend.getBoard();
	private static final Random RANDOM = new Random();
	protected static int[][] coords = {
			{0,3}, {1,3}, {2,3}, {3,3}, 
			{0,2}, {1,2}, {2,2}, {3,2}, 
			{0,1}, {1,1}, {2,1}, {3,1}, 
			{0,0}, {1,0}, {2,0}, {3,0}  
			};

	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}

	public void start() {
		setUpBoard();
	}

	private void setUpBoard() {
		xsend.size(boardSize, boardSize);
		xsend.symbolSizes(GameElement.TILE_SIZE);
		xsend.forms(GameElement.FORM);
		board.addClickListener(this);
		createElements();
		shuffle2();
		renderElements();
		//System.out.println("Hole position = "+Arrays.toString(getElement2DCoords(16)));
	}

	private void createElements() {
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

	// don't include the hole in the shuffle, leave in the position 16
	private void shuffle() {
		Random rand = new Random();

		for (int i = 0; i < elements.length; i++) {
			int randomIndexToSwap = rand.nextInt(elements.length);
			GameElement temp = elements[randomIndexToSwap];
			elements[randomIndexToSwap] = elements[i];
			elements[i] = temp;
		}
		//System.out.println(Arrays.toString(elements));
	}

	private void shuffle2() {
		int i = 0;
		int[] col = shuffleArray1D(4);

		// For each x(column) assign a y(line)
		for (int x = 0; x < 4; x++) {
			int[] line = shuffleArray1D(4);			

			//System.out.print("[");
			for(int y = 0; y < 4; y++) {
				//System.out.print("("+col[x]+", "+line[y]+")");
				elements[i].setX(col[x]);
				elements[i].setY(line[y]);
				renderElement(elements[i]);
				i++;
			}
			//System.out.println("]");
		}
		//System.out.println("Elments after shuffle = "+Arrays.toString(elements));
	}
	
	private List<Integer> shuffleCol1D(int maxElements) {
		List<Integer> col = new ArrayList<> ();
		
		// Populate array
		for (int i = 1; i <= maxElements; i++) col.add(i);
		Collections.shuffle(col);
		System.out.println(col);
		return col;

	}
	
	private void shuffle2D(int[][] coords) {
	    Random random = new Random();

	    for (int i = coords.length - 1; i > 0; i--) {
	        for (int j = coords[i].length - 1; j > 0; j--) {
	            int m = random.nextInt(i + 1);
	            int n = random.nextInt(j + 1);

	            int temp = coords[i][j];
	            coords[i][j] = coords[m][n];
	            coords[m][n] = temp;
	        }
	    }
	    System.out.println(Arrays.deepToString(coords));
	}
	
	private int[] shuffleArray1D(int maxElements) {
		Random randomGen = new Random();
		int[] shuffledArray = new int[maxElements];

		// Initialize Array
		for (int i = 0; i < shuffledArray.length; i++) {
			shuffledArray[i] = i;
		}

		// Shuffle by exchanging each element randomly
		for (int i = 0; i < shuffledArray.length; i++) {
			int randomPos = randomGen.nextInt(shuffledArray.length);
			int temp = shuffledArray[i];
			shuffledArray[i] = shuffledArray[randomPos];
			shuffledArray[randomPos] = temp;
		}
		// System.out.println(Arrays.toString(shuffledArray));
		return shuffledArray;
	}

	private void shuffleArray2D(int maxElements) {
		Random randomGen = new Random();
		int[][] shuffledArray = new int[maxElements][maxElements];
		System.out.println(shuffledArray.length);
		// Initialize Array
		/*
		 * for(int i = 0; i < shuffledArray.length; i++) { shuffledArray[i][i] = i; }
		 */

		// Shuffle by exchanging each element randomly
		/*
		 * for (int i=0; i<shuffledArray.length; i++) { int randomPos =
		 * randomGen.nextInt(shuffledArray.length); int temp = shuffledArray[i];
		 * shuffledArray[i] = shuffledArray[randomPos]; shuffledArray[randomPos] = temp;
		 * System.out.print(shuffledArray[i][i]+", "); }
		 */
	}

	private int shuffleCoord() {
		int randomCoord = (int) (Math.random() * 4);
		System.out.println(randomCoord);
		return randomCoord;
	}

	private void renderElements() {
		for (int i = 0; i < elements.length; i++) {
			renderElement(elements[i]);
		}
	}

	@Override
	public void boardClick(BoardClickEvent click) {
		
		System.out.println("\nboardClick");
		
		int clickedX = click.getX();
		int clickedY = click.getY();

		// Get hole position. In other words, get (x, y) coords from tile number 16.
		getElement2DCoords(16);
		int holeX = element2DCoords[0];
		int holeY = element2DCoords[1];

		// Convert clicked (x, y) coords in 1D position
		clickedPosition1D = convert2DCoordsTo1D(clickedX, clickedY);
		System.out.println("clicked Position = "+clickedPosition1D);

		// Convert hole (x, y) coords in 1D position
		holePosition1D = convert2DCoordsTo1D(holeX, holeY);
		System.out.println("Hole 1D Position = "+holePosition1D);

		// Search direction for multiple tile moves at once
		int direction = getMoveDirection(clickedX, clickedY, holeX, holeY);
		System.out.println("Move direction = "+direction);

		System.out.println("Elements before move: "+Arrays.toString(elements));
		// Move tiles in the direction
		moveElement(direction, clickedX, clickedY, holeX, holeY);
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

	private void moveElement(int direction, int clickedX, int clickedY, int holeX, int holeY) {
		
		System.out.println("\nmoveElement");
		
		if (direction != 0) {
			while (holePosition1D != clickedPosition1D) {
				int newHolePosition1D = holePosition1D + direction;
				System.out.println("New hole position 1D = "+newHolePosition1D);

				// Cache the tile to be moved
				GameElement cachedTile = elements[newHolePosition1D];
				
				System.out.println("Cached tile "+cachedTile.getNumber()+" ("+cachedTile.getX()+", "+cachedTile.getY()+")");

				// Cache x,y values from hole and tile
				int[] cachedTile2DCoords = { elements[newHolePosition1D].getX(), elements[newHolePosition1D].getY() };
				int[] cachedHole2DCoords = { elements[holePosition1D].getX(), elements[holePosition1D].getY() };

				// Switch positions
				elements[newHolePosition1D] = elements[holePosition1D];
				elements[holePosition1D] = cachedTile;

				updateElement2DCoords(elements[newHolePosition1D], cachedTile2DCoords[0], cachedTile2DCoords[1]);
				updateElement2DCoords(cachedTile, cachedHole2DCoords[0], cachedHole2DCoords[1]);
				renderElement(elements[newHolePosition1D]);
				renderElement(cachedTile);
				holePosition1D = newHolePosition1D;
			}
		}
		System.out.println("Elements after move: "+Arrays.toString(elements));
	}

	// Update x,y coords from elements (hole or tile)
	private void updateElement2DCoords(GameElement tile, int x, int y) {
		tile.setX(x);
		tile.setY(y);
	}

	private void renderElement(GameElement tile) {
		xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
		xsend.textColor2(tile.getX(), tile.getY(), tile.getNumberColor());
		xsend.color2(tile.getX(), tile.getY(), tile.getForegroundColor());
		xsend.background2(tile.getX(), tile.getY(), tile.getBackgroundColor());
	}

	private int[] getElement2DCoords(int tileNr) {
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].getNumber() == tileNr) {
				element2DCoords[0] = elements[i].getX();
				element2DCoords[1] = elements[i].getY();
			}
		}
		return element2DCoords;
	}

	private int convert2DCoordsTo1D(int x, int y) {
		int position1D = x - 4 * y + 12;
		return position1D;
	}
}
