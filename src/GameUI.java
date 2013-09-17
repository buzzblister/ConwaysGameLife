import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameUI {
	
	private static final Color COLOR_DEAD = new Color(255, 255, 255);
	private static final Color COLOR_ALIVE = new Color(0,0,0);
	
	private JFrame gameFrame = new JFrame("Conway's Game of life");
	private JButton [][] gridButtons;
	private JButton btnStart;
	private JButton btnClear;
	private JButton btnStep;
	private JMenuItem menuItemStore;
	private JMenuItem menuItemLoad;
		
	public GameUI( LifeSimulation simulation ) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuItemStore = new JMenuItem("Store");
		menuItemLoad = new JMenuItem("Load");
		
		menuFile.getAccessibleContext().setAccessibleDescription("Load/Store game state from/to file");
		menuItemStore.getAccessibleContext().setAccessibleDescription("Store this state of game to file");
		menuItemLoad.getAccessibleContext().setAccessibleDescription("Load a game state from file");
		
		menuBar.add(menuFile);
		menuFile.add(menuItemLoad);
		menuFile.add(menuItemStore);
		
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
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setSize(600,620);
		gameFrame.setJMenuBar(menuBar);
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
		if (gameLogic != null) {
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
	
	public void addMenuItemLoadListener(ActionListener itemLoadClicked) {
		menuItemLoad.addActionListener(itemLoadClicked);
	}
	
	public void addMenuItemStoreListener(ActionListener itemStoreClicked) {
		menuItemStore.addActionListener(itemStoreClicked);
	}
	
	public String getFileName() {
		String inputFileName = JOptionPane.showInputDialog(gameFrame, "Enter file name without extension: ",
				"Save", JOptionPane.PLAIN_MESSAGE);
		return inputFileName;
	}
	
	public File fileBrowser() {
		JFileChooser fileChooser = new JFileChooser("path.dat");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Conway's game files", "dat", "xml", "cfg");
		File file;
		
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(gameFrame);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			return file;
		}
		return null;
	}
	
	public void warningMessage() {
		JOptionPane.showMessageDialog(gameFrame, "Can't load or save while game is Running!");
	}
}
