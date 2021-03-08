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
	private static int aps = 0;
	private static int fps = 0;
	
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
	
	private void updateGame() {
		aps++;
	}
	
	private void showGame() {
		fps++;
	}
	
	@Override
	public void run() {
		final int NS_BY_SECONDS = 1000000000;
		final byte APS_OBJECTIVE = 60;
		final double NS_BY_UPDATE = NS_BY_SECONDS / APS_OBJECTIVE;
		
		long refenceUpdate = System.nanoTime();
		long refCount = System.nanoTime();
		double timeElapsed;
		double delta = 0;
		
		while(running) {
			final long initLoop = System.nanoTime();
			timeElapsed =  initLoop - refenceUpdate;
			refenceUpdate = initLoop;
			delta+= timeElapsed / NS_BY_UPDATE;
			
			while(delta >= 1) {
				updateGame();
				delta-=1;
			}
			showGame();
			if(System.nanoTime() - refCount > NS_BY_SECONDS) {
				window.setTitle(NAME + " APS: " + aps + " FPS:" + fps);
				aps = 0;
				fps =0;
				refCount = System.nanoTime();
			}
		}
	}

}

