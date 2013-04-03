import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;


public class Entity {
	
	public Set<Integer> buffs = new HashSet<Integer>();
	public ArrayList<Projectile> Projectiles = new ArrayList<Projectile>();
	
		BufferedImage img;
		BufferedImage guardianAngel , shield ,sight , cloud , battery , wheel , forceField , hornsLeft,hornsRight ,hornsCenter , horns ;

	long sysTime = new Date().getTime() , tempTime = sysTime; // USED  in the inputHandler
	Trajectory moveTrajectory = new Trajectory(0, 0);
	Trajectory shootTrajectory = new Trajectory(0, 0);
	EntityImageLibrary MyImages ;
	ArrayList<Buff> Buffs = new ArrayList<>();
	int initialX,initialY;
	int maxHealth;
	String name;

	
	private int roundsWon ;
	
	private int id;
	private int clip = 0;
	public int default_Damage , default_Speed , default_Health, default_FireRate; 
	private int damage,speed,health,fireRate;
	private int x;
	private int y;
	public int projectileSpeedModifier = 0;
	public Thread gotHitTimer , hitTimer;
	
	public boolean gotHit = false , hit = false;
	
	public Entity(int x, int y, int id){
		
		try {
			guardianAngel = ImageIO.read(new File("res\\BUFF_GUARDIAN_ANGEL.png"));
			shield = ImageIO.read(new File("res\\BUFF_TANK.PNG"));
			sight = ImageIO.read(new File("res\\BUFF_SHARP_SHOOTER.png"));
			cloud = ImageIO.read(new File("res\\BUFF_AVENGER.png"));
			battery = ImageIO.read(new File("res\\BUFF_REPEATER.png"));
			wheel = ImageIO.read(new File("res\\BUFF_SPEEDSTER.png"));
			forceField = ImageIO.read(new File("res\\BUFF_GUARDIAN_ANGEL_SHIELD.png"));
			hornsLeft = ImageIO.read(new File("res\\BUFF_BLOOD_SEEKER_LEFT.png"));
			hornsRight = ImageIO.read(new File("res\\BUFF_BLOOD_SEEKER_RIGHT.png"));
			hornsCenter = ImageIO.read(new File("res\\BUFF_BLOOD_SEEKER_FRONT.png"));
			horns = hornsLeft ; 
			
			
		} catch (IOException e) {}
		this.x = x ; this.y = y;
		this.id=id;

		if(id==1 || id ==2){ // if the entity is a player
			
			this.default_FireRate = InformationExpert.PLAYER_DEFAULT_FIRE_RATE; this.fireRate = this.default_FireRate;
			this.default_Speed = InformationExpert.PLAYER_DEFAULT_SPEED ; this.speed = this.default_Speed ;
			this.default_Damage = InformationExpert.PLAYER_DEFAULT_DAMAGE ; this.damage = this.default_Damage;
			this.default_Health = InformationExpert.PLAYER_DEFAULT_HEALTH ; this.health = this.default_Health ;
			
			
		}
		if(id ==3 ){
			this.default_FireRate = InformationExpert.BOT_DEFAULT_FIRE_RATE; this.fireRate = this.default_FireRate;
			this.default_Speed = InformationExpert.BOT_DEFAULT_SPEED ; this.speed = this.default_Speed ;
			this.default_Damage = InformationExpert.BOT_DEFAULT_DAMAGE ; this.damage = this.default_Damage;
			this.default_Health = InformationExpert.BOT_DEFAULT_HEALTH ; this.health = this.default_Health ;
			this.projectileSpeedModifier = -2;
		}
		
		this.name = "";
		if(this.id == 1 || this.id == 2){
			this.MyImages = new EntityImageLibrary(this.id);
		}
		
		this.roundsWon=0;
		this.name="";
		this.maxHealth = this.default_Health;
		
		renewGotHit();
		renewHit();
		
		
		
		}
	public void applyBuffs(){
		this.fireRate = this.default_FireRate;
		 this.speed = this.default_Speed ;
		 this.damage = this.default_Damage;
		 this.health = this.default_Health ;
		 this.projectileSpeedModifier = 0;
		for(int i=0;i<Buffs.size();i++){
			setDamage(getDamage()+ Buffs.get(i).damage);
			setHealth(getHealth()+Buffs.get(i).health);
			setFireRate(getFireRate()+ Buffs.get(i).fireRate);
			setSpeed(getSpeed() + Buffs.get(i).speed);
			projectileSpeedModifier+=Buffs.get(i).projectileSpeed;
//			System.out.println("\n" +Buffs.get(i).speed);
//			System.out.println("DAMAGE :"+damage+"     HEALTH :"+health+"     SPEED"+speed+"     FIRE RATE"+fireRate+"     PROJECTILE SPEED"+projectileSpeedModifier);
		}
		
		
		this.maxHealth = this.health;
	}
	public void renewGotHit(){
		gotHitTimer = new Thread(){
			public void run(){
				gotHit = true;
				try {Thread.sleep(InformationExpert.THREAD_HIT_WAIT_TIME);
				} catch (InterruptedException e) {}
				gotHit = false;
			}
		};
	}
	public void renewHit(){
		hitTimer = new Thread(){
			public void run(){
				hit = true;
				try {Thread.sleep(InformationExpert.THREAD_HIT_WAIT_TIME);
				} catch (InterruptedException e) {}
				hit = false;	
			}
		};
	}
	public void drawEntity(Graphics g){
		if(id==1 || id == 2){
		
		boolean beforeEntity = true;
//		handleBuffDisplay(g,  beforeEntity);
		for(int i=0;i<Buffs.size();i++){
			Buffs.get(i).drawBuff(g, x, y, moveTrajectory.x, moveTrajectory.y, beforeEntity, gotHit,hit);
		}
		
		switch(this.moveTrajectory.getX()){
		
		case 0 : // not moving left / right
			switch(this.moveTrajectory.getY()){
				case -1 : updateImage(g, MyImages.up1, MyImages.up2, MyImages.up3); break; // up 					= moving up
				case 0 : this.img = MyImages.stand ; g.drawImage(img, x, y, null);  break; // -						= not moving
				case 1 : updateImage(g,MyImages.down1, MyImages.down2,MyImages.down3); break; // down				= moving down
			}
			break;
			
		case -1: // moving left
			switch ( this.moveTrajectory.getY()){ 
				case -1 : updateImage(g, MyImages.leftUp1, MyImages.leftUp2, MyImages.leftUp3); break;// up			= moving left & up
				case 0 : updateImage(g, MyImages.left1, MyImages.left2, MyImages.left3); break;// -					= moving left
				case 1 : updateImage(g, MyImages.leftDown1, MyImages.leftDown2, MyImages.leftDown3); break;// down	= moving left & down
			}
			break;
			
		case 1: // moving right
			switch(this.moveTrajectory.getY()){
				case -1 : updateImage(g, MyImages.rightUp1,MyImages.rightUp2, MyImages.rightUp3); break;//up		= moving right & up
				case 0 : updateImage(g, MyImages.right1, MyImages.right2, MyImages.right3); break;// -				= moving right
				case 1 : updateImage(g, MyImages.rightDown1, MyImages.rightDown2, MyImages.rightDown3); break;//down= moving right & down
			}
			break;
		}

		if(clip==21){
			clip=0;
		}
		beforeEntity = false;
		for(int i=0;i<Buffs.size();i++){
			Buffs.get(i).drawBuff(g, x, y, moveTrajectory.x, moveTrajectory.y, beforeEntity, gotHit,hit);
		}
		}
		if(id==3){
			g.setColor(Color.BLACK);
			g.fillRect(x+12, y+12, 20, 20);
			g.fillRect(x+9, y+34, 26, 7);
			g.setColor(Color.RED);
			g.fillRect(x+10, y+35, 24, 5);
			g.setColor(Color.GREEN);
			g.fillRect(x+10, y+35, this.health*3  , 5);
		}
	}
	public void updateImage(Graphics g,BufferedImage i1,BufferedImage i2,BufferedImage i3){
		if(clip==0){ 	img = i1;	}
		if(clip==7){ 	img = i2;	}
		if(clip==14){	img = i3;	}
		g.drawImage(img, x, y, null);
		clip++;
	}

	public int getFireRate() {
		return fireRate;
	}


	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public void reset(){
		this.shootTrajectory.setX(0);
		this.shootTrajectory.setY(0);
		this.moveTrajectory.setX(0);
		this.moveTrajectory.setY(0);
		this.Projectiles.clear();
		this.health = this.default_Health;
		this.damage = this.default_Damage;
		this.speed = this.default_Speed;
		this.fireRate = this.default_FireRate;
		applyBuffs();		
	}
	public void randomizeTrajectory(){
		Random rand = new Random();
		int trajectory;
		trajectory = rand.nextInt(3)-1; this.moveTrajectory.x = trajectory; System.out.print(this.moveTrajectory.x+" ");
		trajectory = rand.nextInt(3)-1; this.moveTrajectory.y = trajectory; System.out.print(this.moveTrajectory.y+" ");
		trajectory = rand.nextInt(3)-1; this.shootTrajectory.x = trajectory; System.out.print(this.shootTrajectory.x+" ");
		trajectory = rand.nextInt(3)-1; this.shootTrajectory.y = trajectory; System.out.println(this.shootTrajectory.y+" ");
		
	}
	
	public void updateTrajectory(Trajectory t ,char x,char y){
		if(x != '0'  ){
			if(x==('+')){
				t.setX(t.getX()+1);
			}
			if(x==('-')){
				t.setX(t.getX()-1);
			}
		}
		t.setX( Math.max(Math.min(1, t.getX()), -1	)    );

		if(y !='0'  ){
			if(y==('+')){
				t.setY(t.getY()+1);
			}
			if(y==('-')){
				t.setY(t.getY()-1);
			}
		}
		t.setY( Math.max(Math.min(1, t.getY()), -1	)    );
		
		this.clip = 0;
		
	}
	 	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getInitialX() {
		return initialX;
	}
	public void setInitialX(int initialX) {
		this.initialX = initialX;
	}
	public int getInitialY() {
		return initialY;
	}
	public void setInitialY(int initialY) {
		this.initialY = initialY;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getRoundsWon() {
		return roundsWon;
	}
	public void setRoundsWon(int roundsWon) {
		this.roundsWon = roundsWon;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
}
class Trajectory{
	int x;
	int y;
	
	public Trajectory(int x, int y){
		this.setX(x);
		this.setY(y);
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	
}

