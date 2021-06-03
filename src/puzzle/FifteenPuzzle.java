package puzzle;

import jserver.Board;
import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapterEN;
import plotter.Graphic;

public class FifteenPuzzle implements BoardClickListener{
		
	private XSendAdapterEN xsend = new XSendAdapterEN();
	private int boardSize = 4;

	public static void main(String[] args) {
		FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
		fifteenPuzzle.start();
	}
	
	public void start() {
		System.out.println("15-Puzzle");
		setUpBoard();
	}

	private void setUpBoard() {
		//Board board = xsend.getBoard();			// Board manages the symbols and has a Graphic class embedded
		//Graphic graphic = board.getGraphic();		// Graphic is a simple frame around a Plotter based on JFrame.
													// It creates a main window with a Plotter object and a status line.
		
		Tile tile = new Tile(1,1,XSendAdapterEN.BROWN,1,XSendAdapterEN.WHITE,XSendAdapterEN.DARKRED,true);
		
		xsend.size(boardSize, boardSize);
		xsend.symbolSizes(Tile.TILE_SIZE);
		xsend.forms(Tile.FORM);
		xsend.color2(tile.getX(), tile.getY(), tile.getTileColor());
		xsend.background2(tile.getX(), tile.getY(), tile.getBackgroundColor());
		xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
		xsend.textColor2(tile.getX(), tile.getY(), tile.getNumberColor());
	}

	@Override
	public void boardClick(BoardClickEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
