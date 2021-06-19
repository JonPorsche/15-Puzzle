package puzzle;

import static puzzle.GameBoard.xsend;

import jserver.Board;
import jserver.XSendAdapter;
import jserver.XSendAdapterEN;

public class Renderer {

	private GameBoard gameBoard;
	//private XSendAdapterEN xsend = new XSendAdapterEN();
	
	public void renderElements(GameElement[] elements) {
				
		for (int i = 0; i < elements.length; i++) {
			renderElement(elements[i]);
		}
	}
	
	public void renderElement(GameElement tile) {
		
		xsend.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
		xsend.forms(GameElement.FORM);
		xsend.symbolSizes(GameElement.TILE_SIZE);


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
	
}
