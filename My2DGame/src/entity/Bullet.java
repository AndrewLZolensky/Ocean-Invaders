package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.CollisionChecker;
import main.GamePanel;

/**
 * bullets shot by invaders
 * @author andrewzolensky
 *
 */
public class Bullet extends entity{
	
	//link to game panel
	public GamePanel gp;
	
	//link to invader
	public Invader invader;
	
	//indicates whether bullet has been used
	public boolean used = false;
	
	/**
	 * Construct an instance of Bullet class
	 * @param gp GamePanel for the bullet
	 * @param invader to whom the bullet belongs
	 */
	public Bullet(GamePanel gp, Invader invader, int speed) {
		
		//link to game panel
		this.gp = gp;
		this.invader = invader;
		
		//bullet size and speed
		this.entitySize = (int) (0.75 * this.gp.tileSize);
		this.speed = speed;
		
		//set coordiantes to match invader coordinates
		this.x = this.invader.x;
		this.y = this.invader.y;
		
		//load bullet images
		this.loadBulletImage();
		
		//set type to bullet
		this.type = "bullet";
		
		//bullet squirt hit box
		this.hitBoxSize = (int) (0.5 * gp.tileSize);
		this.hitBoxSizeX = this.hitBoxSize;
		this.hitBoxSizeY = this.hitBoxSize;
		this.solidArea = new Rectangle(10, 10, hitBoxSizeX, hitBoxSizeY);
		this.cc = new CollisionChecker(this.gp, this);
		
	}
	
	/**
	 * load images of bullet
	 */
	public void loadBulletImage() {
		
		//try to load in image, generate exception if file not found
		try {
			this.center1 = ImageIO.read(getClass().getResourceAsStream("/Bullet/Bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * update bullet
	 */
	@Override
	public void update() {
		
		//bullet moves downwards only
		//bullet adding and discarding will occur in the main game loop
		//so update can ignore whether or not bullet has been used
		if (this.y < this.gp.screenHeight) {
			this.y += speed;
		}
		
		//update hitbox
		this.cc.setCoordinates(this);
		
	}
	
	/**
	 * draw bullet
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//set image to center1
		BufferedImage image = null;
		image = this.center1;
		
		//draw image
		g2.drawImage(image, x, y, entitySize, entitySize, null);
		
	}

}
