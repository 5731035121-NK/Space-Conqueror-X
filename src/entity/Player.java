package entity;

import entity.animation.LiveAnimation;
import game.MainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utility.GameConstant;
import utility.ImageSpriteLoader;
import utility.InputHandler;
import utility.RenderManager;

public class Player extends Entity implements IShootable {
	
	private static final int SHOOT_DELAY = 30;
	private static final int RESPWAN_DELAY = 10;
	
	private int fireCooldown, respawnCount;
	private int speed;
	private double damage;
	private boolean sheilded, disabledShoot;
	private Animation animation;
	private ArrayList<Particle> particle;
	
	public Player(int health, int speed, double damage) {
		super(health, MainGame.WIDTH/2, MainGame.HEIGHT - 100);
		
		this.speed = Math.min(speed, GameConstant.MAX_SPEED);
		this.damage = Math.min(damage, GameConstant.MAX_DAMAGE);
		particle = new ArrayList<Particle>();
		
		animation = new LiveAnimation();
		animation.setFrames(ImageSpriteLoader.Spacecraft[0]);
	}

	@Override
	public void fire() {
		if (disabledShoot) {
			return;
		}
		if (fireCooldown<=0) {
			if (InputHandler.getKeyPressed(KeyEvent.VK_SPACE)) {
				fireCooldown = SHOOT_DELAY;
				//TODO Spawn new Bullet
				//MainGame.getInstance().add(new Bullet(x, y, damage, this));
			}
			return;
		}
		fireCooldown--;
	}

	@Override
	public void update() {
		if (destroyed) {
			if (animation.isFinished()) {
				// TODO Game Over
				MainGame.GameState.gameover();
			}
			animation.update();
			return;
		}
		
		if (InputHandler.getKeyPressed(KeyEvent.VK_LEFT)) {
			x = Math.max(70, x-speed);
			((LiveAnimation)animation).setDirectionFrame(LiveAnimation.BACKWARD);
		} else if (InputHandler.getKeyPressed(KeyEvent.VK_RIGHT)) {
			x = Math.min(MainGame.WIDTH-60, x+speed);
			((LiveAnimation)animation).setDirectionFrame(LiveAnimation.FORWARD);
		}
		
		if (respawnCount > 0) {
			respawnCount--;
			return;
		} else if(respawnCount == 0) {
			respawnCount--;
			animation.setFrames(ImageSpriteLoader.Spacecraft[0]);
			disabledShoot = false;
		}
		// Fire
		fire();
		
		animation.update();
	}
	
	public void hit() {
		health = Math.max(0, health-1);
		if (health > 0) {
			disabledShoot = true;
			respawnCount = RESPWAN_DELAY;
			animation.setFrames(ImageSpriteLoader.Spacecraft[1]);
		} else {
			animation = new Animation(ImageSpriteLoader.Spacecraft[2],2);
			destroyed = true;
		}
	}

	public boolean isDisabledShoot() {
		return disabledShoot;
	}

	public void setDisabledShoot(boolean disabledShoot) {
		this.disabledShoot = disabledShoot;
	}

	@Override
	public boolean collideWith(ICollidable obj) {
		Polygon hitPolygon = obj.getPolygon();
		if (sheilded) {
			for (int i=0;i<obj.getPolygon().xpoints.length;i++) {
				if ( Math.hypot(hitPolygon.xpoints[i]+obj.getX()-x,
						hitPolygon.ypoints[i]+obj.getY()-y) >= 45 ) {
					return true;
				}
			}
		} else {
			return super.collideWith(obj);
		}
		
		return false;
	}
	
	@Override
	public int getZ() {
		return 1;
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		graphics2d.drawImage(animation.getImage(), null, x-60, y-60);
	}
	
}