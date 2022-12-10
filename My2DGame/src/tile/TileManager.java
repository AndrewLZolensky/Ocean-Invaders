package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * Manages background tiles
 * @author andrewzolensky
 *
 */
public class TileManager {
	
	//link to game panel
	GamePanel gp;
	
	//arraylist of different kinds of tiles
	Tile[] tile;
	
	//map of tiles
	int mapTileNum[][];
	
	/**
	 * constuct tile manager
	 * @param gp
	 */
	public TileManager(GamePanel gp) {
		
		//link to game panel
		this.gp = gp;
		
		//set arraylist of tiles
		this.tile = new Tile[30];
		
		//set tile map
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		
		//get tile images
		getTileImage();
		
		//load tile map from maps res pack
		this.loadMap("/maps/Map.txt");
		
	}
	
	/**
	 * Loads pixel-art tiles
	 */
	public void getTileImage() {
		
		//load in pixel art
		try {
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Seaweed.png"));
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Seafloor-Main.png"));
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Transition-1.png"));
			
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Transition-7.png"));
			
			tile[17] = new Tile();
			tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Transition-8.png"));
			
			tile[19] = new Tile();
			tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Transition-0-1.png"));
			
			tile[20] = new Tile();
			tile[20].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Transition-sea-sky.png"));
			
			tile[21] = new Tile();
			tile[21].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Sea-Undertone.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Function to load map data
	 */
	public void loadMap(String filePath) {
		
		try {
			
			//load map data
			
			//set up input stream
			InputStream is = getClass().getResourceAsStream(filePath);
			
			//set up buffered reader
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//row and col to read from
			int col = 0;
			int row = 0;
			
			//loop through rows and columns of numeric tile map
			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				
				String line = br.readLine();
				
				while (col < gp.maxScreenCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					
					col++;
					
				}
				
				if (col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
				
			}
			
			br.close();
		
		} catch (Exception e) {
			
		}
		
	}
	
	/**
	 * Draws tiles from map
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		
		//map tile map to game screen
		
		//row and column of tile map
		int col = 0;
		int row = 0;
		
		//x and y of game screen
		int x = 0;
		int y = 0;
		
		//loop through rows and columns and map tiles onto game screen
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			
			col++;
			x += gp.tileSize;
			
			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
			
		}
		
		
	}

}
