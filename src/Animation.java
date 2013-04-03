import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.BoldAction;

public class Animation extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -893501521741612972L;


	public InputHandler player1,player2;
	public static BufferedImage  tile, cherry, wall,wallExtended,crown;
	public static BufferedImage  goldenCherry ,bg ,bullet1,bullet2,bullet3;

	public char[][] tiles;
	public static char[][] temporaryTiles;
	
	public char cherryIndex = '0';
	private BufferedImage bf;

	public Random rand ;
	public static TileMap map;
	public static EventHandler eventHandler;
	ArrayList<Entity> 	botList = new ArrayList<>();
	public Entity firstPlayer, secondPlayer;
	public MapChooser myMaps;
	public boolean isRunning = false;
	public Animation() {
		
		setSize(new Dimension(600, 600));
		setPreferredSize(this.getSize());
		setVisible(true);
		
		rand = new Random();                  
		eventHandler = new EventHandler();
		getPictures();
		firstPlayer = new Entity(0, 0, 1);
		secondPlayer = new Entity(0,0,  2);
		myMaps = new MapChooser(this);
		myMaps.next();
		
		bf = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_RGB);
		
	}

	public boolean onTileType(Entity e, char c) {
		boolean test = false;
		if (tiles[(e.getX()+5) / 50][(e.getY()+5) / 50 ] == c   // left-up
				|| tiles[(e.getX() + 45 ) / 50][(e.getY() + 45) / 50] == c // right-down
				|| tiles[(e.getX() + 45) / 50][(e.getY() +5) / 50] == c
				|| tiles[((e.getX()+5) / 50)][(e.getY() + 45) / 50] == c) {
			test = true;
		}
		return test;
	}

	public void generateTileMap(int mapNum) {
		switch (InformationExpert.MAP_GENERATE_TYPE){
			case InformationExpert.MAP_RANDOM : 	map = new TileMap();  break;
			case InformationExpert.MAP_PREMADE :	map = new TileMap(mapNum); break; 
		}
		firstPlayer.setX(map.getMyX());
		firstPlayer.setY(map.getMyY());
		secondPlayer.setX(map.getMySecondX());
		secondPlayer.setY(map.getMysecondY());
		tiles = map.getTiles();
		temporaryTiles = map.getTemporaryTiles();
	}

	public void run() {
		setVisible(true);
		repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		
		drawPictures(bf.getGraphics());
		g.drawImage(bf, 0, 0, null);
	}
	public void handleInput(){
		final InputHandler player1 = new InputHandler(this.firstPlayer, this);
		final InputHandler player2 = new InputHandler(this.secondPlayer, this);	
		
		class inputReaction extends TimerTask{
			public void run(){
				player1.doRun();
				player2.doRun();
			}
		}
		inputReaction input = new inputReaction();
		Timer myTimer = new  Timer();
		myTimer.schedule(input, 0, 10);
	}
public void handleBotGenerator(){
		Thread botGen = new Thread(){
			public void run(){
				while(true){
					int respawn = (rand.nextInt(8)+7)*1000;
					try {		Thread.sleep(respawn);		}		 catch (InterruptedException e) {	}
					
					if(firstPlayer.getHealth()>0 && secondPlayer.getHealth()>0 && isRunning){
						
						int x , y;
						do{
						x = rand.nextInt(12); y = rand.nextInt(12);
						}while(		tiles[x][y]!='B'	);
							Entity tempBot = new Entity(x*50, y*50, 3);
							if(x==5 || x==6){ tempBot.moveTrajectory.x=0 ;}else if(x<5){	tempBot.moveTrajectory.x=1 ;	}else  { tempBot.moveTrajectory.x=-1; }
							if(y==5 || y==6){ tempBot.moveTrajectory.y=0 ;}else if(y<5){	tempBot.moveTrajectory.y=1 ;	}else { tempBot.moveTrajectory.y=-1; }
							botList.add(tempBot);
					}
				}
			}
		};
		botGen.start();
	}
	public void handleBotTrajectory(){
		Thread botTrajectoryRandomizer = new Thread(){
			public void run(){
				while(true){
					try {		Thread.sleep(500);		}		 catch (InterruptedException e) {	}
					
					if(firstPlayer.getHealth()>0 && secondPlayer.getHealth()>0 && isRunning){
						
						for(int i=0 ; i < botList.size();i++){
							botList.get(i).randomizeTrajectory();
						}
						
						
						
					}
				}
			}
		};botTrajectoryRandomizer.start();
	}
	public void handleBotInput(){
		final Animation myTempAnimation = this;
//		ArrayList<InputHandler> myInputs = new ArrayList<>();
		Thread botInputUpdater = new Thread(){
			public void run(){
				while(true){
					try {		Thread.sleep(10);		}		 catch (InterruptedException e) {	}
					
					if(firstPlayer.getHealth()>0 && secondPlayer.getHealth()>0 && isRunning){
						ArrayList<InputHandler> myInputs = new ArrayList<>();
						for(int i=0;i<botList.size();i++){
							myInputs.add(new InputHandler(botList.get(i), myTempAnimation));
						}
						for(int i=0;i<myInputs.size();i++){
							myInputs.get(i).doRun();
						}
					}
				}
			}
		};botInputUpdater.start();
		
	}
	public void update(Graphics g) { // to override the update method
		paint(g);	
	}

	public void drawPictures(Graphics g) {
		
		g.drawImage(bg, 0, 0, null);
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				if(tiles[i][j]=='x'){
					g.drawImage(wall,i * 50, j * 50-30 + 0, null);
				}
				if(tiles[i][j]=='X'){
					g.drawImage(wall,i * 50, j * 50-30 + 0, null);
				}
			}
					
		}		
		
		
		for(int i=0; i< tiles.length;i++){
			for(int j=0;j<tiles.length;j++){
			EventHandler.handleCherryEvent(g, "firstCherry", i, j, cherryIndex,
					tiles,null);
			EventHandler.handleCherryEvent(g, "secondCherry", i, j, cherryIndex,
					tiles, temporaryTiles);
			EventHandler.handleCherryEvent(g, "thirdCherry", i, j, cherryIndex,
					tiles, temporaryTiles);
			EventHandler.handleCherryEvent(g, "goldenCherry", i, j, cherryIndex,
					tiles, temporaryTiles);
		}
	}
		for(int i=0;i<firstPlayer.Projectiles.size();i++){
			g.drawImage(bullet1, firstPlayer.Projectiles.get(i).getX(),firstPlayer.Projectiles.get(i).getY(), null);
		}
		for(int i=0;i<secondPlayer.Projectiles.size();i++){
			g.drawImage(bullet2, secondPlayer.Projectiles.get(i).getX(),secondPlayer.Projectiles.get(i).getY(), null);
		}
		for(int i=0;i<botList.size();i++){
			for(int j=0;j<botList.get(i).Projectiles.size();j++){
				g.drawImage(bullet3	, 		botList.get(i).Projectiles.get(j).getX()	,		botList.get(i).Projectiles.get(j).getY(), 	null);

			}
		}
		for(int i=0;i<botList.size();i++){
			botList.get(i).drawEntity(bf.getGraphics());
		}
		firstPlayer.drawEntity(bf.getGraphics());
		secondPlayer.drawEntity(bf.getGraphics());
		
		if(firstPlayer.getRoundsWon()>secondPlayer.getRoundsWon()){
			g.drawImage(crown, firstPlayer.getX(), firstPlayer.getY()-30, null);
		}else if(secondPlayer.getRoundsWon()>firstPlayer.getRoundsWon()){
			g.drawImage(crown, secondPlayer.getX(), secondPlayer.getY()-30, null);
		}
		
	
		
		EventHandler.handleBulletEntitycollision(firstPlayer, secondPlayer);
		EventHandler.handleBulletEntitycollision(secondPlayer, firstPlayer) ;
		for(int i=0;i< botList.size();i++){
			
			EventHandler.handleBulletEntitycollision(botList.get(i), secondPlayer);
			EventHandler.handleBulletEntitycollision(botList.get(i), firstPlayer) ;
			EventHandler.handleBulletEntitycollision(firstPlayer, botList.get(i));
			EventHandler.handleBulletEntitycollision(secondPlayer, botList.get(i)) ;
			if(botList.get(i).getHealth()==0){
				botList.remove(i);
			}
		}
		for(int i=0; i< tiles.length;i++){
			for(int j=0;j<tiles.length;j++){
				if(tiles[i][j]=='x'){
					g.drawImage(wallExtended,i * 50, j * 50-50 + 0, null);
					g.drawImage(wallExtended,i * 50, j * 50-35
							+ 0, null);
				}
				if(tiles[i][j]=='X'){
					g.drawImage(wallExtended,i * 50, j * 50-35 + 0, null);
					
				}	
			}
		}
			
	}

	public void getPictures() {
		try {
		
		goldenCherry = ImageIO.read(new File("res\\goldenCherry.png"));
		bg = ImageIO.read(new File("res\\bg.jpg"));
		cherry = ImageIO.read(new File("res\\cherry.png"));
		wall = ImageIO.read(new File("res\\Wall.png"));
		wallExtended = ImageIO.read(new File("res\\WallExtended.png"));
		crown = ImageIO.read(new File("res\\crown.png"));
		bullet1 = ImageIO.read(new File("res\\ProjectileHero1.png"));
		bullet2 = ImageIO.read(new File("res\\ProjectileHero2.png"));
		bullet3 = ImageIO.read(new File("res\\ProjectileBOT.png"));
		} catch (IOException e) {}
	}

}


