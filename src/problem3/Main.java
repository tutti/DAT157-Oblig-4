package problem3;

import no.patternsolutions.javann.Backpropagation;

public class Main {
	
	private final static double[][] OUTPUT = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
	private final static String[] OUTNAMES = {"Left", "Right", "Straight", "Up"};
	
	private static String[] trainingNames = {
		/*"an2i",
		"at33",
		"bpm",
		"ch4f",
		"cheyer",
		"night",
		"saavik",
		"steffi"*/
		"steffi"
	};
	
	private static String[] testNames = {
		/*"boland",
		"kk49"*/
		"steffi"
	};
	
	private static String output(double[] output) {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < output.length; ++i) {
			if (output[i] > 0.5) {
				if (out.length() > 0) {
					out.append(", ");
				}
				out.append(OUTNAMES[i]);
			}
		}
		return out.toString();
	}

	public static void main(String[] args) throws Exception {
		int[] hidden = {100};
		Backpropagation mlp = new Backpropagation(32*30, hidden, 4);
		mlp.setIterations(10000);
		
		// Train the network
		for (int i = 0; i < trainingNames.length; ++i) {
			double[] left =			ImageReader.readImage(trainingNames[i], ImageReader.LEFT);
			double[] right =		ImageReader.readImage(trainingNames[i], ImageReader.RIGHT);
			double[] straight =		ImageReader.readImage(trainingNames[i], ImageReader.STRAIGHT);
			double[] up =			ImageReader.readImage(trainingNames[i], ImageReader.UP);
			double[][] patterns =	{left, right, straight, up};
			
			mlp.trainPatterns(patterns, OUTPUT);
		}
		
		// Test the network
		for (int i = 0; i < testNames.length; ++i) {
			double[] left =			ImageReader.readImage(testNames[i], ImageReader.LEFT);
			double[] right =		ImageReader.readImage(testNames[i], ImageReader.RIGHT);
			double[] straight =		ImageReader.readImage(testNames[i], ImageReader.STRAIGHT);
			double[] up =			ImageReader.readImage(testNames[i], ImageReader.UP);
			
			double[] leftOut =		mlp.run(left);
			double[] rightOut =		mlp.run(right);
			double[] straightOut =	mlp.run(straight);
			double[] upOut =		mlp.run(up);
			
			System.out.println("Test data for " + testNames[i] + ":");
			System.out.println("Left:     " + output(leftOut));
			System.out.println("Right:    " + output(rightOut));
			System.out.println("Straight: " + output(straightOut));
			System.out.println("Up:       " + output(upOut));
			System.out.println();
		}
	}

}
