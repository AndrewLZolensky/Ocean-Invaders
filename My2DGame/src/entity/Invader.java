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
 * invaders to attack player
 * @author andrewzolensky
 *
 */
public class Invader extends entity{
	
	//link to game panel
	GamePanel gp;
	
	//animation count
	int count = 0;
	
	//starting position
	public int startPos;
	
	//string to store start position of invader
	public String startPosString;
	
	//whether the invader is dead or not
	public boolean dead = false;
	
	//bulletshot is null if the invader is not shooting a bullet this turn
	//otherwise, it holds the bullet the invader shoots
	public Bullet bulletshot = null;
	
	//default invader bullet speed
	public int defaultBulletSpeed = 6;
	
	//shot frequency
	public int shotFreq = 90;
	
	//tells whether ship is going up or down
	public String upOrDown;
	public int verticalClock;
	
	
	
	/**
	 * construct Invader object
	 */
	public Invader(GamePanel gp, String startPosString) {
		
		//set game panel for crab
		this.gp = gp;
		
		//randomly decide whether invader is flying up or down
		Random rand = new Random();
		int r = rand.nextInt(2);
		if (r == 1) {
			upOrDown = "up";
		} else {
			upOrDown = "down";
		}
		
		//set vertical clock to one
		this.verticalClock = 1;
	
		
		//set size and speed
		this.speed = 4;
		this.entitySize = (int) (1.5*gp.tileSize);
		
		//get images
		this.getInvaderImage();
		
		//set type of entity to invader
		this.type = "Invader";
		
		//string to store start position of invader
		this.startPosString = startPosString;
		
		//set start coordinates and direction for invader
		if (startPosString == "right") {
			this.x = this.gp.screenWidth + 300;
			this.direction = "left";
		} else {
			this.x = -400;
			this.direction = "right";
		}
		
		this.y = 100;
		
		//invader hit box
		this.hitBoxSize = this.gp.tileSize;
		this.hitBoxSizeX = this.hitBoxSize;
		this.hitBoxSizeY = this.hitBoxSize;
		this.solidArea = new Rectangle(18, 18, hitBoxSizeX, hitBoxSizeY);
		this.cc = new CollisionChecker(this.gp, this);
		
	}
	
	/**
	 * load in images of invader
	 */
	public void getInvaderImage() {
		
		//try to load images and throw exception if not found
		try {
			this.center1 = ImageIO.read(getClass().getResourceAsStream("/Invader/Invader-Normal.png"));
			this.center2 = ImageIO.read(getClass().getResourceAsStream("/Invader/Invader-Curl-1.png"));
			this.left1 = ImageIO.read(getClass().getResourceAsStream("/Invader/Invader-Curl-2.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * update invader
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
		
		//if invader moving upwards
		if (this.upOrDown == "up") {
			
			//if at top
			if (this.y <= 0) {
				
				//switch to down and reset vertical clock
				this.upOrDown = "down";
				this.verticalClock = 1;
			
			//otherwise
			} else {
				
				this.y -= (int) (Math.sqrt(2*verticalClock));
				
			}
			
		} else if (this.upOrDown == "down") {
			
			//if at bottom
			if (this.y >= 150) {
				
				//switch to up and reset vertical clock
				this.upOrDown = "up";
				this.verticalClock = 1;
			
			//otherwise
			} else {
				
				this.y += (int) (Math.sqrt(2*verticalClock));
				
			}
			
		}
		
		if (this.gp.loopCount % this.shotFreq == 0) {
			Bullet bullet = new Bullet(this.gp, this, this.defaultBulletSpeed);
			this.bulletshot = bullet;
		} else {
			this.bulletshot = null;
		}
		
		//update hitbox
		this.cc.setCoordinates(this);
		
		
		this.count++;
		if (count >= 90) {
			count = 0;
		}
		
	}

	
	/**
	 * draw invader
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//functionality for different images based on different keyboard events
		BufferedImage image = null;
		if (count < 30) {
			image = this.center1;
		} else if (count < 60) {
			image = this.center2;
		} else {
			image = this.left1;
		}
	
		//draw image
		g2.drawImage(image, x, y, entitySize, entitySize, null);
		
	}

}
