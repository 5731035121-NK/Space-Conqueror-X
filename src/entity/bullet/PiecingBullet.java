package entity.bullet;

import entity.Entity;
import entity.enemy.EnemyGroup;
import utility.ImageSpriteLoader;

public class PiecingBullet extends Bullet {

	public PiecingBullet(double x, double y, double damage, int speed, Entity attacker) {
		super(x, y, damage, speed, attacker);
		
		setAnimation(ImageSpriteLoader.PiecingBulletSprite);
	}
	
}
