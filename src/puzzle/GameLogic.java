package puzzle;

public class GameLogic {

	public boolean isSolvable(int[] tileNrs) {
		int countInversions = 0;

		for (int i = 0; i < tileNrs.length; i++) {
			for (int j = 0; j < i; j++) {
				if (tileNrs[j] > tileNrs[i]) {
					countInversions++;
				}
			}
		}
		// System.out.println("Inversions = " + countInversions);
		return countInversions % 2 == 0;
	}

	public boolean isSolved(int[] tileNrs) {
		if (tileNrs[tileNrs.length-1] != 16) { // if hole is not in the solved position ==> not solved
			System.out.println("NOT Solved");
			return false;
		}

		for (int i = tileNrs.length - 1; i >= 0; i--) {
			if (tileNrs[i] != i + 1) {
				System.out.println("NOT Solved");
				return false;
			}
		}
		System.out.println("Solved!");
		return true;
	}
}
