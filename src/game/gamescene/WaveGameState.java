package game.gamescene;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;

import entity.Bullet;
import entity.Entity;
import entity.Player;
import entity.enemy.Enemy;
import entity.enemy.EnemyGroup;
import utility.InputUtility;

public class WaveGameState extends JComponent implements GameScene {
	
	public static final int SPAWNING_ENEMY = 0;
	public static final int READY = 1;
	
	private Player player;
	private CopyOnWriteArrayList<EnemyGroup> enemyGroups;
	private CopyOnWriteArrayList<Bullet> playerBullets;
	private CopyOnWriteArrayList<Bullet> enemyBullets;
	private int WaveState;
	
	private boolean pause;

	public WaveGameState(Player player, CopyOnWriteArrayList<EnemyGroup> enemyGroups) {
		super();
		
		this.player = player;
		this.enemyGroups = enemyGroups;
		playerBullets = new CopyOnWriteArrayList<Bullet>();
		enemyBullets = new CopyOnWriteArrayList<Bullet>();
		WaveState = SPAWNING_ENEMY;
	}

	public void updateLogic() {
		if (WaveState == SPAWNING_ENEMY) {
			// If all enemy group ready and label display WaveState to 'READY'
			boolean ready = true;
			for (EnemyGroup enemyGroup : enemyGroups) {
				if (!enemyGroup.isReady()) {
					ready = false;
				}
			}
			
			
			if (ready) {
				WaveState = READY;
				player.setDisabledShoot(false);
			}
			// Update All Entity
			player.update();
			for (EnemyGroup enemyGroup : enemyGroups) {
				enemyGroup.update();
			}
		} else {
			// Check Collision
			for (EnemyGroup enemyGroup : enemyGroups) {
				for (Enemy enemy : enemyGroup.getEnemies()) {
					// Enemy collide with player
					if (player.collideWith(enemy)) {
						player.hit();
						enemy.destroy();
					}
					// Enemy collide with player's bullet
					for (Bullet bullet : playerBullets) {
						// FIXME
						if (bullet.collideWith(enemy)) {
							enemy.hit(bullet);
							bullet.destroy();
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
			
			
			// Update All Entity
			player.update();
			for (EnemyGroup enemyGroup : enemyGroups) {
				enemyGroup.update();
			}
			for (Bullet bullet : enemyBullets) {
				bullet.update();
			}
			for (Bullet bullet : playerBullets) {
				bullet.update();
			}
		}
		// Clear Input
		InputUtility.postUpdate();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		player.draw((Graphics2D) g);
		for (EnemyGroup enemyGroup : enemyGroups) {
			enemyGroup.draw((Graphics2D) g);
		}
		for (Bullet bullet : enemyBullets) {
			bullet.draw((Graphics2D) g);
		}
		for (Bullet bullet : playerBullets) {
			bullet.draw((Graphics2D) g);
		}
	}
	
	public void addEnitity(Entity enitity) {
		if (enitity instanceof Enemy) {
		} else if (enitity instanceof Bullet) {
			Bullet bullet = (Bullet) enitity;
			if (bullet.getAttacker() instanceof Player) {
				playerBullets.add(bullet);
			} else if (bullet.getAttacker() instanceof Enemy) {
				enemyBullets.add(bullet);
			}
		}
	}

	@Override
	public boolean isPause() {
		return pause;
	}

	@Override
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean clearDestroyed() {
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
		return playerBullets.isEmpty() && enemyBullets.isEmpty() && enemyGroups.isEmpty();
	}
	
}
