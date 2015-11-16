package entity;

import java.awt.Polygon;

import utility.GameConstant;

public abstract class Entity implements IRenderable, ICollidable {
	
	protected int health;
	protected int x, y;
	protected boolean destroyed;
	private Polygon boundingPolygon;
	
	public Entity(int health, int x, int y) {
		this.health = Math.max(health, GameConstant.MAX_HEALTH);
		this.x = Math.max(x, 0);
		this.y = Math.max(y, 0);
		boundingPolygon = new Polygon();
	}
	public void decreaseHealth(int decreaseLife) {
		health = Math.max(0, health-decreaseLife);
	}
	
	@Override
	public boolean collideWith(ICollidable obj) {
		Polygon hitPolygon = obj.getPolygon();
		for (int i=0;i<obj.getPolygon().xpoints.length;i++) {
			if (boundingPolygon.contains(
					hitPolygon.xpoints[i]+obj.getX()-x, 
					hitPolygon.ypoints[i]+obj.getY()-y)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int getX() {
		return x;
	}
	@Override
	public int getY() {
		return y;
	}
	protected void setPolygon(Polygon p) {
		this.boundingPolygon = p;
	}
	@Override 
	public Polygon getPolygon() {
		return boundingPolygon;
	}
	
	@Override
	public boolean isDestroyed() {
		return destroyed; 
	}
	@Override
	public boolean isVisible() {
		return true;
	}
	
}
