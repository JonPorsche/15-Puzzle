package puzzle;

public class Tile {
	
	public static final double TILE_SIZE = 0.5;
	public static final String FORM = "Frame";
	private int x;
	private int y;
	private int color;
	private int backgroundColor;
	private int number;
	private int numberColor;
	private boolean isClickable;
	
	public Tile(int x, int y, int color, int number, int numberColor, int backgroundColor, boolean isClickable) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.number = number;
		this.numberColor = numberColor;
		this.backgroundColor = backgroundColor;
		this.isClickable = isClickable;
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

	public int getTileColor() {
		return color;
	}

	public void setTileColor(int tileColor) {
		this.color = tileColor;
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

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}
	
	
}
