import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class StoryReconstructor {
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		try (var reader = new StoryReader("story-input.txt")) {
			String[] values;
			Map<Integer, String> story = new TreeMap<Integer, String>();
			while ((values = reader.readNextLine()) != null) {
				story.put(Integer.parseInt(values[0]), values[1]);
			}
			story.values().forEach( v -> builder.append(v+" "));
		} catch (FileNotFoundException e) {
			System.out.print("File 'story-input.txt' not found!");
		} catch (IOException e) {
			System.out.println("IO Exception occured while parsing story-input.txt! Message:\n" + e.getMessage());
		}
		try {
			FileWriter writer = new FileWriter("Story.txt");
			writer.write(builder.toString().stripTrailing());
			writer.close();
		} catch (IOException e) {
			System.out.println("IO Error while writing file! Message:\n"+e.getMessage());
		}
	}
	
}
