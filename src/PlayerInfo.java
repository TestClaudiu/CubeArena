import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class PlayerInfo extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int e1Health , e2Health;
	String e1Name , e2Name;
	BufferedImage bf;
	BufferedImage heart,bg ,infoPlayer1,infoPlayer2;
	Entity e1,e2;
	
	public PlayerInfo(){			//(Entity e1,Entity e2)
		
		setSize(new Dimension(600,100));
		setPreferredSize(this.getSize());
		setVisible(true);
		bf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		try {
//			heart = ImageIO.read(new File("res\\healthContainer.png"));
			heart = ImageIO.read(new File("res\\healthContainer2.png"));
			bg = ImageIO.read(new File("res\\bgInfo.png"));
			infoPlayer1 = ImageIO.read(new File("res\\infoP1.png"));
			infoPlayer2 = ImageIO.read(new File("res\\InfoP2.png"));
		} catch (IOException e) {}
		e1Health = 5; e2Health = 5;
		
	}
	
	public void update(Entity e1,Entity e2){
		if(e1.getHealth() != e1Health || e2.getHealth() != e2Health || 
				e1Name != e1.name	|| e2Name != e2.name){
			this.e1 = e1;
			this.e2 = e2;
			this.e1Health = e1.getHealth();
			this.e2Health = e2.getHealth();
			this.e1Name = e1.name;
			this.e2Name = e2.name;
			repaint();
		}
		
	}
	public void run(){
		
	}
	public void paintComponent(Graphics g){
		g.setFont(new Font(g.getFont().getName(), Font.BOLD, 20));
		
		g.setColor(new Color(50, 50, 50));
		g.fillRect(0, 0, 600, 150);
		
		g.drawImage(infoPlayer1, 0,	0	,null);
		g.drawImage(infoPlayer2, 300, 0, null);
		// fill hearts
		
		
//		g.setColor(Color.RED);
//		g.fillRect(475, heart.getHeight(getParent())-e2Health* ( heart.getHeight(getParent())/5 ), // location
//				heart.getWidth(getParent()), e2Health*(heart.getHeight(getParent()) )/5 );	// size
//		
//		g.fillRect(175, heart.getHeight(getParent())-e1Health* ( heart.getHeight(getParent())/5 ), // location
//				heart.getWidth(getParent()), e1Health*(heart.getHeight(getParent()) )/5 );
//		g.fillRect(175, 100-e1Health*20, 100, e1Health*20);
		
	
		// WRITE NAMES
		g.setColor(Color.WHITE);
		if(e1Name!=null || e2Name!=null){
			g.drawString(e1Name, 110, 25);
			g.drawString(e2Name, 410, 25);
		}
		
//		if(e1.getHealth() ==  5*e1.maxHealth/5){	g.setColor(Color.GREEN);			}
//		if(e1.getHealth() ==  4*e1.maxHealth/5){	g.setColor(Color.YELLOW);			}
//		if(e1.getHealth() ==  3*e1.maxHealth/5){	g.setColor(Color.ORANGE);			}
//		if(e1.getHealth() ==  2*e1.maxHealth/5){	g.setColor(Color.RED);				}
//		if(e1.getHealth() ==  1*e1.maxHealth/5){	g.setColor(new Color(135, 6, 0));	}
		if(e1!=null && e2!=null){
//		drawHealth(e1, g);
		g.setColor(Color.WHITE);
		g.drawString(e1.getHealth()+" / "+e1.maxHealth, 110, 50);
//		drawHealth(e2, g);
		g.drawString(e2.getHealth()+" / "+e2.maxHealth, 410, 50);
		}
		// DRAW HEARTS CONTAINERS
//		g.drawImage(heart, 175, 0, null);
//		g.drawImage(heart, 475, 0, null);
		
		
		
		
	}
	void drawHealth(Entity e,Graphics g){
		if(e!=null ){
			if(e.getHealth() >=
					5*e.maxHealth/5){
				g.setColor(Color.GREEN);			}
			if(e.getHealth() ==  4*e.maxHealth/5){	g.setColor(Color.YELLOW);			}
			if(e.getHealth() ==  3*e.maxHealth/5){	g.setColor(Color.ORANGE);			}
			if(e.getHealth() ==  2*e.maxHealth/5){	g.setColor(Color.RED);				}
			if(e.getHealth() ==  1*e.maxHealth/5 || e.getHealth() < 1*e.maxHealth/5 ){	g.setColor(new Color(135, 6, 0));	}
		}
	}
}
