import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class InputHandler {
	Entity myEntity;
	Animation A;
	Projectile temp;

	public InputHandler(Entity myEntity, Animation A ) {
		this.myEntity = myEntity;
		this.A = A;

	}


	public void doRun() {	
		int tempSpeed = myEntity.getSpeed();
		int tempFireRate = myEntity.getFireRate();
		
		
		// ADDITIONAL CODE FOR THE BERZERKER BUFF
		// Increasing speed and decreasing fire rate if the entity hit someone in the last second 
		if(myEntity.hit){
			if(myEntity.buffs.contains(InformationExpert.BUFF_BERZERKER)){
				myEntity.setSpeed(myEntity.getSpeed()+1);
				myEntity.setFireRate(myEntity.getFireRate()-100);
			}
		}
		
		
		myEntity.sysTime = new Date().getTime();
		myEntity.setInitialX(myEntity.getX());
		myEntity.setInitialY(myEntity.getY());
		reactToTrajectory();
		
		generateProjectiles();
		EventHandler.handleProjectileEvent( myEntity, A.tiles);
		
				
		myEntity.setSpeed(tempSpeed);
		myEntity.setFireRate(tempFireRate);
	}
	public void reactToTrajectory(){
	
		myEntity.setX(myEntity.getX() + myEntity.moveTrajectory.getX() * ( myEntity.getSpeed() +  myEntity.hitEnemy) );
		myEntity.setX(	Math.max(Math.min(550, myEntity.getX()), 0	)   );
		
		if (A.onTileType(myEntity, 'X') || A.onTileType(myEntity, 'x')  ) {
			myEntity.setX(myEntity.getInitialX());
		}
	
		
		
		
		myEntity.setY(myEntity.getY() + myEntity.moveTrajectory.getY() * ( myEntity.getSpeed() +  myEntity.hitEnemy) );
		myEntity.setY(	Math.max(Math.min(550, myEntity.getY()), 0	)	);
		if (A.onTileType(myEntity, 'X') || A.onTileType(myEntity, 'x') ) {
			myEntity.setY(myEntity.getInitialY());
		}
	
	}
	
	public void generateProjectiles(){
		if (myEntity.sysTime - myEntity.tempTime >= myEntity.getFireRate()) {
			temp = new Projectile(InformationExpert.PROJECTILE_DEFAULT_SPEED +
					myEntity.projectileSpeedModifier , myEntity.shootTrajectory, 
					myEntity.getX(), myEntity.getY());
			
			if (temp.hasValidPosition()) {
				myEntity.tempTime = myEntity.sysTime;
				myEntity.Projectiles.add(temp);
			}
			
		}
	}


}
