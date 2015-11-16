package entity;

import java.awt.Graphics2D;

public interface IRenderable {
	
	void draw(Graphics2D graphics2d);
	
	boolean isVisible();
	
	int getZ();
	
	void update();
	
	boolean isDestroyed();
	
}
