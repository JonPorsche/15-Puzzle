package puzzle;

import jserver.XSendAdapter;
import jserver.XSendAdapterEN;

public class Renderer {
	
	public void renderElements(GameElement[] elements) {
				
		for (int i = 0; i < elements.length; i++) {
			renderElement(elements[i]);
		}
	}
	
	public void renderElement(GameElement tile) {
		
		FifteenPuzzle.XSEND.text2(tile.getX(), tile.getY(), String.valueOf(tile.getNumber()));
		FifteenPuzzle.XSEND.forms(GameElement.FORM);
		FifteenPuzzle.XSEND.symbolSizes(GameElement.TILE_SIZE);


		if (tile.getNumber() == 16) {
			FifteenPuzzle.XSEND.textColor2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
			FifteenPuzzle.XSEND.color2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
			FifteenPuzzle.XSEND.background2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
		} else {
			FifteenPuzzle.XSEND.textColor2(tile.getX(), tile.getY(), XSendAdapter.WHITE);
			FifteenPuzzle.XSEND.color2(tile.getX(), tile.getY(), XSendAdapterEN.BLUE);
			FifteenPuzzle.XSEND.background2(tile.getX(), tile.getY(), XSendAdapter.LIGHTGRAY);
		}
	}
	
}
