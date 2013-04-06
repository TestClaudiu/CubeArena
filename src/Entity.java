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
	
	public Set<Integer> buffs = new HashSet<Integer>(); // a set filled with the buff indexes
	public ArrayList<Projectile> Projectiles = new ArrayList<Projectile>(); // we store the projectiles , trajectories and their pseed
	
	BufferedImage img ,projectileImg ;
	EntityImageLibrary MyImages ;
	
	long sysTime = new Date().getTime() , tempTime = sysTime; // USED  in the inputHandler
	Trajectory moveTrajectory = new Trajectory(0, 0); // moving trajectory
	Trajectory shootTrajectory = new Trajectory(0, 0); // shooting trajectory
	
	ArrayList<Buff> Buffs = new ArrayList<>(); // a list filled with our entity's buffs
	int initialX,initialY; //
	int maxHealth; int botkillCount = 0;
	String name;
	
	private int roundsWon ;
	
	private int id;
	private int clip = 0;
	public int default_Damage , default_Speed , default_Health, default_FireRate; 
	private int damage,speed,health,fireRate;
	public int projectileSpeedModifier = 0;
	private int x;	private int y;
	
	
	public Thread gotHitTimer , hitTimer;
	public boolean gotHit = false , hit = false;
	
	public Entity(int x, int y, int id){
		
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
//		if(this.id == 1 || this.id == 2){
			this.MyImages = new EntityImageLibrary(this.id);
//		}
		try {
			
		switch(id){
		case 1: projectileImg = ImageIO.read(new File("res\\ProjectileHero1.png")); break;
		case 2: projectileImg = ImageIO.read(new File("res\\ProjectileHero2.png")); break;
		case 3: projectileImg = ImageIO.read(new File("res\\ProjectileBOT.png")); break;
			
		}
		} catch (IOException e) {}
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
		}
		
		
		this.maxHealth = this.health;
	}
	// This method is calle every time an enity is being hit and there has been more than one second since the last received hit

	public void drawEntity(Graphics g){
		
		
		/* We use this variable because some buffs must be drawn before we draw the entity, 
		 * the sprites being behind the entity . Such an example is the Guardian Angel buff,
		 *  which draws wings 
		 */
		boolean beforeEntity = true; 
		
		// drawing every buff in the Buff list
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

		if(id==3){ // We are drawing the health bar here for the bots
			
			// The black outline for the bar
			g.setColor(Color.BLACK);
			g.fillRect(x+7, y+39, InformationExpert.BOT_HEALTHBAR_WIDTH+2, 
					InformationExpert.BOT_HEALTHBAR_HEIGHT+2);
			
			// The red health bar background
			g.setColor(Color.RED);
			// Those variables don't change , as we use the green color to display the percentage of the life left
			g.fillRect(x+8, y+40, InformationExpert.BOT_HEALTHBAR_WIDTH,
					InformationExpert.BOT_HEALTHBAR_HEIGHT);
			
			
			// the green part of the background
			g.setColor(Color.GREEN);
			
			/* The green rectangle's width covers the whole health bar at full health ,
			 * the entity having their max health at that point.
			 * When the entity lost health, the green rectangle's health gets smaller 
			 * creating the illusion that red color fills the health bar a symbol of health we miss/lost
			 */
			g.fillRect(x+8, y+40, (InformationExpert.BOT_HEALTHBAR_WIDTH/this.maxHealth) * this.health , InformationExpert.BOT_HEALTHBAR_HEIGHT);
		}
	}
	public void drawProjectiles(Graphics g){
		for(int i=0;i<Projectiles.size();i++){
			g.drawImage(projectileImg, Projectiles.get(i).getX(),Projectiles.get(i).getY(), null);
		}
	}
	// we use this method to give the entity an animated feel, cycling between images every 7 frames
	public void updateImage(Graphics g,BufferedImage i1,BufferedImage i2,BufferedImage i3){
		
		if(clip==0){ 	img = i1;	}
		if(clip==7){ 	img = i2;	}
		if(clip==14){	img = i3;	}
		g.drawImage(img, x, y, null);
		clip++;
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
	// This method is called every time an entity hits someone
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
	public void reset(){
		this.shootTrajectory.setX(0);
		this.shootTrajectory.setY(0);
		this.moveTrajectory.setX(0);
		this.moveTrajectory.setY(0);
		this.Projectiles.clear();		this.damage = this.default_Damage;
		this.speed = this.default_Speed;
		this.fireRate = this.default_FireRate;
		this.botkillCount = 0;
	}
	public void randomizeTrajectory(){
		Random rand = new Random();
		int trajectory;
		trajectory = rand.nextInt(3)-1; this.moveTrajectory.x = trajectory; this.shootTrajectory.x = trajectory;
		trajectory = rand.nextInt(3)-1; this.moveTrajectory.y = trajectory; this.shootTrajectory.y = trajectory;
//		trajectory = rand.nextInt(3)-1; this.shootTrajectory.x = trajectory;
//		trajectory = rand.nextInt(3)-1; this.shootTrajectory.y = trajectory; 
		
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
	
	public int getFireRate() {
		return fireRate;
	}


	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
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

