package entity.particle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.IRenderable;

public abstract class Particle implements IRenderable{

	protected BufferedImage[] animation;
	protected int[] frameDuration;
	protected int frame;
	protected int state;
	protected double x, y;
	
	public Particle(double x, double y) {
		frame = 0;
		state = 0;
		this.x = x;
		this.y = y;
	}
	
	public boolean isDestroyed() {
		return state == Entity.DESTROYED;
	}
	
}
