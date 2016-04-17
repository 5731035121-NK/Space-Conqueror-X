package entity;

import game.GameManager;
import game.gamescene.StageScene;
import game.gamescene.WaveGameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.bullet.Bullet;
import entity.bullet.IceBullet;
import entity.bullet.PiecingBullet;
import entity.particle.BombDestroyedParticle;
import entity.particle.BombParticle;
import entity.particle.Particle;
import entity.particle.fireParticle;
import utility.AudioUtility;
import utility.ImageSpriteLoader;
import utility.InputUtility;
import utility.PlayerConstant;

public class Player extends Entity implements IShootable {
	
	private int shootCounter, fireRate;
	private int moveCount, moveDuration = 0;
	
	private double speed, damage, damagePiecing, damageIce, slowIce;
	private boolean sheilded;
	private ArrayList<Particle> particles;
	
	public Player() {
		this(PlayerConstant.HEALTH, PlayerConstant.SPEED, 
				PlayerConstant.DAMAGE, PlayerConstant.DAMAGE_PIECE, PlayerConstant.DAMAGE_ICE, PlayerConstant.SLOW_ICE, 
				PlayerConstant.FIRERATE);
	}
	
	public Player(int health, double speed, double damage, double damagePiecing, double damageIce, double slowIce, int fireRate) {
		super(health, GameManager.WIDTH/2, GameManager.HEIGHT + 140);
		
		speed = Math.min(speed, PlayerConstant.MAX_SPEED);
		this.damage = Math.min(damage, PlayerConstant.MAX_DAMAGE);
		this.damagePiecing = Math.min(damagePiecing, PlayerConstant.MAX_DAMAGE);
		this.damageIce = Math.min(damageIce, PlayerConstant.MAX_DAMAGE);
		this.slowIce = slowIce;
		this.speed = speed;
		this.fireRate = fireRate;
		particles = new ArrayList<Particle>();
		particles.add(new fireParticle(this, x-10, y-10));
		
		// Frame Duration
		frameDuration = new int[5][];
		frameDuration[0] = new int[37];
		frameDuration[1] = new int[37];
		for (int i=0;i<37;i++) {
			frameDuration[0][i] = 0;
			frameDuration[1][i] = 0;
		}
		frameDuration[2] = new int[1];
		frameDuration[2][0] = 0;
		frameDuration[3] = new int[1];
		frameDuration[3][0] = 0;
		frameDuration[4] = new int[1];
		frameDuration[4][0] = 0;

		moveCount = moveDuration;
		frameCounter = frameDuration[0][18];
		shootCounter = fireRate;
		
		setAnimation(ImageSpriteLoader.SpacecraftSprite);
		setCollisionBox(ImageSpriteLoader.SpacecraftCollision);
		setAct(1);
		setFrame(17);
	}

	@Override
	public void update() {
		if (state == DESTROYED)
			return;
		
		if (state == SPAWNING && y <= GameManager.HEIGHT-100)
			setReady();
		
		// State is spawning or ready
		if (state != DESTROYED || state != DESTROYING)
			move();
		
		attack();
		
		if (shootCounter>0)
			shootCounter--;

		updateFrame();
		
		for (Particle particle : particles) {
			particle.update();
		}
	}	
	
	public void attack() {
		if (InputUtility.isFireNormal()) {
			if (StageScene.instance.getState() != WaveGameState.READY)
				return;
			if (shootCounter<=0) {
				shootCounter = fireRate;
				// Spawn bullet
				if (GameManager.getGameScene() instanceof StageScene) {
					AudioUtility.playSound("shoot");
					StageScene scene = (StageScene)GameManager.getGameScene();
					scene.getCurrentWave().addBullet(new Bullet(x, y-29, damage, -8, this));
				}
				// TODO Particle
			}
		} else if (InputUtility.isFireIce()) {
			if (StageScene.instance.getState() != WaveGameState.READY)
				return;
			if (shootCounter<=0) {
				shootCounter = fireRate;
				// Spawn bullet
				if (GameManager.getGameScene() instanceof StageScene) {
					AudioUtility.playSound("shoot");
					StageScene scene = (StageScene)GameManager.getGameScene();
					scene.getCurrentWave().addBullet(new IceBullet(x, y-29, damageIce, slowIce, -8, this));
				}
				// TODO Particle
			}
		} else if (InputUtility.isFirePiece()) {
			if (StageScene.instance.getState() != WaveGameState.READY)
				return;
			if (shootCounter<=0) {
				shootCounter = fireRate;
				// Spawn bullet
				if (GameManager.getGameScene() instanceof StageScene) {
					AudioUtility.playSound("shoot");
					StageScene.instance.getCurrentWave().addBullet(new PiecingBullet(x, y-29, damagePiecing/5, -25, this));
				}
				// TODO Particle
			}
		}
	}
	
	private void updateFrame() {
		if (frameCounter > 0) {
			frameCounter--;
			return;
		}
		if (state == DESTROYING) {
			if (frame+1 >= getNumberFrame()) {
				// End Animation DESTROYING
				StageScene.instance.getCurrentWave().addParticle(new BombDestroyedParticle(x, y));
				state = DESTROYED;
			} else {
				setFrame(frame+1);
			}
		} else {
			if (InputUtility.isLeft()) {
				setFrame(frame-1);
			} else if (InputUtility.isRight()) {
				setFrame(frame+1);
			} else {
				if (getFrame() < 18) {
					setFrame(frame+1);
				} else if (getFrame() > 18){
					setFrame(frame-1);
				}
			}
		}
		// FIXME fix get frameDuration to function when finish
		frameCounter = frameDuration[act][frame];
	}

	private void move() {
		if (moveCount > 0) {
			moveCount--;
			return;
		}

		moveCount = moveDuration;
		if (state == SPAWNING) {
			y = (int) (y - 4);
			return;
		}
		
		if (InputUtility.isLeft()) {
			setX((int) (x-speed));
		} else if (InputUtility.isRight()) {
			setX((int) (x+speed));
		}
	}
	
	public void hit() {
		destroy();
	}
	
	@Override
	public void destroy() {
		state = DESTROYING;
		particles.clear();
		setAct(4);
		setFrame(0);
		frameCounter = frameDuration[act][frame];
	}
	
	@Override
	public boolean collideWith(ICollidable obj) {
		Polygon hitPolygon = obj.getCollisionBox();
		if (sheilded) {
			for (int i=0;i<obj.getCollisionBox().xpoints.length;i++) {
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
	public void draw(Graphics2D graphics2d) {
		if (isVisible()) {
			for (Particle particle : particles) {
				particle.draw(graphics2d);
			}
			
			graphics2d.drawImage(getImage(), null, (int) (x-60), (int) (y-60));
		}
	}
	
	@Override
	public int getZ() { return 10; }
	private void setX(int x) {
		if (x>=79 && x<=GameManager.WIDTH-79)
			this.x = x;
	}
	
	public boolean isShootReady() {
		return shootCounter == 0;
	}
	
}