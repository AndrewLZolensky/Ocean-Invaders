package main;

import entity.entity;

/**
 * checks for collision with associated entity
 * @author andrewzolensky
 *
 */
public class CollisionChecker {
	
	//link to GamePanel
	GamePanel gp;
	
	//parameters of hitbox
	public int leftX, rightX, topY, bottomY;
	
	//link to entity for which hitbox is being constructed
	entity e;
	
	/**
	 * construct instance of collision checker class
	 * @param gp GamePanel to operate within
	 * @param e Entity to frame
	 */
	public CollisionChecker(GamePanel gp, entity e) {
		
		//link to game panel and entity
		this.gp = gp;
		this.e = e;
		
		//set coordinates of hitbox
		setCoordinates(e);
		
	}
	
	/**
	 * calculates coordinates of hitbox
	 * @param e
	 */
	public void setCoordinates(entity e) {
		
		//left, right, top and bottom coordinates
		this.leftX = e.x + e.solidArea.x;
		this.rightX = e.x + e.solidArea.x + e.hitBoxSizeX;
		this.bottomY = e.y + e.solidArea.y + e.hitBoxSizeY;
		this.topY = e.y + e.solidArea.y;
	}
	
	
	/**
	 * checks if hitbox is overlapping with other entity's hitbox
	 * @param other entity to capture collision with
	 * @return type of entity colliding with main object, or noneif no collision
	 */
	public String checkOverlapsWith(entity other) {
		
		//find coordinates of other entity
		int otherLeftX = other.cc.leftX;
		int otherRightX = other.cc.rightX;
		int otherTopY = other.cc.topY;
		int otherBottomY = other.cc.bottomY;
		
		//right side collision
		
		//if main object right border in between other object left and right borders
		if ((otherLeftX <= this.rightX) && (this.rightX <= otherRightX)) {
			
			//if main object bottom in between other object top and bottom
			if ((otherTopY <= this.bottomY) && (this.bottomY <= otherBottomY)) {
				return other.type;
			}
			
			//if main object top in between other object top and bottom
			if ((otherTopY <= this.topY) && (this.topY <= otherBottomY)) {
				return other.type;
			}
		}
		
		//left side collision
		
		//if main object right border in between other object left and right borders
		if ((otherLeftX <= this.leftX) && (this.leftX <= otherRightX)) {
			
			//if main object bottom in between other object top and bottom
			if ((otherTopY <= this.bottomY) && (this.bottomY <= otherBottomY)) {
				return other.type;
			}
			
			//if main object top in between other object top and bottom
			if ((otherTopY <= this.topY) && (this.topY <= otherBottomY)) {
				return other.type;
			}
		}
		
		
		//if no collision, return "none"
		return "none";
		
		
	}

}
