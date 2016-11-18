package problem4;

import java.io.IOException;

import no.patternsolutions.javann.Kohonen;

public class Main {

	private static String letters = "ABCDEJK";

	// Prints a list of letters suggested by an output set
	// private static String findLetter(boolean[] output) {
	// StringBuilder sb = new StringBuilder();
	// boolean first = true;
	// for (int i = 0; i < output.length; ++i) {
	// if (output[i]) {
	// if (!first) sb.append(", ");
	// sb.append(letters.charAt(i));
	// first = false;
	// }
	// }
	// return sb.toString();
	// }

	// Prints a letter suggested by a double list, if any.
	private static String findLetter(double[] output) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < output.length; ++i) {
			if (output[i] >= 0.5) {
				if (!first)
					sb.append(", "); // Won't happen for outputs with total
										// value <= 1
				sb.append(letters.charAt(i));
				first = false;
			}
		}
		return sb.toString();
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

	// Converts a boolean true/false array to a double 1.0/0.0 array.
	private static double[] toDoubles(boolean[] arr) {
		double[] out = new double[arr.length];
		for (int i = 0; i < arr.length; ++i) {
			out[i] = arr[i] ? 1 : 0;
		}
		return out;
	}

	public static void main(String[] args) throws IOException {
		FontReader fontReader = new FontReader("letters.txt");
		double[][] font1 = fontReader.getFont(0);
		double[][] font2 = fontReader.getFont(1);
		double[][] font3 = fontReader.getFont(2);


		double[][] competing = new double[7][7];
		for (int i = 0; i < 7; ++i) {
			competing[i][i] = 1.0;
		}
		// create the kohonen network
		Kohonen kohonen = new Kohonen(competing);

		kohonen.trainPatterns(font1);
		kohonen.trainPatterns(font2);
		kohonen.trainPatterns(font3);

		double[][][] fonts = { font1, font2, font3 };
		

		// Print the tests and their outputs
		System.out.println("Testing an kohonen network:");
		for (int f = 0; f < 3; ++f) {
			System.out.println("Font " + (f + 1));
			for (int i = 0; i < letters.length(); ++i) {
				double[] out = kohonen.run(fonts[f][i]);
				System.out.print("Testing with " + letters.charAt(i) + ": ");
				System.out.println(findLetter(out));
			}
			System.out.println();
		}
	}

}