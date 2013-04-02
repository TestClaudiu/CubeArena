

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class EntityImageLibrary {
	BufferedImage right1 , right2,right3;
	BufferedImage left1,left2,left3;
	
	BufferedImage rightDown1,rightDown2,rightDown3;
	BufferedImage leftDown1,leftDown2,leftDown3;
	
	BufferedImage rightUp1,rightUp2,rightUp3;
	BufferedImage leftUp1,leftUp2,leftUp3;
	
	BufferedImage down1,down2,down3;
	BufferedImage up1,up2,up3;
	
	BufferedImage stand;
	
	public EntityImageLibrary(int id){
		
			try {
				right1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_1.png"));
			
			right2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_2.png"));
			right3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_3.png"));
			
			left1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_1.png"));
			left2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_2.png"));
			left3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_3.png"));
			
			rightDown1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_DOWN_1.png"));
			rightDown2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_DOWN_2.png"));
			rightDown3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_DOWN_3.png"));
			
			leftDown1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_DOWN_1.png"));
			leftDown2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_DOWN_2.png"));
			leftDown3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_DOWN_3.png"));
			
			rightUp1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_UP_1.png"));
			rightUp2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_UP_2.png"));
			rightUp3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_RIGHT_UP_3.png"));
			
			leftUp1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_UP_1.png"));
			leftUp2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_UP_2.png"));
			leftUp3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_LEFT_UP_3.png"));
		
			down1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_NONE.png"));
			down2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_DOWN_1.png"));
			down3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_DOWN_2.png"));
			
			up1 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_UP_1.png"));
			up2 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_UP_2.png"));
			up3 =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_UP_3.png"));
		
			stand =  ImageIO.read(new File("res\\hero"+id+"_TRAJECTORY_NONE.png"));
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
