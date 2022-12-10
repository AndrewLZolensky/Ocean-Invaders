package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key handler class for player
 * @author andrewzolensky
 *
 */
public class KeyHandler implements KeyListener{
	
	//variables to store motion input
	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
	
	//game panel
	GamePanel gp;
	
	//variables to tell game to move between frames
	public boolean kPressed, rPressed, mPressed;
	
	/**
	 * construct new KeyHandler
	 * @param gp
	 */
	public KeyHandler(GamePanel gp) {
		
		//link to gamepanel
		this.gp = gp;
		
	}

	/**
	 * do nothing here
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * get key events
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
		//returns number for key that was pressed
		int code = e.getKeyCode();
		
		//if user pressed "w" key
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		
		//if user pressed "s" key
		if(code == KeyEvent.VK_S) {
			downPressed = true;
			
		}
		
		//if user pressed "a" key
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
			
		}
		
		//if user pressed "d" key
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		
		//if user pressed "space" key
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
		
		//if user pressed "p" key
		if(code == KeyEvent.VK_P) {
			
			//if unpaused, pause
			if (gp.gameState == gp.playState) {
				
				gp.gameState = gp.pauseState;
			
			//if paused, unpause
			} else if (gp.gameState == gp.pauseState) {
				
				gp.gameState = gp.playState;
				
			}
		}
		
		//if user pressed "k" key
		if(code == KeyEvent.VK_K) {
			
			this.kPressed = true;
		}
		
		//if user pressed "r" key
		if(code == KeyEvent.VK_R) {
			
			this.rPressed = true;
		}
		
		//if user pressed "m" key
		if(code == KeyEvent.VK_M) {
			
			this.mPressed = true;
		}
		
	}

	/**
	 * release keys
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		//if user released "w" key
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		
		//if user released "s" key
		if(code == KeyEvent.VK_S) {
			downPressed = false;
			
		}
		
		//if user released "a" key
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
			
		}
		
		//if user released "d" key
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
		//if user released "space" key
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		
		//if user pressed "k" key
		if(code == KeyEvent.VK_K) {
			
			this.kPressed = false;
		}
		
		//if user pressed "r" key
		if(code == KeyEvent.VK_R) {
			
			this.rPressed = false;
		}
		
		//if user pressed "m" key
		if(code == KeyEvent.VK_M) {
			
			this.mPressed = false;
		}
		
		
		
	}

}
