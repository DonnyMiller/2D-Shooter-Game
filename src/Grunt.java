import java.util.Random;

public class Grunt extends Enemies {
	Random r;
	static final int GRUNT_SPEED = 3;
	static final int GRUNT_WIDTH = 20;
	static final int GRUNT_HEIGHT = 20;

	Grunt(int x, int y) {
		super(x, y, GRUNT_SPEED, GRUNT_WIDTH, GRUNT_HEIGHT); 
		this.moveUp = true;
		this.moveDown = false;
		this.moveRight = false;
		this.moveLeft = false;


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
	
		
//		if (this.y < height - borderLimit) {
//			this.y += this.speed;	
//		}

		
		
//			if (direction == 1 && this.y > borderLimit) { // move up
//						this.y -= this.speed;
//			}
//			if (direction == 2 && this.x < width - borderLimit) { // move right
//				while (this.x  < borderLimit)
//					this.x += this.speed;
//			}
//			if (direction == 3 && this.y < height - borderLimit) { // move down
//					this.y += this.speed;
//			}
//			if (direction == 4 && this.x > (width / 2)) { // move left
//					this.x -= this.speed;
//			}
		
	}

}
