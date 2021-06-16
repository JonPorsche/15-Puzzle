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
		//System.out.println("Inversions = " + countInversions);
		return countInversions % 2 == 0;
	}
}
