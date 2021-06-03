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
	private Tile[] tiles = new Tile[16];

	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}

	public void start() {
		System.out.println("15-Puzzle");
		setUpBoard();
	}

	private void setUpBoard() {
		
		xsend.size(boardSize, boardSize);
		xsend.symbolSizes(Tile.TILE_SIZE);
		xsend.forms(Tile.FORM);
		
		createTiles();
		positionTilesOnBoard(tiles);
		// Board board = xsend.getBoard(); // Board manages the symbols and has a
		// Graphic class embedded
		// Graphic graphic = board.getGraphic(); // Graphic is a simple frame around a
		// Plotter based on JFrame.
		// It creates a main window with a Plotter object and a status line.
	}

	private Tile[] createTiles() {
		int index = 0;
		int tileNumber;
		for (int tileY = 3; tileY >= 0; tileY--) {
			for (int tileX = 0; tileX < 4; tileX++) {
				tileNumber = index + 1;
				if(tileNumber < 16) {
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
		for(int i = 0; i < tiles.length; i++) {
			xsend.color2(tiles[i].getX(), tiles[i].getY(), tiles[i].getForegroundColor());
			xsend.background2(tiles[i].getX(), tiles[i].getY(), tiles[i].getBackgroundColor());
			xsend.text2(tiles[i].getX(), tiles[i].getY(), String.valueOf(tiles[i].getNumber()));
			xsend.textColor2(tiles[i].getX(), tiles[i].getY(), tiles[i].getNumberColor());
		}
	}


	@Override
 	public void boardClick(BoardClickEvent arg0) {
		// TODO Auto-generated method stub

	}
}
