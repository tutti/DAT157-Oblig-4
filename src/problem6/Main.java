package problem6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.patternsolutions.javann.Backpropagation;

public class Main {
	
	private static Map<Character, Integer> chars;
	private static Map<Integer, Character> bkchars;
	static {
		chars = new HashMap<Character, Integer>();
		chars.put('*',  0); chars.put('A',  1); chars.put('B',  2); chars.put('C',  3); chars.put('D',  4);
		chars.put('E',  5); chars.put('F',  6); chars.put('G',  7); chars.put('H',  8); chars.put('I',  9);
		chars.put('J', 10); chars.put('K', 11); chars.put('L', 12); chars.put('M', 13); chars.put('N', 14);
		chars.put('O', 15); chars.put('P', 16); chars.put('Q', 17); chars.put('R', 18); chars.put('S', 19);
		chars.put('T', 20); chars.put('U', 21); chars.put('V', 22); chars.put('W', 23); chars.put('X', 24);
		chars.put('Y', 25); chars.put('Z', 26); chars.put('Æ', 27); chars.put('Ø', 28); chars.put('Å', 29);
		
		bkchars = new HashMap<Integer, Character>();
		for (Map.Entry<Character, Integer> e : chars.entrySet()) {
			bkchars.put(e.getValue(), e.getKey());
		}
	}
	
	private static char charAt(String s, int pos) {
		if (pos >= 0 && pos < s.length()) {
			return s.charAt(pos);
		}
		return '*';
	}
	
	private static boolean[][] wordToBoolArray(String word) {
		String clean = word.replace("-", "");
		
		boolean[][] out = new boolean[clean.length() - 1][240];
		
		for (int i = 0; i < clean.length() - 1; ++i) {
			//boolean[] characters = new boolean[240];
			for (int j = 0; j < 8; ++j) {
				char c = charAt(clean, i + j - 3);
				int val = chars.get(c);
				out[i][30*j + val] = true;
			}
			//trainingData.add(characters);
			//trainingAnswers.add(answers[i]);
		}
		
		return out;
	}
	
	private static boolean[] wordToAnswers(String word) {
		String clean = word.replace("-", "");
		boolean[] answers = new boolean[clean.length() - 1];
		int found = 0;
		for (int pos = 1; pos < answers.length; ++pos) {
			if (word.charAt(pos + found + 1) == '-') {
				++found;
				answers[pos] = true;
			}
		}
		
		return answers;
	}

	public static void main(String[] args) {
		List<String> words = WordListReader.getWords();
		Collections.shuffle(words);
		
		int wordCount = words.size();
		//int limit = (int)(wordCount * 0.8);
		int limit = wordCount - 20; // Just 20 words to see that this works
		List<String> trainingSet = words.subList(0, limit);
		List<String> testSet = words.subList(limit, words.size());
		
		ArrayList<boolean[]> trainingData = new ArrayList<boolean[]>();
		ArrayList<Boolean> trainingAnswers = new ArrayList<Boolean>();
		
		/*/
		trainingSet = new ArrayList<String>();
		trainingSet.add("TIL-LITS-SKAP-ENDE");
		/**/
		
		for (String word : trainingSet) {
			if (word.equals("") || word.equals("-")) continue;
			boolean[][] train = wordToBoolArray(word);
			boolean[] answers = wordToAnswers(word);
			for (int i = 0; i < train.length; ++i) {
				trainingData.add(train[i]);
				trainingAnswers.add(answers[i]);
			}
		}
		
		// Construct the proper arrays, because Java.
		boolean[][] _trainingData = new boolean[trainingData.size()][240];
		boolean[][] _trainingAnswers = new boolean[trainingAnswers.size()][1];
		for (int i = 0; i < trainingData.size(); ++i) {
			for (int j = 0; j < trainingData.get(i).length; ++j) {
				_trainingData[i][j] = trainingData.get(i)[j];
			}
			_trainingAnswers[i][0] = trainingAnswers.get(i);
		}
		
		int[] hidden = {100};
		Backpropagation mlp = new Backpropagation(240, hidden, 1);
		mlp.setIterations(10000);
		mlp.trainPatterns(_trainingData, _trainingAnswers);
		
		int correctWithDash = 0;
		int correctNoDash = 0;
		int total = 0;
		
		for (String testWord : testSet){
			//String testWord = "INN-STILL-ING-ER";
			boolean[][] _testInput = wordToBoolArray(testWord);
			
			double[][] testInput = new double[_testInput.length][_testInput[0].length];
			for (int i = 0; i < testInput.length; ++i) {
				for (int j = 0; j < testInput[0].length; ++j) {
					testInput[i][j] = _testInput[i][j] ? 1.0 : 0.0;
				}
			}
			
			boolean[] expected = wordToAnswers(testWord);
			
			for (int i = 0; i < testInput.length; ++i) {
				double[] testOutput = mlp.run(testInput[i]);
				++total;
				//if ((!expected[i]) )
			}
			
			/*
			System.out.print(testWord + " / ");
			
			for (int i = 0; i < testInput.length; ++i) {
				double[] testOutput = mlp.run(testInput[i]);
				System.out.print(cleanTestWord.charAt(i));
				if (testOutput[0] > 0.5) {
					System.out.print("-");
				}
			}
			String cleanTestWord = testWord.replace("-", "");
			System.out.println(cleanTestWord.charAt(cleanTestWord.length() - 1));
			*/
		}
	}

}
