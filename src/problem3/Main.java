package problem3;

import no.patternsolutions.javann.Backpropagation;

public class Main {
	
	private final static double[][] OUTPUT = {{1.0, 0, 0, 0}, {0, 1.0, 0, 0}, {0, 0, 1.0, 0}, {0, 0, 0, 1.0}};
	private final static String[] OUTNAMES = {"Left", "Right", "Straight", "Up"};
	
	private static String[] trainingNames = {
		"an2i",
		"at33",
		"bpm",
		"ch4f",
		"cheyer",
		"night",
		"saavik",
		"steffi"
		//"saavik"
	};
	
	private static String[] testNames = {
		"boland",
		"kk49"
		//"saavik"
	};
	
	private static String output(double[] output) {
		//StringBuilder out = new StringBuilder();
		int largest = 0;
		for (int i = 0; i < output.length; ++i) {
			if (output[i] > 0.5) {
				/*if (out.length() > 0) {
					out.append(", ");
				}
				out.append(OUTNAMES[i]);*/
				if (output[i] > output[largest]) largest = i;
			}
		}
		return OUTNAMES[largest];
	}

	public static void main(String[] args) throws Exception {
		int[] hidden = {160};
		Backpropagation mlp = new Backpropagation(32*30, hidden, 4);
	    mlp.setLearnRate(0.15);
	    mlp.setIterations(1000);
	    mlp.setMomentum(0.3);
	    mlp.randomizeWeights(-1, 1);
		
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
			
			
			System.out.println("Test data for " + testNames[i] + ":");
			double[] leftOut =		mlp.run(left);
			System.out.println("Left:     " + output(leftOut));
			double[] rightOut =		mlp.run(right);
			System.out.println("Right:    " + output(rightOut));
			double[] straightOut =	mlp.run(straight);
			System.out.println("Straight: " + output(straightOut));
			double[] upOut =		mlp.run(up);
			System.out.println("Up:       " + output(upOut));
			System.out.println();
		}
	}

}
