import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	
	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Donny's 2D Shooter");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null); // sets screen in the middle of screen
	}

}
