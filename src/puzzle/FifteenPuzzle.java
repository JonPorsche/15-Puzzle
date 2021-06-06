package puzzle;

import jserver.Board;
import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapter;
import jserver.XSendAdapterEN;

public class FifteenPuzzle implements BoardClickListener {

	private XSendAdapterEN xsend = new XSendAdapterEN();
	private int boardSize = 4;
	private Tile[] tiles = new Tile[boardSize * boardSize];
	private int[] holePosition = new int[2];
	private int clickedPosition1D;
	private int holePosition1D;
	private Board board = xsend.getBoard(); // Board manages the symbols and has a Graphic class embedded

	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}

	public void start() {
		setUpBoard();
	}

	private void setUpBoard() {

		xsend.size(boardSize, boardSize);
		xsend.symbolSizes(Tile.TILE_SIZE);
		xsend.forms(Tile.FORM);

		createTiles();
		positionTilesOnBoard(tiles);
		board.addClickListener(this);
	}

	private Tile[] createTiles() {
		int index = 0;
		int tileNumber;
		for (int tileY = 3; tileY >= 0; tileY--) {
			for (int tileX = 0; tileX < 4; tileX++) {
				tileNumber = index + 1;
				if (tileNumber < 16) {
					tiles[index] = new Tile(tileX, tileY, tileNumber, XSendAdapter.WHITE, XSendAdapterEN.BLUE,
							XSendAdapter.LIGHTGRAY, true);
				} else {
					tiles[index] = new Tile(tileX, tileY, tileNumber, XSendAdapter.LIGHTGRAY, XSendAdapterEN.LIGHTGRAY,
							XSendAdapter.LIGHTGRAY, false);
				}
				index++;
			}
		}
		return tiles;
	}

	private void positionTilesOnBoard(Tile[] tiles) {
		for (int i = 0; i < tiles.length; i++) {
			renderElement(tiles[i]);
		}
	}

	@Override
	public void boardClick(BoardClickEvent click) {

		int clickedX = click.getX();
		int clickedY = click.getY();

		// Get hole position. In other words, get (x, y) coords from tile number 16.
		getHole2DCoords(16);
		int holeX = holePosition[0];
		int holeY = holePosition[1];

		// Convert clicked (x, y) coords in 1D position
		clickedPosition1D = convertPositionTo1D(clickedX, clickedY);

		// Convert hole (x, y) coords in 1D position
		holePosition1D = convertPositionTo1D(holeX, holeY);

		// Search direction for multiple tile moves at once
		int direction = searchDirection(clickedX, clickedY, holeX, holeY);

		// Move tiles in the direction
		moveTile(direction, clickedX, clickedY, holeX, holeY);
	}

	private void moveTile(int direction, int clickedX, int clickedY, int holeX, int holeY) {
		if (direction != 0) {
			while (holePosition1D != clickedPosition1D) {
				int newHolePosition1D = holePosition1D + direction;

				// Cache the tile to be moved
				Tile cachedTile = tiles[newHolePosition1D];

				// Cache x,y values from hole and tile
				int[] cachedTile2DCoords = { tiles[newHolePosition1D].getX(), tiles[newHolePosition1D].getY() };
				int[] cachedHole2DCoords = { tiles[holePosition1D].getX(), tiles[holePosition1D].getY() };

				// Switch positions
				tiles[newHolePosition1D] = tiles[holePosition1D];
				tiles[holePosition1D] = cachedTile;

				updateElement2DCoords(tiles[newHolePosition1D], cachedTile2DCoords[0], cachedTile2DCoords[1]);
				updateElement2DCoords(cachedTile, cachedHole2DCoords[0], cachedHole2DCoords[1]);
				renderElement(tiles[newHolePosition1D]);
				renderElement(cachedTile);
				holePosition1D = newHolePosition1D;
			}
		}
	}

	// Update x,y coords from elements (hole or tile)
	private void updateElement2DCoords(Tile tile, int x, int y) {
		tile.setX(x);
		tile.setY(y);
	}

	private void renderElement(Tile tile) {
		xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
		xsend.textColor2(tile.getX(), tile.getY(), tile.getNumberColor());
		xsend.color2(tile.getX(), tile.getY(), tile.getForegroundColor());
		xsend.background2(tile.getX(), tile.getY(), tile.getBackgroundColor());
	}

	private int searchDirection(int clickedX, int clickedY, int holeX, int holeY) {

		int direction = 0;

		if (clickedX == holeX) { // Same x?
			System.out.print("Same x and ");
			if (clickedY - holeY > 0) { // Is the hole above or under the clicked tile
				return direction = -4; // Is under -> Hole moves back 4 positions
			} else {
				return direction = 4; // Is above -> Hole moves forward 4 positions
			}
		} else if (clickedY == holeY) { // Same y?
			System.out.print("Same y and ");
			if (clickedX - holeX > 0) { // Is the hole before or after the clicked tile?
				return direction = 1; // Is before -> Hole moves forward 1 position
			} else {
				return direction = -1; // Is after -> Hole moves back 1 position
			}
		}
		return direction;
	}

	private int convertPositionTo1D(int x, int y) {
		int position1D = x - 4 * y + 12;
		return position1D;
	}

	private int[] getHole2DCoords(int tileNr) {

		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i].getNumber() == tileNr) {
				holePosition[0] = tiles[i].getX();
				holePosition[1] = tiles[i].getY();
			}
		}
		return holePosition;
	}
}
