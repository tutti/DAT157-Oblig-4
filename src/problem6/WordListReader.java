package problem6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordListReader {
	
	public static List<String> getWords() {
		Scanner sc;
		try {
			sc = new Scanner(new BufferedReader(new FileReader("orddeling.txt")));
		} catch (FileNotFoundException e) {
			return null;
		}
		
		ArrayList<String> words = new ArrayList<String>();
		
		while (sc.hasNext()) {
			String word = sc.nextLine();
			word = word
				.replace("(", "")
				.replace(")", "")
				.replace(" ", "")
				.replace("_", "-")
				.replace("%", "")
				.replace("~-", "")
			;
			for (int i = 0; i < word.length(); ++i) {
				char c = word.charAt(i);
				if ((c < 'A' || c > 'Z') && c != '-' && c != 'Æ' && c != 'Ø' && c != 'Å') {
					//System.out.println("Unrecognised character " + c + " (" + (int)c + ") in word " + word + " - discarding word.");
					word = "discard";
					break;
				}
			}
			if (!word.equals("discard")) {
				words.add(word);
			}
		}
		
		sc.close();
		
		return words;
	}
}
