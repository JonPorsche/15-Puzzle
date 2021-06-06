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
	//private int direction = 0;
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
					tiles[index] = new Tile(tileX, tileY, tileNumber, XSendAdapter.WHITE, XSendAdapterEN.BLUE, XSendAdapter.LIGHTGRAY, true);
				} else {
					tiles[index] = new Tile(tileX, tileY, tileNumber, XSendAdapter.LIGHTGRAY, XSendAdapterEN.LIGHTGRAY, XSendAdapter.LIGHTGRAY, false);
				}
				index++;
			}
		}
		return tiles;
	}

	private void positionTilesOnBoard(Tile[] tiles) {
		for (int i = 0; i < tiles.length; i++) {			
			updateElementVisual(tiles[i], tiles[i].getX(),tiles[i].getY());
		}
	}

	@Override
	public void boardClick(BoardClickEvent click) {

		// Get clicked (x, y) coords
		int clickedX = click.getX();
		int clickedY = click.getY();
		System.out.println("\n#\nClicked 2D position: x = " + clickedX + ", y = " + clickedY);

		// Get hole position. In other words, get (x, y) coords from tile number 16.
		getHolePosition(16);
		int holeX = holePosition[0];
		int holeY = holePosition[1];
		System.out.println("Hole    2D position: x = " + holeX + ", y = " + holeY+"\n");
		
		// Convert clicked (x, y) coords in 1D position
		clickedPosition1D = convertPositionTo1D(clickedX, clickedY);
		System.out.println("Clicked 1D position = " + clickedPosition1D);

		// Convert hole (x, y) coords in 1D position
		holePosition1D = convertPositionTo1D(holeX, holeY);
		System.out.println("Hole    1D position = " + holePosition1D+"\n");

		// Search direction for multiple tile moves at once
		int direction = searchDirection(clickedX, clickedY, holeX, holeY);

		// Move tiles in the direction
		moveTile(direction, clickedX, clickedY, holeX, holeY);
	}

	private void moveTile(int direction, int clickedX, int clickedY, int holeX, int holeY) {
			
		int newHolePosition1D;
		int cachedTileX;
		int cachedTileY;
		int cachedHoleX;
		int cachedHoleY;
		
		if (direction != 0) {			
			while(holePosition1D != clickedPosition1D) {
				
				// Calculate next hole position
				newHolePosition1D = holePosition1D + direction;						
				
				// Cache x,y values from hole and tile
				cachedTileX = tiles[newHolePosition1D].getX();
				cachedTileY = tiles[newHolePosition1D].getY();
				
				cachedHoleX = tiles[holePosition1D].getX();
				cachedHoleY = tiles[holePosition1D].getY();
				
				// Cache the tile to save it's visuals (Is it really important for the visuals?)
				Tile cachedTile = tiles[newHolePosition1D];
				
				// Switch positions between hole and tile
				tiles[newHolePosition1D] = tiles[holePosition1D];
				
				// Update hole with same x,y from cached tile (Is not the visual move yet)
				tiles[newHolePosition1D].setX(cachedTileX);
				tiles[newHolePosition1D].setY(cachedTileY);
				
				// Place cached tile where hole was
				tiles[holePosition1D] = cachedTile;
				
				// Update tile with same x,y from hole (Is not the visual move yet)
				cachedTile.setX(cachedHoleX);
				cachedTile.setY(cachedHoleY);
				
				// 4. Update hole visual
				renderElement(tiles[newHolePosition1D]);
				
				// Update tile visual
				renderElement(cachedTile);
				
				// 8. Update hole position
				holePosition1D = newHolePosition1D;													
			}
		}
	}

	
	// Update x,y coords from moved tile
	private void updateElement2DCoords(Tile tile, int x, int y) {
		tile.setX(x); 					
		tile.setY(y);		
	}
	
	private void renderElement(Tile tile) {
		if(tile.getNumber() == 16) {
			xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
			
			tile.setNumberColor(XSendAdapterEN.LIGHTGRAY);
			xsend.textColor2(tile.getX(), tile.getY(), tile.getNumberColor());

			tile.setForegroundColor(XSendAdapterEN.LIGHTGRAY);
			xsend.color2(tile.getX(), tile.getY(), tile.getForegroundColor());
			
			tile.setBackgroundColor(XSendAdapterEN.LIGHTGRAY);
			xsend.background2(tile.getX(), tile.getY(), tile.getBackgroundColor());
		} else {
			xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
			
			tile.setNumberColor(XSendAdapterEN.WHITE);
			xsend.textColor2(tile.getX(), tile.getY(), tile.getNumberColor());		

			tile.setForegroundColor(XSendAdapterEN.BLUE);
			xsend.color2(tile.getX(), tile.getY(), tile.getForegroundColor());
			
			tile.setBackgroundColor(XSendAdapterEN.LIGHTGRAY);
			xsend.background2(tile.getX(), tile.getY(), tile.getBackgroundColor());	
		}
	}
	
	private void updateElementVisual(Tile tile, int x, int y) {
		xsend.color2(tile.getX(), tile.getY(), tile.getForegroundColor());
		xsend.background2(tile.getX(), tile.getY(), tile.getBackgroundColor());
		xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
		xsend.textColor2(tile.getX(), tile.getY(), tile.getNumberColor());		
	}

	private int searchDirection(int clickedX, int clickedY, int holeX, int holeY) {
		
		int direction = 0;
		
		if (clickedX == holeX) {				// Same x?
			System.out.print("Same x and ");
			if (clickedY - holeY > 0) {			// Is the hole above or under the clicked tile
				System.out.println("hole is under clicked tile -> direction = -4");
				return direction = -4; 	// Is under -> Hole moves back 4 positions
			}
			else {
				System.out.println("hole is above clicked tile -> direction = +4");
				return direction = 4;	// Is above -> Hole moves forward 4 positions
			}
		} 
		else if (clickedY == holeY) {			// Same y?
			System.out.print("Same y and ");
			if (clickedX - holeX > 0) {			// Is the hole before or after the clicked tile?
				System.out.println("hole is before clicked tile -> direction = +1");
				return direction = 1;			// Is before -> Hole moves forward 1 position
			} else {
				System.out.println("hole is after clicked tile -> direction = -1");
				return direction = -1;			// Is after -> Hole moves back 1 position
			}
		}		
		return direction;
	}

	private int convertPositionTo1D(int x, int y) {
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
