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
	
	private BufferedImage[][] animation;
	private Polygon[][] collisionBox;
	private int act, frame;
	private boolean endAnimation;

	protected double health;
	protected int x, y;
	private int state;
	
	public Entity(double health, int x, int y) {
		this.health = Math.min(health, PlayerConstant.MAX_HEALTH);
		this.x = x;
		this.y = y;
		
		act = 0;
		frame = 0;
		state = SPAWNING;
		endAnimation = false;
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

	public int getX() { return x; }
	public int getY() { return y; }
	public void setCollisionBox(Polygon[][] collisionBox) { this.collisionBox = collisionBox; }
	public Polygon getCollisionBox() {
		try {
			return collisionBox[act][frame];
		} catch (ArrayIndexOutOfBoundsException e) {
			return new Polygon();
		}
	}
	public double getHealth() { return health; }
	protected int getState() { return state; }
	protected void setState(int state) { this.state = state; }
	public boolean isDestroyed() { return state == DESTROYED; }
	
	public boolean isVisible() { return true; }
	public void setAnimation(BufferedImage[][] animation) {
		act = 0;
		frame = 0;
		this.animation = animation;
	}
	public BufferedImage getImage() {
		if (animation[act][frame] == null)
			return null;
		return animation[act][frame];
	}
	public boolean isEndAnimation() {
		return endAnimation;
	}
	public void setAct(int act) {
		/*if (act >= animation.length) {
			System.err.println("setAct(act): act is more than animation.lenght");
			return;
		}*/
		this.act = act;
	}
	public int getAct() {
		if (animation == null)
			return -1;
		return act; 
	}
	public void setFrame(int frame) {
		endAnimation = false;
		if (frame >= animation[act].length) {
			endAnimation = true;
			return;
		} else if (frame < 0) {
			return;
		}
		this.frame = frame;
	}
	public int getFrame() {
		if (animation == null)
			return -1;
		return frame;
	}
	public int getNumFrame() {
		return animation[act].length;
	}
	
}
