package problem3;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageReader {
	private final static String BASE = "faces/";
	
	private final static String[] DIRECTIONS = {"left", "right", "straight", "up"};
	private final static String[] EXPRESSIONS = {"angry", "happy", "neutral", "sad"};
	private final static String[] COVERS = {"open", "sunglasses"};
	
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	public final static int STRAIGHT = 2;
	public final static int UP = 3;
	public final static int ANGRY = 0;
	public final static int HAPPY = 1;
	public final static int NEUTRAL = 2;
	public final static int SAD = 3;
	public final static int OPEN = 0;
	public final static int SUNGLASSES = 1;
	
	public static double[] readImage(String name, int direction) throws Exception {
		return readImage(name, direction, NEUTRAL, OPEN);
	}
	
	public static double[] readImage(String name, int direction, int expression, int cover) throws Exception {
		StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append(BASE);
		pathBuilder.append(name);
		pathBuilder.append("/");
		pathBuilder.append(name);
		pathBuilder.append("_");
		pathBuilder.append(DIRECTIONS[direction]);
		pathBuilder.append("_");
		pathBuilder.append(EXPRESSIONS[expression]);
		pathBuilder.append("_");
		pathBuilder.append(COVERS[cover]);
		pathBuilder.append("_4.pgm");
		
		//System.out.println("Reading " + pathBuilder.toString() + "...");
		Path path = Paths.get(pathBuilder.toString());
		byte[] contents = Files.readAllBytes(path);
		
		if (contents[0] != 'P' || contents[1] != '5') {
			throw new RuntimeException("Bad file: " + pathBuilder.toString());
		}
		
		int width = 0;
		int height = 0;
		int maximum = 0;
		int i = 3;
		while (contents[i] >= '0' && contents[i] <= '9') {
			width *= 10;
			width += contents[i++] - '0';
		}
		++i;
		while (contents[i] >= '0' && contents[i] <= '9') {
			height *= 10;
			height += contents[i++] - '0';
		}
		++i;
		while (contents[i] >= '0' && contents[i] <= '9') {
			maximum *= 10;
			maximum += contents[i++] - '0';
		}
		++i;
		
		
		double[] ret = new double[width*height];
		for (int j = 0; j < width * height; ++j, ++i) {
			double val = contents[i];
			if (val < 0) val += 128;
			val /= maximum;
			ret[j] = val;
		}
		
		FileOutputStream stream = new FileOutputStream("test.pgm");
		try {
			byte[] head = {'P', '5', 10, 51, 50, 32, 51, 48, 10, 49, 53, 54, 10};
			stream.write(head);
		    stream.write(contents);
		} finally {
		    stream.close();
		}
		
		return ret;
	}
}
