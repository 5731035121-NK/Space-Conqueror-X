package entity;

import game.GameManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

import utility.GameConstant;
import utility.ImageSpriteLoader;
import utility.InputUtility;

public class Bullet extends Entity {

	private int moveCount, moveDuration = 1;
	private int frameCount, frameDuration = 1;
	private int speed;
	private double damage;
	private Entity attacker;
	
	public Bullet(int x, int y, double damage, int speed, Entity attacker) {
		super(1, x, y);
		
		this.speed = speed;
		moveCount = moveDuration;
		this.damage = damage;
		this.attacker = attacker;
		
		setAnimation(ImageSpriteLoader.BulletSprite);
		setAct(0);
		setCollisionBox(ImageSpriteLoader.BulletCollision);
	}
	
	public void update() {
		if (getState() == DESTROYED)
			return;
		if (0 > getY() || getY() >= GameManager.HEIGHT) {
			destroy();
			return;
		}
		
		move();
		
		updateFrame();
	}
	
	private void move() {
		if (moveCount > 0) {
			moveCount--;
			return;
		}
		moveCount = moveDuration;
		y = y + speed;
	}
	
	private void updateFrame() {
		if (frameCount > 0) {
			frameCount--;
			return;
		}
		frameCount = frameDuration;
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		if (isVisible()) {
			if (GameConstant.WIRE_FRAME) {
				graphics2d.setColor(Color.BLUE);
				getCollisionBox().translate(x, y);
				graphics2d.drawPolygon(getCollisionBox());
				getCollisionBox().translate(-x, -y);
			}
			graphics2d.drawImage(getImage(), null, x-20	, y-50);
		}
	}

	@Override
	public int getZ() {
		return 2;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public Entity getAttacker() {
		return attacker;
	}

	@Override
	public void destroy() {
		setState(DESTROYED);
	}
	
}
