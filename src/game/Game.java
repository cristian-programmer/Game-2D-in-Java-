package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	public static final long serialVersinUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGH = 600;
	public static final String NAME="ZombiLand";
	private static JFrame window;
	private static Thread thread;
	private volatile boolean  running = true;
	
	private Game() {
		setPreferredSize(new Dimension(WIDTH, HEIGH));
		window = new JFrame(NAME);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.add(this, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		 Game game = new Game();
		 game.start();
		 
	}

	public synchronized void start() {
		 this.running = true;
		 thread =  new Thread(this, "graphics");
		 thread.start();
	}
	
	public synchronized void stop() {
		this.running= false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(running) {
			
		}
	}

}
