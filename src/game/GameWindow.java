package game;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.gamescene.GameScene;

public class GameWindow extends JFrame {

	private JComponent currentScene;
	
	protected GameWindow(JComponent scene){
		super("Space Conqueror X");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		requestFocusInWindow();
		setResizable(false);
		this.currentScene = scene;
		getContentPane().setPreferredSize(new Dimension(GameManager.WIDTH, GameManager.HEIGHT));
		getContentPane().add(currentScene);
		pack();
		setVisible(true);
		currentScene.requestFocus();
	}
	
	public void switchScene(JComponent scene){
		getContentPane().remove(currentScene);
		this.currentScene = scene;
		getContentPane().add(currentScene);
		getContentPane().validate();
		pack();
		currentScene.requestFocus();
	}
	
	public JComponent getCurrentScene(){
		return currentScene;
	}
	
}
