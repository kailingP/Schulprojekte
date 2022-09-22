import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileCopy {
	public static void main(String[] args) {
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		String filePath = "";
		try {
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter Filepath:\n");
			filePath = inputReader.readLine();
			File inputFile = new File(filePath);
			System.out.print("Enter Filepath of new File:\n");
			File targetFile = new File(inputReader.readLine());
			inStream = new FileInputStream(inputFile);
			outStream = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			inStream.close();
			outStream.close();
			System.out.println("Copied filecontent from " + inputFile + " to " + targetFile);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find inputfile " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
