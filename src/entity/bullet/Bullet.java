package entity.bullet;

import game.GameManager;
import game.gamescene.StageScene;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

import entity.Entity;
import entity.Player;
import entity.enemy.EnemyGroup;
import entity.particle.BombParticle;
import utility.AudioUtility;
import utility.ImageSpriteLoader;
import utility.InputUtility;

public class Bullet extends Entity {

	private int moveCount, moveDuration = 1;
	private int speed;
	private double damage;
	private Entity attacker;
	
	public Bullet(double x, double y, double damage, int speed, Entity attacker) {
		super(1, x, y);
		
		this.speed = speed;
		moveCount = moveDuration;
		this.damage = damage;
		this.attacker = attacker;
		
		frameDuration = new int[2][1];
		frameDuration[0][0] = 0;
		frameDuration[1][0] = 0;
		frameCounter = frameDuration[0][0];
		
		setAnimation(ImageSpriteLoader.BulletSprite);
		if (speed >= 0) {
			setAct(1);
		} else {
			setAct(0);
		}
		setCollisionBox(ImageSpriteLoader.BulletCollision);
	}
	
	public void update() {
		if (state == DESTROYED)
			return;
		if (0 > getY() || getY() >= GameManager.HEIGHT) {
			destroyByOutofScreen();
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
		if (frameCounter > 0) {
			frameCounter--;
			return;
		}
		frameCounter = frameDuration[act][frame];
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		if (isVisible()) {
			graphics2d.drawImage(getImage(), null, (int) (x-9), (int) (y-22.5));
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
	
	public void destroy() {
		state = DESTROYED;
		// Spawn Bomb particle
		if (GameManager.getGameScene() instanceof StageScene) {		
			StageScene.instance.getCurrentWave().addParticle(new BombParticle(getX(), getY()));
		}
	}
	
	private void destroyByOutofScreen() {
		state = DESTROYED;
	}
	
}
