import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.jws.Oneway;
import javax.swing.ImageIcon;

public class EventHandler {
	private static BufferedImage  goldenCherry,  cherry ,bullet1,bullet2 ;
	int checkImages;

	public EventHandler() {
		getPictures();
	}

	public static void handleCherryEvent(Graphics g, String toHandle, int i, int j,
			int index, char[][] TilesArray, char[][] additionalTilesArray) {

		if (toHandle.equals("firstCherry")) {
			if (TilesArray[i][j] == '1') {
				if (index == '0') { // Cherry is visible
					g.drawImage(cherry, i * 50, j * 50 + 0, null);
				} else { // Cherry is not Visible
					TilesArray[i][j] = '-'; // replace the character with a
											// default
					// one in the event that we go over the
					// tile again
				}
			}
		} // firstCherry Event handle

		if (toHandle.equals("secondCherry")) {
			if (additionalTilesArray[i][j] == '2') {
				/**
				  * * we put additionalTilesArray in here because we don't want
				  * to compare from the TilesArray array as the TilesArray[i][j]
				  * doesn't store the invisible cherries, as the user might go
				  * over an invisible one and trigger the next if, bypassing the
				  * one before.
				  */
				if (index == '1') { // cherry is visible
					TilesArray[i][j] = additionalTilesArray[i][j];
					/*
					 * we give the TilesArray[i][j] the value of the temp array,
					 * so event will be triggered
					 */
					g.drawImage(cherry, i * 50, j * 50 + 0, null);
				} else { // Cherry not visible
					TilesArray[i][j] = '-'; // replay the char with a default
											// one
				}
			}

		} // secondCherry Event handle

		if (toHandle.equals("thirdCherry")) {

			if (additionalTilesArray[i][j] == '3') {
				if (index == '2') { // cherry is visible
					TilesArray[i][j] = additionalTilesArray[i][j];
					g.drawImage(cherry, i * 50, j * 50 + 0, null);
				} else { // Cherry not visible
					TilesArray[i][j] = '-'; // replay the char with a default
											// one
				}
			}
		}// thirdCherry Event handle

		if (toHandle.equals("goldenCherry")) {
			if (additionalTilesArray[i][j] == '4' && index == '3') {
				TilesArray[i][j] = additionalTilesArray[i][j];
				g.drawImage(goldenCherry, i * 50, j * 50 + 0, null);
			}
		}
		
		
	

	}
	public static void handleProjectileEvent( Entity myEntity , char[][] TilesArray){ // Graphics g,
		for(int i=0;i<myEntity.Projectiles.size();i++){
			boolean test = false;
			if(myEntity.Projectiles.get(i).hasValidPosition() 
					&& !onTileType(myEntity.Projectiles.get(i), 'x', TilesArray) && !onTileType(myEntity.Projectiles.get(i), 'X', TilesArray)
					
					){
					test = true;
					myEntity.Projectiles.get(i).update();
				
					
				}
			else{
				myEntity.Projectiles.remove(i);
				i--;
				
			}
		}
	}
	public static void handleBulletEntitycollision(Entity shooter, Entity shooted){
		
		for (int i = 0; i < shooter.Projectiles.size(); i++) {
			if (onSameTileType(shooter.Projectiles.get(i), shooted)) { // shooted got hit
				
				shooter.Projectiles.remove(i);
				if(shooter.gotHit== true){ // if the person who shot got hit less than 1 second ago
					if(shooter.buffs.contains(InformationExpert.BUFF_AVENGER)){
						if( shooter.getDamage()>0) {
							shooter.setHealth(Math.min(shooter.getHealth()+shooter.getDamage()-1, shooter.maxHealth));
						}
					}	
				}

		
					shooter.hitTimer.interrupt(); // used for the berzerker
					shooter.renewHit();
					shooter.hitTimer.start();
				
				if(shooted.gotHit == false){ // got hit more than 1 second before last hit
					shooted.setHealth(shooted.getHealth() - shooter.getDamage());
					if( !shooted.gotHitTimer.isAlive()){
						shooted.renewGotHit();
						try {	Thread.sleep(10);} catch (InterruptedException e) {}
						shooted.gotHitTimer.start();
					}
				}
				
				if(shooted.gotHit == true){  // got hit less than 1 second before last hit  
					if(!shooted.buffs.contains(InformationExpert.BUFF_GUARDIAN_ANGEL)){ // guardian angel
						shooted.setHealth(shooted.getHealth() - shooter.getDamage());
					}
						shooted.gotHit = false;
				}		
				
				if (shooted.getHealth() < 0) {
					shooted.setHealth(0);
				}
			}
		}
	}
	public void getPictures() {
		try {
			cherry = ImageIO.read(new File("res\\cherry.png"));
		
		goldenCherry = ImageIO.read(new File("res\\goldenCherry.png"));
		bullet1 = ImageIO.read(new File("res\\ProjectileHero1.png"));
		bullet2 = ImageIO.read(new File("res\\ProjectileHero2.png"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static boolean onTileType(Projectile e, char c,char[][] TilesArray) {
		boolean test = false;
	
		if (TilesArray[(e.getX()-1+25) / 50][(e.getY()		+25) / 50 ] == c
				|| TilesArray[(e.getX() + 45 -1				-10) / 50][(e.getY() + 45 -1 - 15) / 50] == c
				|| TilesArray[((e.getX()-1) + 45		 -10) / 50][(e.getY()	 +25) / 50] == c
				|| TilesArray[((e.getX()-1+25) / 50)][(e.getY() + 45 -1 - 15) / 50] == c) {
			test = true;
			
		}
		return test;
	}
	public static boolean onSameTileType(Projectile P, Entity e) {
		boolean test = false;
		if (P.getX() >= e.getX() - 20 && P.getX() <= e.getX() + 25
				&& P.getY() >= e.getY() - 20 && P.getY() <= e.getY() + 20) {
			test = true;
		}
		return test;
	}
	public static void handleSameTrajectoryKeysEvent( int firstKey, int secondKey , int trajectoryAxis,int keyCode,Set<Integer> mySet){
		System.out.println(trajectoryAxis+"and the keyCode is "+keyCode+" , the first key is "+firstKey+" , the second key is "+secondKey+", the set is"+mySet);
		if((keyCode== firstKey &&  !mySet.contains(secondKey)) || (keyCode== secondKey  && !mySet.contains(firstKey))){ // if you pressed 2 buttons ( left- right or A- D) and you released one
			trajectoryAxis=0;
		}else if( keyCode== firstKey && mySet.contains(secondKey)){
			trajectoryAxis=1;
		}else if( keyCode== secondKey  && mySet.contains(firstKey)){
			trajectoryAxis=-1;
		}
		System.out.println(trajectoryAxis);
	}
	public static void handleBuffSelectionEvent(StartPanel initPanel, int goUpKey , int goDownKey , int selectedController ,int pressedKey){
			int myButton = 0 ;
			int P1 = initPanel.selectedButtonP1 , P2 = initPanel.selectedButtonP2 ;
			switch (selectedController) {
				case 1: myButton = initPanel.selectedButtonP1;	break;
				case 2: myButton = initPanel.selectedButtonP2; 	break;
			}
			
			if(pressedKey == goUpKey)
			{
				if(myButton==1){	myButton=5;
					}else{
						myButton--;
					}
//					initPanel.repaint();
				System.out.println(myButton+" , pressed up !");
			}
			if(pressedKey == goDownKey) 
			{
				if(myButton==5){	myButton= 1;
				}else{
					myButton++;
				}
				System.out.println(myButton+" , pressed down !");
			}
			switch (myButton) {
			case 1: P1 = myButton ; 	break;
			case 2: P2 = myButton	 ; break;
		}
			initPanel.updateSelectedButtons(P1, P2);
			initPanel.repaint();
				
	}
}
