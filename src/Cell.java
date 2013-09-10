import java.awt.Color;
import javax.swing.JButton;

public class Cell extends JButton{
	private boolean isAlive;
	private boolean nextGenIsAlive;
	private final Color alive = new Color(0, 0, 0);
	private final Color dead = new Color(238, 238, 238);
	
	Cell(){
		super();
		isAlive = false;
		nextGenIsAlive = false;
		setBackground(dead);
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
		isAlive = nextGenIsAlive;
		if(nextGenIsAlive){
			setBackground(alive);
		}
		else{
			setBackground(dead);
		}
	}
	
	public void setColorToIsAlive(){
		if(getBackground() == Color.black)
			isAlive = true;
		else
			isAlive = false;
	}
}

