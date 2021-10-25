import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 800; // width dimension of program
	static final int SCREEN_HEIGHT = 450; // height dimension of program
	static final int DELAY = 50; // speed of program
	static final int BORDER_LIMIT = 25;
	static final int PLAYER_SPEED = 20;
	static final int PLAYER_BULLET_SPEED = 20;
	static final int DELAY_SHOTS = 100;
	static final int NUMBER_OF_BULLETS = 10;
	static final int SIZE_OF_BULLETS = 5;
	static final int MAX_NUMBER_OF_ENEMIES = 1;
	
	boolean once = true; // workaround so client can't hold down fire to spam bullets
	boolean running = false;
	Timer timer;
	Player p; // the player
	long lastPress = 0; // discourages spamming bullets with holding spacebar
	Random r;

	Bullet[] b = new Bullet[NUMBER_OF_BULLETS];
	Grunt[] e = new Grunt[MAX_NUMBER_OF_ENEMIES]; // can hold any type of enemy 
										
				
	
	GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true); // can gain focus from keyboards
		this.addKeyListener(new MyKeyAdapter()); // program can get keyboard inputs
		this.startGame();
	}
	
	public void startGame() {
		this.running = true;
		timer = new Timer(DELAY, this);
		timer.start(); // hopefully this will make the game run at 'DELAY' speed
		this.spawnPlayer();
		//this.spawnEnemies();

	}
	
	public void spawnPlayer() {
		p = new Player(50, SCREEN_HEIGHT / 2, PLAYER_SPEED); // spawn the player entity
	}
	
	public void spawnEnemies() {
		// adds enemies in Entity[] array
		for (int i = 0; i < e.length; i++) {
			if (e[i] == null) {
				e[i] = new Grunt(r.nextInt(BORDER_LIMIT) + (SCREEN_HEIGHT - BORDER_LIMIT), SCREEN_WIDTH - 50, 10);
				// TODO randomize the enemy class!
			}
		}
	}
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// player
		g.setColor(Color.red);
		g.fillRect(p.x, p.y, 20, 20); 
		
		// player bullets
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				g.setColor(Color.white);
				g.fillOval(b[i].x, b[i].y, SIZE_OF_BULLETS, SIZE_OF_BULLETS); 
			}
		}
	}
	
	
	public void movePlayer() {
		if (p.moveUp == true && p.y > BORDER_LIMIT) { // checks upper border
			p.y -= p.speed;
		}
		if (p.moveDown == true && p.y < SCREEN_HEIGHT - BORDER_LIMIT) { // checks lower border
			p.y += p.speed;
		}
	}
	
	public void firePlayer() {
		if (p.fire == true) {
			for (int i = 0; i < b.length; i++) {
				if (b[i] == null && System.currentTimeMillis() - lastPress > DELAY_SHOTS) {
					b[i] = new Bullet(p.x + 20, p.y, PLAYER_BULLET_SPEED);
					lastPress = System.currentTimeMillis();
//					break;
				}
			}
		}
	}
	
	public void moveBullets() {
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				b[i].x += 10;
				
			}
		}
		
	}
	
	public void checkCollisions() {
		// bullet hits the border
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null && b[i].x > SCREEN_WIDTH) {
				b[i] = null;
			}
		}
		
		
	}
	
	public void gameOver(Graphics g) {
		 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.movePlayer();
		this.firePlayer();
		this.moveBullets();
		this.checkCollisions();
		this.repaint();
		
		
		
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {	


	
			if (e.getKeyCode() == KeyEvent.VK_W) {
				p.moveUp = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				p.moveDown = false;

			}
			
			if (e.getKeyChar() == KeyEvent.VK_SPACE) {
				p.fire = false;
			}
		}
		
		
		@Override
		public void keyPressed(KeyEvent e) {	
			
			if (e.getKeyCode() == KeyEvent.VK_W) {
				p.moveUp = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				p.moveDown = true;
			}
			
			if (e.getKeyChar() == KeyEvent.VK_SPACE) {
				p.fire = true;
			}
		}
		
	
	}

}
