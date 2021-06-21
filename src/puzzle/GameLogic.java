package puzzle;

import java.util.Random;

public class GameLogic {

	private static final Random RANDOM = new Random();

	public boolean isSolvable(int[] tileNrs) {
		int countInversions = 0;

		for (int i = 0; i < tileNrs.length; i++) {
			for (int j = 0; j < i; j++) {
				if (tileNrs[j] > tileNrs[i]) {
					countInversions++;
				}
			}
		}
		return countInversions % 2 == 0;
	}

	public boolean isSolved(int[] tileNrs) {
				
		if (tileNrs[tileNrs.length - 1] != 16) { // if hole is not in the solved position ==> not solved
			System.out.print("NOT Solved | ");
			return false;
		}

		for (int i = tileNrs.length - 1; i >= 0; i--) {
			if (tileNrs[i] != i + 1) {
				System.out.println("NOT Solved | ");
				return false;
			}
		}
		System.out.println("Solved!");
		ButtonBar.timeControl.setStarted(false);
		ButtonBar.btnStart.setText("Start");
		ButtonBar.timeControl.stop();
		return true;
	}

	public void shuffle(GameElement[] elements) {

		int[] baseArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
		int newNr = 0;
		
		for (int i = 0; i < baseArray.length - 1; i++) {
			int randomIndexToSwap = RANDOM.nextInt(baseArray.length - 1);
			int temp = baseArray[randomIndexToSwap];
			baseArray[randomIndexToSwap] = baseArray[i];
			baseArray[i] = temp;
		}

		for (int i = 0; i < elements.length-1; i++) {
			newNr = baseArray[i];
			elements[i].setNumber(newNr);
		}
	}
}
