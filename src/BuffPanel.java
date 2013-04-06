import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class BuffPanel extends JPanel{
	public int selectedButtonP1 , selectedButtonP2 ; // the cursor for the currently selected buff
	public Set<Integer> buffIndexesP1 = new HashSet<>(); //
	public  Set<Buff> P1buffs = new HashSet<>();
	public Set<Integer> buffIndexesP2 = new HashSet<>();
	public Set<Buff> P2buffs = new HashSet<>();
	String buffDescriptionsP1= "" , buffDescriptionsP2 ="";
//	public String
	
	
	
	public BufferedImage frameP1 , selectedP1, // the "cursor" and the "check box" for the first player
	frameP2 , selectedP2 , // the "cursor" and the "check box" for the second player
	button , // the button in which every buff name is displayed
	buttonFrame, // the panel behind all the buttons
	buffFrame , // the panel in which we find the description of buffs
	initBG; // background
	public boolean notEnoughBuffs = false;
	
	public BuffPanel(){
		
		selectedButtonP1 = 1;
		selectedButtonP2 = 1;
		
		setPreferredSize(new Dimension(700,700));
		setVisible(true);
		try {
		frameP1 = ImageIO.read(new File("res\\frameP1.png"));
		selectedP1 = ImageIO.read(new File("res\\selectedBuffP1.png"));
		frameP2 = ImageIO.read(new File("res\\frameP2.png"));
		selectedP2 = ImageIO.read(new File("res\\selectedBuffP2.png"));
		button = ImageIO.read(new File("res\\BUTTON.jpg"));
		buttonFrame = ImageIO.read(new File("res\\BUTTON_FRAME.png"));
		buffFrame = ImageIO.read(new File("res\\BUFF_FRAME.png"));
		
		initBG = ImageIO.read(new File("res\\INIT_BACKGROUND.jpg"));
		} catch (IOException e) {}
		
		
		
		
	}
	public void paintComponent(Graphics g){
		
		g.setFont(new Font("Emulator", Font.PLAIN, 13));
		g.drawImage(initBG, 0, 0, null);
		g.drawImage(buttonFrame, 200, 30, null);
		
		for(int i=0;i<InformationExpert.NUM_OF_BUFFS ;i++){
		
			g.drawImage(button	, 225, 50+InformationExpert.DISTANCE_BETWEEN_BUTTONS*i, null);
			g.setColor(Color.BLACK);
			g.drawString(InformationExpert.BUFF_LIST.get(i), 262, 75+InformationExpert.DISTANCE_BETWEEN_BUTTONS*i);
			
			
			if(buffIndexesP1.contains(i+1)){
				g.drawImage(selectedP1, 230, 56+InformationExpert.DISTANCE_BETWEEN_BUTTONS*i, null);
			}
			if(buffIndexesP2.contains(i+1)){
				g.drawImage(selectedP2, 440 ,56+InformationExpert.DISTANCE_BETWEEN_BUTTONS*i, null);
			}
		}
		g.drawString("PRESS ENTER TO PLAY", 225, 25);
		generateBuffDescriptions(g, new Color(91,157,255), new Color(73,127,204), 515, buffDescriptionsP2);
		generateBuffDescriptions(g, new Color(157,255,91), new Color(86,137,49) , 15, buffDescriptionsP1);
		
		
		if(notEnoughBuffs){
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 15));
			g.drawString("PLAYER 1 : move:UP/DOWN KEYS , select buff:LEFT KEY , deselect buff:RIGHT KEY", 0, 660);
			g.drawString("PLAYER 2 : move:W/S KEYS ,     select buff:D KEY ,    deselect buff:A KEY", 0, 680);
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 20));
		}
			
		g.drawImage(frameP1, 225, 50+(selectedButtonP1-1)*InformationExpert.DISTANCE_BETWEEN_BUTTONS, null);
		g.drawImage(frameP2, 225,  50+(selectedButtonP2-1)*InformationExpert.DISTANCE_BETWEEN_BUTTONS ,null);
	}
	public void updateSelectedButtons(int x,int y){
		this.selectedButtonP1 = x;
		this.selectedButtonP2 = y;
	}
	public void handleButtons(int keyCode,Entity p1 , Entity p2){
		System.out.println(keyCode);
		if(keyCode == InformationExpert.KEY_P1_GO_UP){
			
			if(this.selectedButtonP1==1)	{	
				this.selectedButtonP1 = InformationExpert.NUM_OF_BUFFS;	}
			else{this.selectedButtonP1--;}	
		}
		if(keyCode == InformationExpert.KEY_P1_GO_DOWN){
			if(this.selectedButtonP1== InformationExpert.NUM_OF_BUFFS)	{	
				this.selectedButtonP1=1;	}
			else{this.selectedButtonP1 ++;	}
		}
		
		
		
if(keyCode == InformationExpert.KEY_P2_GO_UP){
			
			if(this.selectedButtonP2==1)	{	
				this.selectedButtonP2 = InformationExpert.NUM_OF_BUFFS;	}
			
			else{
				this.selectedButtonP2--;
			}
			
		}
		if(keyCode == InformationExpert.KEY_P2_GO_DOWN){
			if(this.selectedButtonP2== InformationExpert.NUM_OF_BUFFS)	{	
				this.selectedButtonP2=1;	}
			
			else{
				this.selectedButtonP2 ++;	
				}
		}
		
		if(keyCode == InformationExpert.KEY_P1_GO_LEFT || keyCode == InformationExpert.KEY_P1_GO_RIGHT){

			if(buffIndexesP1.size()<3 && !buffIndexesP1.contains(this.selectedButtonP1)){ // we don't have the buff added yet and we don't have 3 buffs already
				
				buffIndexesP1.add(this.selectedButtonP1); 
				
			}else if(buffIndexesP1.contains(this.selectedButtonP1)){ // we have it in our buff list
				
				buffIndexesP1.remove(this.selectedButtonP1);
			}
				// we reset the buff descriptions and the buff list
				buffDescriptionsP1="";
				P1buffs.clear();
				for(int x : buffIndexesP1){ 
					// we re make the buff description and the buff list
					P1buffs.add(new Buff(x));
					buffDescriptionsP1+= new Buff(x).description+"\n\n";
					
				}
		
		}
		if(keyCode == InformationExpert.KEY_P2_GO_LEFT || keyCode == InformationExpert.KEY_P2_GO_RIGHT){

			
			if(buffIndexesP2.size()<3 && !buffIndexesP2.contains(this.selectedButtonP2)){// we don't have the buff added yet and we don't have 3 buffs already
				
				buffIndexesP2.add(this.selectedButtonP2);
				
			}else if(buffIndexesP2.contains(this.selectedButtonP2)){ // we have it in our buff list
				
				buffIndexesP2.remove(this.selectedButtonP2);
				
			}
			// we reset the buff descriptions and the buff list
				buffDescriptionsP2="";
				P2buffs.clear();
				for(int x : buffIndexesP2){ 
					// we remake the buff description and the buff list
					P2buffs.add(new Buff(x)); 
					buffDescriptionsP2+= new Buff(x).description+"\n\n";	
				}
		
		}
		
		
	}
	public void updateEntityBuffs(Entity e){
		if(e.getId()==1){	e.Buffs.addAll(P1buffs); e.buffs.addAll(buffIndexesP1);		}
		if(e.getId()==2){	e.Buffs.addAll(P2buffs); e.buffs.addAll(buffIndexesP2);		}
	}
	public void generateBuffDescriptions(Graphics g , Color ColorName , Color ColorDesc , int posX , String desc){
		int posY = 100;
		Scanner lineProcessor = new Scanner(desc);
	
		while(lineProcessor.hasNextLine()){ // there are still lines to be processed from the buff descriptions
			
			String temp = lineProcessor.nextLine(); // we process the description line by line

			// set the font and color for the default buff description line
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 11));
			g.setColor(ColorDesc);
			
			if(temp.startsWith("$")){ // the temp String is a buff name
				temp = temp.substring(1, temp.length()); // removing the dollar char
				
				// set the font and color for the buff name line
				g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 13));
				g.setColor(ColorName);
				
				g.drawImage(buffFrame, posX-3, posY-20, null); // draw the buff frame to a possition relative to the name
			}
			g.drawString(temp, posX, posY);
			posY+= 20;// we increment the position of a description line
		}
		lineProcessor.close();
		
	}
}
