package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * on-screen component to indicate how many bullets the player has
 * @author andrewzolensky
 *
 */
public class BulletCounter extends entity{

	//link to Game Panel
	GamePanel gp;
	
	//link to player
	Player p;
	
	//number of bullets
	public int numBullets;
	
	
	/**
	 * construct a bullet counter
	 */
	
	public BulletCounter(GamePanel gp, Player p) {
		
		//link BulletCounter to GamePanel
		this.gp = gp;
		
		//link HealthBar to Player
		this.p = p;
		
		//get images of health bar
		this.getBulletCounterImage();
		
		//set screen position
		this.x = this.gp.screenWidth - 103;
		this.y = 0;
		
		//set size of image
		this.entitySize = (int) (1.5*gp.tileSize);
		
		//set num bullets equal to player number of bullets
		this.numBullets = p.numInks;
		
		
	}
	
	/**
	 * load in bullet counter image
	 */
	public void getBulletCounterImage() {
		
		//try to load in image of bullet count bar, throw exception if file is not found
		try {
			this.center1 = ImageIO.read(getClass().getResourceAsStream("/Bullet/Ink-shot-counter.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * update bullet counter by updating health level
	 */
	@Override
	public void update() {
		
		//set number of bulletss equal to player number of bullets
		this.numBullets = this.p.numInks;
		
	}

	
	/**
	 * draw bullet counter
	 */
	@Override
	public void draw(Graphics2D g2) {
		
		//set image equal to null
		BufferedImage image = null;
		
		//set image the same each time
		image = this.center1;
	
		
		//draw image
		g2.drawImage(image, this.x, this.y, this.entitySize, this.entitySize, null);
		
		
		//draw numbr of bullets
		g2.setColor(Color.black);
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		String pointString2 = String.valueOf(this.p.numInks);
		g2.drawString(pointString2, this.x + 63, this.y + 58);
			
			
			
		}

}
