import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameUI {
	private GameOfLife gameSim;
	public static final Color dead = new Color(255, 255, 255);
	public static final Color alive = new Color(0,0,0);
	private JButton [][] gridButtons;
	private JButton btnStart;
	private JButton btnClear;
	private JButton btnStep;
		
	public GameUI( GameOfLife simulation ){
		gameSim = simulation;
		btnStart = new JButton("Start");
		btnClear = new JButton("Clear");
		btnStep = new JButton("Step by Step");
		gridButtons = new JButton[gameSim.getRow()][gameSim.getColumn()];
		JPanel gridPanel = new JPanel();
		JPanel panelButtons = new JPanel();
		JFrame gameFrame = new JFrame("Conway's Game of life");
		
		gridPanel.setSize(600, 600);
		gridPanel.setLayout(new GridLayout(gameSim.getRow(), gameSim.getColumn()));
		for(int row=0; row < gameSim.getRow(); row++){
			for(int col=0; col < gameSim.getColumn(); col++){
				gridButtons[row][col] = new JButton("");
				setColor(row, col, dead);
				gridButtons[row][col].setName(row+","+col);
				gridPanel.add(gridButtons[row][col]);
			}
		}
		
		panelButtons.add(btnStart);
		panelButtons.add(btnStep);
		panelButtons.add(btnClear);
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setSize(600,620);
		
		gameFrame.add(gridPanel);
		gameFrame.add(BorderLayout.SOUTH, panelButtons);
		
		gameFrame.setVisible(true);
	}
	
	public void setColor(int row, int col, Color life){
		gridButtons[row][col].setBackground(life);
	}
	
	public void addBtnStartActionListener(ActionListener startClicked){
		btnStart.addActionListener(startClicked);
	}
	
	public void addBtnClearActionListener(ActionListener clearClicked){
		btnClear.addActionListener(clearClicked);
	}
	
	public void addBtnStepActionListener(ActionListener stepClicked){
		btnStep.addActionListener(stepClicked);
	}
	
	public void addGridButtonListener(ActionListener btnClicked){
		for(int row=0; row < gameSim.getRow(); row++){
			for(int col=0; col < gameSim.getColumn(); col++){
				gridButtons[row][col].addActionListener(btnClicked);
			}
		}
	}
}
