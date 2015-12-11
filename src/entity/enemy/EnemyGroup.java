package entity.enemy;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EnemyGroup {

	protected CopyOnWriteArrayList<Enemy> enemies;
	protected int speed;
	protected boolean ready;
	private Polygon boundingBox; 
	
	public EnemyGroup(CopyOnWriteArrayList<Enemy> enemies, int speed,
			int top, int left, int right, int bottom) {
		super();
		this.enemies = enemies;
		this.speed = speed;
		int[] plotX = {left, right, right, left};
		int[] plotY = {top, top, bottom, bottom};
		boundingBox = new Polygon(plotX, plotY, 4);
	}
	
	public abstract void update();
	
	public int getSpeed() {
		return speed;
	}

	public boolean isReady() {
		return ready;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void draw(Graphics2D g) {
		for (Enemy enemy : enemies) {
			enemy.draw(g);
		}
	}

	public boolean clearDestroyed() {
		for (Enemy enemy : enemies) {
			if (enemy.isDestroyed()) {
				enemies.remove(enemy);
			}
		}
		return enemies.isEmpty();
	}

	public boolean isInBox(Polygon p, int x, int y) {
		for (int i=0;i<p.npoints;i++) {
			if (!boundingBox.contains(p.xpoints[i]+x, p.ypoints[i]+y))
				return false;
		}
		return true;
	}
}
