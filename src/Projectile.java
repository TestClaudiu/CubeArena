import java.awt.Image;


public class Projectile {
	private int speed;
	private String direction;
	Trajectory trajectory = new Trajectory(0, 0);

	private int y;
	private int x;
	private int prevX,prevY ;
	
	public Projectile(int speed, String direction , int x , int y){
		this.speed = InformationExpert.PROJECTILE_DEFAULT_SPEED;
		this.direction = direction;
		this.x = x;
		this.y = y;
		
	}
	public Projectile(int speed, Trajectory t, int x , int y){
		this.speed = speed;
		this.x = x;
		this.y = y;
		this.trajectory.x = t.x;
		this.trajectory.y = t.y;
	}
	
	public void update(){
		this.x = x + trajectory.x * speed;
		this.y = y + trajectory.y * speed;
		this.prevX = x;
		this.prevY = y;
		
	}
	public boolean hasValidPosition(){
		if(y>=0 && y<=550 &&
				x>=0 && x<=550 && (this.trajectory.x!=0 || this.trajectory.y!=0)){
			return true;
		}
		return false;
	}
	// Setters and getters
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
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
	public int getPrevX() {
		return prevX;
	}
	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}
	public int getPrevY() {
		return prevY;
	}
	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}
}
