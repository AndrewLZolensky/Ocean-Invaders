package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * on-screen component to keep track of player health
 * @author andrewzolensky
 *
 */
public class HealthBar extends entity{
	
	//link to Game Panel
	GamePanel gp;
	
	//link to player
	Player p;
	
	//health level
	int healthLevel;
	
	
	/**
	 * construct a health bar
	 */
	
	public HealthBar(GamePanel gp, Player p) {
		
		//link HealthBar to GamePanel
		this.gp = gp;
		
		//link HealthBar to Player
		this.p = p;
		
		//get images of health bar
		this.getHealthBarImage();
		
		//set screen position
		this.x = 10;
		this.y = 0;
		
		//set size of image
		this.entitySize = (int) (1.5*gp.tileSize);
		
		//set health level equal to player health
		this.healthLevel = p.health;
		
		
	}
	
	/**
	 * load in health bar images
	 */
	public void getHealthBarImage() {
		
		//try to load in images of health bar, throw exception if file is not found
		try {
			this.left1 = ImageIO.read(getClass().getResourceAsStream("/HealthBar/Health-Bar-Full.png"));
			this.left2 = ImageIO.read(getClass().getResourceAsStream("/HealthBar/Health-Bar-2-Hearts.png"));
			this.right1 = ImageIO.read(getClass().getResourceAsStream("/HealthBar/Health-Bar-1-Heart.png"));
			this.right2 = ImageIO.read(getClass().getResourceAsStream("/HealthBar/Health-Bar-Empty.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * update health bar by updating health level
	 */
	@Override
	public void update() {
		
		//set health level equal to player health level
		this.healthLevel = this.p.health;
		
		
		
	}

	
	/**
	 * draw health bar
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//set image equal to null
		BufferedImage image = null;
		
		//use different images based on health level
		if (this.p.health == 3) {
			image = this.left1;
		} else if (this.p.health == 2) {
			image = this.left2;
		} else if (this.p.health == 1) {
			image = this.right1;
		} else {
			image = this.right2;
		}
		
		//draw image
		g2.drawImage(image, this.x, this.y, this.entitySize, this.entitySize, null);
	
		
		
		
	}

}
