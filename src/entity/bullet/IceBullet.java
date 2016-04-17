package entity.bullet;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;
import entity.enemy.EnemyGroup;
import utility.ImageSpriteLoader;

public class IceBullet extends Bullet {

	private double slow;
	
	public IceBullet(double x, double y, double damage, double slow, int speed, Entity attacker) {
		super(x, y, damage, speed, attacker);
		
		setAnimation(ImageSpriteLoader.IceBulletSprite);

		this.slow = slow;
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		if (isVisible()) {
			graphics2d.drawImage(getImage(), null, (int) (x-15), (int) (y-22.5));
		}
	}
	
	public void destroy(EnemyGroup hitted) {
		super.destroy();
		
		hitted.slow(slow, 20);
	}
	
}
