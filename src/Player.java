
public class Player extends Entity {
	static final int NUMBER_OF_BULLETS = 10; 
	static final int BULLET_SPEED = 10; 
	static final int SHOT_DELAY = 200; 
	static final int PLAYER_SPEED = 10; 
	static final int PLAYER_WIDTH = 20; 
	static final int PLAYER_HEIGHT = 20; 
	static final int PLAYER_BULLET_WIDTH = 5; 
	static final int PLAYER_BULLET_HEIGHT = 5; 

	boolean moveUp;
	boolean moveDown;
	boolean fire;
	Bullet[] b = new Bullet[NUMBER_OF_BULLETS];
	long lastPress = 0; 


	
	Player(int x, int y) {
		super(x, y, PLAYER_SPEED, PLAYER_WIDTH, PLAYER_HEIGHT);
		this.moveUp = false;
		this.moveDown = false;
		this.fire = false;
	}
	
	
	public void fire() {
		if (fire == true) {
			for (int i = 0; i < b.length; i++) {
				if (b[i] == null && System.currentTimeMillis() - lastPress > SHOT_DELAY) {
					b[i] = new Bullet(x + 20, y + 6, BULLET_SPEED, PLAYER_BULLET_WIDTH , PLAYER_BULLET_HEIGHT);
					lastPress = System.currentTimeMillis();
				}
			}
		}
	}
	


}
