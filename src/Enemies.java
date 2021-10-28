import java.awt.image.BufferedImage;

public abstract class Enemies extends Entity {
	BufferedImage image = null;
	boolean moveUp;
	boolean moveDown;
	boolean moveRight;
	boolean moveLeft;

	Enemies(int x, int y, int speed, int width, int height) {
		super(x, y, speed, width, height);
	}
	
	public abstract void move(int width, int height, int borderLimit);
	
	//public abstract void fire(int width, int height, int borderLimit);

	


}
