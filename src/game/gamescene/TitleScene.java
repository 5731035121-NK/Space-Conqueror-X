package game.gamescene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import utility.InputUtility;

public class TitleScene extends JPanel implements GameScene {

	private static TitleScene instance = new TitleScene();
	
	public TitleScene() {
		addListener();
		validate();
		
		setDoubleBuffered(true);
	}
	
	private void addListener(){
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				InputUtility.setMouseLeftDown(false);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					InputUtility.setMouseLeftDown(true);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				InputUtility.setMouseOnScreen(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				InputUtility.setMouseOnScreen(true);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//MouseMotionListener
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (InputUtility.isMouseOnScreen()) {
					InputUtility.setMouseX(e.getX());
					InputUtility.setMouseY(e.getY());
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (InputUtility.isMouseOnScreen()) {
					InputUtility.setMouseX(e.getX());
					InputUtility.setMouseY(e.getY());
				}
			}
		});
		
		//KeyListener
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				InputUtility.setKeyTriggered(e.getKeyCode(), false);
				InputUtility.setKeyPressed(e.getKeyCode(), false);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (InputUtility.getKeyPressed(e.getKeyCode()) == false) {
					InputUtility.setKeyPressed(e.getKeyCode(), true);
					InputUtility.setKeyTriggered(e.getKeyCode(), true);
				}
			}
		});
	}
	
	public static TitleScene getInstance() {
		return instance;
	}
	
	public void updateLogic() {
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		// assign the background color to be "black"
		g2d.setBackground(Color.BLACK);
				
		// clear all the objects
		Dimension dimension = getSize();
		g2d.clearRect(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("Space Conquerer X", 120, 120);
	}

	@Override
	public boolean isPause() {
		return false;
	}

	@Override
	public void setPause(boolean pause) {}

}
