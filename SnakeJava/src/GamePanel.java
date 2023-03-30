import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	
	
	static final int WIDTH = 500;
	static final int HEIGHT = 500;
	static final int UNITS = 25;
	static final int TOT_UNITS = (WIDTH* HEIGHT)/UNITS;
	static final int DELAY = 75;
	final int x[] = new int[TOT_UNITS];
	final int y[] = new int[TOT_UNITS];
	int initialLength = 6;
	int foodGained ;
	int foodX;
	int foodY;
	char dir ='R';
	boolean running = false;
	Timer timer;
	Random rand;
	

	GamePanel(){
		
		rand = new Random();
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();

	}

	public void startGame() {
		
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();

	}
	private void newApple() {
		
		foodX = rand.nextInt((int)(WIDTH/UNITS))*UNITS;
		foodY = rand.nextInt((int)(HEIGHT/UNITS))*UNITS;
		
		
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
		

	}
	public void draw(Graphics g) {
		
		if (running) {
			g.setColor(new Color(216,228,188));
			for (int i = 0; i < HEIGHT / UNITS; i++) {
				g.drawLine(i * UNITS, 0, i * UNITS, HEIGHT);
				g.drawLine(0, i * UNITS, WIDTH, i * UNITS);
			}
			g.setColor(Color.RED);
			g.fillOval(foodX, foodY, UNITS, UNITS);
			for (int i = 0; i < initialLength; i++) {

				if (i == 0) {
					g.setColor(new Color(50, 205, 50));
				} else {
					g.setColor(new Color(11, 218, 81));
				}
				g.fillRect(x[i], y[i], UNITS, UNITS);
			} 
			g.setColor(Color.BLACK);
			g.setFont( new Font("Monospace",Font.BOLD, 20));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+foodGained, (WIDTH - metrics1.stringWidth("Score: "+foodGained))/2, g.getFont().getSize());

		}
		else {
			gameOver(g);
		}

	}

	public void move() {
		
		for( int i = initialLength ; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(dir) {
		
		case 'R':
			x[0]+=UNITS;
			break;
		case 'L':
			x[0]-=UNITS;
			break;
		case 'U':
			y[0]-=UNITS;
			break;
		case 'D':
			y[0]+=UNITS;
			break;		

		}

	}


	public void checkFood() {
		if ((x[0] == foodX ) && (y[0] == foodY)) {
			foodGained++;
			initialLength++;
			newApple();
		}

	}


	public void checkCollisions() {
		
		for ( int i = initialLength ; i>0 ; i--) {
			
			if ((x[0] == x[i]) && (y[0]== y[i])) {
				running = false;
			}
		}
		
		if (( x[0]<0 ) || (x[0] > WIDTH )|| (y[0] < 0 )|| (y[0] > HEIGHT))
		{
			running = false;
		}
		
		if (!running) {
			timer.stop();
		}

	}


	public void gameOver(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont( new Font("Monospace",Font.BOLD, 20));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+foodGained, (WIDTH - metrics1.stringWidth("Score: "+foodGained))/2, g.getFont().getSize());

		g.setFont( new Font("Monospace",Font.BOLD, 50));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over!!", (WIDTH - metrics2.stringWidth("Game Over!!"))/2, HEIGHT/2);
		

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (running) {
			move();
			checkFood();
			checkCollisions();
		}
		repaint();

	}

	public class myKeyAdapter extends KeyAdapter{


		@Override
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				if (dir!='R') {
					dir = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (dir!='L') {
					dir = 'R';
				}
				
				break;
			case KeyEvent.VK_UP:
				if (dir!='D') {
					dir = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (dir!='U') {
					dir = 'D';
				}
				break;
			}
		}

	}
}
