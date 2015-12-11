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
	void setAnimation(BufferedImage[][] animation);
	BufferedImage getImage();
	void setAct(int act); /* X axis in picture */
	int getAct();
	void setFrame(int frame); /* Y axis in picutre */
	int getFrame();
	
}
