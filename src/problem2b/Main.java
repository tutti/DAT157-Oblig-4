package problem2b;

import java.util.HashMap;
import java.util.Map;

import no.patternsolutions.javann.Counterpropagation;
import no.patternsolutions.javann.Kohonen;

public class Main {

	// Output patterns for each amino acid
	private static double[] Alanine = { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Arginine = { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Asparagine = { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] AsparticAcid = { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Cysteine = { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] GlutamicAcid = { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Glutamine = { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Glycine = { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Histidine = { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Isoleucine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Leucine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Lysine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Methionine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Phenylalanine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 };
	private static double[] Proline = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 };
	private static double[] Serine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 };
	private static double[] Threonine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 };
	private static double[] Tryptophan = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 };
	private static double[] Tyrosine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 };
	private static double[] Valine = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 };
	private static double[] STOP = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };

	// Names of the amino acids in string form, for the output
	private static String[] names = { "Alanine", "Arginine", "Asparagine", "Aspartic acid", "Cysteine", "Glutamic acid",
			"Glutamine", "Glycine", "Histidine", "Isoleucine", "Leucine", "Lysine", "Methionine", "Phenylalanine",
			"Proline", "Serine", "Threonine", "Tryptophan", "Tyrosine", "Valine", "STOP" };

	// Gets the correct name for an output pattern
	private static String getName(double[] thing) {
		for (int i = 0; i < thing.length; ++i) {
			if (thing[i] == 1) {
				return names[i];
			}
		}
		return "STOP"; // Fallback, should never happen.
	}

	// Map from each codon to the corresponding output pattern
	private static Map<String, double[]> solution;
	static {
		solution = new HashMap<String, double[]>();
		solution.put("UUU", Phenylalanine);
		solution.put("UCU", Serine);
		solution.put("UAU", Tyrosine);
		solution.put("UGU", Cysteine);
		solution.put("UUC", Phenylalanine);
		solution.put("UCC", Serine);
		solution.put("UAC", Tyrosine);
		solution.put("UGC", Cysteine);
		solution.put("UUA", Leucine);
		solution.put("UCA", Serine);
		solution.put("UAA", STOP);
		solution.put("UGA", STOP);
		solution.put("UUG", Leucine);
		solution.put("UCG", Serine);
		solution.put("UAG", STOP);
		solution.put("UGG", Tryptophan);

		solution.put("CUU", Leucine);
		solution.put("CCU", Proline);
		solution.put("CAU", Histidine);
		solution.put("CGU", Arginine);
		solution.put("CUC", Leucine);
		solution.put("CCC", Proline);
		solution.put("CAC", Histidine);
		solution.put("CGC", Arginine);
		solution.put("CUA", Leucine);
		solution.put("CCA", Proline);
		solution.put("CAA", Glutamine);
		solution.put("CGA", Arginine);
		solution.put("CUG", Leucine);
		solution.put("CCG", Proline);
		solution.put("CAG", Glutamine);
		solution.put("CGG", Arginine);

		solution.put("AUU", Isoleucine);
		solution.put("ACU", Threonine);
		solution.put("AAU", Asparagine);
		solution.put("AGU", Serine);
		solution.put("AUC", Isoleucine);
		solution.put("ACC", Threonine);
		solution.put("AAC", Asparagine);
		solution.put("AGC", Serine);
		solution.put("AUA", Isoleucine);
		solution.put("ACA", Threonine);
		solution.put("AAA", Lysine);
		solution.put("AGA", Arginine);
		solution.put("AUG", Methionine);
		solution.put("ACG", Threonine);
		solution.put("AAG", Lysine);
		solution.put("AGG", Arginine);

		solution.put("GUU", Valine);
		solution.put("GCU", Alanine);
		solution.put("GAU", AsparticAcid);
		solution.put("GGU", Glycine);
		solution.put("GUC", Valine);
		solution.put("GCC", Alanine);
		solution.put("GAC", AsparticAcid);
		solution.put("GGC", Glycine);
		solution.put("GUA", Valine);
		solution.put("GCA", Alanine);
		solution.put("GAA", GlutamicAcid);
		solution.put("GGA", Glycine);
		solution.put("GUG", Valine);
		solution.put("GCG", Alanine);
		solution.put("GAG", GlutamicAcid);
		solution.put("GGG", Glycine);
	}

	// Takes a codon string (e.g. "UCG"), returns an input pattern for it.
	private static double[] codon(String s) {
		double[] ret = new double[4 * s.length()];
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if ("ACGU".indexOf(c) < 0) {
				throw new RuntimeException("Bad codon - only ACGU accepted.");
			}
			if (c == 'A')
				ret[4 * i] = 1;
			if (c == 'C')
				ret[4 * i + 1] = 1;
			if (c == 'G')
				ret[4 * i + 2] = 1;
			if (c == 'U')
				ret[4 * i + 3] = 1;
		}
		return ret;
	}

	public static void main(String[] args) {
		// train counterpropagation
		Counterpropagation counterpropagation = new Counterpropagation(12, 150, 21);
		counterpropagation.setIterations(200000);
		counterpropagation.setNeighbourRange(0.5);
		counterpropagation.setNeighbourDecrese(Kohonen.LINEAR_DECRESE);
		counterpropagation.setTwoStep(false);
		counterpropagation.setRandomUpdate(false);
		counterpropagation.setLearnRateA(0.5);
		counterpropagation.setLearnRateB(0.5);
		
		
		// Build the input data from the above
        double[][] input = new double[64][12];
        double[][] answers = new double[64][21];
        { // I want to use i as counter without leaking it, so here's a block
	        int i = 0; // Because I need a counter but Java's foreach doesn't give me one
	        for (Map.Entry<String, double[]> entry : solution.entrySet()) {
	            input[i] = codon(entry.getKey());
	            answers[i] = entry.getValue();
	            ++i;
	        }
        }
        //training network
        counterpropagation.trainPatterns(input, answers);
        
     // Test the network
        double noCorrect = 0;
        double total = 63;
        for (Map.Entry<String, double[]> entry : solution.entrySet()) {
            double[] output = counterpropagation.run(codon(entry.getKey()));
            
            int bestChoiceIndex = 0;
            double bestChoiceValue = 0;
            
            for (int j = 0; j < output.length; ++j) {
            	if (output[j] > bestChoiceValue) {
            		bestChoiceIndex = j;
            		bestChoiceValue = output[j];
            	}
            }
            
            String guessName = names[bestChoiceIndex];
            
            double[] correct = entry.getValue();
            String correctName = getName(correct);
            
            System.out.print("Best match for ");
            System.out.print(entry.getKey());
            System.out.print(": ");
            System.out.print(guessName);
            System.out.print(", with confidence ");
            System.out.print((int)(bestChoiceValue*100));
            System.out.print("%");
            
            if (!guessName.equals(correctName)) {
                System.out.print(" (correct answer: ");
                System.out.print(correctName);
                System.out.print(")");
            } else {
            	noCorrect++;
            }
            
            System.out.println();
            
        }
        
        System.out.println("");
        double percentage = (noCorrect/total*100);
        System.out.println("Correct answers: " + percentage + "%");

	}

}
