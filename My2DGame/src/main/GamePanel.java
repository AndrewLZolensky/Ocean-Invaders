package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entity.Bullet;
import entity.BulletCounter;
import entity.Clownfish;
import entity.Crab;
import entity.Eel;
import entity.HealthBar;
import entity.InkSquirt;
import entity.Invader;
import entity.Player;
import entity.Shark;
import entity.entity;
import tile.TileManager;

/**
 * Main game panel
 * @author andrewzolensky
 *
 */
public class GamePanel extends JPanel implements Runnable{
	
	/**
	 * screen settings
	 */
	
	//set box size in units of pixels
	final int originalTileSize = 16; //16x16 tile
	final int scale = 4;
	public final int tileSize = originalTileSize * scale; //48x48 tile
	
	//set cols and rows in units of boxes
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	
	//get total height and width in units of pixels
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//FPS
	int FPS = 60;
	
	//game state
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int deadState = 3;
	
	
	
	/**
	 * counts and params
	 */
	
	//default invader parameters
	public int invaderSpeed;
	public int invaderBulletSpeed;
	public int invaderShotFreq;
	
	//loop and kill counters
	public int loopCount;
	public int killCounter;
	
	//high score
	public int highScore = 0;
	
	/**
	 * set up components
	 */
	
	//create clock, tile manager, key handler, player, Health Bar, bullet and point counter bars
	TileManager tileM;
	KeyHandler keyH;
	Thread gameThread;
	Player player;
	HealthBar healthBar;
	BulletCounter bulletCounter;
	
	//UI
	public UI ui = new UI(this);
	
	
	//create sea creatures, list of collidable entities, and list of components
	ArrayList<Clownfish> clownfishes;
	ArrayList<entity> collidables;
	ArrayList<entity> components;
	ArrayList<Invader> invaders;
	ArrayList<Bullet> bullets;
	
	
	
	
	/**
	 * construct game panel
	 */
	public GamePanel() {
		
		
		//set size to width and height (units of pixels)
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		
		//change background color
		this.setBackground(Color.black);
		
		//for speed
		this.setDoubleBuffered(true);
		
		this.setUpGame();
		
		
		

	}
	
	/**
	 * starts thread
	 */
	public void startGameThread() {
		
		//instantiate thread
		gameThread = new Thread(this);
		
		//start thread --> calls run()
		gameThread.start();
		
	}
	
	/**
	 * set up game with default values
	 */
	public void setUpGame() {
		
		//set game state to play state
		this.gameState = this.titleState;
		
		//tile manager
		this.tileM = new TileManager(this);
		
		//key handler
		this.keyH = new KeyHandler(this);
		
		//player
		this.player = new Player(this, this.keyH);
		
		//health bar
		this.healthBar = new HealthBar(this, player);
		
		//bullet counter
		this.bulletCounter = new BulletCounter(this, player);
		
		//loop and kill counts
		this.loopCount = 1;
		this.killCounter = 1;
		
		//add key listener
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		//build clownfish arraylist
		this.clownfishes = new ArrayList<Clownfish>();
		for (int i = 0; i < 5; i++) {
			Clownfish clownfish = new Clownfish(this);
			this.clownfishes.add(clownfish);
		}
		
		//build collidables arraylist
		collidables = new ArrayList<entity>();
		
		//add ocean creatures to collidables
		Crab crab1 = new Crab(this);
		Crab crab2 = new Crab(this);
		Shark shark = new Shark(this);
		Eel eel = new Eel(this);
		
		
		this.collidables.add(shark);
		this.collidables.add(crab1);
		this.collidables.add(crab2);
		this.collidables.add(eel);
		
		//build components arraylist
		this.components = new ArrayList<entity>();
		
		this.components.add(this.healthBar);
		this.components.add(this.bulletCounter);
		//this.components.add(this.pointCounter);
		
		//build invader arraylist
		this.invaders = new ArrayList<Invader>();
		
		this.invaderSpeed = 4;
		this.invaderBulletSpeed = 10;
		this.invaderShotFreq = 90;
		
		
		Invader invader1 = new Invader(this, "left");
		Invader invader2 = new Invader(this, "right");
		
		invader1.speed = this.invaderSpeed;
		invader1.shotFreq = this.invaderShotFreq;
		invader1.defaultBulletSpeed = this.invaderBulletSpeed;
		
		invader2.speed = this.invaderSpeed;
		invader2.shotFreq = this.invaderShotFreq;
		invader2.defaultBulletSpeed = this.invaderBulletSpeed;
		
		
		invaders.add(invader1);
		invaders.add(invader2);
		
		//build bullet arraylist
		this.bullets = new ArrayList<Bullet>();
		
		
	}

	@Override
	/**
	 * method to run game
	 */
	public void run() {
		
		//get desired number of nanoseconds per frame from desired FPS
		double drawInterval = 1000000000/FPS; //0.016666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		//while gameThread exists, loop
		while(gameThread != null) {
			
			
			//1 UPDATE: information such as character position
			//--> call update()
			update();
			
			//2 DRAW: draw screen with updated information
			//--> call paintComponent() (this is how)
			repaint();
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				
				remainingTime = remainingTime/1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * updates game information each turn
	 */
	public void update() {
		
		
		//start player in recovery mode to avoid damage
		if (this.loopCount < 50) {
			this.player.collisionState = "recovering";
		}
		
		//if game is in play state
		if (this.gameState == this.playState) {
			
			//set player eating back to false (effectively does nothing if already false)
			this.player.eating = false;
			
			/**
			 * update sea creatures
			 */
			
			//fish
			for (Clownfish clownfish : clownfishes) {
				clownfish.update();
			}
			
			//sharks and crabs
			for (entity creature : collidables) {
				
				creature.update();
				
			}
			
			
			
			
			/**
			 * update enemies
			 */
			
			//invaders
			for (Invader invader : invaders) {
				
				invader.update();
				
				//check if invader has bullet to shoot
				if (invader.bulletshot != null) {
					
					//if so, add to arraylist of bullets
					Bullet bullet = invader.bulletshot;
					this.bullets.add(bullet);
				}
					
			}
			
			//update bullets
			for (Bullet bullet : this.bullets) {
				bullet.update();
			}
			
			//check each bullet for being out of bounds or a collision with player
			for (Bullet bullet : this.bullets) {
				
				//if bullet hits player
				if (bullet.cc.checkOverlapsWith(this.player) == "Player") {
					
					//bullet switches to used
					bullet.used = true;
					
					//player takes damage and enters recovery
					if ((this.player.collisionState == "none") && (this.player.health > 0)) {
						
						this.player.health -= 1;
						
						if (this.player.health > 0) {
							
							this.player.collisionState = "recovering";
						}
					}
				}
				
				//if bullet is out of bounds, bullet switches to used
				if (bullet.y > this.screenHeight) {
					bullet.used = true;
				}
				
			}
			
			//drop used bullets from arraylist
			ArrayList<Bullet> newBullets = new ArrayList<Bullet>();
			
			for (Bullet bullet : this.bullets) {
				if (bullet.used == false) {
					newBullets.add(bullet);
				}
			}
			
			this.bullets = newBullets;
			
			
			
			
			/**
			 * update player
			 */
			
			//update player
			this.player.update();
			
			
			
			
			
			/**
			 * implement player collision-triggered special actions
			 */
			
			//find all player collisions
			for (entity creature : collidables) {
				
				
				//if player collides with crab
				if (this.player.cc.checkOverlapsWith(creature) == "Crab") {
					
					//player eats crab
					creature.eaten = true;
					player.eating = true;
					player.crabsEaten++;
					
					//if player has eaten 5 crabs, increment health
					if (player.crabsEaten == 3) {
						
						player.crabsEaten = 0;
						
						if (player.health < 3) {
							player.health++;
						}
					}
					
				}
				
				//if player collides with eel
				if (this.player.cc.checkOverlapsWith(creature) == "Eel") {
					
					//player takes damage and enters recovery
					if ((this.player.collisionState == "none") && (this.player.health > 0)) {
						this.player.health -= 1;
						if (this.player.health > 0) {
							this.player.collisionState = "recovering";
						}
					}
					
				}
				
				//if player collides with shark
				if (this.player.cc.checkOverlapsWith(creature) == "Shark") {
					
					//player takes damage and enters recovery
					if ((this.player.collisionState == "none") && (this.player.health > 0)) {
						this.player.health -= 1;
						if (this.player.health > 0) {
							this.player.collisionState = "recovering";
						}
					}
					
				}
				
			}
			
			
			
			
			
			/**
			 * replace dead invaders and crabs
			 */
			
			//replace eaten creatures in collidables
			ArrayList<entity> updatedCollidables = new ArrayList<entity>();
			for (entity creature : collidables) {
				
				//keep uneaten creatues
				if (creature.eaten == false) {
					updatedCollidables.add(creature);
				
				//replace eaten creatures
				} else {
					
					//if eaten creature was crab
					if (creature.type == "Crab") {
						Crab newCrab = new Crab(this);
						updatedCollidables.add(newCrab);
					}
				}
				
			}
			
			//reset collidables
			collidables = updatedCollidables;
			
			//for each invader in the game
			for (Invader invader : invaders) {
				
				//for each bullet flying about
				for (InkSquirt inkSquirt : this.player.shots) {
					
					//if the invader has been hit
					if (invader.cc.checkOverlapsWith(inkSquirt) == "Ink Squirt") {
						
						//set the invader to dead
						invader.dead = true;
						
						//set ink squirt to used
						inkSquirt.used = true;
						
						//add a point to the player score
						this.player.points++;
						
					}
					
				}
			}
			
			//updated invader arraylist
			ArrayList<Invader> updatedInvaders = new ArrayList<Invader>();
			
			//if user kill counter is high enough
			//reset and increase player and invader stats
			if (this.killCounter == 4) {
				this.killCounter = 0;
				this.invaderSpeed++;
				this.invaderBulletSpeed++;
				if (this.invaderShotFreq > 3) {
					this.invaderShotFreq--;
				}
				this.player.inkSpeed++;
			}
			
			//keep living invaders and replace dead ones
			for (Invader invader : invaders) {
				
				if (invader.dead == false) {
					
					updatedInvaders.add(invader);
					
				} else {
					
					this.killCounter++;
					Invader newInvader = new Invader(this, invader.startPosString);
					newInvader.speed = this.invaderSpeed;
					newInvader.shotFreq = this.invaderShotFreq;
					newInvader.defaultBulletSpeed = this.invaderBulletSpeed;
					updatedInvaders.add(newInvader);
				
				}
				
			}
			
			//every so often, add another invader
			if (this.loopCount % 2000 == 0) {
				
				Invader newInvader = new Invader(this, "right");
				newInvader.speed = this.invaderSpeed;
				newInvader.shotFreq = this.invaderShotFreq;
				newInvader.defaultBulletSpeed = this.invaderBulletSpeed;
				updatedInvaders.add(newInvader);
				
			}
			
			//reset invaders arraylist
			this.invaders = updatedInvaders;

			
			
			
			/**
			 * update other on screen components
			 */
			
			//update all components
			for (entity component : components) {
				
				component.update();
				
			}
			
			/**
			 * clean up
			 */
			
			
			
			//increment master count
			this.loopCount++;
			
		}
		
		//if game is paused
		if (this.gameState == this.pauseState) {
			
			//nothing
			
		}
		
		//if game in menu state
		if (this.gameState == this.titleState) {
			
			//if k pressed
			if (this.keyH.kPressed == true) {
				
				//create new game and send game into play state
				this.setUpGame();
				this.gameState = this.playState;
			}
		}
		
		//if game in death state
		if (this.gameState == this.deadState) {
			
			//if player presses r
			if (this.keyH.rPressed == true) {
				
				//set up new game and restart
				this.setUpGame();
				this.gameState = this.playState;
			}
			
			
			//if player presses m
			if (this.keyH.mPressed == true) {
				
				//return to main menu
				this.gameState = this.titleState;
			}
		}
		
		
		
	
		
		
		
		
	}
	
	/**
	 * paints objects on screen each turn
	 */
	public void paintComponent(Graphics g) {
		
		//call parent class on Graphics g
		super.paintComponent(g);
		
		//cast Graphics g as Graphics2D object g2
		Graphics2D g2 = (Graphics2D) g;
		
		//if user is in menu
		if (this.gameState == this.titleState) {
			
			//draw ui
			this.ui.draw(g2);
		
		//otherwise
		} else if (this.gameState == this.deadState) {
			
			//draw ui
			this.ui.draw(g2);
			
		}
		
		
		
		else {
			
			//draw tiles
			this.tileM.draw(g2);
			
			
			//draw clownfishes
			for (Clownfish clownfish : clownfishes) {
				
				clownfish.draw(g2);
				
			}
			
			//draw collidables
			for (entity creature : collidables) {
				
				creature.draw(g2);
				
			}
			
			//draw invaders
			for (Invader invader : invaders) {
				
				invader.draw(g2);
				
			}
			
			//draw bullets
			for (Bullet bullet : bullets) {
				bullet.draw(g2);
			}
			
			
			//draw components
			for (entity component : components) {
				
				component.draw(g2);
				
			}
			
			//draw player
			this.player.draw(g2);
			
			//draw UI
			this.ui.draw(g2);
			
			//clear
			g2.dispose();
			
		}
		
	}

}
