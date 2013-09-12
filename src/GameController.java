import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;

public class GameController {
	
	private LifeSimulation gameLogic;
	private GameUI userInterface;
	private Timer timer;
	private TimerTask task;
	private boolean isRunning;
	
	public GameController(LifeSimulation logic, GameUI uInterface) {
		gameLogic = logic;
		userInterface = uInterface;
		isRunning = false;
		timer = new Timer();
		
		setListeners();
	}
	
	private ActionListener gridButtonClicked = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JButton cell = ((JButton)e.getSource());
			userInterface.setLifeFromGrid(cell, gameLogic);
		}
	};

	private ActionListener clearClicked = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			stopTimer(timer);
			userInterface.clearGrid(gameLogic);
		}
	};

	private ActionListener stepClicked = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			stopTimer(timer);
			evolve();
		}
	};

	private ActionListener startClicked = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			startTask();
		}
	};
	
	private void startTask() {
		if (!isRunning) {
			timer = new Timer();
			task = new TimerTask() {
				public void run() {
					evolve();
				}
			};
			timer.schedule(task, 500, 500);
			isRunning = true;
		}
	}
	
	private void setListeners() {
		userInterface.addBtnStartActionListener(startClicked);
		userInterface.addBtnStepActionListener(stepClicked);
		userInterface.addBtnClearActionListener(clearClicked);
		userInterface.addGridButtonListener(gridButtonClicked, gameLogic);
	}
	
	private void evolve() {
		gameLogic.evolve();
		userInterface.setLifeInGrid(gameLogic);
	}
	
	private void stopTimer(Timer timer) {
		try {
			timer.cancel();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		isRunning = false;
	}
}
