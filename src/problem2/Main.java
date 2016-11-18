package problem2;

import java.io.IOException;
import no.patternsolutions.javann.Counterpropagation;
import no.patternsolutions.javann.Kohonen;

public class Main {

	private static void printCell(boolean[] pattern, int width) {
		for (int i = 0; i < pattern.length; ++i) {
			if (i % width == 0) {
				System.out.println();
			}
			System.out.print(pattern[i] ? '#' : '-');
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		// Leverage the font reader to get cell shapes from a text file
		FontReader cellReader = new FontReader("oppg6cancers.txt");
		
		boolean[][] cell1 = cellReader.getFont(0);
		boolean[][] cell2 = cellReader.getFont(1);
		boolean[][] cell3 = cellReader.getFont(2);
		boolean[][] cell4 = cellReader.getFont(3);
		
		boolean[][][] cells = {cell1, cell2, cell3, cell4};
		
		boolean[][] patterns = {cell1[0], cell2[0], cell3[0], cell4[0]};
		boolean[][] outpatterns = {cell1[1], cell2[1], cell3[1], cell4[1]};
		
		//train counterpropagation
		Counterpropagation counterpropagation = new Counterpropagation(144, 144, 144);
		counterpropagation.setIterations(10000);
		counterpropagation.setNeighbourRange(0.95);
		counterpropagation.setNeighbourDecrese(Kohonen.LINEAR_DECRESE);
		counterpropagation.trainPatterns(patterns, outpatterns);
		
		
		for (int i = 0; i < 4; ++i) {
			boolean[] counterpropagationTest = counterpropagation.run(cells[i][0]);
			//boolean[] counterpropagationTestOut = counterpropagation.run(cells[i][1]);
			
			System.out.print("Test cell input " + (i+1) + ":");
			printCell(cells[i][0], 12);
			System.out.println();
			System.out.println();
			
			System.out.println("Test cell expected output " + (i+1) + ":");
			printCell(cells[i][1], 12);
			System.out.println();
			System.out.println();
			
			System.out.print("Counterpropagation test cell output " + (i+1) + " (in):");
			printCell(counterpropagationTest, 12);
			System.out.println();
			System.out.println();
			
			/*System.out.print("counterpropagation test cell output " + (i+1) + " (out):");
			printCell(counterpropagationTestOut, 12);
			System.out.println();
			System.out.println();*/
		}
	}

}
