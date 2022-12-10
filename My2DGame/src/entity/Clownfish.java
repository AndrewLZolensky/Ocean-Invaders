package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * clownfish to swim around in game background
 * @author andrewzolensky
 *
 */
public class Clownfish extends entity{
	
	//link to gamepanel
	GamePanel gp;
	
	//animation count
	int count = 0;
	
	
	/**
	 * Construct an instance of clownfish
	 */
	public Clownfish(GamePanel gp) {
		
		//set game panel for crab
		this.gp = gp;
	
		
		//set size and speed
		this.speed = 2;
		this.entitySize = (int) (0.3*gp.tileSize);
		
		//set type of entity
		this.type = "Clownfish";
		
		//get images
		this.getPlayerImage();
		
		//random object
		Random rand = new Random();
		
		//random position fluctuations
		int numPosY = rand.nextInt(100);
		int numNegY = -rand.nextInt(100);
		int numY = (numPosY + numNegY);
		int numPosX = rand.nextInt(100);
		int numNegX = -rand.nextInt(100);
		int numX = (numPosX + numNegX);
		
		//clownfish starts to the right
		this.x = this.gp.screenWidth - 50 + numX;
		int scaled = (int) (0.70 * this.gp.screenHeight);
		this.y = scaled - this.entitySize + numY;
		this.direction = "left";
		
	
	}
	
	/**
	 * Load in clownfish images
	 */
	public void getPlayerImage() {
		
		//try to load in clownfish images, throw exception if files not found
		try {
			this.left1 = ImageIO.read(getClass().getResourceAsStream("/Clownfish/Clownfish-Left.png"));
			this.right1 = ImageIO.read(getClass().getResourceAsStream("/Clownfish/Clownfish-Right.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * crab controls
	 */
	@Override
	public void update() {
		
		//if clownfish is moving left
		if (this.direction == "left") {
			
			//if at border
			if (this.x < 0) {
				
				//switch direction
				this.direction = "right";
			
			//otherwise
			} else {
				
				//continue left
				this.x -= this.speed;
			}
		
		//if clownfish is moving right
		} else if (this.direction == "right") {
			
			//if at border
			if (this.x > this.gp.screenWidth - 100) {
				
				//switch direction
				this.direction = "left";
			
			//otherwise
			} else {
				
				//continue right
				this.x += this.speed;
			}
		}
		
		//increment animation count, or reset if at 70
		this.count++;
		if (count > 70) {
			count = 0;
		}
	}
	
	/**
	 * draws crab
	 * @param g2
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//variable to hold relevant clownfish image
		BufferedImage image = null;
		
		//image depends on direction
		switch(direction) {
		
		//if moving to the right, use right image
		case "right":
			image = this.right1;
			break;
		
		//if moving to the left, use left image
		case "left":
			image = this.left1;
			break;
		}
	
		
		
		//draw image
		g2.drawImage(image, x, y, entitySize, entitySize, null);
		
	}

}
