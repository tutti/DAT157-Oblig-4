package problem6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileFixer {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("__orddeling.txt");
		byte[] data = Files.readAllBytes(path);
		
		for (int i = 0; i < data.length; ++i) {
			if (data[i] == -110) {
				//data[i] = 'e';
				data[i] = -58;
			} else if (data[i] == -99) {
				//data[i] = 'o';
				data[i] = -40;
			} else if (data[i] == -113) {
				//data[i] = 'a';
				data[i] = -59;
			}
			if ((data[i] < 'A' || data[i] > 'Z') && data[i] != '-' && data[i] != 'a' && data[i] != 'e' && data[i] != 'o' && data[i] != 10 && data[i] != 13) {
				System.out.println((char)data[i] + " " + data[i]);
			}
		}
		
		Files.write(new File("orddeling.txt").toPath(), data);
		
		System.out.println("Done.");
		
		/*System.out.println((byte)'Æ');
		System.out.println((byte)'Ø');
		System.out.println((byte)'Å');*/
	}
}
