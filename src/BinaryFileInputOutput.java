import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BinaryFileInputOutput implements InputOutputLifeSimulation {

	public LifeSimulation readLifeSim(File selectedFile) {
		FileInputStream fileInput;
		ObjectInputStream inputStream;
		LifeSimulation simulation = null;
		
		if (selectedFile != null) {
			try {
				fileInput = new FileInputStream(selectedFile);
				inputStream = new ObjectInputStream(fileInput);
				
				simulation = (LifeSimulation) inputStream.readObject();
				inputStream.close();
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException classEx) {
				classEx.getMessage();
			} catch (IOException ioEx) {
				ioEx.getMessage();
			}
			
			return simulation;
		}
		return new LifeSimulation(50, 50);
	}

	public void writeLifeSim(String fileName, LifeSimulation simulation) {
		if (fileName != null) {
			fileName += ".dat";
			FileOutputStream fileOutput;
			ObjectOutputStream outputStream;
			try {
				fileOutput = new FileOutputStream(fileName);
				outputStream = new ObjectOutputStream(fileOutput);
				outputStream.writeObject(simulation);
				
			} catch (FileNotFoundException fne) {
				fne.printStackTrace();
			} catch (IOException iox) {
				iox.printStackTrace();
			}
		}
	}
}
