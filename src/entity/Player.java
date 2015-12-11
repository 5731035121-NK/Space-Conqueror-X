package entity;

import game.GameManager;
import game.gamescene.StageScene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import utility.GameConstant;
import utility.ImageSpriteLoader;
import utility.InputUtility;
import utility.PlayerConstant;

public class Player extends Entity implements IShootable {
	
	private static final int SHOOT_DELAY = 50;
	private static final int DESTROY_FRAME_DELAY = 20;
	
	private int fireCooldown, moveCount, moveDuration = 2;
	private int frameCount, frameDuration = 4;
	
	private int speed, speedY = 4;
	private double damage;
	private boolean sheilded, disabledShoot;
	private ArrayList<Particle> particle;
	
	public Player() {
		this(PlayerConstant.HEALTH, PlayerConstant.SPEED, PlayerConstant.DAMAGE);
	}
	
	public Player(int health, int speed, double damage) {
		super(health, GameManager.WIDTH/2, GameManager.HEIGHT + 100);
		
		speed = Math.min(speed, PlayerConstant.MAX_SPEED);
		this.damage = Math.min(damage, PlayerConstant.MAX_DAMAGE);
		this.speed = speed;
		particle = new ArrayList<Particle>();

		moveCount = moveDuration;
		frameCount = frameDuration;
		fireCooldown = SHOOT_DELAY;
		
		setAnimation(ImageSpriteLoader.SpacecraftSprite);
		setAct(1);
		setFrame(17); /*Middle frame*/
		setCollisionBox(ImageSpriteLoader.SpacecraftCollision);
	}

	@Override
	public void update() {
		// CHANGE STATE
		switch (getState()) {
		case SPAWNING:
			if (y<=GameManager.HEIGHT-100) {
				ready();
			}
			break;
		case ATK:
			// TODO
			if (true) {
				// Spawn bullet
				if (GameManager.getGameScene() instanceof StageScene) {
					StageScene scene = (StageScene)GameManager.getGameScene();
					scene.getCurrentWave().addEnitity(new Bullet(x, y, damage, -5, this));
				}
				// ready
				ready();
			}
			break;
		case DESTROYING:
			if (isEndAnimation()) {
				setState(DESTROYED);
			}
			break;
		case DESTROYED:
			return;
		default:
			break;
		}
		
		// State is spawning or ready
		if (getState() == SPAWNING || getState() == READY) {
			move();
			if (fireCooldown>0) fireCooldown--;
			if (InputUtility.getKeyPressed(KeyEvent.VK_SPACE))
				atk();
		}

		updateFrame();
	}

	private void ready() {
		setState(READY);
		setAct(0);
	}
	
	public void atk() {
		if (disabledShoot)
			return;
		if (fireCooldown<=0) {
			fireCooldown = SHOOT_DELAY;			
			setState(ATK);
			// TODO Particle
		}
	}
	
	private void move() {
		if (moveCount > 0) {
			moveCount--;
			return;
		}

		moveCount = moveDuration;
		if (getState() == SPAWNING) {
			y = y - speedY;
			return;
		}
		
		if (InputUtility.getKeyPressed(KeyEvent.VK_LEFT)) {
			setX(x-speed);
		} else if (InputUtility.getKeyPressed(KeyEvent.VK_RIGHT)) {
			setX(x+speed);
		}
	}
	
	private void updateFrame() {
		if (frameCount > 0) {
			frameCount--;
			return;
		}
		frameCount = frameDuration;
		if (getState() == DESTROYING) {
			setFrame(getFrame()+1);
		} else {
			if (InputUtility.getKeyPressed(KeyEvent.VK_LEFT)) {
				setFrame(getFrame()-1);
			} else if (InputUtility.getKeyPressed(KeyEvent.VK_RIGHT)) {
				setFrame(getFrame()+1);
			} else {
				if (getFrame() < 18) {
					setFrame(getFrame()+1);
				} else if (getFrame() > 18){
					setFrame(getFrame()-1);
				}
			}
		}
	}
	
	public void hit() {
		health = Math.max(0, health-1);
		// TODO spawn particle boom at x, y
		if (health <= 0) {
			destroy();
		}
	}
	
	@Override
	public void destroy() {
		setState(DESTROYING);
		frameDuration = DESTROY_FRAME_DELAY;
		frameCount = frameDuration;
		//FIXME
		setAct(4);
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
			if (GameConstant.WIRE_FRAME) {
				graphics2d.setColor(Color.BLUE);
				getCollisionBox().translate(x, y);
				graphics2d.drawPolygon(getCollisionBox());
				getCollisionBox().translate(-x, -y);
			}
			graphics2d.drawImage(getImage(), null, x-60, y-60);
		}
	}
	
	public boolean isDisabledShoot() { return disabledShoot; }
	public void setDisabledShoot(boolean disabledShoot) { this.disabledShoot = disabledShoot; }
	@Override
	public int getZ() { return 1; }
	private void setX(int x) {
		if (x>=79 && x<=GameManager.WIDTH-79)
			this.x = x;
	}
	
}