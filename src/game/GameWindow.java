package game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.gamescene.GameScene;
import utility.AudioUtility;
import utility.GameLoader;
import utility.InputUtility;
import utility.PlayerConstant;

public class GameWindow extends JFrame {

	private static GameWindow world;
	
	private GameScene currentScene;
	
	protected GameWindow(){
		super("Space Conqueror X");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(GameManager.WIDTH, GameManager.HEIGHT));
		pack();
		setVisible(true);
		requestFocus();
		addListener();
		
		PlayerConstant.loadPlayerStatus();
		GameLoader.loadKeyBind();
		AudioUtility.playSound("theme");
	}
	
	public void switchScene(GameScene scene){
		if (currentScene != null)
			getContentPane().remove(((JComponent) currentScene));
		this.currentScene = scene;
		getContentPane().add(((JComponent) currentScene));
		getContentPane().validate();
		pack();
	}
	
	public GameScene getCurrentScene(){
		return currentScene;
	}

	private void addListener(){
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				InputUtility.setMouseLeftDown(false);
				InputUtility.setMouseLeftTriggered(false);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					InputUtility.setMouseLeftDown(true);
					InputUtility.setMouseLeftTriggered(true);
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
	
}
