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
 * ink shots for player to fire at invaders
 * @author andrewzolensky
 *
 */
public class InkSquirt extends entity{
	
	//link to game panel
	GamePanel gp;
	
	//link to player
	Player p;
	
	//assume ink has not been shot
	public boolean shot = false;
	public boolean used = false;
	
	/**
	 * construct an instance of InkSquirt
	 */
	public InkSquirt(GamePanel gp, Player player, int inkSpeed) {
		
		//link to game panel
		this.gp = gp;
		
		//link to player
		this.p = player;
		
		//initialize at player coordinates
		this.x = player.x;
		this.y = player.y;
		
		//make fast
		this.speed = inkSpeed;
		
		//type
		this.type = "Ink Squirt";
		
		//size
		this.entitySize = (int) (0.75 * this.gp.tileSize);
		
		//ink squirt hit box
		this.hitBoxSize = (int) (0.5 * gp.tileSize);
		this.hitBoxSizeX = this.hitBoxSize;
		this.hitBoxSizeY = this.hitBoxSize;
		this.solidArea = new Rectangle(10, 10, hitBoxSizeX, hitBoxSizeY);
		this.cc = new CollisionChecker(this.gp, this);
		
		this.loadInkSquirtImage();
		
	}
	
	/**
	 * load in images of ink squirts
	 */
	public void loadInkSquirtImage() {
		
		//try to load images and throw exception if not found
		try {
			this.center1 = ImageIO.read(getClass().getResourceAsStream("/Ink/Ink-Shot.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * update ink squirt object
	 */
	@Override
	public void update() {
		
		if (this.y > -100) {
			this.y -= speed;
		}
		
		this.cc.setCoordinates(this);
		
	}

	/**
	 * draw ink squirt object
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//use image center1
		BufferedImage image = this.center1;
	
		//draw image
		g2.drawImage(image, x, y, entitySize, entitySize, null);
		
	}

}
