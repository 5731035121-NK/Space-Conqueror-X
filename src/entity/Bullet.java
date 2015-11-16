package entity;

import entity.animation.LiveAnimation;
import entity.animation.LoopAnimation;
import game.MainGame;

import java.awt.Graphics2D;
import java.awt.Polygon;

import utility.ImageSpriteLoader;

public class Bullet extends Entity {

	private int speed;
	private double damage;
	private Entity attacker;
	private LoopAnimation animation;
	
	public Bullet(int x, int y, double damage, Entity attacker) {
		super(1, x, y);
		
		this.speed = 5; 
		this.damage = damage;
		this.attacker = attacker;
		animation = new LoopAnimation();
		animation.setFrames(ImageSpriteLoader.Bullet[0]);
		
		int xpoints[] = {-5, 5, -5, 5};
		int ypoints[] = {-15, -15, 15, 15};
		Polygon p = new Polygon(xpoints, ypoints, 4); 
		setPolygon(p);
	}
	
	public void update() {		
		if (y<MainGame.HEIGHT) {
			y = y - speed; 
		} else {
			destroyed = true;
		}
		
		animation.update();
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		graphics2d.drawImage(animation.getImage(), null, x-5, y-15);
	}

	@Override
	public int getZ() {
		return 2;
	}
	
}
