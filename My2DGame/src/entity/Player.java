package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;
import main.KeyHandler;

/**
 * Represents a player
 * @author andrewzolensky
 *
 */
public class Player extends entity{
	
	/**
	 * Instance variables
	 */
	
	//link to game panel
	GamePanel gp;
	
	//link to key handler
	KeyHandler keyH;
	
	//animation pacer
	int count1 = 0;
	
	//player health
	public int health;
	
	//to handle recovery after collisions (collisionState changes to "recovery", recovery count set in motion)
	public String collisionState = "none";
	public int recoveryCount = 0;
	
	//number of crabs eaten by player
	public int crabsEaten = 0;
	
	//number of bullets the player has
	public int numInks = 3;
	
	//array of shot inks
	public ArrayList<InkSquirt> shots = new ArrayList<InkSquirt>();
	
	//cool down period counter after shot
	public int coolDownCounter = 11;
	
	//count number of game loops until a bullet is replaced
	public int refill = 0;
	
	//player points
	public int points = 0;
	
	//indicates if player is dead
	public boolean isDead = false;
	
	//speed of ink shot
	public int inkSpeed = 11;
	
	//indicates if player is highest scorer
	public boolean highScorer = false;
	
	/**
	 * Constructor
	 * @param gp GamePanel
	 * @param keyH KeyHandler
	 */
	public Player(GamePanel gp, KeyHandler keyH) {
		
		//complete link to game panel
		this.gp = gp;
		
		//complete link to key handler
		this.keyH = keyH;
		
		//set coordinates, speed, direction and health
		this.setDefaultValues();
		
		//load in player images
		this.getPlayerImage();
		
		//set player size
		this.entitySize = 2 * gp.tileSize;
		this.type = "Player";
		
		//create hitbox
		this.hitBoxSize = gp.tileSize;
		this.hitBoxSizeX = this.hitBoxSize;
		this.hitBoxSizeY = this.hitBoxSize;
		this.solidArea = new Rectangle(15, 40, hitBoxSizeX, hitBoxSizeY);
		
		//link to collision checker
		this.cc = new CollisionChecker(gp, this);

		
	}
	
	/**
	 * player default values
	 */
	public void setDefaultValues() {
		
		//set player coordinates
		this.x = this.gp.screenWidth - 200;
		this.y = this.gp.screenHeight - 130;
		
		//set player speed
		this.speed = 8;
		
		//set player direction
		this.direction = "down";
		
		//set player health
		this.health = 3;
	}
	
	/**
	 * load in player images
	 */
	public void getPlayerImage() {
		
		try {
			
			//load in images for different actions and motions
			this.center1 = ImageIO.read(getClass().getResourceAsStream("/player/Octodude-Left.png"));
			this.center2 = ImageIO.read(getClass().getResourceAsStream("/player/Octodude-Center-2.png"));
			this.left1 = ImageIO.read(getClass().getResourceAsStream("/player/Octodude-Swim-Left-1.png"));
			this.right1 = ImageIO.read(getClass().getResourceAsStream("/player/Octodude-Swim-Right-1.png"));
			this.left2 = ImageIO.read(getClass().getResourceAsStream("/player/Octodude-Swim-Left-2.png"));
			this.right2 = ImageIO.read(getClass().getResourceAsStream("/player/Octodude-Swim-Right-2.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * accommodate user input
	 */
	@Override
	public void update() {
		
		//if player has no health, set to dead
		if (this.health == 0) {
			this.isDead = true;
		}
		
		//update each inkSquirt bullet that has been shot
		for (InkSquirt inkSquirt : shots) {
			inkSquirt.update();
		}
		
		//if player has recently been hit, increment count for safety period
		if (this.collisionState == "recovering") {
			if (this.recoveryCount < 150) {
				this.recoveryCount++;
			} else {
				this.collisionState = "none";
				this.recoveryCount = 0;
			}
		}
		
		//if up key is pressed
		if (this.keyH.upPressed == true) {
			
			//if player at top boundary, halt
			if (this.y < 300) {
				this.y += 0;
				
			//otherwise, move upwards
			} else {
				this.y -= speed;
			}
		}
		
		//if down key is pressed
		if (this.keyH.downPressed == true) {
			
			//if player at bottom boundary, halt
			if (this.y > this.gp.screenHeight - entitySize) {
				this.y += 0;
			
			//otherwise, move downwards
			} else {
				this.y += speed;
			}
		}
		
		//if left key is pressed
		if (this.keyH.leftPressed == true) {
			
			//set direction to left for drawing purposes
			direction = "left";
			
			//if player at left boundary, halt
			if (this.x < 0) {
				this.x += 0;
				
			//otherwise, move left
			} else {
				this.x -= speed;
			}
		}
		
		//if right key is pressed
		if (this.keyH.rightPressed == true) {
			
			//change direction to right
			direction = "right";
			
			//if player at right boundary, halt
			if (this.x > this.gp.screenWidth - 125) {
				this.x += 0;
			
				
			//otherwise, move right
			} else {
				this.x += speed;
			}
		}
		
		//if neither right nor left key is pressed, set direction to "none"
		if (!(this.keyH.leftPressed == true || this.keyH.rightPressed == true)) {
			direction = "none";
		}
		
		
		//if the user tries to shoot
		if (this.keyH.spacePressed == true) {
			
			//if there are more than zero ink squirts left
			if (this.numInks > 0 && coolDownCounter > 10) {
				
				//subtract one ink squirt from allowance
				this.numInks -= 1;
				
				//make new ink squirt
				InkSquirt ink = new InkSquirt(this.gp, this, this.inkSpeed);
				
				//add ink squirt to player's arraylist of shots
				this.shots.add(ink);
				
				//reset cooldown counter
				coolDownCounter = 1;
			}
			
		}
		
		//get rid of used ink shots
		ArrayList<InkSquirt> newShots = new ArrayList<InkSquirt>();
		
		for (InkSquirt ink : this.shots) {
			
			if (ink.used == false) {
				
				newShots.add(ink);
				
			}
			
		}
		
		this.shots = newShots;
		
		//increment cooldowncounter
		if (coolDownCounter > 0 && coolDownCounter <= 10) {
			coolDownCounter++;
		}
		
		//update coordinates of player hitbox to reflect position of player
		this.cc.setCoordinates(this);
		
		//increment animation pacer unless at limit, in which case reset 
		if (count1 < 40) {
			this.count1++;
		} else {
			count1 = 0;
		}
		
		//determine whether to refill ammo
		if (this.numInks < 3) {
			refill++;
		}
		if (refill == 75) {
			this.numInks = 3;
			refill = 0;
		}
		
	}
	
	/**
	 * draw player on screen
	 * @param g2
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//draw ink squirts
		for (InkSquirt inkSquirt : shots) {
			
			inkSquirt.draw(g2);
			
		}
		
		//functionality for different images based on different keyboard events
		
		//start with image = null
		BufferedImage image = null;
		
		//if image is null, set to center1
		if (image == null) {
			image = this.center1;
		}
		
		//in case of different directions of travel
		switch(direction) {
		
		//if no direction of travel
		case "none":
			
			//if in recovery, implement blinking and animation cycle
			if (this.collisionState == "recovering") {
				
				//if in first quarter of count, null
				if (count1 < 10) {
					image = null;
					
				//if in second quarter, image
				} else if (count1 < 20) {
					image = this.center1;
				}
				
				//if in third quarter, null
				else if (count1 < 30){
					image = null;
				}
				
				//if in fourth quarter, image
				else if (count1 < 40){
					image = this.center2;
				}
				
			//if not in recovery, just implement animation cycle
			} else {
				
				//if in first half of count, center1
				if (count1 < 20) {
					image = this.center1;
					
				//if in second half, center2
				} else  {
					image = this.center2;
				}
				
			}
			
			//break
			break;
		case "right":
			
			//if in recovery, implement blinking and animation cycle
			if (this.collisionState == "recovering") {
				
				//if in first quarter of count, null
				if (count1 < 10) {
					image = null;
					
				//if in second quarter, image
				} else if (count1 < 20) {
					image = this.right1;
				}
				
				//if in third quarter, null
				else if (count1 < 30){
					image = null;
				}
				
				//if in fourth quarter, image
				else if (count1 < 40){
					image = this.right2;
				}
				
			//if not in recovery, just implement animation cycle
			} else {
				
				//if in first half of count, right1
				if (count1 < 20) {
					image = this.right1;
					
				//if in second half, right2
				} else  {
					image = this.right2;
				}
				
			}
			
			//break
			break;
		case "left":
			
			//if in recovery, implement blinking and animation cycle
			if (this.collisionState == "recovering") {
				
				//if in first quarter of count, null
				if (count1 < 10) {
					image = null;
					
				//if in second quarter, image
				} else if (count1 < 20) {
					image = this.left1;
				}
				
				//if in third quarter, nothing
				else if (count1 < 30){
					image = null;
				}
				
				//if in fourth quarter, image
				else if (count1 < 40){
					image = this.left2;
				}
				
			//if not in recovery, just implement animation cycle
			} else {
				
				//if in first half of count, left1
				if (count1 < 20) {
					image = this.left1;
					
				//if in second half, left2
				} else  {
					image = this.left2;
				}
				
			}
			
			//break
			break;
		}
		
		
		//draw image
		g2.drawImage(image, this.x, this.y, entitySize, entitySize, null);
		
		
	}

}
