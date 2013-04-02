import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Buff {
	public int speed = 0,fireRate = 0,damage = 0,health = 0,projectileSpeed = 0 , myIndex;
	public String description , name ,special;
	public BufferedImage myImage1 , myImage2, myImage3 , tempImage;
	public Buff(int index){
		myIndex = index;
		
	
		try{
		
		if(index == InformationExpert.BUFF_BLOOD_SEEKER){
			name = "BLOOD SEEKER";
			damage = 1;
			health = - InformationExpert.PLAYER_DEFAULT_HEALTH / 3;
			myImage1 = ImageIO.read(new File("res\\BUFF_BLOOD_SEEKER_LEFT.png"));
			myImage2 = ImageIO.read(new File("res\\BUFF_BLOOD_SEEKER_RIGHT.png"));
			myImage3 = ImageIO.read(new File("res\\BUFF_BLOOD_SEEKER_FRONT.png"));
			tempImage = myImage3;
		}
		
		if(index == InformationExpert.BUFF_GUARDIAN_ANGEL){
			name = "GUARDIAN ANGEL";
			fireRate = InformationExpert.PLAYER_DEFAULT_FIRE_RATE / 4;
			special = "+SHIELD" ;
			myImage1 = ImageIO.read(new File("res\\BUFF_GUARDIAN_ANGEL.png"));
			myImage2 = ImageIO.read(new File("res\\BUFF_GUARDIAN_ANGEL_SHIELD.png"));
		}
		
		if(index == InformationExpert.BUFF_TANK){
			name = "TANK";
			health = InformationExpert.PLAYER_DEFAULT_HEALTH * 2 /3 ; // 28.03
			speed = -1;
			projectileSpeed = - 1;
			myImage1 = ImageIO.read(new File("res\\BUFF_TANK.PNG"));
		}
		
		
		if(index == InformationExpert.BUFF_SHARPSHOOTER){
			name = "SHARPSHOOTER";
			fireRate = InformationExpert.PLAYER_DEFAULT_FIRE_RATE;
			projectileSpeed = 2 ;
			damage = 1;
			myImage1 = ImageIO.read(new File("res\\BUFF_SHARP_SHOOTER.png"));
		}

		if(index == InformationExpert.BUFF_AVENGER){
			speed = -1;
			name = "AVENGER";
			special = "+LIFESTEAL" ;
			myImage1 = ImageIO.read(new File("res\\BUFF_AVENGER.png"));
		}
		
		if(index == InformationExpert.BUFF_SPEEDSTER){
			name = "SPEEDSTER";
			speed = 2;
			projectileSpeed = - 2;
			myImage1 = ImageIO.read(new File("res\\BUFF_SPEEDSTER.png"));
		}
		
		if(index == InformationExpert.BUFF_REPEATER){
			name = "REPEATER";
			damage = -1;
			fireRate = - InformationExpert.PLAYER_DEFAULT_FIRE_RATE / 2;
			myImage1 = ImageIO.read(new File("res\\BUFF_REPEATER.png"));
		}
		if(index == InformationExpert.BUFF_BERZERKER){
			name = "BERZERKER";
			health = - InformationExpert.PLAYER_DEFAULT_HEALTH /3;
			special = "+STATS WHEN HITS";
			myImage1 = ImageIO.read(new File("res\\BUFF_BERZERKER.png"));
			myImage2 = ImageIO.read(new File("res\\BUFF_BERZERKER_RING.png"));
		}
		name ="$"+ name;
		description = name +"\n";
		if(damage !=0){	description+=("DAMAGE "+getSign(damage)+"\n");	}
		if(speed !=0){	description+=("SPEED "+getSign(speed)+"\n");	}
		if(fireRate !=0){	description+=("FIRE RATE "+getSign(fireRate)+"\n");	}
		if(health !=0){	description+=("HEALTH "+getSign(health)+"\n");	}
		if(projectileSpeed !=0){	description+=("P.SPEED "+getSign(projectileSpeed)+"\n");	}
		if(special!=null){	description+=(special+"\n");	}
		
		int numLines = 0;
		Scanner getNumLines = new Scanner(description);
		while(getNumLines.hasNextLine()){
			String temp = getNumLines.nextLine();
			numLines++;
		}
		getNumLines.close(); 
		
		
		if(numLines<4){
			int numLinesToAdd = 4 - numLines ;
			for(int i=0;i<numLinesToAdd;i++){
				description+="\n";
			}
		}
		
		}catch(IOException ignored){}
	}
	public String getSign(int x){
		String temp;
		if(x>0){
			temp = "+"+x;
		}else temp = "-"+Math.abs(x);
		return temp;
	}	
	public void drawBuff(Graphics g , int x , int y, int trajectoryX, int trajectoryY , boolean beforeEntity , boolean gotHit,boolean hit){
		
		if(myIndex == InformationExpert.BUFF_BLOOD_SEEKER){
			if(beforeEntity){
				int xTempLocation = 0  , yTempLocation ;
				if(trajectoryX == -1){	tempImage = myImage1 ;  xTempLocation = x;	}	
				if(trajectoryX == 1 ){	tempImage = myImage2 ; xTempLocation = x+14 ;	}
				if(trajectoryX == 0){		tempImage = myImage3; xTempLocation = x+7;	}
				g.drawImage(tempImage, xTempLocation, y-20, null);
			}
		}
		
		if(myIndex == InformationExpert.BUFF_GUARDIAN_ANGEL){
			if(beforeEntity){
				if(trajectoryY!=-1){
					g.drawImage(myImage1, x-8, y-10, null);
				}
			}else{
				if(trajectoryY==-1){
					g.drawImage(myImage1, x-8, y-10, null);
				}
				if(gotHit){
					g.drawImage(myImage2, x-20, y-30, null);
				}
			}
			
		}
	
		if(myIndex == InformationExpert.BUFF_TANK){
			if(beforeEntity){
				if(trajectoryY==-1){
					g.drawImage(myImage1, x+25, y+12, null);
				}
			}else{
				if(trajectoryY!=-1){
					g.drawImage(myImage1, x+25, y+20, null);
				}
			}
		}
		
		if(myIndex == InformationExpert.BUFF_SHARPSHOOTER){
			if(!beforeEntity){
				if(trajectoryY!=-1){
					g.drawImage(myImage1, x+4, y+10, null);
				}
			}
		}
		
		if(myIndex == InformationExpert.BUFF_AVENGER){
			g.drawImage(myImage1, x-5, y-30, null);
		}
		
		if(myIndex == InformationExpert.BUFF_SPEEDSTER){
			
				if(		(beforeEntity && ((trajectoryX==-1 && trajectoryY!=-1) ||	(trajectoryX==1 && trajectoryY==-1)		))
					||	( !beforeEntity && ((trajectoryX==-1 && trajectoryY==-1) ||	(trajectoryX==1 && trajectoryY!=-1)	))
					){
					g.drawImage(myImage1, x+2, y+27, null);	
				}
			
				if(		(beforeEntity && ((trajectoryX==1 && trajectoryY!=-1) ||	(trajectoryX==1 && trajectoryY==-1)		))
						||	( !beforeEntity && ((trajectoryX==-1 && trajectoryY!=-1) ||	(trajectoryX==1 && trajectoryY==-1)	))
						)
				{
					g.drawImage(myImage1, x+25, y+27, null);
				}
			
		}
		if(myIndex == InformationExpert.BUFF_REPEATER){
			if(!beforeEntity){
				if(trajectoryY == -1){
					g.drawImage(myImage1, x+12, y+11, null);
					g.drawImage(myImage1, x+23, y+11, null);
				}
			}
		}
		if(myIndex == InformationExpert.BUFF_BERZERKER){
			if(beforeEntity){
					if(hit){
						g.drawImage(myImage2, x-17, y+15, null);
					}
				
			}
			
			
			
			if(!beforeEntity){
				if(trajectoryY!= -1){
					g.drawImage(myImage1,x+19,y+9,null);
				}
			}
		}
		
	}
	public void applyBuff(Entity e){
		if(e.getId()==1){
			e.setDamage(e.getDamage()+ damage);
			e.setHealth(e.getHealth()+health);
			e.setFireRate(e.getFireRate()+ fireRate);
			e.setSpeed(e.getSpeed() + fireRate);
			e.projectileSpeedModifier+=projectileSpeed;
		}
	}
	public String toString(){
		return name;
	}
}
