package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface IRenderable {
	
	void draw(Graphics2D graphics2d);
	
	boolean isVisible();
	int getZ();
	void update();
	boolean isDestroyed();
	// Animation
	BufferedImage getImage();
	int getAct();
	int getFrame();
	
}
