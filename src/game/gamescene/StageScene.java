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
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import entity.Player;
import entity.enemy.Enemy;
import entity.enemy.EnemyGroup;
import entity.enemy.OneEnemiesGroup;
import entity.enemy.OneEnemy;
import game.GameManager;
import utility.InputUtility;

public class StageScene extends JPanel implements GameScene {

	private WaveGameState waveGameState[];
	private WaveController waveController[];
	private Thread thread[];
	private int currentWave;
	
	private boolean pause;
	
	public StageScene(int Stage) {
		addListener();
		validate();
		
		currentWave = 0;
		waveGameState = new WaveGameState[1];
		waveController = new WaveController[1];
		thread = new Thread[1];
		
		Player player = new Player();
		player.setDisabledShoot(true );
		
		/*
		GameLoader.loadStage(Stage);
		*/
		// Test stage
		CopyOnWriteArrayList<EnemyGroup> enemyGroups = new CopyOnWriteArrayList<EnemyGroup>();
		CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
		
		OneEnemiesGroup group1 = new OneEnemiesGroup(enemies, 20,
				50, 50, GameManager.WIDTH-50, 200,
				1, 2);
		enemies.add(new OneEnemy(1, 160, -20, group1));
		enemies.add(new OneEnemy(1, 240, -20, group1));
		enemies.add(new OneEnemy(1, 320, -20, group1));
		enemyGroups.add(group1);
		
		waveGameState[0] = new WaveGameState(player, enemyGroups);
		waveController[0] = new WaveController(waveGameState[0], null);
		thread[0] = new Thread(waveController[0]);
		currentWave = 0;
		
		add(waveGameState[0]);
	}
	
	@Override
	public void updateLogic() {}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// assign the background color to be "black"
		g2d.setBackground(Color.BLACK);
				
		// clear all the objects
		Dimension dimension = getSize();
		g2d.clearRect(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
		
		// paint Current Wave
		waveGameState[currentWave].paint(g);
	}
	
	public WaveGameState getCurrentWave() {
		return waveGameState[currentWave];
	}

	public void startAllThread() {
		thread[0].start();
	}
	
	@Override
	public boolean isPause() {
		return pause;
	}

	@Override
	public void setPause(boolean pause) {
		this.pause = pause;
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
	
}
