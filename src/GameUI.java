import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameUI extends JFrame {
	private GameOfLife objTable;
	private Color dead = new Color(238, 238, 238);
	private Color alive = new Color(0,0,0);
	private JButton btnStart;
	private	JButton btnStop;
	private JButton btnStep;
	private boolean startClicked;
	java.util.Timer timer;
	TimerTask timerTask;
	
	private ActionListener bl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Cell cell = ((Cell)e.getSource());
			if(cell.getBackground() == Color.black)
				cell.setBackground(dead);
			else
				cell.setBackground( Color.black );
			cell.setColorToIsAlive();
		}
	};
	
	private ActionListener stopClick = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			try{
				timer.cancel();
			}catch(IllegalStateException e){
				e.printStackTrace();
			}
			startClicked = true;
			
			for(int i=0; i<objTable.getRow(); i++){
				for(int j=0; j<objTable.getColumn(); j++){
					objTable.setLifeByColor(i, j, dead);
				}
			}
		}
	};
	
	private ActionListener stepClick = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			try{
				timer.cancel();
			}catch(IllegalStateException e){
				e.printStackTrace();
			}
			startClicked = true;
			objTable.evolve();
		}
	};
	
	private ActionListener startClick = new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			if(startClicked){
				timer = new java.util.Timer();
				timerTask = new TimerTask() {
					@Override
					public void run() {
						objTable.evolve();
					}
				};
				timer.schedule(timerTask, 500, 500);
				startClicked = false;
			}
		}
	};
		
	public GameUI(){
		objTable = new GameOfLife(50, 50);
		btnStart = new JButton("Start");
		btnStop = new JButton("Stop");
		btnStep = new JButton("Step by Step");
		startClicked = true;
		JPanel gridPanel = new JPanel();
		JPanel panelButtons = new JPanel();
		JFrame gameFrame = new JFrame("Game of life");
		
		gridPanel.setSize(600,600);
		gridPanel.setLayout(new GridLayout(50,50));
		for(int i = 0; i < 50; i++)
			for(int j=0; j < 50; j++){
				objTable.getTable()[i][j].addActionListener(bl);
				gridPanel.add(objTable.getTable()[i][j]);
			}
		btnStart.addActionListener(startClick);
		btnStop.addActionListener(stopClick);
		btnStep.addActionListener(stepClick);
		
		panelButtons.add(btnStart);
		panelButtons.add(btnStep);
		panelButtons.add(btnStop);
		
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setSize(600,620);
		
		gameFrame.add(gridPanel);
		gameFrame.add(BorderLayout.SOUTH, panelButtons);
		
		gameFrame.setVisible(true);
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