import java.awt.Color;
import javax.swing.JButton;

public class Cell extends JButton{
	public boolean isAlive, nextGenIsAlive;
	
	Cell(){
		super();
		isAlive = false;
		nextGenIsAlive = false;
		setBackground(new Color(238, 238, 238));
	}
	
	public boolean getIsAlive(){
		return isAlive;
	}
	
	public void setLife(boolean life){
		isAlive = life;
	}
	
	public void setNextGenLife(boolean nextLife){
		nextGenIsAlive = nextLife;
	}
	
	public void setNextGenToCurrentGen(){
		if(nextGenIsAlive){
			isAlive = true;
			setBackground(Color.black);
		}
		else{
			isAlive = false;
			setBackground(new Color(238, 238, 238));
		}
		
	}
	
	public void setColorToIsAlive(){
		if(getBackground() == Color.black)
			isAlive = true;
		else
			isAlive = false;
	}
}

