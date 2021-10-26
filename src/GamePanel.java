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
	static final int DELAY = 10; // speed of program
	static final int BORDER_LIMIT = 25;
	static final int NUMBER_OF_BULLETS = 10;
	static final int MAX_NUMBER_OF_ENEMIES = 6;
	
	boolean once = true; // workaround so client can't hold down fire to spam bullets
	boolean running = false;
	Timer timer;
	Player p; // the player
	Random r;

	Enemies[] e = new Enemies[MAX_NUMBER_OF_ENEMIES]; // can hold any type of enemy 
										
				
	
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
		this.spawnEnemies();

	}
	
	public void spawnPlayer() {
		p = new Player(50, SCREEN_HEIGHT / 2); // spawn the player entity
	}
	
	public void spawnEnemies() {
		// adds enemies in Entity[] array
		for (int i = 0; i < e.length; i++) {
			r = new Random();

			if (e[i] == null) {
				e[i] = new Grunt(SCREEN_WIDTH - 60, r.nextInt(SCREEN_HEIGHT - BORDER_LIMIT) + BORDER_LIMIT);
				//e[i] = new Grunt(500, SCREEN_HEIGHT / 2);
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
		for (int i = 0; i < p.b.length; i++) {
			if (p.b[i] != null) {
				g.setColor(Color.white);
				g.fillOval(p.b[i].x, p.b[i].y + 6, p.bulletSize, p.bulletSize); 
			}
		}
		
		// grunt
		for (int i = 0; i < e.length; i++) {
			if (e[i] != null) {
				g.setColor(Color.white);
				g.fillOval(e[i].x, e[i].y - 20, e[i].sizeX, e[i].sizeY); 
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
	
	public void moveEnemies() {
		for (int i = 0; i < e.length; i++) {
			r = new Random();
			if (e[i] != null) {
				int x = r.nextInt(3) + 1;
				if (x == 1 && e[i].y > SCREEN_HEIGHT - BORDER_LIMIT) { // UP
					e[i].y -= e[i].speed;
				}
			}
		}
	}
	
	public void moveBullets() {
		for (int i = 0; i < p.b.length; i++) {
			if (p.b[i] != null) {
				p.b[i].x += p.b[i].speed;
			}
		}
	}
	
	public void checkCollisions() {
		// player bullet hits the border
		for (int i = 0; i < p.b.length; i++) {
			if (p.b[i] != null && p.b[i].x > SCREEN_WIDTH) {
				p.b[i] = null;
			}
		}
		
		// player bullet hits enemy
		for (int i = 0; i < p.b.length; i++) {
			for (int j = 0; j < e.length; j++) {

				if ((p.b[i] != null && e[j] != null) && 
						((Math.abs(e[j].x - p.b[i].x) < e[j].hitBox) && (Math.abs(e[j].y - p.b[i].y) < e[j].hitBox))) {
					e[j] = null;
					p.b[i] = null; 
					System.out.println("testing");
				}  
			}
		}
		
	}
	
	public void gameOver(Graphics g) {
		 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.movePlayer();
		this.moveEnemies();
		this.p.fire();
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
