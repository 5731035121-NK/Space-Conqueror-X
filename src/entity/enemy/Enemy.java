package entity.enemy;

import entity.Entity;
import entity.bullet.Bullet;

public abstract class Enemy extends Entity implements Cloneable{

	protected double speed;
	protected double nextX, nextY;
	private double normalSpeed;
	private boolean slow;
	
	public Enemy(double health, double x, double y, double nextX, double nextY, double speed) {
		super(health, x, y);
		
		this.nextX = nextX;
		this.nextY = nextY;
		this.speed = speed;
	}
	
	public void hit(Bullet hitBullet) {
		if (hitBullet == null)
			return;
		health = Math.max(0, health-hitBullet.getDamage());
		if (health <= 0) {
			destroy();
		} else {
			state = HIT;
			setAct(2);
			setFrame(0);
			frameCounter = frameDuration[act][frame];
		}
	}

	public void setSpeed(double d) {
		this.speed = d;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setNextX(int nextX) {
		this.nextX = nextX;
	}
	
	public void setNextY(int nextY) {
		this.nextY = nextY;
	}
	
	@Override
	public int getZ() {
		return 3;
	}
	
	public abstract void move();

	public void slow() {
		slow = true;
		normalSpeed = speed;
		speed = speed/2;
	}
	
	public void deSlow() {
		slow = false;
		speed = normalSpeed;
	}

	public boolean isInRightPos() {
		if (Math.abs(x-nextX) <= 0.0001 && Math.abs(y-nextY) <= 0.0001) {
			return true;
		}
		return false;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
