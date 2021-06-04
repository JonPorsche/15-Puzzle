package puzzle;

import java.util.Arrays;

import jserver.Board;
import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapter;
import jserver.XSendAdapterEN;
import plotter.Graphic;

public class FifteenPuzzle implements BoardClickListener {

	private XSendAdapterEN xsend = new XSendAdapterEN();
	private int boardSize = 4;
	private Tile[] tiles = new Tile[boardSize * boardSize];
	private int[] holePosition = new int[2];
	private int clickedPosition1D;
	private int holePosition1D;
	private int direction = 0;
	private Board board = xsend.getBoard(); // Board manages the symbols and has a Graphic class embedded
	private Graphic graphic = board.getGraphic(); // Graphic is a simple frame around a
	// Plotter based on JFrame.
	// It creates a main window with a Plotter object and a status line.


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
					tiles[index] = new Tile();
					tiles[index].setX(tileX);
					tiles[index].setY(tileY);
					tiles[index].setNumber(tileNumber);
					tiles[index].setNumberColor(XSendAdapter.WHITE);
					tiles[index].setForegroundColor(XSendAdapterEN.BLUE);
					tiles[index].setBackgroundColor(XSendAdapter.LIGHTGRAY);
					tiles[index].setClickable(true);
				} else {
					tiles[index] = new Tile();
					tiles[index].setX(tileX);
					tiles[index].setY(tileY);
					tiles[index].setNumber(tileNumber);
					tiles[index].setNumberColor(XSendAdapter.LIGHTGRAY);
					tiles[index].setForegroundColor(XSendAdapter.LIGHTGRAY);
					tiles[index].setBackgroundColor(XSendAdapter.LIGHTGRAY);
					tiles[index].setClickable(false);
				}
				index++;
			}
		}
		System.out.println(Arrays.toString(tiles));
		return tiles;
	}

	private void positionTilesOnBoard(Tile[] tiles) {
		System.out.println("1st tile position = " + tiles[0].getX() + ", " + tiles[0].getY());
		for (int i = 0; i < tiles.length; i++) {
			xsend.color2(tiles[i].getX(), tiles[i].getY(), tiles[i].getForegroundColor());
			xsend.background2(tiles[i].getX(), tiles[i].getY(), tiles[i].getBackgroundColor());
			xsend.text2(tiles[i].getX(), tiles[i].getY(), String.valueOf(tiles[i].getNumber()));
			xsend.textColor2(tiles[i].getX(), tiles[i].getY(), tiles[i].getNumberColor());
		}
	}

	@Override
	public void boardClick(BoardClickEvent click) {

		// Get clicked (x, y) coords
		int clickedX = click.getX();
		int clickedY = click.getY();
		System.out.println("\nClicked 2D position: x = " + clickedX + ", y = " + clickedY);

		// Convert clicked (x, y) coords in 1D position
		clickedPosition1D = convertTo1D(clickedX, clickedY);
		System.out.println("Clicked 1D position = " + clickedPosition1D);

		// Get hole position. In other words, get (x, y) coords from tile number 16.
		getHolePosition(16);
		int holeX = holePosition[0];
		int holeY = holePosition[1];
		System.out.println("Hole 2D position:    x = " + holeX + ", y = " + holeY);

		// Convert hole (x, y) coords in 1D position
		holePosition1D = convertTo1D(holeX, holeY);
		System.out.println("Hole 1D position    = " + holePosition1D);

		// Search direction for multiple tile moves at once
		searchDirection(clickedX, clickedY, holeX, holeY);
		System.out.println("Direction = " + direction);

		// Move tiles in the direction
		if (direction != 0) {
			System.out.println("Entered if");
			do {
				int newHolePosition1D = holePosition1D + direction;
				System.out.println("new hole 1D position = "+newHolePosition1D);
				
				tiles[holePosition1D] = tiles[newHolePosition1D];
				holePosition1D = newHolePosition1D;
			} while (holePosition1D != clickedPosition1D);

			//tiles[holePosition1D] = 0;
		}
		graphic.repaint();
	}

	private int searchDirection(int clickedX, int clickedY, int holeX, int holeY) {
		if (clickedX == holeX && Math.abs(clickedY - holeY) > 0) {
			if (clickedY - holeY > 0)
				return direction = -boardSize;
			else
				return direction = boardSize;
		} else if (clickedY == holeY && Math.abs(clickedX - holeY) > 0) {
			if (clickedX - holeX > 0) {
				return direction = 1;
			} else
				return direction = -1;
		}
		return direction;
	}

	private int convertTo1D(int x, int y) {
		int position1D = x - 4 * y + 12;
		return position1D;
	}

	private int[] getHolePosition(int tileNr) {

		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i].getNumber() == tileNr) {
				holePosition[0] = tiles[i].getX();
				holePosition[1] = tiles[i].getY();
			}
		}
		return holePosition;
	}

	private int getClickedTileNr(int x, int y) {
		int tileNr = 0;
		for (int i = 0; i < tiles.length; i++) {
			if (x == tiles[i].getX() && y == tiles[i].getY()) {
				tileNr = tiles[i].getNumber();
				System.out.println("tile number = " + tileNr);
			}
		}
		return tileNr;
	}
}
