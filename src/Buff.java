import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Buff {
	public int speed = 0,fireRate = 0,damage = 0,health = 0,projectileSpeed = 0 , myIndex;
	public int counter = 0;
	public String description , name ,special;
	public BufferedImage myImage1 , myImage2, myImage3 , tempImage;
	public Buff(int index){
		myIndex = index;
		
	
		try{
		
		if(index == InformationExpert.BUFF_BLOOD_SEEKER){
			name = "BLOOD SEEKER";
			damage = 1;
			health = - InformationExpert.PLAYER_DEFAULT_HEALTH / 3;
			myImage1 = ImageIO.read(new File("res\\BUFF\\BLOOD SEEKER\\ENTITY_LEFT.png"));
			myImage2 = ImageIO.read(new File("res\\BUFF\\BLOOD SEEKER\\ENTITY_RIGHT.png"));
			myImage3 = ImageIO.read(new File("res\\BUFF\\BLOOD SEEKER\\ENTITY_FRONT.png"));
			tempImage = myImage3;
		}
		
		if(index == InformationExpert.BUFF_GUARDIAN_ANGEL){
			name = "GUARDIAN ANGEL";
			fireRate = InformationExpert.PLAYER_DEFAULT_FIRE_RATE / 4;
			special = "+SHIELD" ;
			myImage1 = ImageIO.read(new File("res\\BUFF\\GUARDIAN ANGEL\\ENTITY.png"));
			myImage2 = ImageIO.read(new File("res\\BUFF\\GUARDIAN ANGEL\\SHIELD.png"));
		}
		
		if(index == InformationExpert.BUFF_TANK){
			name = "TANK";
			health = InformationExpert.PLAYER_DEFAULT_HEALTH * 1 /3 ; // 28.03
			projectileSpeed = - 1;
			myImage1 = ImageIO.read(new File("res\\BUFF\\TANK\\ENTITY.PNG"));
		}
		
		
		if(index == InformationExpert.BUFF_SHARPSHOOTER){
			name = "SHARPSHOOTER";
			fireRate = InformationExpert.PLAYER_DEFAULT_FIRE_RATE;
			projectileSpeed = 2 ;
			damage = 1;
			myImage1 = ImageIO.read(new File("res\\BUFF\\SHARPSHOOTER\\ENTITY.png"));
		}

		if(index == InformationExpert.BUFF_AVENGER){
			speed = -1;
			name = "AVENGER";
			special = "+LIFESTEAL" ;
			myImage1 = ImageIO.read(new File("res\\BUFF\\AVENGER\\ENTITY.png"));
		}
		
		if(index == InformationExpert.BUFF_SPEEDSTER){
			name = "SPEEDSTER";
			speed = 2;
			projectileSpeed = - 2;
			myImage1 = ImageIO.read(new File("res\\BUFF\\SPEEDSTER\\ENTITY.png"));
		}
		
		if(index == InformationExpert.BUFF_REPEATER){
			name = "REPEATER";
			damage = -1;
			fireRate = - InformationExpert.PLAYER_DEFAULT_FIRE_RATE / 2;
			myImage1 = ImageIO.read(new File("res\\BUFF\\REPEATER\\ENTITY.png"));
		}
		if(index == InformationExpert.BUFF_BERZERKER){
			name = "BERZERKER";
			health = - InformationExpert.PLAYER_DEFAULT_HEALTH /3;
			special = "+STATS WHEN IT \n HITS ENEMIES";
			myImage1 = ImageIO.read(new File("res\\BUFF\\BERZERKER\\ENTITY.png"));
			myImage2 = ImageIO.read(new File("res\\BUFF\\BERZERKER\\RING.png"));
		}
		if(index == InformationExpert.BUFF_BOT_KILLER){
			name = "BOT KILLER";
			fireRate = InformationExpert.PLAYER_DEFAULT_FIRE_RATE * 3 / 8;
			special = "+2 HEALTH WHEN \nIT KILLS BOTS";
			myImage1 = ImageIO.read(new File("res\\BUFF\\BOT KILLER\\ENTITY.png"));
			
		}
		if( index == InformationExpert.BUFF_REFLECTER){
			name = "REFLECTER";
			speed = -1;
			special = "REFLECTS SOME\nENEMY BULLETS ";
			myImage1 = ImageIO.read(new File("res\\BUFF\\REFLECTER\\SHIELD_READY.png"));
			myImage2 = ImageIO.read(new File("res\\BUFF\\REFLECTER\\SHIELD_NOT_READY.png"));
		}
		if( index == InformationExpert.BUFF_GHOST){
			name = "GHOST";
//			projectileSpeed = -2;
//			speed = -2;
//			damage = -2;
			special ="GOES THROUGH \nWALLS";
			myImage1 = ImageIO.read(new File("res\\BUFF\\GHOST\\ENTITY.png"));
		}
		name ="$"+ name;  // $ is used to identify the name and to place a buffFrame accordingly
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
		
		
		if(numLines<5){
			int numLinesToAdd = 5 - numLines ;
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
		if(myIndex == InformationExpert.BUFF_BOT_KILLER){
//			System.out.println(counter);
			if(counter>0){
				g.drawImage(myImage1, x-15, y, null); // 1
				
				if(counter>1){
					g.drawImage(myImage1, x+45 ,y, null); //2
				
					if(counter>2){
						g.drawImage(myImage1, x-5, y- 15, null); //3
						
						if(counter>3){
							g.drawImage(myImage1, x+35, y-15, null);//4
							
							if(counter>4){
								g.drawImage(myImage1, x+15, y- 25, null); //5

							}
						}
					}
				}
			}
			
		}
		if(myIndex == InformationExpert.BUFF_REFLECTER){
			if(!beforeEntity){
				if(gotHit){
					g.drawImage(myImage1, x-15, y-22, null);
					
				}else{
					g.drawImage(myImage2, x-15, y-22, null);
				}
			}
		}
//		if(myIndex == InformationExpert.BUFF_GHOST){
//			g.drawImage(myImage1, x+5, y-10, null);
//		}
		
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
