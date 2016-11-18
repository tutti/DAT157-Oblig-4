package problem4;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import no.patternsolutions.javann.Kohonen;

public class Main {

	private static String letters = "ABCDEJK";
	
	public static double[][] createRandomCompLayer(int i, int j) {
		double[][] d = new double[i][j];
		for (int k = 0; k < i; k++) {
			for (int m = 0; m < j; m++) {
				d[k][m] = ThreadLocalRandom.current().nextDouble();
			}
		}
		return d;
	}
	// Converts a two dimensional boolean true/false array to a two dimensional double 1.0/0.0 array.
	private static double[][] toDoubles(boolean[][] arr) {
		double[][] out = new double[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; ++i) {
			for (int k = 0; k < arr[i].length; ++k) {
				out[i][k] = arr[i][k] ? 1.0 : 0.0;
			}
		}
		return out;
	}

	public static void main(String[] args) throws IOException {
		int categories = 7, patternVariations = 3, rows = 9, cols = 7, inputNodes = rows*cols, outputNodes = patternVariations*categories;
		
		FontReader fontReader = new FontReader("letters.txt");
		boolean[][] font1 = fontReader.getFont(0);
		boolean[][] font2 = fontReader.getFont(1);
		boolean[][] font3 = fontReader.getFont(2);
		
		double[][] dFont1 = toDoubles(font1);
		double[][] dFont2 = toDoubles(font2);
		double[][] dFont3 = toDoubles(font3);


		// create the kohonen network
		Kohonen kohonen = new Kohonen();
		double[][] competingLayer = createRandomCompLayer(categories, inputNodes);
		kohonen.setRandomUpdate(true);
		kohonen.setIterations(70000);
		kohonen.setLearnRate(0.5);
		//koh.setNetworkSize(2, 2);
		kohonen.setCompetingLayer(competingLayer);
		kohonen.setDecreaseLearnRate(true);
		kohonen.setMatchType(Kohonen.EUCLIDIC_DISTANCE);
		kohonen.setNeighbourDecrease(Kohonen.LINEAR_DECRESE);
		kohonen.setNeighbourRange(0.65);

		kohonen.trainPatterns(dFont1);
		kohonen.trainPatterns(dFont2);
		kohonen.trainPatterns(dFont3);

		double[][][] fonts = { dFont1, dFont2, dFont3 };
		

		// Print the tests and their outputs
		System.out.println("Testing an kohonen network:");
		for (int f = 0; f < 3; ++f) {
			System.out.println("Font " + (f + 1));
			for (int i = 0; i < letters.length(); ++i) {
				int out = kohonen.runIndex(fonts[f][i]);
				System.out.print("Testing with " + letters.charAt(i) + ": ");
				System.out.println(out);
				
			}
			System.out.println();
		}
	}

}
