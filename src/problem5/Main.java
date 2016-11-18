package problem5;

import no.patternsolutions.javann.Kohonen;

public class Main {
	private final static String CIRCLE =
        "    ##    " +
        "  ######  " +
        " ##    ## " +
        " #      # " +
        "##      ##" +
        "##      ##" +
        " #      # " +
        " ##    ## " +
        "  ######  " +
        "    ##    ";
	
	private final static String SHIT_CIRCLE =
        "##########" +
        "##########" +
        "##      ##" +
        "##      ##" +
        "##      ##" +
        "##      ##" +
        "##      ##" +
        "##      ##" +
        "##########" +
        "##########";
	
	private static void printOutput(double[] output, int rowSize) {
		for (int i = 0; i < output.length; ++i) {
			if (i % rowSize == 0) System.out.println();
			//System.out.print(output[i] > 0.9 ? '#' : output[i] < 0.1 ? '-' : '?');
			
			if (output[i] < 0.05) {
				System.out.print(' ');
			} else if (output[i] < 0.25) {
				System.out.print('-');
			} else if (output[i] < 0.5) {
				System.out.print('*');
			} else if (output[i] < 0.75) {
				System.out.print('¤');
			} else if (output[i] < 0.95) {
				System.out.print('X');
			} else {
				System.out.print('#');
			}
		}
	}
	
	private static boolean compare(double[] expected, double[] got) {
		if (expected.length != got.length) return false;
		for (int i = 0; i < expected.length; ++i) {
			if (expected[i] > 0.95 && got[i] <= 0.95) return false;
			if (expected[i] < 0.05 && got[i] >= 0.05) return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		double[][] circle = new double[1][CIRCLE.length()];
		for (int i = 0; i < CIRCLE.length(); ++i) {
			circle[0][i] = CIRCLE.charAt(i) == '#' ? 1.0 : 0.0;
		}
		double[] shitCircle = new double[SHIT_CIRCLE.length()];
		for (int i = 0; i < SHIT_CIRCLE.length(); ++i) {
			shitCircle[i] = SHIT_CIRCLE.charAt(i) == '#' ? 1.0 : 0.0;
		}
		
		System.out.print("Input pattern:");
		printOutput(circle[0], 10);
		
		double[] out = new double[0];
		int k;
		
		for (k = 0; !compare(circle[0], out); ++k) {
			double[][] comp = new double[1][100];
			for (int i = 0; i < 1; ++i) {
				for (int j = 0; j < 100; ++j) {
					comp[i][j] = Math.random();
				}
			}
			
			Kohonen koh = new Kohonen(comp);
			koh.setCompetingLayer(comp);
			koh.setDecreaseLearnRate(true);
			koh.setIterations(k);

			koh.trainPatterns(circle);
			
			out = koh.run(circle[0]);
			System.out.println();
			System.out.print("Iteration " + k);
			printOutput(out, 10);
		}
		
		System.out.println();
		System.out.println("Pattern found after " + k + " iterations.");
		
		System.out.println("Harder input pattern:");
		printOutput(shitCircle, 10);
		
		out = new double[0];
		
		for (k = 0; !compare(circle[0], out); ++k) {
			double[][] comp = new double[1][100];
			for (int i = 0; i < 1; ++i) {
				for (int j = 0; j < 100; ++j) {
					comp[i][j] = Math.random();
				}
			}
			
			Kohonen koh = new Kohonen(comp);
			koh.setCompetingLayer(comp);
			koh.setDecreaseLearnRate(true);
			koh.setIterations(k);

			koh.trainPatterns(circle);
			
			out = koh.run(shitCircle);
			System.out.println();
			System.out.print("Iteration " + k);
			printOutput(out, 10);
		}
		
		System.out.println();
		System.out.println("Pattern found after " + k + " iterations.");
		
	}
}
