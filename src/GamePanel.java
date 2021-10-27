import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 800; // width dimension of program
	static final int SCREEN_HEIGHT = 450; // height dimension of program
	static final int DELAY = 10; // speed of program
	static final int BORDER_LIMIT = 50;
	static final int NUMBER_OF_BULLETS = 10;
	static final int MAX_NUMBER_OF_ENEMIES = 5;
	
	boolean once = true; // workaround so client can't hold down fire to spam bullets
	boolean running;
	boolean gameOver;
	Timer timer;
	Player p; // the player
	Random r;

	Enemies[] e = new Enemies[MAX_NUMBER_OF_ENEMIES]; // can hold any type of enemy 
										
				
	
	GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true); // can gain focus from keyboards
		this.addKeyListener(new MyKeyAdapter()); // program can get keyboard inputs
	}
	
	public void startGame() {
		this.running = true;
		timer = new Timer(DELAY, this);
		timer.start(); // hopefully this will make the game run at 'DELAY' speed
		this.spawnPlayer();
		this.spawnEnemies();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (this.running == true) {
			this.movePlayer();
			this.moveEnemies();
			this.p.fire();
			this.moveBullets();
			this.checkCollisions();
			this.repaint();
		}

	}
	
	public void spawnPlayer() {
		p = new Player(50, SCREEN_HEIGHT / 2); // spawn the player entity
	}
	
	public void spawnEnemies() {
		// adds enemies in Entity[] array
		for (int i = 0; i < e.length; i++) {
			r = new Random();

			if (e[i] == null) {
				int x = SCREEN_WIDTH - BORDER_LIMIT;
				int y = r.nextInt((SCREEN_HEIGHT - BORDER_LIMIT) - BORDER_LIMIT) + BORDER_LIMIT;
				for (int j = 0; i < e.length; i++) {
					if (e[j] != null && x != e[j].x && y != e[j].y) {
						e[i] = new Grunt(x, y);
					}
				}
				// TODO randomize the enemy class!
			}
		}
	}
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// intro
		if (this.running == false && this.gameOver == false) {
			String introMessage = "Donny's 2D Shooter Game";
			Font introFont = new Font("Helvetica", Font.BOLD, 14);
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 14));
	        FontMetrics metr = this.getFontMetrics(introFont);
			g.drawString(introMessage, (SCREEN_WIDTH / 2) - 100, 20);



			String instructions = "Press space to start (w,a,s,d to move and spacebar to fire!)";

		}
		
		// in game
		if (this.running == true) {
			
			BufferedImage playerImage = null;
			
	        try {
				playerImage = ImageIO.read(new File("player.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			
			// player
			g.drawImage(playerImage, p.x, p.y, p.width, p.height, this);

			
			// player bullets
			for (int i = 0; i < p.b.length; i++) {
				if (p.b[i] != null) {
					g.setColor(Color.white);
					g.fillOval(p.b[i].x, p.b[i].y, p.b[i].width, p.b[i].height); 
				}
			}
			
			// grunt
			for (int i = 0; i < e.length; i++) {
				if (e[i] != null) {
					System.out.println(e[i].speed);
					g.setColor(Color.white);
					g.fillRect(e[i].x, e[i].y, e[i].width, e[i].height); 
				}
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
			if (e[i] != null) {
				e[i].move(SCREEN_WIDTH, SCREEN_HEIGHT, BORDER_LIMIT); 
				// works because of polymorphism
//				e[i].y += e[i].speed;
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
				if ((p.b[i] != null && e[j] != null)) {
					Rectangle playerBullet = new Rectangle(p.b[i].x, p.b[i].y, p.b[i].width, p.b[i].height);
					Rectangle enemy = new Rectangle(e[j].x, e[j].y, e[j].width, e[j].height);
					
					if (playerBullet.intersects(enemy)) {
						e[j] = null;
						p.b[i] = null; 
					}
				}
			}
		}
		
	}
	
	public void gameOver(Graphics g) {
		 
	}


	
	public class MyKeyAdapter extends KeyAdapter {
	
		
		@Override
		public void keyReleased(KeyEvent e) {	
			
			
			if (running == true) {
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
		}
		
		
		@Override
		public void keyPressed(KeyEvent e) {

			if (running == true) {
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
			
			
			if (running == false) {
				if (e.getKeyChar() == KeyEvent.VK_SPACE) {
					startGame();
				}
			}
			
		}
		
	
	}

}
