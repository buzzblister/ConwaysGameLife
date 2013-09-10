import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import javax.swing.*;

public class GameUI extends JFrame {
	private GameOfLife objTable;
	private Color c = new Color(238, 238, 238);
	private JButton btnStart;
	private	JButton btnStop;
	private JButton btnStep;
	private boolean flag;
	java.util.Timer timer;
	TimerTask timerTask;
	
	private ActionListener bl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(((Cell)e.getSource()).getBackground() == Color.black)
				((Cell)e.getSource()).setBackground(c);
			else
				((Cell)e.getSource()).setBackground( Color.black );
			((Cell)e.getSource()).setColorToIsAlive();
		}
	};
	
	private ActionListener stopClick = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			try{
				timer.cancel();
			}catch(Exception e){
				
			}
			flag = true;
			for(int i=0; i<objTable.getRow(); i++){
				for(int j=0; j<objTable.getColumn(); j++){
					objTable.getTable()[i][j].setBackground(new Color(238, 238, 238));
					objTable.getTable()[i][j].setColorToIsAlive();
				}
			}
		}
	};
	
	private ActionListener stepClick = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			try{
				timer.cancel();
			}catch(Exception e){
				
			}
			flag = true;
			objTable.population();
			objTable.enterNextGeneration();
		}
	};
	
	private ActionListener startClick = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			if(flag){
				timer = new java.util.Timer();
				timerTask = new TimerTask() {
					@Override
					public void run() {
						objTable.population();
						objTable.enterNextGeneration();
					}
				};
				timer.schedule(timerTask, 500, 500);
				flag = false;
			}
		}
	};
		
	public GameUI(){
		objTable = new GameOfLife(50, 50);
		btnStart = new JButton("Start");
		btnStop = new JButton("Stop/Clear");
		btnStep = new JButton("Pause/Step by Step");
		flag = true;
		JPanel panel = new JPanel();
		JPanel panelButtons = new JPanel();
		
		panel.setSize(600,600);
		panel.setLayout(new GridLayout(50,50));
		for(int i = 0; i < 50; i++)
			for(int j=0; j < 50; j++){
				objTable.getTable()[i][j].addActionListener(bl);
				panel.add(objTable.getTable()[i][j]);
			}
		btnStart.addActionListener(startClick);
		btnStop.addActionListener(stopClick);
		btnStep.addActionListener(stepClick);
		
		panelButtons.add(btnStart);
		panelButtons.add(btnStep);
		panelButtons.add(btnStop);
		
		setTitle("Game Of life");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(600,620);
		
		add(panel);
		add(BorderLayout.SOUTH, panelButtons);
		
		setVisible(true);
	}
	
	static GameUI objGame;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				objGame = new GameUI();
			}
		} );
	}
}