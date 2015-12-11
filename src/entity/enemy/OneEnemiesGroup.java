package entity.enemy;

import java.awt.Polygon;
import java.util.concurrent.CopyOnWriteArrayList;

public class OneEnemiesGroup extends EnemyGroup{

	private int direction, nextDirection;
	/*
	 * -2 LEFT
	 * -1 UP
	 * 0 STAY
	 * 1 DOWN
	 * 2 RIGHT
	 */
	
	public OneEnemiesGroup(CopyOnWriteArrayList<Enemy> enemies, int speed,
			int top, int left, int right, int bottom,
			int direction, int nextDirection) {
		super(enemies, speed, top, left, right, bottom);
		
		this.enemies = enemies;
		this.speed = speed;
		this.direction = direction;
		this.nextDirection = nextDirection;
	}
	
	public void update() {
		// Check ready?
		if (!ready) {
			ready = true;
			for (Enemy enemy : enemies) {
				if (!enemy.isReady()) {
					ready = false;
					break;
				}
			}
			if (ready)
				direction = nextDirection;
		} else {		
			// Check next direction
			for (Enemy enemy : enemies) {
				switch (direction) {
				case -2:
					if (!isInBox(enemy.getCollisionBox(), enemy.getX()-speed, enemy.getY()))
						direction = -direction;
					break;
				case -1:
					if (!isInBox(enemy.getCollisionBox(), enemy.getX(), enemy.getY()-speed))
						direction = -direction;
					break;
				case 1:
					if (!isInBox(enemy.getCollisionBox(), enemy.getX(), enemy.getY()+speed))
						direction = -direction;
					break;
				case 2:
					if (!isInBox(enemy.getCollisionBox(), enemy.getX()+speed, enemy.getY()))
						direction = -direction;
					break;
				default:
					break;
				}
			}
		}
		
		for (Enemy enemy : enemies) {
			enemy.update();
		}
	}
	
	public int getStepX() {
		switch (direction) {
		case -2:
			return -speed;
		case 2:
			return speed;
		default:
			return 0;
		}
	}
	
	public int getStepY() {
		switch (direction) {
		case -1:
			return -speed;
		case 1:
			return speed;
		default:
			return 0;
		}
	}
	
}
