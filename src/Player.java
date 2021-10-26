
public class Player extends Entity {
	static final int NUMBER_OF_BULLETS = 10; 
	static final int BULLET_SPEED = 10; 
	static final int SHOT_DELAY = 200; 
	static final int PLAYER_SPEED = 10; 


	boolean moveUp;
	boolean moveDown;
	boolean fire;
	Bullet[] b = new Bullet[NUMBER_OF_BULLETS];
	long lastPress = 0; 
	int bulletSize;


	
	Player(int x, int y) {
		super(x, y, PLAYER_SPEED);
		this.moveUp = false;
		this.moveDown = false;
		this.fire = false;
		this.bulletSize = 5;
	}
	
	
	public void fire() {
		if (fire == true) {
			for (int i = 0; i < b.length; i++) {
				if (b[i] == null && System.currentTimeMillis() - lastPress > SHOT_DELAY) {
					b[i] = new Bullet(x + 20, y, BULLET_SPEED);
					lastPress = System.currentTimeMillis();
				}
			}
		}
	}
	


}
