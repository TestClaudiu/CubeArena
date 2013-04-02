import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class InputHandler {
	Entity myEntity;
	Animation A;
	int  shootUpButtonIndex, shootDownButtonIndex,
			shootRightButtonIndex, shootLeftButtonIndex;
	long sysTime, tempTime;
	Projectile temp;

	public InputHandler(Entity myEntity, Animation A ) {
		this.myEntity = myEntity;
		this.A = A;

	}


	public void doRun() {	
		int tempSpeed = myEntity.getSpeed();
		int tempFireRate = myEntity.getFireRate();
		
		if(myEntity.hit){
			if(myEntity.buffs.contains(InformationExpert.BUFF_BERZERKER)){
				myEntity.setSpeed(myEntity.getSpeed()+1);
				myEntity.setFireRate(myEntity.getFireRate()-100);
			}
		}
		
//		if(myEntity.getId()==1){
//			System.out.println(myEntity.name+" VITEZA :D "+myEntity.getSpeed()+"  RATA DE FOC :( "+
//			myEntity.getFireRate()+" HIT ?"+myEntity.hit+" GOT HIT ? "+myEntity.gotHit);
//		}
		
		myEntity.sysTime = new Date().getTime();
		myEntity.setInitialX(myEntity.getX());
		myEntity.setInitialY(myEntity.getY());
		reactToTrajectory();
		
		generateProjectiles();
		EventHandler.handleProjectileEvent( myEntity, A.tiles);
		
		
		char[] cherries = {'1','2','3'};
		for( int i=0;i<cherries.length;i++){
			if (A.onTileType(myEntity, cherries[i]) && A.cherryIndex != cherries[i]) {
				myEntity.setHealth(myEntity.getHealth() + 2);
				A.cherryIndex = cherries[i];
			}
			if(myEntity.getHealth()>myEntity.maxHealth){
				myEntity.setHealth(myEntity.maxHealth);
			}
		}
		if (A.onTileType(myEntity, '4') && A.cherryIndex != '4') {
			myEntity.setFireRate(myEntity.getFireRate() - 50);
			A.cherryIndex = '4';
		}
		
		
		
		myEntity.setSpeed(tempSpeed);
		myEntity.setFireRate(tempFireRate);
	}
	public void reactToTrajectory(){
	
		myEntity.setX(myEntity.getX() + myEntity.moveTrajectory.getX() * myEntity.getSpeed());
		myEntity.setX(	Math.max(Math.min(550, myEntity.getX()), 0	)   );
		if (A.onTileType(myEntity, 'X') || A.onTileType(myEntity, 'x') ) {
			myEntity.setX(myEntity.getInitialX());
		}
		
		myEntity.setY(myEntity.getY() + myEntity.moveTrajectory.getY() * myEntity.getSpeed());
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
