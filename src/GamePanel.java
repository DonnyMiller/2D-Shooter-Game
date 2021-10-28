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
	BufferedImage bgImage = null;
	static final int SCREEN_WIDTH = 1000; // width dimension of program
	static final int SCREEN_HEIGHT = 600; // height dimension of program
	static final int DELAY = 5; // speed of program
	static final int BORDER_LIMIT = 50;
	static final int NUMBER_OF_BULLETS = 10000;
	static final int MAX_NUMBER_OF_ENEMIES = 5;

	
	boolean once = true; // workaround so client can't hold down fire to spam bullets
	boolean running;
	boolean gameOver;
	Timer timer;
	Player p; // the player
	Random r;

	Enemies[] e; // can hold any type of enemy 
	Bullet[] b; // holds ALL bullet entities
	int level;
	int score;
	
		
	
	GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setFocusable(true); // can gain focus from keyboards
		this.addKeyListener(new MyKeyAdapter()); // program can get keyboard inputs
		
		timer = new Timer(DELAY, this);
		timer.start(); // hopefully this will make the game run at 'DELAY' speed
		

		
		try {
			bgImage = ImageIO.read(new File("pictures/background.png"));
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void startGame() {
		this.running = true;
		this.gameOver = false;
		
		this.e = new Enemies[MAX_NUMBER_OF_ENEMIES];
		this.b = new Bullet[NUMBER_OF_BULLETS];
		this.score = 0;
		this.level = 1;	
		this.spawnPlayer();
		this.Level1();
	}
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (this.running == true) {
			this.p.move(SCREEN_HEIGHT, BORDER_LIMIT);
			this.moveEnemies();
			this.fireEnemies();
			b = this.p.fire(b);
			this.moveBullets();
			this.checkCollisions();
			this.isGameOver();

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
		g.drawImage(bgImage, 0, 0, null);


		
		// intro
		if (this.running == false && this.gameOver == false) {
			String introMessage = "2D Unispace";
			g.setColor(Color.red);
			g.setFont(new Font("Unispace", Font.BOLD, 50));
			FontMetrics metricsIM = getFontMetrics(g.getFont());
			g.drawString(introMessage, ((SCREEN_WIDTH - metricsIM.stringWidth(introMessage)) / 2), SCREEN_HEIGHT / 2);
			
			String byDonny = "by donny miller";
			g.setColor(Color.white);
			g.setFont(new Font("Unispace", Font.BOLD, 20));
			FontMetrics metricsBDM = getFontMetrics(g.getFont());
			g.drawString(byDonny, ((SCREEN_WIDTH - metricsBDM.stringWidth(byDonny)) / 2), (SCREEN_HEIGHT / 2) + 30);
			
			
			String instructions = "up = w / down = s / space = fire";
			g.setColor(Color.white);
			g.setFont(new Font("Unispace", Font.BOLD, 20));
			FontMetrics metricsINST = getFontMetrics(g.getFont());
			g.drawString(instructions, ((SCREEN_WIDTH - metricsINST.stringWidth(instructions)) / 2), SCREEN_HEIGHT - 110);
			
			String toStart = "Press space to start";
			g.setColor(Color.white);
			g.setFont(new Font("Unispace", Font.BOLD, 20));
			FontMetrics metricsSTRT = getFontMetrics(g.getFont());
			g.drawString(toStart, ((SCREEN_WIDTH - metricsSTRT.stringWidth(toStart)) / 2), SCREEN_HEIGHT - 20);
		}

		
		// in game
		if (this.running == true) {
			// level
			String l = "Level: " + this.level;
			g.setColor(Color.yellow);
			g.setFont(new Font("Unispace", Font.BOLD, 20));
			FontMetrics metricsLVL = getFontMetrics(g.getFont());
			g.drawString(l, (SCREEN_WIDTH - metricsLVL.stringWidth(l)) / 2, 30);
			
			// score
			String s = "Score: " + this.score;
			g.setColor(Color.yellow);
			g.setFont(new Font("Unispace", Font.BOLD, 20));
			g.drawString(s, 20, 30);
			
			// lives
			String lives = "Lives: " + p.lives;
			g.setColor(Color.yellow);
			g.setFont(new Font("Unispace", Font.BOLD, 20));
			g.drawString(lives, SCREEN_WIDTH - 110, 30);
	
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
		
		if (this.gameOver == true) { 
			// game over message
			String gameOver = "Game Over";
			g.setColor(Color.red);
			g.setFont(new Font("Unispace", Font.BOLD, 50));
			FontMetrics metricsGO = getFontMetrics(g.getFont());
			g.drawString(gameOver, (SCREEN_WIDTH - metricsGO.stringWidth(gameOver)) / 2, SCREEN_HEIGHT / 2);
			
			
			// play again message
			String playAgain = "Press Space to Play Again";
			g.setColor(Color.white);
			g.setFont(new Font("Unispace", Font.BOLD, 15));
			FontMetrics metricsPA = getFontMetrics(g.getFont());
			g.drawString(playAgain, (SCREEN_WIDTH - metricsPA.stringWidth(playAgain)) / 2, SCREEN_HEIGHT - 30);
		}

		
	
	}
	
	
	public void moveEnemies() {
		for (int i = 0; i < e.length; i++) {
			if (e[i] != null) {
				e[i].move(SCREEN_WIDTH, SCREEN_HEIGHT, BORDER_LIMIT); 
			}
		}
	}
	
	public void fireEnemies() {
		for (int i = 0; i < e.length; i++) {
			if (e[i] != null) {
				e[i].fire(b); 
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
				if ((b[i] != null && e[j] != null) && this.running == true) {
					Rectangle bullet = new Rectangle(b[i].x, b[i].y, b[i].width, b[i].height);
					Rectangle enemy = new Rectangle(e[j].x, e[j].y, e[j].width, e[j].height);
					Rectangle player = new Rectangle(p.x, p.y, p.width, p.height);
					
					// player bullet hit enemy
					if (bullet.intersects(enemy) && b[i].speed > 0) { 
						// only player bullets have > 0 bullet speed
						// so that enemies can't shoot eachother
						score += e[j].score;
						e[j] = null;
						b[i] = null; 
					}
					

					// enemy bullet hit player
					if (bullet.intersects(player) && b[i].speed < 0 ) { 
						//p.lastHit = System.currentTimeMillis(); // so one bullet doesn't take 3 hits
						b[i] = null; 
						p.decrementLives();
					}
					
				}
			}
		}
		
	}
	
	public void isGameOver() {
		this.gameOver = !(p.isAlive());
		
		if (this.gameOver == true) {
			this.running = false;
		}
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
