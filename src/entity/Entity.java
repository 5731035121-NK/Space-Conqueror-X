package entity;

import java.awt.Polygon;
import java.awt.image.BufferedImage;

import utility.PlayerConstant;

public abstract class Entity implements IRenderable, ICollidable {

	public static final int SPAWNING = 0;
	public static final int READY = 1;
	public static final int ATK = 2;
	public static final int HIT = 3;
	public static final int HIT_ATK = 4;
	public static final int DESTROYING = 5;
	public static final int DESTROYED = -1;
	
	protected BufferedImage[][] animation;
	protected int[][] frameDuration; 
	protected Polygon[][] collisionBox;
	protected int frameCounter, act, frame;

	protected double health;
	protected double x, y;
	protected boolean willAttack;
	protected int state;
	private boolean endAnimation;
	
	public Entity(double health, double x, double y) {
		this.health = Math.min(health, PlayerConstant.MAX_HEALTH);
		this.x = x;
		this.y = y;
		
		act = 0;
		frame = 0;
		endAnimation = false;
		state = SPAWNING;
	}
	
	@Override
	public boolean collideWith(ICollidable obj) {
		if (state==DESTROYED || state==DESTROYING)
			return false;
		if (getCollisionBox() == null)
			return false;
		
		Polygon myPolygon = getCollisionBox();
		Polygon hitPolygon = obj.getCollisionBox();
		
		if (hitPolygon !=  null) {
			for (int i=0;i<hitPolygon.npoints;i++) {
				if (myPolygon.contains(
						hitPolygon.xpoints[i]+obj.getX()-x, 
						hitPolygon.ypoints[i]+obj.getY()-y)) {
					return true;
				}
			}
			for (int i=0;i<myPolygon.npoints;i++) {
				if (hitPolygon.contains(
						myPolygon.xpoints[i]+getX()-obj.getX(), 
						myPolygon.ypoints[i]+getY()-obj.getY())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public abstract void destroy(); 

	public double getHealth() {
		return health;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public void setCollisionBox(Polygon[][] collisionBox) { this.collisionBox = collisionBox; }
	public Polygon getCollisionBox() {
		try {
			return collisionBox[act][frame];
		} catch (ArrayIndexOutOfBoundsException e) {
			return new Polygon();
		}
	}
	public boolean isDestroyed() { return state == DESTROYED; }
	public boolean isReady() { return state == READY; }
	public boolean isVisible() { return true; }
	protected void setAnimation(BufferedImage[][] animation) {
		act = 0;
		frame = 0;
		this.animation = animation;
	}
	public BufferedImage getImage() {
		try {
			return animation[act][frame];
		} catch (Exception e) {
			return null;
		}
	}

	public void setReady() {
		state = READY;
		setAct(0);
		setFrame(0);
	}
	
	public void setAttack() {
		state = ATK;
	}
	
	protected void setAct(int act) {
		this.act = act;
		endAnimation = false;
	}
	public int getAct() {
		if (animation == null)
			return -1;
		return act; 
	}
	protected void setFrame(int frame) {
		if (frameDuration[act] == null) {
			endAnimation = true;
			return;
		}
		if (frame >= frameDuration[act].length) {
			endAnimation = true; 
			return;
		}
		
		if (0 <= frame && frame < frameDuration[act].length) {
			this.frame = frame;
			endAnimation = false;
		}
			
	}
	public int getFrame() {
		if (animation == null)
			return -1;
		return frame;
	}
	protected boolean isEndAnimation() {
		if (endAnimation)
			return true;
		return false;
	}
	protected int getNumberFrame() {
		return frameDuration[act].length;
	}
	
	public void willAttack() {
		this.willAttack = true;
	}
}
