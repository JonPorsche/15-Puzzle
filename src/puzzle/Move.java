package puzzle;

public class Move {

	public Move() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public int getMoveDirection(int clickedX, int clickedY, int holeX, int holeY) {
		int direction = 0;
		
		if (clickedX == holeX) { // Same x?
			if (clickedY - holeY > 0) { // Is the hole above or under the clicked tile
				return direction = -4; // Is under -> Hole moves back 4 positions
			} else {
				return direction = 4; // Is above -> Hole moves forward 4 positions
			}
		} else if (clickedY == holeY) { // Same y?
			if (clickedX - holeX > 0) { // Is the hole before or after the clicked tile?
				return direction = 1; // Is before -> Hole moves forward 1 position
			} else {
				return direction = -1; // Is after -> Hole moves back 1 position
			}
		}
		return direction;
	}
}
