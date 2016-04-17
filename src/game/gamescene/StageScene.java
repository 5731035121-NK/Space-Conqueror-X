package game.gamescene;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import entity.Player;
import entity.enemy.Boss;
import entity.enemy.Enemy;
import entity.enemy.EnemyGroup;
import entity.enemy.OneEnemy;
import exception.FileFormatException;
import exception.FileMissingException;
import game.GameManager;
import utility.GameLoader;
import utility.InputUtility;
import utility.GameLoader.WaveLoader;

public class StageScene extends JPanel implements GameScene {

	public static StageScene instance;
	protected static final AlphaComposite transcluentBlack = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
	protected static final AlphaComposite opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
	
	private WaveGameState waveGameState[];
	private WaveController waveController[];
	private Thread thread[];
	private int currentWave;
	public Player player;
	
	private boolean error, gameover;
	
	public StageScene(int Stage, int subStage) {
		gameover = false;
		
		validate();

		try {
			GameLoader.loadStage(Stage, subStage);
			currentWave = 0;
			waveGameState = new WaveGameState[GameLoader.numberWave];
			waveController = new WaveController[GameLoader.numberWave];
			thread = new Thread[GameLoader.numberWave];
			
			player = new Player();
			int i = 0;

			for (WaveLoader wave : GameLoader.wave) {
				waveGameState[i] = new WaveGameState(this, wave.getWaveName(), wave.getEnemyGroups());				
				if (i > 0)
					waveController[i] = new WaveController(waveGameState[i], thread[i-1]);
				else 
					waveController[i] = new WaveController(waveGameState[i], null);
				thread[i] = new Thread(waveController[i]);
				i++;
			}
		} catch (FileMissingException | FileFormatException e) {
			error = true;
		}
	}
	
	@Override
	public synchronized void updateLogic() {
		if (error) {
			GameManager.switchScene(TitleScene.getInstance());
			return;
		}
		if (!thread[currentWave].isAlive()) {
			if (thread.length <= currentWave+1) {
				if (gameover)
					return;
				
				gameover = true;
				for (WaveGameState waveGameState : waveGameState) {
					waveGameState.forceEnd();
				}
				// TODO END WAVE
				GameManager.switchScene(new AfterStageScene(true));
			} else {
				currentWave++;
			}
		} else {
			if (InputUtility.isPauseButton() || InputUtility.isMouseLeftClicked() && waveGameState[currentWave].isPause() ) {
				waveController[currentWave].togglePause();
			}
		}
	}
	
	public synchronized void togglePause() {
		waveController[currentWave].togglePause();
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// assign the background color to be "black"
		g2d.setBackground(Color.BLACK);
				
		// clear all the objects
		Dimension dimension = getSize();
		g2d.clearRect(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
		
		// paint Current Wave
		if (waveGameState != null) {
			waveGameState[currentWave].paint(g);
			
			// paint Pause Screen 
			if (waveGameState[currentWave].isPause()) {
				g2d.setComposite(transcluentBlack);
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, 800, 600);
				g2d.setComposite(opaque);
			}
		}
	}
	
	public synchronized WaveGameState getCurrentWave() {
		return waveGameState[currentWave];
	}
	
	public synchronized Player getPlayer() {
		return player;
	}
	
	public synchronized void setPlayer(Player player) {
		this.player = player;
	}
	
	public synchronized int getState() {
		return waveGameState[currentWave].getState();
	}

	public synchronized void startAllThread() {
		if (thread != null)
		for (Thread t : thread) {
			t.start();
		}
	}

	public synchronized void gameover() {
		if (gameover)
			return;
		
		gameover = true;
		for (WaveGameState waveGameState : waveGameState) {
			waveGameState.forceEnd();
		}
		// TODO END GAME
		GameManager.switchScene(new AfterStageScene(false));
	}
	
}
