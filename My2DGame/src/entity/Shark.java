package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;

/**
 * shark to injure player
 * @author andrewzolensky
 *
 */
public class Shark extends entity{
	
	//link to Game Panel
	GamePanel gp;
	
	//animation count
	int count = 0;
	
	//second animation count 
	int cycleCount = 0;
	
	//decide whether shark goes up or down
	int upOrDown = 1;
	
	
	/**
	 * Construct an instance of shark
	 */
	public Shark(GamePanel gp) {
		
		//set game panel for shark
		this.gp = gp;
		
		//set size and speed
		this.speed = 5;
		this.entitySize = (int) (2*gp.tileSize);
		
		//set type of entity to "shark"
		this.type = "Shark";
		
		//get images of shark
		this.getPlayerImage();
		
		//shark starts to the right
		this.x = this.gp.screenWidth - 100;
		int scaled = (int) (0.80 * this.gp.screenHeight);
		this.y = scaled - this.entitySize + 20;
		this.direction = "left";
		
		//initialize shark hit box
		this.hitBoxSize = gp.tileSize;
		this.hitBoxSizeX = this.hitBoxSize;
		this.hitBoxSizeY = this.hitBoxSize;
		this.solidArea = new Rectangle(30, 20, hitBoxSizeX, hitBoxSizeY);
		this.cc = new CollisionChecker(this.gp, this);
	
	}
	
	/**
	 * Load in shark images
	 */
	public void getPlayerImage() {
		
		//try to read in shark images, throw exception if files not found
		try {
			this.left1 = ImageIO.read(getClass().getResourceAsStream("/Shark/Shark-Left.png"));
			this.right1 = ImageIO.read(getClass().getResourceAsStream("/Shark/Shark-Right.png"));
			this.left2 = ImageIO.read(getClass().getResourceAsStream("/Shark/Shark-Swim-Left-1.png"));
			this.right2 = ImageIO.read(getClass().getResourceAsStream("/Shark/Shark-Swim_Right-1.png"));
			this.left3 = ImageIO.read(getClass().getResourceAsStream("/Shark/Shark-Swim-Left-2.png"));
			this.right3 = ImageIO.read(getClass().getResourceAsStream("/Shark/Shark-Swim-Right-2.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * update shark
	 */
	@Override
	public void update() {
		
		//update coordinates
		
		//if shark moving left
		if (this.direction == "left") {
			
			//if at border, switch direction to "right"
			if (this.x < 0) {
				this.direction = "right";
			
			//otherwise, continue left
			} else {
				this.x -= this.speed;
			}
		
		//if shark moving right
		} else if (this.direction == "right") {
			
			//if at border, switch direction to "left"
			if (this.x > this.gp.screenWidth - 100) {
				this.direction = "left";
			
			//otherwise, continue right
			} else {
				this.x += this.speed;
			}
		}
		
		
		//if (slowly incrementing) cycleCount is even and shark is not at bottom, have shark go down
		if (cycleCount % 2 == 0) {
			if (this.y < this.gp.screenHeight - 200) {
				this.y += 2;
			}
		
		//otherwise, if shark not at top, have shark go up
		} else {
			if (this.y > 0.50 * this.gp.screenHeight) {
				this.y -= 2;
			}
		}
		
		
		
		//update hit box
		this.cc.setCoordinates(this);
		
		//increment animation count, or if at 60, reset and increment 'up-or-down' cycleCount
		this.count++;
		if (count > 60) {
			count = 0;
			cycleCount++;
		}
	}
	
	/**
	 * draw shark
	 * @param g2 graphics object
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//draw hitbox
		//g2.setColor(Color.white);
		//g2.drawLine(this.cc.leftX, this.cc.topY, this.cc.rightX, this.cc.topY);
		//g2.drawLine(this.cc.leftX, this.cc.bottomY, this.cc.rightX, this.cc.bottomY);
		//g2.drawLine(this.cc.leftX, this.cc.topY, this.cc.leftX, this.cc.bottomY);
		//g2.drawLine(this.cc.rightX, this.cc.topY, this.cc.rightX, this.cc.bottomY);
		
		
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
