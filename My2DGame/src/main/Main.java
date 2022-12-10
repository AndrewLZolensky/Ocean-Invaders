package main;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author andrewzolensky
 * Welcome to Ocean Invaders!
 * 
 * art by andrewzolensky
 * 	- except for crabs: by grace haley (a friend)
 * followed youtube tutorial to get started:
 * 	- youtuber: RyiSnow
 * 	- tutorial URL: https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 * 
 * 
 * This game works as follows:
 * 	- start on menu screen
 * 	- press k to start game
 * 	- try not to get shot by ocean invaders
 * 	- try not to get hit by shark or eel
 * 	- eat crabs to regain health
 * 	- shoot invaders to get points
 * 	- die by losing 3 hearts
 * 	- go to death screen
 * 	- press r to try again to beat your high score
 * 	- press m to get back to main menu
 *
 */
public class Main {

	public static void main(String[] args) {
		
		//new JFrame window
		JFrame window = new JFrame();
		
		//make closable
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//not resizable
		window.setResizable(false);
		
		//title
		window.setTitle("Ocean Invaders");
		
		
		//create new Game Panel object and add to window
		//then, pack
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		//center window and make visible
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		

	}

}