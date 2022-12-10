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
 * crabs for player to eat
 * @author andrewzolensky
 *
 */
public class Crab extends entity {
	
	//game panel for crab object
	GamePanel gp;
	
	//animation cycle count
	int count = 0;
	
	//entity size
	int entitySize;
	
	
	/**
	 * Construct an instance of crab
	 */
	public Crab(GamePanel gp) {
		
		//set game panel for crab
		this.gp = gp;
	
		
		//set size and speed
		this.speed = 2;
		this.entitySize = (int) (1.5*gp.tileSize);
		
		//get images
		this.getPlayerImage();
		
		//set type of entity
		this.type = "Crab";
		
		//randomly decide if crab starts from left or right of screen
		Random rand = new Random();
		int direction = rand.nextInt(2);
		
		//set coordinates and direction for crab
		if (direction == 1) {
			this.x = this.gp.screenWidth + rand.nextInt(300);
			this.direction = "left";
		} else {
			this.x = -10 - rand.nextInt(300);
			this.direction = "right";
		}
		
		this.y = this.gp.screenHeight - this.entitySize + 20;
		
		//crab hit box
		this.hitBoxSize = (int) (0.5 * gp.tileSize);
		this.hitBoxSizeX = this.hitBoxSize;
		this.hitBoxSizeY = this.hitBoxSize;
		this.solidArea = new Rectangle(32, 35, hitBoxSizeX, hitBoxSizeY);
		this.cc = new CollisionChecker(this.gp, this);
		
	
	}
	
	/**
	 * Load in crab images
	 */
	public void getPlayerImage() {
		
		//try to load in images and throw exception if images not found
		try {
			this.center1 = ImageIO.read(getClass().getResourceAsStream("/crab/Crab-Center.png"));
			this.center2 = ImageIO.read(getClass().getResourceAsStream("/crab/Crab-Center-2.png"));
			this.left1 = ImageIO.read(getClass().getResourceAsStream("/crab/Crab-Left.png"));
			this.right1 = ImageIO.read(getClass().getResourceAsStream("/crab/Crab-Right.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * crab controls
	 */
	@Override
	public void update() {
		
		//update coordinates
		if (this.direction == "left") {
			if (this.x < 0) {
				this.direction = "right";
			} else {
				this.x -= this.speed;
			}
		} else if (this.direction == "right") {
			if (this.x > this.gp.screenWidth - 100) {
				this.direction = "left";
			} else {
				this.x += this.speed;
			}
		}
		
		//update hitbox
		this.cc.setCoordinates(this);
		
		//increment animation count
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
		
		//functionality for different images based on different keyboard events
		BufferedImage image = null;
		switch(direction) {
		case "none":
			image = this.center1;
			break;
		case "right":
			if (count > 35) {
				image = this.right1;
			} else {
				image = this.left1;
			}
			break;
		case "left":
			if (count > 35) {
				image = this.right1;
			} else {
				image = this.left1;
			}
			break;
		}
		if (image == null) {
			image = this.center1;
		}
	
		
		
		//draw image
		g2.drawImage(image, x, y, entitySize, entitySize, null);
		
	}
	
	

}
