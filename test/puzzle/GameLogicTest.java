package puzzle;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameLogicTest {

	GameLogic gameLogic = new GameLogic();
	int[] solved = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	int[] unsolved1 =  {1,2,3,4,5,6,7,8,9,10,11,12,13,15,14,16};
	int[] unsolved2 =  {1,2,3,4,5,6,7,8,9,10,11,12,13,14,16,15};
	
	@Test
	public void testIsSolved() {
		assertTrue("Solved. The hole and all the tiles are in the solved position.", gameLogic.isSolved(solved));
		assertFalse("Not solved. Hole is in solved position, but the tiles are not.", gameLogic.isSolved(unsolved1));
		assertFalse("Not solved. Hole is not in solved position.", gameLogic.isSolved(unsolved2));
	}
}
