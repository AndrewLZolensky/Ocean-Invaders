package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.CollisionChecker;
import main.GamePanel;

/**
 * Represents an entity
 * @author andrewzolensky
 *
 */
public abstract class entity {

	
	//coordinates and speed of entity
	public int x, y;
	public int speed;
	
	//picture of entity
	public BufferedImage center1, center2, left1, right1, left2, right2, left3, right3;
	public String direction;
	
	//type and size of entity
	public String type;
	public int entitySize;
	
	//hit box and collision checker
	public Rectangle solidArea;
	public String collisionType = "none";
	public CollisionChecker cc;
	public int hitBoxSize;
	
	//assume entity has not been eaten
	public boolean eaten = false;
	
	//assume entity is not eating
	public boolean eating = false;
	public int hitBoxSizeX;
	public int hitBoxSizeY;
	
	
	//update method
	public abstract void update();

	//draw method
	public abstract void draw(Graphics2D g2);

}
