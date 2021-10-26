
public class Enemies extends Entity {
	boolean moveUp;
	boolean moveDown;
	boolean moveRight;
	boolean moveLeft;
	int hitBox;
	int sizeX;
	int sizeY;

	Enemies(int x, int y, int speed) {
		super(x, y, speed);
	}

}
