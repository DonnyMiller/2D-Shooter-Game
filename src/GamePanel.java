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

public class GamePanel extends JPanel implements ActionListener {
	BufferedImage playerImage = null;
	static final int SCREEN_WIDTH = 1000; // width dimension of program
	static final int SCREEN_HEIGHT = 600; // height dimension of program
	static final int DELAY = 5; // speed of program
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
	Bullet[] b = new Bullet[NUMBER_OF_BULLETS]; // holds ALL bullet entities

	
		
	
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
		this.Level1();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (this.running == true) {
			this.p.move(SCREEN_HEIGHT, BORDER_LIMIT);
			this.moveEnemies();
			b = this.p.fire(b);
			this.moveBullets();
			this.checkCollisions();
			this.repaint();
		}

	}
	
	public void spawnPlayer() {
		// creates player instance
		p = new Player(SCREEN_HEIGHT, BORDER_LIMIT); // spawn the player entity
	}
	
	public void Level1() {
		final int AMOUNT = 5;
		for (int i = 0; i < AMOUNT; i++) {
			e[i] = new Grunt(SCREEN_WIDTH, SCREEN_HEIGHT, BORDER_LIMIT);
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
			g.drawString(introMessage, ((SCREEN_WIDTH / 2) - 100), 20);



			String instructions = "Press space to start (w,a,s,d to move and spacebar to fire!)";

		}
		
		// in game
		if (this.running == true) {
			
		
		
	        
			
			// player
			g.drawImage(p.image, p.x, p.y, p.width, p.height, this);

			
			//  bullets
			for (int i = 0; i < b.length; i++) {
				if (b[i] != null) {
					g.setColor(Color.white);
					g.fillOval(b[i].x, b[i].y, b[i].width, b[i].height); 
				}
			}
			
			// enemies
			for (int i = 0; i < e.length; i++) {
				if (e[i] != null) {
					g.drawImage(e[i].image, e[i].x, e[i].y, e[i].width, e[i].height, this);
				}
			}
			
		}
		
	
	}
	
	
	public void moveEnemies() {
		for (int i = 0; i < e.length; i++) {
			if (e[i] != null) {
				e[i].move(SCREEN_WIDTH, SCREEN_HEIGHT, BORDER_LIMIT); 
			}
		}
	}
	
	public void moveBullets() {
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null) {
				b[i].x += b[i].speed;
			}
		}
	}
	
	public void checkCollisions() {
		
		// player bullet hits the border
		for (int i = 0; i < b.length; i++) {
			if (b[i] != null && b[i].x > SCREEN_WIDTH) {
				b[i] = null;
			}
		}
		
		// player bullet hits enemy
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < e.length; j++) {
				if ((b[i] != null && e[j] != null)) {
					Rectangle playerBullet = new Rectangle(b[i].x, b[i].y, b[i].width, b[i].height);
					Rectangle enemy = new Rectangle(e[j].x, e[j].y, e[j].width, e[j].height);
					
					if (playerBullet.intersects(enemy)) {
						e[j] = null;
						b[i] = null; 
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
