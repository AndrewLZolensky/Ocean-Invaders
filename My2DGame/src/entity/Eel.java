package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;

/**
 * eel to injure player
 * @author andrewzolensky
 *
 */
public class Eel extends entity{
	
	//link to Game Panel
	GamePanel gp;
	
	//animation count
	int count = 0;
	
	
	/**
	 * Construct an instance of shark
	 */
	public Eel(GamePanel gp) {
		
		//set game panel for eel
		this.gp = gp;
		
		//set size and speed
		this.speed = 4;
		this.entitySize = (int) (2*gp.tileSize);
		
		//set type of entity to "shark"
		this.type = "Eel";
		
		//get images of eel
		this.loadEelImages();
		
		//eel starts to the right, on the bottom, moving left
		this.x = 0;
		this.y = this.gp.screenHeight - this.entitySize;
		this.direction = "right";
		
		//initialize eel hit box
		this.hitBoxSize = gp.tileSize - 10;
		this.hitBoxSizeX = this.hitBoxSize;
		this.hitBoxSizeY = this.hitBoxSize;
		this.solidArea = new Rectangle(30, 30, hitBoxSizeX, hitBoxSizeY);
		this.cc = new CollisionChecker(this.gp, this);
		
	
	}
	
	/**
	 * load in eel images
	 */
	public void loadEelImages() {
		
		//try to read in eel images, throw exception if files not found
		try {
			this.left1 = ImageIO.read(getClass().getResourceAsStream("/Eel/Eel-Left-1.png"));
			this.right1 = ImageIO.read(getClass().getResourceAsStream("/Eel/Eel-Right-1.png"));
			this.left2 = ImageIO.read(getClass().getResourceAsStream("/Eel/Eel-Left-2.png"));
			this.right2 = ImageIO.read(getClass().getResourceAsStream("/Eel/Eel-Right-2.png"));
			this.left3 = ImageIO.read(getClass().getResourceAsStream("/Eel/Eel-Left-3.png"));
			this.right3 = ImageIO.read(getClass().getResourceAsStream("/Eel/Eel-Right-3.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * eel trajectory
	 */
	@Override
	public void update() {
		
		//update coordinates
		
		//if eel moving left
		if (this.direction == "left") {
			
			//if at border, switch direction to "right"
			if (this.x < 0) {
				this.direction = "right";
			
			//otherwise, continue left
			} else {
				this.x -= this.speed;
			}
		
		//if eel moving right
		} else if (this.direction == "right") {
			
			//if at border, switch direction to "left"
			if (this.x > this.gp.screenWidth - 100) {
				this.direction = "left";
			
			//otherwise, continue right
			} else {
				this.x += this.speed;
			}
		}
		
		
		
		//update hit box
		this.cc.setCoordinates(this);
		
		//increment animation count, or if at 60, reset and increment 'up-or-down' cycleCount
		this.count++;
		if (count > 60) {
			count = 0;
		}
	}
	
	/**
	 * draw eel
	 * @param g2
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//start with empty image
		BufferedImage image = null;
		
		//relevant image depends on direction of travel
		switch(direction) {
		
		//if no direction, use center1
		case "none":
			image = this.center1;
			break;
		
		//if right, use either right1, right2 or right3 according to count
		case "right":
			if (count < 20) {
				image = this.right1;
			} else if (20 <= count && count < 40){
				image = this.right2;
			} else {
				image = this.right3;
			}
			break;
		
		//if left, use either left1, left2 or left3 depending on count
		case "left":
			if (count < 20) {
				image = this.left1;
			} else if (20 <= count && count < 40){
				image = this.left2;
			} else {
				image = this.left3;
			}
			break;
		}
		
		//if null, set image to right1
		if (image == null) {
			image = this.right1;
		}
	
		
		//draw image
		g2.drawImage(image, x, y, entitySize, entitySize, null);
		
	}

}
