package puzzle;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameLogicTest {

	GameLogic gameLogic;
	
	@Test
	public void testIsSolved() {
		int[] tileNrs = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		assertEquals(true, gameLogic.isSolved(tileNrs));
	}

}
