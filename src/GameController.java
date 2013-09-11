import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;


public class GameController {
	private GameOfLife gameLogic;
	private GameUI userInterface;
	private Timer timer;
	private TimerTask task;
	private boolean isRunning;
	
	public GameController(GameOfLife logic, GameUI uInterface) {
		gameLogic = logic;
		userInterface = uInterface;
		isRunning = false;
		timer = new Timer();
		
		userInterface.addBtnStartActionListener(startClicked);
		userInterface.addBtnStepActionListener(stepClicked);
		userInterface.addBtnClearActionListener(clearClicked);
		userInterface.addGridButtonListener(panelButtonClicked);
	}
	
	private ActionListener panelButtonClicked = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JButton btn = ((JButton)e.getSource());
			if(btn.getBackground() == GameUI.alive){
				btn.setBackground(GameUI.dead);
				gameLogic.setLifeByString(btn.getName(), false);
			}
			else{
				btn.setBackground(GameUI.alive);
				gameLogic.setLifeByString(btn.getName(), true);
			}
		}
	};

	private ActionListener clearClicked = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			try{
				timer.cancel();
			}catch(IllegalStateException e){
				e.printStackTrace();
			}
			isRunning = false;
		
			for(int row=0; row<gameLogic.getRow(); row++){
				for(int col=0; col<gameLogic.getColumn(); col++){
					gameLogic.setLife(row, col, false);
					userInterface.setColor(row, col, GameUI.dead);
				}
			}
		}
	};

	private ActionListener stepClicked = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			try{
				timer.cancel();
			}catch(IllegalStateException e){
				e.printStackTrace();
			}
			isRunning = false;
			gameLogic.evolve();
			setLifeInGrid();
		}
	};
	private void setLifeInGrid() {
		for(int row=0; row < gameLogic.getRow(); row++){
			for(int col=0; col < gameLogic.getColumn(); col++){
				if(gameLogic.getLife(row, col))
					userInterface.setColor(row, col, GameUI.alive);
				else
					userInterface.setColor(row, col, GameUI.dead);
			}
		}
	}

	private ActionListener startClicked = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			if(! isRunning){
				timer = new Timer();
				task = new TimerTask() {
					public void run() {
						gameLogic.evolve();
						setLifeInGrid();
					}
				};
				timer.schedule(task, 500, 500);
				isRunning = true;
			}
		}
	};
	
	public static void main(String[] args) {
		GameOfLife logic = new GameOfLife(50, 50);
		GameUI view = new GameUI(logic);
		GameController controller = new GameController(logic, view);
	}
}
