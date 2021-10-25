
public class Player extends Entity {
	boolean moveUp;
	boolean moveDown;
	boolean fire;
	
	Player(int x, int y, int speed) {
		super(x, y, speed);
		moveUp = false;
		moveDown = false;
	}

}
