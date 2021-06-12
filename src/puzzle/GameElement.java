package puzzle;

public class GameElement {
	
	public static final double TILE_SIZE = 0.475;
	public static final String FORM = "Square";
	private int x;
	private int y;
	private int number;
	private int numberColor;
	private int foregroundColor;
	private int backgroundColor;
	
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
		return "Tile [x=" + x + ", y=" + y + ", color=" + foregroundColor + ", backgroundColor=" + backgroundColor + ", number="
				+ number + ", numberColor=" + numberColor + "]";
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
	
}
