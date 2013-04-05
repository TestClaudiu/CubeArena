import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainGame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static EventHandler events = new EventHandler();
	public	static Animation A = new Animation();
	public static StartPanel initPanel = new StartPanel();
	public static PlayerInfo Info = new PlayerInfo();
	static Set<Integer> mySet = new HashSet<Integer>() ;
	
	public static JPanel gamePanel ;
	public static int state ,prevState ,keyCode;
	
	public MainGame() {
		
		// registering our new fonts so we can use them
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res\\Others\\acknowtt.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res\\Others\\Emulator.ttf")));
		} catch (FontFormatException | IOException e1) {}
		
		
		
		// Main Frame settings
		new JFrame("My game frame");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setName(" Cube Arena Alpha v.31");
		
		// The panel in which the actual game is played
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.setPreferredSize(new Dimension(600,700));	
		
		// the Animation-class panel in which the players move and the whole game is visually 
		gamePanel.add(A, BorderLayout.CENTER);
		
		// the PlayerInfo-class panel in which we display the players' health and name and other information
		gamePanel.add(Info,BorderLayout.SOUTH );
		
		state = 1; prevState = 0 ;
		add(gamePanel);
		add(initPanel);
		pack();
		
		addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}
			// react to pressed keys
			public void keyPressed(KeyEvent e) {
				
				keyCode = e.getKeyCode();	
				 // WE ARE IN THE BUFFS STATE
				if(state==InformationExpert.STATE_BUFFS){

					// PRESSING THE ENTER BUTTON USUALLY 
					if(keyCode == InformationExpert.KEY_ENTER){
						if(initPanel.buffIndexesP1.size()==3 && initPanel.buffIndexesP2.size()==3){
							state = InformationExpert.STATE_GAME;
							A.firstPlayer.Buffs.clear() ; A.secondPlayer.Buffs.clear();
							A.firstPlayer.buffs.clear(); A.secondPlayer.buffs.clear();
							
							initPanel.updateEntityBuffs(A.firstPlayer);
							A.firstPlayer.applyBuffs();
							initPanel.updateEntityBuffs(A.secondPlayer);
							A.secondPlayer.applyBuffs();
							
						}else{
							initPanel.notEnoughBuffs= true;
						}
					}
					initPanel.handleButtons(keyCode,A.firstPlayer,A.secondPlayer);	
					initPanel.repaint();
				}
				
				if(state== InformationExpert.STATE_GAME){ // if we are in a game	
					if(!mySet.contains(keyCode)){		// 12.03.2013
						
						updateTrajectory(A.firstPlayer.moveTrajectory, InformationExpert.KEY_P1_GO_LEFT, InformationExpert.KEY_P1_GO_UP,
								InformationExpert.KEY_P1_GO_RIGHT, InformationExpert.KEY_P1_GO_DOWN, "Pressed");
						updateTrajectory(A.secondPlayer.moveTrajectory, InformationExpert.KEY_P2_GO_LEFT, InformationExpert.KEY_P2_GO_UP,
								InformationExpert.KEY_P2_GO_RIGHT, InformationExpert.KEY_P2_GO_DOWN, "Pressed" );
						updateTrajectory(A.firstPlayer.shootTrajectory, InformationExpert.KEY_P1_SHOOT_LEFT, InformationExpert.KEY_P1_SHOOT_UP,
								InformationExpert.KEY_P1_SHOOT_RIGHT, InformationExpert.KEY_P1_SHOOT_DOWN, "Pressed");
						updateTrajectory(A.secondPlayer.shootTrajectory, InformationExpert.KEY_P2_SHOOT_LEFT, InformationExpert.KEY_P2_SHOOT_UP,
								InformationExpert.KEY_P2_SHOOT_RIGHT, InformationExpert.KEY_P2_SHOOT_DOWN, "Pressed" );						
					}
					
					if ( keyCode == 27 ){ 
						A.firstPlayer.reset();
						A.secondPlayer.reset();
						state = 1;
						initPanel.repaint() ;
					}
					mySet.add(keyCode);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyCode = e.getKeyCode();
			
				mySet.remove(keyCode);
				if(state==InformationExpert.STATE_GAME){		// IF WE ARE IN A GAME STATE
					
					updateTrajectory(A.firstPlayer.moveTrajectory, InformationExpert.KEY_P1_GO_LEFT, InformationExpert.KEY_P1_GO_UP,
							InformationExpert.KEY_P1_GO_RIGHT, InformationExpert.KEY_P1_GO_DOWN, "Released");
					updateTrajectory(A.secondPlayer.moveTrajectory, InformationExpert.KEY_P2_GO_LEFT, InformationExpert.KEY_P2_GO_UP,
							InformationExpert.KEY_P2_GO_RIGHT, InformationExpert.KEY_P2_GO_DOWN, "Released" );
					updateTrajectory(A.firstPlayer.shootTrajectory, InformationExpert.KEY_P1_SHOOT_LEFT, InformationExpert.KEY_P1_SHOOT_UP,
							InformationExpert.KEY_P1_SHOOT_RIGHT, InformationExpert.KEY_P1_SHOOT_DOWN, "Released");
					updateTrajectory(A.secondPlayer.shootTrajectory, InformationExpert.KEY_P2_SHOOT_LEFT, InformationExpert.KEY_P2_SHOOT_UP,
							InformationExpert.KEY_P2_SHOOT_RIGHT, InformationExpert.KEY_P2_SHOOT_DOWN, "Released" );
					
		
				} // ENDED BEING IN STATE = 2 ( GAME PHASE )
				
			}
		});
		setVisible(true);
		setResizable(false);
		setSize(new Dimension(600, 750));
		pack();
	}
	
	public static void main(String[] args) {
		
		A.handleInput();
		A.handleBotGenerator();
		A.handleBotInput();
		A.handleBotTrajectory();
		String fName = JOptionPane.showInputDialog(null, "Enter First player's name !");
		A.firstPlayer.name = fName;
		String sName = JOptionPane.showInputDialog(null, "Enter Second player's name !");
		A.secondPlayer.name = sName;
		
		
		MainGame main = new MainGame();
		
		
		// this timer is used to repaint the Animation panel 
		repainterTask taskMain = main.new repainterTask(); 
		Timer timerMain = new Timer();
		timerMain.scheduleAtFixedRate(taskMain, 0, 1000/InformationExpert.FRAME_RATE);
		
		// this timer is used to change panels' visibility based on the current state
		updateStateTask taskA = main.new updateStateTask();
		Timer timerA = new Timer();
		timerA.schedule(taskA, 0, 100);
		
		 // this timer is used to check if any of the players is dead . If so , it asks if we want to play again
		testForWinTask taskB = main.new testForWinTask();
		Timer timerB = new Timer();
		timerB.schedule(taskB, 0, 100);
		
		 // this timer is used to update the health information during the game
		updateInfoTask taskC = main.new updateInfoTask();
		Timer timerC = new Timer();
		timerC.schedule(taskC, 0, 100);
		
		
	}
	public void updateTrajectory(Trajectory myTrajectory , final int LEFT , int UP , int RIGHT , int DOWN , String typeOfKeyEvent){
			if(typeOfKeyEvent.equals("Pressed")){
				if(keyCode == LEFT ) { 	 A.firstPlayer.updateTrajectory(myTrajectory,'-', '0') ;  }
				if(keyCode == UP ) {	A.firstPlayer.updateTrajectory(myTrajectory,'0', '-') ; }
				if(keyCode == RIGHT) { A.firstPlayer.updateTrajectory(myTrajectory,'+', '0') ; }
				if(keyCode == DOWN) { A.firstPlayer.updateTrajectory(myTrajectory,'0', '+') ; }
			}
			if(typeOfKeyEvent.equals("Released")){
				if(keyCode == LEFT ) { 	 A.firstPlayer.updateTrajectory(myTrajectory,'+', '0') ;  }
				if(keyCode == UP ) {	A.firstPlayer.updateTrajectory(myTrajectory,'0', '+') ; }
				if(keyCode == RIGHT) { A.firstPlayer.updateTrajectory(myTrajectory,'-', '0') ; }
				if(keyCode == DOWN) { A.firstPlayer.updateTrajectory(myTrajectory,'0', '-') ; }
			}
					
		
	}
	class updateStateTask extends TimerTask{
		public void run(){
			
			if(state != prevState){
				if(state == 1){
					initPanel.setVisible(true);
					gamePanel.setVisible(false);
					A.isRunning = false;
				}
				if(state == 2){
					initPanel.setVisible(false);
					gamePanel.setVisible(true);
					A.isRunning= true;
				}
				prevState = state;
				pack();

			}
			
		}
	}
	class repainterTask extends TimerTask{
		public void run(){
			if(state==2){
			A.repaint();
			}
		}
	}
	class updateInfoTask extends TimerTask{
		public void run(){
			if(state==2){
				Info.update(A.firstPlayer, A.secondPlayer);
			}
		}
	}
	class testForWinTask extends TimerTask{
		
		public void run(){
			if(state==2){
				if (A.firstPlayer.getHealth() <= 0 || A.secondPlayer.getHealth() <= 0) {
					
					mySet.clear();
					A.firstPlayer.reset();
					A.secondPlayer.reset();
					A.botList.clear();

					// First player is dead
					if (A.firstPlayer.getHealth() <= 0) {
						JOptionPane.showMessageDialog(null, "Player 2 Won !");
						A.secondPlayer.setRoundsWon(A.secondPlayer.getRoundsWon()+1);
					}
					
					// second player is dead
					if (A.secondPlayer.getHealth() <= 0) {
						JOptionPane.showMessageDialog(null, "Player 1 Won !");
						A.firstPlayer.setRoundsWon(A.firstPlayer.getRoundsWon()+1);
					}
					
					
					try {
						Thread.sleep(50);
					} catch (Exception ignored) {}
					
	
					int answer = JOptionPane.showConfirmDialog(null,
							"Would you like to play again ?");
					if (answer == 0) {					
						A.myMaps.next(); // go to next 
						A.cherryIndex = '0';
						
						mySet.clear();
						A.firstPlayer.setHealth(InformationExpert.PLAYER_DEFAULT_HEALTH);
						A.secondPlayer.setHealth(InformationExpert.PLAYER_DEFAULT_HEALTH);
						A.firstPlayer.applyBuffs();
						A.secondPlayer.applyBuffs();
					} else {
						JOptionPane
								.showMessageDialog(null,
										"Thanks for playing . This game was made by Claudiu Bele");
						System.exit(1);
					}
				}
			}
		}
	
	}

}
