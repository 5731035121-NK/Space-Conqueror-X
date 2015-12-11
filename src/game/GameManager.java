package game;

import javax.swing.JComponent;

import game.gamescene.GameScene;
import game.gamescene.StageScene;
import utility.InputUtility;

public class GameManager {
	
	// Dimensons
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final int REFRESH_DELAY = 10;
	public static final int TICK_PER_SECONDS = 1000/REFRESH_DELAY;

	private static GameWindow gameWindow;
	
	public static void runGame(){
		gameWindow = new GameWindow(new StageScene(1));
		if (gameWindow.getCurrentScene() instanceof StageScene) {
			((StageScene) gameWindow.getCurrentScene()).startAllThread();
		}
		
		
		while(true){
			try {
				Thread.sleep(REFRESH_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameWindow.getCurrentScene().repaint();
			((GameScene) gameWindow.getCurrentScene()).updateLogic();
			InputUtility.postUpdate();
		}
	}
	
	public static JComponent getGameScene() {
		return (JComponent) gameWindow.getCurrentScene();
	}
	
}
