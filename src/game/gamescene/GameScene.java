package game.gamescene;

import java.awt.Component;

import javax.swing.JComponent;

public interface GameScene {
	
	void updateLogic();
	int getState();
	
}
