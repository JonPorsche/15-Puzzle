package puzzle;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;
import jserver.XSendAdapter;
import jserver.XSendAdapterEN;

public class FifteenPuzzleTest {

	FifteenPuzzle fifteenPuzzle = new FifteenPuzzle();
	GameElement[] elements = new GameElement[16];
	
	
	@Test
	public void testMoveElement() {
		assertEquals(0, testMoveElementTileNumber());
		
	}
	
	/* Test to ensure that the array elements will
	 * never have duplicate tile numbers */
	
	public int testMoveElementTileNumber() {
		
		int countSameNr = 0;
		int iNumber = 0;
		int jNumber = 0;
		
		createElements();
		shuffle2();
	
		// Method execution
		fifteenPuzzle.moveElements(-1, 1, 0, 3, 0);
		
		for (int i = 0; i < elements.length; i++) {
			iNumber = elements[i].getNumber();
            for (int j = 0; j < elements.length; j++) {
            	jNumber = elements[j].getNumber();
                if (iNumber == jNumber && i != j) {
                    countSameNr++;
                }
            }
        }
		return countSameNr;
	}
	
	public void createElements() {
		int index = 0;
		int tileNumber;
		for (int tileY = 3; tileY >= 0; tileY--) {
			for (int tileX = 0; tileX < 4; tileX++) {
				tileNumber = index + 1;
				if (tileNumber < 16) {
					elements[index] = new GameElement(tileX, tileY, tileNumber, XSendAdapter.WHITE, XSendAdapterEN.BLUE,
							XSendAdapter.LIGHTGRAY);
				} else {
					elements[index] = new GameElement(tileX, tileY, tileNumber, XSendAdapter.LIGHTGRAY,
							XSendAdapterEN.LIGHTGRAY, XSendAdapter.LIGHTGRAY);
				}
				index++;
			}
		}
	}
	
	private void shuffle2() {
		int i = 0;
		int[] col = shuffleArray1D(4);

		// For each x(column) assign a y(line)
		for (int x = 0; x < 4; x++) {
			int[] line = shuffleArray1D(4);

			// System.out.print("[");
			for (int y = 0; y < 4; y++) {
				// System.out.print("("+col[x]+", "+line[y]+")");
				elements[i].setX(col[x]);
				elements[i].setY(line[y]);
				i++;
			}
			// System.out.println("]");
		}
		// System.out.println("Elments after shuffle = "+Arrays.toString(elements));
	}

	private int[] shuffleArray1D(int maxElements) {
		Random randomGen = new Random();
		int[] shuffledArray = new int[maxElements];

		// Initialize Array
		for (int i = 0; i < shuffledArray.length; i++) {
			shuffledArray[i] = i;
		}

		// Shuffle by exchanging each element randomly
		for (int i = 0; i < shuffledArray.length; i++) {
			int randomPos = randomGen.nextInt(shuffledArray.length);
			int temp = shuffledArray[i];
			shuffledArray[i] = shuffledArray[randomPos];
			shuffledArray[randomPos] = temp;
		}
		// System.out.println(Arrays.toString(shuffledArray));
		return shuffledArray;
	}
}
