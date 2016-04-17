package game;

import javax.swing.JComponent;

import game.gamescene.GameScene;
import game.gamescene.StageScene;
import game.gamescene.TitleScene;
import utility.InputUtility;

public class GameManager {
	
	// Dimensons
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final int REFRESH_DELAY = 10;
	public static final int TICK_PER_SECONDS = 1000/REFRESH_DELAY;
	
	private static GameWindow gameWindow;
	
	public static void runGame(){
		gameWindow = new GameWindow();
		gameWindow.switchScene(TitleScene.getInstance());
		
		while(true){
			try {
				Thread.sleep(REFRESH_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			((GameScene) gameWindow.getCurrentScene()).updateLogic();
			((JComponent) gameWindow.getCurrentScene()).repaint();
			InputUtility.postUpdate();
		}
	}
	
	public static JComponent getGameScene() {
		return (JComponent) gameWindow.getCurrentScene();
	}
	
	public static void switchScene(GameScene scene) {
		gameWindow.switchScene(scene);
		if (scene instanceof StageScene) {
			((StageScene) scene).startAllThread();
		}
	}
	
}
