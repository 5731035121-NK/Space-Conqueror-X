package entity.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

import entity.Bullet;
import entity.IShootable;
import game.GameManager;
import game.gamescene.StageScene;
import utility.GameConstant;
import utility.ImageSpriteLoader;
import utility.InputUtility;

public class OneEnemy extends Enemy implements IShootable {

	private static int DESTROY_FRAME_DELAY = 2;
	
	private OneEnemiesGroup group;
	private int shootDuration = 50 /*50 Frames*/;
	private int shootCounter;
	private int wait;
	
	private int moveCount, moveDuration = 20;
	private int frameCount, frameDuration = 4;
	
	public OneEnemy(double health, int x, int y, OneEnemiesGroup group) {
		super(health, x, y);

		this.group = group;
		
		moveCount = moveDuration;
		shootCounter = shootDuration;
		frameCount = frameDuration;
		wait = 0;

		setCollisionBox(ImageSpriteLoader.OneEnemyCollision);
	}

	@Override
	public void update() {
		System.out.println(getState());
		switch (getState()) {
		case SPAWNING:
			if (group.isInBox(getCollisionBox(), x, y))
				ready();
			break;
		case ATK:
			if (wait<=0) {
				if (GameManager.getGameScene() instanceof StageScene) {
					StageScene scene = (StageScene)GameManager.getGameScene();
					scene.getCurrentWave().addEnitity(new Bullet(x, y, 1, 3, this));
				}
				ready();
			}
			break;
		case HIT: 
			// TODO
			if (wait<=0) 
				ready();
			break;
		case HIT_ATK:
			// TODO
			if (wait<=0)
				atk();
			break;
		case DESTROYING:
			if (wait<=0)
				setState(DESTROYED);
			break;
		case DESTROYED:
			return;
		default:
			break;
		}
		
		if (wait>0)
			wait--;
		
		// Move Entity
		if (getState() != DESTROYED || getState() != DESTROYING)
			move();
		// TODO Shoot
		
		//updateFrame();
	}
	
	private void move() {
		if (moveCount  > 0) {
			moveCount--;
			return;
		}
		moveCount = moveDuration;
		x += group.getStepX();
		y += group.getStepY();
	}

	private void ready() {
		setState(READY);
		setAct(0);
	}
	
	private void updateFrame() {
		if (frameCount > 0) {
			frameCount--;
			return;
		}
		int currentState = getState();
		frameCount = frameDuration;
		if (currentState == DESTROYING) {
			setFrame(getFrame()+1);
		} else if (currentState == ATK) {
			setFrame(getFrame()+1);
		} else if (currentState == HIT) {
			setFrame(getFrame()+1);
		} else {
			setFrame(getFrame()+1);
			if (isEndAnimation())
				setFrame(0);
		}
	}

	@Override
	public void atk() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void destroy() {
		setState(DESTROYING);
		wait = DESTROY_FRAME_DELAY;
		frameDuration = DESTROY_FRAME_DELAY;
		frameCount = frameDuration;
		//FIXME 
		setAct(4);
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
			// TODO Draw animation
		}
	}
	
}
