package entity.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import entity.IShootable;
import entity.bullet.Bullet;
import entity.particle.BombDestroyedParticle;
import entity.particle.BombParticle;
import game.GameManager;
import game.gamescene.StageScene;
import game.gamescene.WaveGameState;
import utility.ImageSpriteLoader;
import utility.InputUtility;

public class OneEnemy extends Enemy implements IShootable {

	private static final int shootDelay = 100;
	
	private int shootCounter;
	
	public OneEnemy(double health, double x, double y, int nextX, int nextY) {
		super(health, x, y, nextX, nextY, 1);
		
		// Frame Duration
		frameDuration = new int[5][];
		frameDuration[0] = new int[51];
		frameDuration[1] = new int[51];
		frameDuration[2] = new int[12];
		frameDuration[3] = new int[16];
		for (int i=0;i<4;i++) {
			for (int j=0;j<frameDuration[i].length;j++) {
				if (i==3 || i==2) {
					frameDuration[i][j] = 3;
				} else {
					frameDuration[i][j] = 2;
				}
			}
		}
		frameDuration[4] = new int[1];
		frameDuration[4][0] = 0;
				
		shootCounter = shootDelay;

		setAnimation(ImageSpriteLoader.OneEnemySprite);
		setCollisionBox(ImageSpriteLoader.OneEnemyCollision);
		
		Random rand = new Random();
		setFrame(rand.nextInt(41));
	}

	@Override
	public void update() {
		if (state == SPAWNING && Math.abs(nextX - x) <= 0.01 && Math.abs(nextY - y) <= 0.01)
			setReady();
		
		if (willAttack && state == READY) {
			attack();
		}
		
		move();
		
		updateFrame();
	}
	
	@Override
	public void hit(Bullet hitBullet) {
		super.hit(hitBullet);
	};
	
	public void move() {
		if (state == DESTROYED || state == DESTROYING)
			return;
		
		double nextMoveX = x + speed * (nextX-x) / Math.sqrt((x-nextX)*(x-nextX) + (y-nextY)*(y-nextY));
		double nextMoveY = y + speed * (nextY-y) / Math.sqrt((x-nextX)*(x-nextX) + (y-nextY)*(y-nextY));
		
		if (state != SPAWNING) {
			if (x<0 || x>=800 || y<0 || y>=600)
				state = DESTROYED;
		} 
		
		if (x <= nextX && nextX <= nextMoveX || Math.abs(x-nextX) <= 0.0001) {
			x = nextX;
		} else {
			x = nextMoveX;
		}
			
		if (y <= nextY && nextY <= nextMoveY  || Math.abs(y-nextY) <= 0.0001) {
			y = nextY;
		} else {
			y = nextMoveY;
		}
		
	}
	
	private void updateFrame() {
		if (frameCounter > 0) {
			frameCounter--;
			return;
		}
		
		if (state == DESTROYING) {
			if (frame+1 >= getNumberFrame()) {
				// Destroy animation End set state to DESTROY
				StageScene.instance.getCurrentWave().addParticle(new BombDestroyedParticle(x, y));
				state = DESTROYED;
			} else {
				setFrame(frame+1);
			}
		} else if (state == ATK) {
			if (getFrame() == 8) {
				// Spawn Bullet
				if (GameManager.getGameScene() instanceof StageScene) {
					StageScene scene = (StageScene)GameManager.getGameScene();
					scene.getCurrentWave().addBullet(new Bullet(x, y+10, 1, 3, this));
				}
			}
			if (frame+1 >= getNumberFrame()) {
				// Attack animation End
				// Set to Ready
				setReady();
			}
			else {
				setFrame(frame+1);
			}
		} else if (state == HIT) {
			if (frame+1 >= getNumberFrame()) {
				// Hitted animation End set state to READY
				setReady();
			} else {
				setFrame(frame+1);
			}
		} else {
			// Loop animation
			if (frame+1 >= getNumberFrame()) {
				setFrame(0);
			}
			setFrame(frame+1);
		}
		frameCounter = frameDuration[act][frame];
	}

	@Override
	public void attack() {
		if (shootCounter > 0) {
			shootCounter--;
			return;
		}

		state = ATK;
		setFrame(0);
		setAct(3);
		shootCounter = shootDelay;
		// FIXME
		if (StageScene.instance.getState() != WaveGameState.READY)
			return;	
		willAttack = false;
		// TODO Particle
	}
	
	@Override
	public void destroy() {
		state = DESTROYING;
		setAct(4);
		setFrame(0);
		//FIXME frameDuration to function
		frameCounter = frameDuration[act][frame];
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		if (isVisible()) {
			graphics2d.drawImage(getImage(), null, (int) (x-45), (int) (y-45));
		}
	}
	
}
