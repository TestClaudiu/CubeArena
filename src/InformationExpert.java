import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InformationExpert {
	public final static int  // KEYBOARD KEYS , used in the key listener of MainGame
		KEY_P1_GO_DOWN =  40,KEY_P1_GO_UP =  38,KEY_P1_GO_LEFT = 37,KEY_P1_GO_RIGHT = 39,
		KEY_P1_SHOOT_DOWN = 75,KEY_P1_SHOOT_UP = 73 ,KEY_P1_SHOOT_LEFT = 74,KEY_P1_SHOOT_RIGHT = 76 ,
		KEY_P2_GO_DOWN = 83,KEY_P2_GO_UP = 87 ,KEY_P2_GO_LEFT = 65 ,KEY_P2_GO_RIGHT = 68 ,
		KEY_P2_SHOOT_DOWN = 66,KEY_P2_SHOOT_UP = 71 ,KEY_P2_SHOOT_LEFT = 86 ,KEY_P2_SHOOT_RIGHT = 78 ,
		KEY_ENTER = 10 ,
		
		
		// DEFAULT PLAYER VARIABLES , used in the constructor of Entity 
		PLAYER_DEFAULT_HEALTH = 20, PLAYER_DEFAULT_DAMAGE = 2, PLAYER_DEFAULT_SPEED = 3, PLAYER_DEFAULT_FIRE_RATE = 400	,THREAD_HIT_WAIT_TIME = 1000,
		BOT_DEFAULT_HEALTH = 10, BOT_DEFAULT_DAMAGE = 2, BOT_DEFAULT_SPEED = 2 , BOT_DEFAULT_FIRE_RATE = 600 , BOT_HEALTHBAR_HEIGHT = 5 , BOT_HEALTHBAR_WIDTH = 30;;
	
	// USED IN THE StartPanel.java  ( the buff panel )
	public final static String[] buffNames
		= { "BLOOD SEEKER" ,"GUARDIAN ANGEL","TANK", "SHARPSHOOTER" , "AVENGER" , "SPEEDSTER" ,"REPEATER" , "BERZERKER"};// NAME OF BUFFS , used in StartPanel.java draw
	public final static List<String> BUFF_LIST =  Arrays.asList(buffNames);
	public final static int NUM_OF_BUFFS = BUFF_LIST.size(), DISTANCE_BETWEEN_BUTTONS = 50 ,
			BUFF_BLOOD_SEEKER = 1 , BUFF_GUARDIAN_ANGEL = 2, BUFF_TANK = 3 ,
			BUFF_SHARPSHOOTER = 4 , BUFF_AVENGER = 5, BUFF_SPEEDSTER = 6 , BUFF_REPEATER = 7 ,
			BUFF_BERZERKER = 8,
	
	// FOR CHOOSING MAPS
			MAP_RANDOM = 1,  MAP_PREMADE = 2,
			MAP_GENERATE_TYPE = MAP_PREMADE,
			STATE_GAME = 2 , STATE_BUFFS = 1,
			FRAME_RATE = 100,
			
			
			
			
			PROJECTILE_DEFAULT_SPEED = 5 ;
	
		public InformationExpert(){
		
	}
	
}
	