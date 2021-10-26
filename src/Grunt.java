
public class Grunt extends Enemies {
	static final int GRUNT_SPEED = 20;
	
	Grunt(int x, int y) {
		super(x, y, GRUNT_SPEED); 
		this.sizeX = 40;
		this.sizeY = 40;
		this.moveUp = false;
		this.moveDown = false;
		this.moveRight = false;
		this.moveLeft = false;
		this.hitBox = 22;

	}

}
