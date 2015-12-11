package entity.enemy;

import entity.Bullet;
import entity.Entity;

public abstract class Enemy extends Entity {

	public Enemy(double health, int x, int y) {
		super(health, x, y);
	}
	
	public void hit(Bullet hitBullet) {
		if (hitBullet == null)
			return;
		health = Math.max(0, health-hitBullet.getDamage());
		System.out.println(health);
		if (health <= 0) {
			System.out.println("P");
			destroy();
		}
	}
	
	public boolean isReady() {
		return getState() == READY;
	}
	
	@Override
	public int getZ() {
		return 3;
	}
	
}
