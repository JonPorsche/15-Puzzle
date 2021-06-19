package puzzle;

import java.util.Arrays;

public class GameElement {
	
	public static final double TILE_SIZE = 0.475;
	public static final String FORM = "s";
	private int x;
	private int y;
	private int number;
	private int numberColor;
	private int foregroundColor;
	private int backgroundColor;
	private int[] element2DCoords = new int[2];
	private GameBoard gameBoard;
	
	public GameElement(int x, int y, int number, int numberColor, int foregroundColor, int backgroundColor) {
		super();
		this.x = x;
		this.y = y;
		this.foregroundColor = foregroundColor;
		this.number = number;
		this.numberColor = numberColor;
		this.backgroundColor = backgroundColor;
	}


	public GameElement() {
		super();
	}

	@Override
	public String toString() {
		return "\nGame Element "+number+" ("+x+", "+y+")";
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(int foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getNumberColor() {
		return numberColor;
	}

	public void setNumberColor(int numberColor) {
		this.numberColor = numberColor;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int[] getElement2DCoords(GameElement[] elements, int tileNr) {
		
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].getNumber() == tileNr) {
				element2DCoords[0] = elements[i].getX();
				element2DCoords[1] = elements[i].getY();
			}
		}
		return element2DCoords;
	}

	public int convert2DCoordsTo1D(int x, int y) {
		int position1D = x - 4 * y + 12;
		return position1D;
	}
}
