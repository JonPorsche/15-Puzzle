package puzzle;

import jserver.Board;
import jserver.BoardClickEvent;
import jserver.BoardClickListener;
import jserver.XSendAdapter;
import jserver.XSendAdapterEN;
import plotter.Graphic;

public class Test implements BoardClickListener{
	
	private XSendAdapterEN xsend = new XSendAdapterEN();

	public static void main(String[] args) {
		Test test = new Test();
		test.start();
	}
	
	public void start() {
		System.out.println("BoS integration test.");
		setUpBoard();
	}

	public void boardClick(BoardClickEvent arg0) {
		
	}
	
	private void setUpBoard() {
		Board board = xsend.getBoard();
		Graphic graphic = board.getGraphic(); // einfacher Rahmen um einen Plotter. Erstellt Hauptfenster und darin ein
												// Plotter-Objekt sowie eine Statuszeile

		board.addClickListener(this);
		board.setSize(800, 150);

		xsend.colors(XSendAdapter.RED);
		xsend.color2(0, 0, XSendAdapter.YELLOW);
	}

}
