import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameUI {
	
	private static final Color COLOR_DEAD = new Color(255, 255, 255);
	private static final Color COLOR_ALIVE = new Color(0,0,0);
	
	private JButton [][] gridButtons;
	private JButton btnStart;
	private JButton btnClear;
	private JButton btnStep;
		
	public GameUI( LifeSimulation simulation ) {
		btnStart = new JButton("Start");
		btnClear = new JButton("Clear/Stop");
		btnStep = new JButton("Step by Step");
		gridButtons = new JButton[simulation.getRow()][simulation.getColumn()];
		
		JPanel gridPanel = new JPanel();
		initializeGrid(gridPanel, simulation);
		
		JPanel panelButtons = new JPanel();
		panelButtons.add(btnStart);
		panelButtons.add(btnStep);
		panelButtons.add(btnClear);
		
		JFrame gameFrame = new JFrame("Conway's Game of life");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setSize(600,620);
		gameFrame.add(gridPanel);
		gameFrame.add(BorderLayout.SOUTH, panelButtons);
		gameFrame.setVisible(true);
	}
	
	private void initializeGrid(JPanel gridPanel, LifeSimulation simulation) {
		gridPanel.setSize(600, 600);
		gridPanel.setLayout(new GridLayout(simulation.getRow(), simulation.getColumn()));
		
		for (int row=0; row < simulation.getRow(); row++) {
			for (int col=0; col < simulation.getColumn(); col++) {
				gridButtons[row][col] = new JButton("");
				gridButtons[row][col].setBackground(COLOR_DEAD);
				gridButtons[row][col].setName(row+","+col);
				gridPanel.add(gridButtons[row][col]);
			}
		}
	}
	
	public void clearGrid(LifeSimulation gameLogic) {
		for (int row=0; row<gameLogic.getRow(); row++) {
			for (int col=0; col<gameLogic.getColumn(); col++) {
				gameLogic.setLife(row, col, false);
				gridButtons[row][col].setBackground(COLOR_DEAD);
			}
		}
	}
	
	public void setLifeInGrid(LifeSimulation gameLogic) {
		for (int row=0; row < gameLogic.getRow(); row++) {
			for (int col=0; col < gameLogic.getColumn(); col++) {
				if (gameLogic.getLife(row, col)) {
					gridButtons[row][col].setBackground(COLOR_ALIVE);
				}
				else {
					gridButtons[row][col].setBackground(COLOR_DEAD);
				}
			}
		}
	}
	
	public void setLifeFromGrid(JButton cell, LifeSimulation gameSimulation) {
		if (cell.getBackground() == COLOR_ALIVE) {
			cell.setBackground(COLOR_DEAD);
			gameSimulation.setLifeByString(cell.getName(), false);
		}
		else {
			cell.setBackground(COLOR_ALIVE);
			gameSimulation.setLifeByString(cell.getName(), true);
		}
	}
	
	//Action listeners of buttons..
	public void addBtnStartActionListener(ActionListener startClicked) {
		btnStart.addActionListener(startClicked);
	}
	
	public void addBtnClearActionListener(ActionListener clearClicked) {
		btnClear.addActionListener(clearClicked);
	}
	
	public void addBtnStepActionListener(ActionListener stepClicked) {
		btnStep.addActionListener(stepClicked);
	}
	
	public void addGridButtonListener(ActionListener btnClicked, LifeSimulation simulation) {
		for (int row=0; row < simulation.getRow(); row++) {
			for (int col=0; col < simulation.getColumn(); col++) {
				gridButtons[row][col].addActionListener(btnClicked);
			}
		}
	}
}
