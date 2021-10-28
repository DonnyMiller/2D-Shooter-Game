import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
	BufferedImage image = null;
	static final int NUMBER_OF_BULLETS = 10; 
	static final int BULLET_SPEED = 10; 
	static final int SHOT_DELAY = 250; 
	static final int PLAYER_SPEED = 10; 
	static final int PLAYER_WIDTH = 30; 
	static final int PLAYER_HEIGHT = 30; 
	static final int PLAYER_BULLET_WIDTH = 5; 
	static final int PLAYER_BULLET_HEIGHT = 5; 
	
	boolean moveUp;
	boolean moveDown;
	boolean fire;
	long lastPress = 0; 


	
	Player(int height, int borderLimit) {
		super(borderLimit, height / 2, PLAYER_SPEED, PLAYER_WIDTH, PLAYER_HEIGHT);
		this.moveUp = false;
		this.moveDown = false;
		this.fire = false;
		
		try {
			image = ImageIO.read(new File("pictures/player.png"));
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}


	}
	
	
	public Bullet[] fire(Bullet[] b) {
		if (fire == true) {
			for (int i = 0; i < b.length; i++) {
				if (b[i] == null && System.currentTimeMillis() - lastPress > SHOT_DELAY) {
					b[i] = new Bullet(x + 10, y + 11, BULLET_SPEED, PLAYER_BULLET_WIDTH , PLAYER_BULLET_HEIGHT);
					lastPress = System.currentTimeMillis();
				}
			}
		}
		return b;
	}
	
	public void move(int height, int borderLimit) {
		if (this.moveUp == true && this.y > borderLimit) { // checks upper border
			this.y -= this.speed;
		}
		if (this.moveDown == true && this.y < height - borderLimit) { // checks lower border
			this.y += this.speed;
		}
		
		
	}
	


}
