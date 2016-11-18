package ann;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import no.patternsolutions.javann.Backpropagation;
import no.patternsolutions.javann.Kohonen;
import no.patternsolutions.javann.Perceptron;

/*
 * The following is code for Excercise 4
 * Trains & tests a Kohonen network with the letters
 * A to E, J & K, each letter having 2 or 3 variations
 */
public class Exercise4 {
	public static void main(String[] args) {
		String fname = "Bokstaver_vektorer.txt";
		int categories = 7, patternVariations = 3, rows = 9, cols = 7, inputNodes = rows*cols, outputNodes = patternVariations*categories,
				kohIterations = 110000;
		double[][] inputs = new double[outputNodes][inputNodes];
		
		// Initialize the networks
		Kohonen koh = new Kohonen(inputs);
		//Fill the competetive layer with random double values.
		double[][] compRandom = createRandomCompLayer(categories, inputNodes);
		koh.setRandomUpdate(true);
		koh.setIterations(kohIterations);
		koh.setLearnRate(1);
		//koh.setNetworkSize(2, 2);
		koh.setCompetingLayer(compRandom);
		koh.setDecreaseLearnRate(true);
		koh.setMatchType(Kohonen.EUCLIDIC_DISTANCE);
		koh.setNeighbourDecrease(Kohonen.LINEAR_DECRESE);
		koh.setNeighbourRange(0.65);
		
		// Read the input patterns for training
		inputs = deserializePatternsFile(inputs, fname);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		// Create the answer/truth patterns/targets used in the supervised learning of the neural net
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		// Use this if the patterns in the patterns file are grouped per letter (A1A2A3...B1B2B3...C1C2C3...)
		boolean[][] answers = new boolean[outputNodes][categories];
		for(int j = 0; j < patternVariations; j++) { // 3 variations of each letter
			for(int i = 0; i < categories; i++) { // 7 actual letters/categories/classifications
				answers[i*patternVariations + j][i] = true;
			}
		}
		// Use this if the patterns in the patterns file are grouped per font (A1B1C1...A2B2C2...)
//		for(int i = 0; i < answers.length; i++) {
//			for(int j = 0; j < patternVariations; j++) {
//				answers[i][i%categories] = true;
//			}
//		}
		
		
		// Debug the answers array/look at it's structure
		for (int i = 0; i < answers.length; ++i) {
			for (int j = 0; j < answers[i].length; ++j) {
				System.out.print(answers[i][j] ? '#' : '-');
			}
			System.out.println();
		}
		System.out.println();
		
		// Print the current input pattern/letter for debug purposes
		double[] letter0;
		StringBuilder outstring0 = new StringBuilder();
		for(int patternInd = 0; patternInd < outputNodes; patternInd++) { //for alle bokstaver
			letter0 = inputs[patternInd];
			for(int i = 0; i < inputNodes; i++) { // For hvert tegn i bokstaven
				if (i % 7 == 0) {
					outstring0.append("\n");
				}
				outstring0.append((letter0[i]>0) ? "#" : "-");
			}
			System.out.println(outstring0.toString() + "\n");
			outstring0.setLength(0);
			System.out.println("\n");
		}
		
		
		// Train the neural nets
		koh.trainPatterns(inputs);
		
		//tweake parametere ihht. boken her?
		//koh.trainPatterns(inputs);
		
		/////////////////////////////////////////////////////////////////
		// Scramble the first letter with 30% noise for debug purposes
		/////////////////////////////////////////////////////////////////
//		int pos;
//		HashSet<String> posMem = new HashSet<String>();
//		Random r = new Random();
//		
//		for(int i = 0; i < inputNodes*0.3; i++) {
//			pos = r.nextInt(inputNodes);
//			while(posMem.contains(Integer.toString(pos))) {
//				pos = r.nextInt(inputNodes);
//			}
//			inputs[0][pos] = (r.nextBoolean() ? 0.0 : 1.0);
//			posMem.add(Integer.toString(pos));
//		}
		
		
		////////////////////////////////////////////////////////
		// Test the neural net & print the results
		////////////////////////////////////////////////////////
		double[] letter, answerNodesKoh;
		String[] letters = {"A", "B", "C", "D", "E", "J", "K"}; // For visualization/debug purposes
		String debugOutput;
		StringBuilder outstring = new StringBuilder();
		int letterInd, outputVectorIndex;
		DecimalFormat df = new DecimalFormat("0.00");
		
		// For each pattern, test the Kohonen network and print the results
		for(int patternInd = 0; patternInd < outputNodes; patternInd++) {
			letter = inputs[patternInd]; // Each letter variation (A1, A2, ..., B1, B2, ...)
			
			answerNodesKoh = koh.run(letter); // The output matrix
			outputVectorIndex = koh.runIndex(letter); // The output matrix index
			
			// Use this if the patterns in the patterns file are grouped per letter (A1A2A3...B1B2B3...C1C2C3...)
			letterInd = patternInd/patternVariations;
			
			// Use this if the patterns in the patterns file are grouped per font (A1B1C1...A2B2C2...)
//			letterInd = patternInd%categories;
			
			debugOutput = "Har vi sendt gjennom ein " + letters[letterInd] + "?  "
//					+ (answerNodesKoh[letterInd] > 0 ? "TRUE" : "FALSE");
//					+ answerNodesKoh[letterInd];
					+ outputVectorIndex;
			System.out.println(debugOutput);
			
			// Print the answer array for each neural net
//			for (int i = 0; i < categories; i++) {
//				System.out.print(letters[outputVectorIndex]);
//			}
			System.out.println("\tKohonen network answer");
//			for (int i = 0; i < categories; i++) {
////				System.out.print(answerNodesKoh[i]>0 ? '#' : '-');
//				System.out.print(answerNodesKoh[i] + "|");
//			}
			for(int i = 0; i < answerNodesKoh.length; i++) {
				if(i%cols == 0) {
					System.out.println();
//					output.append("\n");
				}
				System.out.print(df.format(answerNodesKoh[i]) + " ");
//				output.append(df.format(map[i]) + " ");
			}
			System.out.println();
			
			// Print the current input pattern/letter for debug purposes
			for(int i = 0; i < inputNodes; i++) {
				if (i % cols == 0) {
					outstring.append("\n");
				}
				outstring.append((letter[i])>0 ? "#" : "-");
			}
			System.out.println(outstring.toString() + "\n");
			outstring.setLength(0);
			
			System.out.println("\n");
		}
	}
	
	// Reads the patterns for all the letters and populates the given input array, encoded as boolean true & false
	public static boolean[][] deserializePatternsFile(boolean[/*outputs*/][/*inputs*/] array, String fname) {
		try {
			FileReader inputFile = new FileReader(fname);
			BufferedReader br = new BufferedReader(inputFile);
			String line;
			StringBuilder sb = new StringBuilder();
			int outputPos = 0;
			
			// Read until eof
			while((line = br.readLine()) != null) {
				// Ignore commented lines
				if (!line.startsWith("//")) {
					// New pattern when the line contains the "=" character
					if (line.contains("=")) {
						// Finish adding the previous pattern if any exists
						if (sb.length() > 0 && sb.length() <= array[0].length) {
							// Parse the binary encodings as true/false, populate the input array
							for(int i = 0; i < sb.length(); i++) {
								array[outputPos][i] = (sb.charAt(i) == '0') ? false : true;
							}
							outputPos++;
						}
						sb.setLength(0); //Reset the StringBuilder instance (no need to create a new)
					}
					else sb.append(line); // Read the actual lines with binary encodings here
				}
			}
			
			// Ugly repeated code adds the very last buffered pattern that's not added in the while-loop
			// (the "line" variable is basically "null" at this point so the loop breaks)
			if (sb.length() > 0 && sb.length() <= array[0].length) {
				// Parse the binary encodings as true/false, populate the input array
				for(int i = 0; i < sb.length(); i++) {
					array[outputPos][i] = (sb.charAt(i) == '0') ? false : true;
				}
			}
			System.out.println("Patterns: " + (outputPos+1) + "\n"); // Debug
			inputFile.close();
		}
		catch (IOException e) {
            System.out.println("Sorry, unable to read the patterns, file IO error:\n\n" + e);
            System.exit(1);
        }
		return array;
	}
	
	
	// Reads the patterns for all the letters and populates the given input array, encoded as doubles 1.0 & 0.0
	public static double[][] deserializePatternsFile(double[/*outputs*/][/*inputs*/] array, String fname) {
		try {
			FileReader inputFile = new FileReader(fname);
			BufferedReader br = new BufferedReader(inputFile);
			String line;
			StringBuilder sb = new StringBuilder();
			int outputPos = 0;
			
			// Read until eof
			while((line = br.readLine()) != null) {
				// Ignore commented lines
				if (!line.startsWith("//")) {
					// New pattern when the line contains the "=" character
					if (line.contains("=")) {
						// Finish adding the previous pattern if any exists
						if (sb.length() > 0 && sb.length() <= array[0].length) {
							// Parse the binary encodings as true/false, populate the input array
							for(int i = 0; i < sb.length(); i++) {
								array[outputPos][i] = (sb.charAt(i) == '0') ? 0.0 : 1.0;
							}
							outputPos++;
						}
						sb.setLength(0); //Reset the StringBuilder instance (no need to create a new)
					}
					else sb.append(line); // Read the actual lines with binary encodings here
				}
			}
			
			// Ugly repeated code adds the very last buffered pattern that's not added in the while-loop
			// (the "line" variable is basically "null" at this point so the loop breaks)
			if (sb.length() > 0 && sb.length() <= array[0].length) {
				// Parse the binary encodings as true/false, populate the input array
				for(int i = 0; i < sb.length(); i++) {
					array[outputPos][i] = (sb.charAt(i) == '0') ? 0.0 : 1.0;
				}
			}
			System.out.println("Patterns: " + (outputPos+1) + "\n"); // Debug
			inputFile.close();
		}
		catch (IOException e) {
            System.out.println("Sorry, unable to read the patterns, file IO error:\n\n" + e);
            System.exit(1);
        }
		return array;
	}
	
	public static double[][] createRandomCompLayer(int i, int j) {
		double[][] d = new double[i][j];
		for (int k = 0; k < i; k++) {
			for (int m = 0; m < j; m++) {
				d[k][m] = ThreadLocalRandom.current().nextDouble();
			}
		}
		return d;
	}
}