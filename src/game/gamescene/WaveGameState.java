package game.gamescene;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;

import entity.Background;
import entity.Entity;
import entity.Label;
import entity.Player;
import entity.PlayerStatus;
import entity.bullet.Bullet;
import entity.bullet.IceBullet;
import entity.bullet.PiecingBullet;
import entity.enemy.Enemy;
import entity.enemy.EnemyGroup;
import entity.particle.BombParticle;
import entity.particle.Particle;
import game.GameManager;
import game.GameWindow;
import utility.AudioUtility;
import utility.GameLoader;
import utility.ImageSpriteLoader;
import utility.InputUtility;
import utility.PlayerConstant;

public class WaveGameState extends JComponent implements GameScene {
	
	public static final int SPAWNING_ENEMY = 0;
	public static final int READY = 1;
	public static final int FINISH = 2;
	public static final int GAMEOVER = 3;
	public static final int DEAD = -1;
	
	private String waveName;
	private Background backGround;
	private PlayerStatus playerStatus;
	private StageScene stage;
	private Label flag;
	private CopyOnWriteArrayList<EnemyGroup> enemyGroups;
	private CopyOnWriteArrayList<Bullet> playerBullets;
	private CopyOnWriteArrayList<Bullet> enemyBullets;
	private CopyOnWriteArrayList<Particle> particles;
	private int WaveState, delay = -1;
	private boolean pause;

	public WaveGameState(StageScene stage, String waveName, CopyOnWriteArrayList<EnemyGroup> enemyGroups) {
		super();
		
		backGround = new Background(stage);
		this.stage = stage;
		this.waveName = waveName;
		this.enemyGroups = enemyGroups;
		playerStatus = new PlayerStatus(stage);
		flag = new Label(waveName);
		playerBullets = new CopyOnWriteArrayList<Bullet>();
		enemyBullets = new CopyOnWriteArrayList<Bullet>();
		particles = new CopyOnWriteArrayList<Particle>();
		WaveState = SPAWNING_ENEMY;
	}

	public void updateLogic() {
		if (WaveState == DEAD)
			return;
		
		Player player = stage.getPlayer();
		if (WaveState == SPAWNING_ENEMY) {
			// If all enemy group ready and label display WaveState to 'READY'
			WaveState = READY;
			for (EnemyGroup enemyGroup : enemyGroups) {
				if (!enemyGroup.isReady()) {
					WaveState = SPAWNING_ENEMY;
				}
			}
			if (WaveState == READY)
				flag.nextAnimation();
			
			if (!flag.isDestroyed()) {
				WaveState = SPAWNING_ENEMY;
				flag.update();
			}
		} else if (WaveState == GAMEOVER) {
			if (!flag.isDestroyed()) {
				flag.update();
				
				for (int i=0;i<256;i++) {
					if (InputUtility.getKeyPressed(i)) {
						flag.nextAnimation();
					}
				}
			} else {
				stage.gameover();
				WaveState = DEAD; 
			}
		} else if (WaveState == FINISH) {
			delay--;
			if (delay <= 0) {
				WaveState = DEAD;
			}
		} else {
			// Check Collision
			for (EnemyGroup enemyGroup : enemyGroups) {
				for (Enemy enemy : enemyGroup.getEnemies()) {
					// Enemy collide with player
					if (enemy != null) {
						if (player.collideWith(enemy)) {
							player.hit();
							enemy.destroy();
						}
						// Enemy collide with player's bullet
						for (Bullet bullet : playerBullets) {
							if (bullet.collideWith(enemy)) {
								enemy.hit(bullet);
								AudioUtility.playSound("laser");
								addParticle(new BombParticle(bullet.getX(), bullet.getY()));
								if (bullet instanceof IceBullet)
									((IceBullet) bullet).destroy(enemyGroup);
								if (bullet instanceof PiecingBullet) {
									
								} else {
									bullet.destroy();
								}	
							}
						}
					}
				}
			}
			
			for (Bullet bullet : enemyBullets) {
				if (player.collideWith(bullet)) {
					player.hit();
					bullet.destroy();
				}
			}

			if (player.isDestroyed()) {
				if (player.getHealth() > 1) {
					// Respwan new Player
					PlayerConstant.decreaseHealth();
					stage.setPlayer(new Player());
				} else {
					flag = new Label("Game Over!!!");
					WaveState = GAMEOVER;
				}
			}
		}
		
		// Update All Entity
		backGround.update();
		for (Bullet bullet : enemyBullets) {
			bullet.update();
		}
		for (Bullet bullet : playerBullets) {
			bullet.update();
		}
		player.update();
		for (EnemyGroup enemyGroup : enemyGroups) {
			enemyGroup.update();
		}
		for (Particle particle : particles) {
			particle.update();
		}
		playerStatus.update();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (WaveState == DEAD)
			return;
		backGround.draw((Graphics2D) g);
		
		Player player = stage.getPlayer();

		for (Bullet bullet : enemyBullets) {
			bullet.draw((Graphics2D) g);
		}
		for (Bullet bullet : playerBullets) {
			bullet.draw((Graphics2D) g);
		}
		player.draw((Graphics2D) g);
		for (Particle particle : particles) {
			particle.draw((Graphics2D) g);
		}
		int i =0;
		for (EnemyGroup enemyGroup : enemyGroups) {
			enemyGroup.draw((Graphics2D) g);
		}
		flag.draw((Graphics2D) g); 
		
		// Draw status bar
		playerStatus.draw((Graphics2D) g);
		
	}
	
	public synchronized void addBullet(Bullet bullet) {
		if (WaveState == READY) {
			if (bullet.getAttacker() instanceof Player) {
				playerBullets.add(bullet);
			} else if (bullet.getAttacker() instanceof Enemy) {
				enemyBullets.add(bullet);
			}
		}
	}
	
	public synchronized void addParticle(Particle particle) {
		particles.add(particle);
	}

	public void clearDestroyed() {
		Player player = stage.getPlayer();
		for (Bullet bullet : playerBullets) {
			if (bullet.isDestroyed()) {
				playerBullets.remove(bullet);
			}
		}
		for (Bullet bullet : enemyBullets) {
			if (bullet.isDestroyed()) {
				enemyBullets.remove(bullet);
			}
		}
		for (EnemyGroup enemyGroup : enemyGroups) {
			if (enemyGroup.clearDestroyed()) {
				enemyGroups.remove(enemyGroup);
			}
		}
		
		for (Particle particle : particles) {
			if (particle.isDestroyed()) {
				particles.remove(particle);
			}
		}
		
		if (player.isDestroyed())
			return;
		
		if (enemyGroups.isEmpty() && WaveState == READY) {
			WaveState = FINISH;
			if (delay == -1) {
				delay = 80;
			}
		}
		//return playerBullets.isEmpty() && enemyBullets.isEmpty() && enemyGroups.isEmpty() && delay <= 0;
	}
	
	public int getState() {
		return WaveState;
	}
	
	public void forceEnd() {
		WaveState = DEAD;
	}
	
	public boolean isGameover() {
		return WaveState == GAMEOVER && flag.isDestroyed();
	}
	
	public boolean isPause() {
		return pause;
	}
	
	public void setPause(boolean flag) {
		this.pause = flag;
	}

	@Override
	public String toString() {
		return flag.getDisplayString();
	}
	
	public String getWaveName() {
		return waveName;
	}
	
	public synchronized boolean addGroup(EnemyGroup group) {
		if (WaveState == READY) {
			enemyGroups.add(group);
			return true;
		}
		return false;
	}
	
}
