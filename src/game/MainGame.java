package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;

import entity.IRenderable;
import utility.InputHandler;
import utility.RenderManager;

public class MainGame extends JComponent {

	private static final long serialVersionUID = 7700909327634054113L;
	
	// FPS
	private static final int MAX_FPS = 50;
	private static final int MAX_FRAME_SKIPS = 5;
	private static final int FRAME_PERIOD = 1000 / MAX_FPS;
	// dimensons
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private GameState gms;
	
	public MainGame() {
		super();
				
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(MainGame.WIDTH, MainGame.HEIGHT));
		setVisible(true);
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				InputHandler.setKeyPressed(arg0.getKeyCode(), false);
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				InputHandler.setKeyPressed(arg0.getKeyCode(), true);
			}
		});
	}
	
	public void logicUpdate() {
		
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		((Graphics2D) graphics).setBackground(Color.WHITE);
		((Graphics2D) graphics).clearRect(0, 0, MainGame.WIDTH, MainGame.HEIGHT);
		
	}
	
}
