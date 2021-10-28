import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Grunt extends Enemies {
	static final int SHOT_DELAY = 250; 

	static Random r = new Random();
	static final int GRUNT_SPEED = 3;
	static final int GRUNT_WIDTH = 30;
	static final int GRUNT_HEIGHT = 30;
	long lastPress = 0;
	

	Grunt(int width, int height, int borderLimit) {
		super(r.nextInt((width - borderLimit) - (width / 2)) + (width / 2), r.nextInt((height - borderLimit) - borderLimit) + borderLimit, GRUNT_SPEED, GRUNT_WIDTH, GRUNT_HEIGHT); 
		if ((r.nextInt(2) + 1) == 1) {
			this.moveUp = true;
			this.moveDown = false;
			this.moveRight = false;
			this.moveLeft = false;
		}
		else {
			this.moveDown = true;
			this.moveUp = false;
			this.moveRight = false;
			this.moveLeft = false;
		}
		this.fire = false;
		this.score = 10;
		
		try {
			image = ImageIO.read(new File("pictures/ufo.png"));
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}


	}

	@Override
	public void move(int width, int height, int borderLimit) {
		// TODO Auto-generated method stub
//		
		if (this.y > borderLimit && this.moveUp == true) { // starts here
			if (this.y - this.speed <= borderLimit) {
				this.moveUp = false;
				this.moveDown = true;
			}
			this.y -= this.speed;
		}
		
		if (this.y < height - borderLimit && this.moveDown == true) { // starts here
			if (this.y + this.speed >= height - borderLimit) {
				this.moveUp = true;
				this.moveDown = false;
			}
			this.y += this.speed;
		}
	}

	@Override
	public Bullet[] fire(Bullet[] b) {
		// TODO Auto-generated method stub
		if ((r.nextInt(200) + 1) == 3 ) {
			for (int i = 0; i < b.length; i++) {
				if (b[i] == null && System.currentTimeMillis() - lastPress > SHOT_DELAY) {
					b[i] = new Bullet(x - 10, y + 6, -10, 10, 10);
					lastPress = System.currentTimeMillis();
				}
			}
		}
		return b;
		
	}
}


		
