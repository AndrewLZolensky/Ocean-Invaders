package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Invader;

/**
 * user interface for different game screens
 * @author andrewzolensky
 *
 */
public class UI {
	
	//link to game panel
	GamePanel gp;
	
	//set font
	Font arial_40;
	
	//link to image
	BufferedImage scoreImage;
	
	//link to graphics object
	Graphics2D g2;
	
	/**
	 * construct instance of UI object
	 * @param gp
	 */
	public UI(GamePanel gp) {
		
		//link to game panel
		this.gp = gp;
		
		//set font
		this.arial_40 = new Font("Arial", Font.PLAIN, 40);
		
		//get invader image for score board
		Invader invader = new Invader(this.gp, "right");
		scoreImage = invader.center1;
		
		
	}
	
	/**
	 * draw different screens
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		
		//set g2
		this.g2 = g2;
		
		//if user is on menu
		if (this.gp.gameState == this.gp.titleState) {
			
			//draw title screen
			this.drawTitleScreen();
			
		} else if (this.gp.gameState == this.gp.deadState) {
			
			this.drawDeadScreen();
		
		//if in play state
		} else if (this.gp.gameState == this.gp.playState) {
			
			//if player just died
			if (this.gp.player.isDead == true) {
				
				//if player beat high score
				if (this.gp.player.points > this.gp.highScore) {
					
					//replace high score
					this.gp.highScore = this.gp.player.points;
					
					//set player to high scorer
					this.gp.player.highScorer = true;
					
				}
				
				//send game to dead state
				this.gp.gameState = this.gp.deadState;
				
				
				
			}
			
			//if game is paused
			if (this.gp.gameState == this.gp.pauseState) {
				
				//set font
				g2.setFont(arial_40);
				g2.setColor(Color.magenta);
				
				//draw text
				String pauseText = "Game Paused";
				int pTextLength = (int) g2.getFontMetrics().getStringBounds(pauseText, g2).getWidth();
				int x = this.gp.screenWidth/2 - pTextLength/2;
				int y = this.gp.screenHeight/2;
				g2.drawString(pauseText, x, y);
				
			}
			
			
			//draw score
			g2.setFont(arial_40);
			g2.setColor(Color.black);
			g2.drawImage(scoreImage, this.gp.screenWidth/2 - 85, 10, gp.tileSize, gp.tileSize, null);
			g2.drawString("X " + this.gp.player.points, this.gp.screenWidth/2 - 10, 50);
			
		}
		
		
		
	}
	
	/**
	 * draw title screen
	 */
	public void drawTitleScreen() {
		
		//background color
		g2.setColor(Color.CYAN);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//draw title
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		String text = "Ocean Invaders";
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = this.gp.screenWidth/2 - textLength/2;
		int y = this.gp.screenHeight/7;
		
		//shadow
		g2.setColor(Color.black);
		g2.drawString(text, x-5, y-5);
		
		//main color
		g2.setColor(Color.BLUE);
		g2.drawString(text, x, y);
		
		//set font
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
		
		//draw instructions
		
		//text
		text = "W-A-S-D to move";
		x = 275;
		y = 200;
		
		//draw
		g2.setColor(Color.blue);
		g2.drawString(text, x, y);
		
		//text
		text = "Space to shoot";
		x = 575;
		y = 200;
		
		//draw
		g2.drawString(text, x, y);
		
		//text
		text = "5 crabs = 1 heart";
		x = 275;
		y = 300;
		
		//draw
		g2.drawString(text, x, y);
		
		//text
		text = "Watch out for sharks...";
		x = 550;
		y = 300;
		
		//draw
		g2.drawString(text, x, y);
		
		//draw title
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		text = "Press K to start";
		textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = this.gp.screenWidth/2 - textLength/2;
		y = 6*this.gp.screenHeight/7;
		
		//shadow
		g2.setColor(Color.black);
		g2.drawString(text, x-5, y-5);
		
		//main color
		g2.setColor(Color.blue);
		g2.drawString(text, x, y);
		
		g2.drawImage(this.gp.player.center1, 375, 325, 4*this.gp.tileSize, 4*this.gp.tileSize, null);
		
	}
	
	/**
	 * draw player dead screen
	 */
	public void drawDeadScreen() {
		
		//background color
		g2.setColor(Color.CYAN);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		//set font
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F));
		
		//draw dead text
		String text = "You died!";
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = this.gp.screenWidth/2 - textLength/2;
		int y = this.gp.screenHeight/7;
		
		//shadow
		g2.setColor(Color.black);
		g2.drawString(text, x-5, y-5);
		
		//color text
		g2.setColor(Color.blue);
		g2.drawString(text, x, y);
		
		//reset font
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
		
		//draw score
		String scoreText = (String) ("Final Score: " + this.gp.player.points);
		x = 150;
		y = 200;
		
		//color text
		g2.setColor(Color.blue);
		g2.drawString(scoreText, x, y);
		
		//if player is high scorer
		if (this.gp.player.highScorer == true) {
			
			//print congratulatory message
			String hScoreText = (String) ("New High Score!");
			x = 150;
			y = 300;
			
			
			//color text
			g2.setColor(Color.blue);
			g2.drawString(hScoreText, x, y);
			
		} else {
			
			//print un-congratulatory message
			String hScoreText = (String) ("No High Score");
			x = 150;
			y = 300;
			
			//color text
			g2.setColor(Color.blue);
			g2.drawString(hScoreText, x, y);
		}
		
		//draw high score
		String hScoreText = (String) ("High Score: " + this.gp.highScore);
		x = 350;
		y = 500;
		
		//color text
		g2.setColor(Color.blue);
		g2.drawString(hScoreText, x, y);
		
		//print press r to restart
		String msg = (String) ("Press R to Restart");
		textLength = (int) g2.getFontMetrics().getStringBounds(msg, g2).getWidth();
		x = 500;
		y = 200;
		
		//color text
		g2.setColor(Color.blue);
		g2.drawString(msg, x, y);
		
		//print press m for main menu
		String msg2 = (String) ("Press M for Main Menu");
		textLength = (int) g2.getFontMetrics().getStringBounds(msg2, g2).getWidth();
		x = 500;
		y = 300;
		
		//color text
		g2.setColor(Color.blue);
		g2.drawString(msg2, x, y);
		
		g2.drawImage(this.gp.player.center1, 75, 500, 4*this.gp.tileSize, 4*this.gp.tileSize, null);
		g2.drawImage(this.gp.invaders.get(0).center1, 650, 500, 4*this.gp.tileSize, 4*this.gp.tileSize, null);
		
	}

}
