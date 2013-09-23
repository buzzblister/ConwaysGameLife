import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileIO implements InputOutputLifeSimulation{

	public LifeSimulation readLifeSim(File selectedFile) {
		Properties readGameLogicProperties = new Properties();
		FileInputStream fileInput;
		LifeSimulation simulation = null;
		int row;
		int col;
		String strStates;

		if (selectedFile != null) {
			try {
				fileInput = new FileInputStream(selectedFile);
				readGameLogicProperties.load(fileInput);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			row = Integer.parseInt(readGameLogicProperties.getProperty("rows", "50"));
			col = Integer.parseInt(readGameLogicProperties.getProperty("columns", "50"));
			strStates = readGameLogicProperties.getProperty("states", "false");

			simulation = new LifeSimulation(row, col);
			String[] arrStates = strStates.split(" ");
			int index = 0;

			try {
				for (int i=0; i < row; i++) {
					for (int j=0; j < col; j++) {
						simulation.setLife(i, j, Boolean.parseBoolean(arrStates[index]));
						++index;
					}
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.println("Illegal states! Loading default values..");
			}
			return simulation;
		}
		return new LifeSimulation(50, 50);
	}

	public void writeLifeSim(String fileName, LifeSimulation simulation) {
		Properties gameLogicProperties = new Properties();
		FileOutputStream fileOutput;
		if (fileName != null) {
			fileName += ".cfg";
			try {
				fileOutput = new FileOutputStream(fileName);
				gameLogicProperties.put("rows", Integer.toString(simulation.getRow()));
				gameLogicProperties.put("columns", Integer.toString(simulation.getColumn()));
				
				String strStates = new String();
				for (int row=0; row < simulation.getRow(); row++) {
					for (int column=0; column < simulation.getColumn(); column++) {
						strStates += simulation.getLife(row, column) + " ";
					}
				}

				gameLogicProperties.put("states", strStates);
				gameLogicProperties.store(fileOutput, "Properties for Game of life");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
