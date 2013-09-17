import java.io.File;

public interface InputOutputLifeSimulation {
	
	public LifeSimulation readLifeSim(File selectedFile);
	public void writeLifeSim(String fileName, LifeSimulation simulation);
}
