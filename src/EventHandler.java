import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.jws.Oneway;
import javax.swing.ImageIcon;

public class EventHandler {
	
	int checkImages;
	public EventHandler() {
		
	}

	
	public static void handleProjectileEvent( Entity myEntity , char[][] TilesArray){ 
		for(int i=0;i<myEntity.Projectiles.size();i++){
			boolean test = false;
			if(myEntity.Projectiles.size()-1 >= i){
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
	}
	public static void handleBulletEntitycollision(Entity shooter, Entity shooted){
		
		for (int i = 0; i < shooter.Projectiles.size(); i++) {
			if (onSameTileType(shooter.Projectiles.get(i), shooted)) { // shooted got hit
				
//				shooter.Projectiles.remove(i);
				if(shooter.gotHit== true){ // if the person who shot got hit less than 1 second ago
					if(shooter.buffs.contains(InformationExpert.BUFF_AVENGER)){
						if( shooter.getDamage()>0) {
							shooter.setRelativeHealth(shooter.getDamage()-1);
						}
					}	
				}

		
					shooter.hitTimer.interrupt(); // used for the berzerker
					shooter.renewHit();
					shooter.hitTimer.start();
				
				if(shooted.gotHit == false){ // got hit more than 1 second before last hit
					shooted.setRelativeHealth(-shooter.getDamage());
					if( !shooted.gotHitTimer.isAlive()){
						shooted.renewGotHit();
						try {	Thread.sleep(10);} catch (InterruptedException e) {}
						shooted.gotHitTimer.start();
					}
				}
				
				if(shooted.gotHit == true){  // got hit less than 1 second before last hit  
					if(!shooted.buffs.contains(InformationExpert.BUFF_GUARDIAN_ANGEL)){ // guardian angel
						shooted.setRelativeHealth(-shooter.getDamage());
					}
					if(shooted.buffs.contains(InformationExpert.BUFF_REFLECTER)){
						System.out.println("Entered Reflecter trigger");
						int tempTrajx = shooter.Projectiles.get(i).trajectory.x * -1;
						int tempTrajy = shooter.Projectiles.get(i).trajectory.y * -1;

						shooted.Projectiles.add(new Projectile(shooted.projectileSpeedModifier+ InformationExpert.PROJECTILE_DEFAULT_SPEED,
								new Trajectory(tempTrajx, tempTrajy), shooted.getX()+10*tempTrajx, shooted.getY()+10*tempTrajy	) );
						
					}
						shooted.gotHit = false;
				}		
				
				
				// Entity that got hit is dead
				if (shooted.getHealth() <= 0) { 
					System.out.println("Dead enemy");
					shooted.setHealth(0);
					if(shooted.getId()==3){ // dead enemy is a bot
						System.out.println("Hit a bot :(");
						shooter.botkillCount++;
						
					
						for(int j=0;j<shooter.Buffs.size();j++){// used to display the gears
							
							if(shooter.getHealth()<shooter.maxHealth){
								if(shooter.Buffs.get(j).myIndex==InformationExpert.BUFF_BOT_KILLER){
									shooter.Buffs.get(j).counter++;
									if(shooter.Buffs.get(j).counter<5){
										// increases shooter's health by 2 if he has less than 5 charges used
										shooter.setRelativeHealth(2);
											
									}  
								}
							}
						} // finished displaying BOT KILLER's gears
						
					}// the dead enemy is a bot
					
				}// finish of dead enemy code
								
				
				
				
				if(!shooter.Projectiles.isEmpty()){
					shooter.Projectiles.remove(i);	
				}
			}
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


}
