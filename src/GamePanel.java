import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	boolean once = true; // workaround so client can't hold down fire to spam bullets
	boolean running = false;
	Timer timer;
	Player p; // the player
	long lastPress = 0; // discourages spamming bullets

	Bullet[] b = new Bullet[10];

	
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
		p = new Player(50, SCREEN_HEIGHT / 2, PLAYER_SPEED); // initialize the player entity

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
				g.fillOval(b[i].x, b[i].y, 5, 5);
			}
		}
		
		
	}
	
	
	public void movePlayer() {
		if (p.moveUp == true && p.y > BORDER_LIMIT) {
			p.y -= p.speed;
		}
		if (p.moveDown == true && p.y < SCREEN_HEIGHT - BORDER_LIMIT) {
			p.y += p.speed;
		}
	}
	
	public void firePlayer() {
		if (p.fire == true) {
			for (int i = 0; i < b.length; i++) {
				if (b[i] == null && System.currentTimeMillis() - lastPress > 100) {
					b[i] = new Bullet(p.x + 20, p.y, PLAYER_BULLET_SPEED);
					lastPress = System.currentTimeMillis();
					break;
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
