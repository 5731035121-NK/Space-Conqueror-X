package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.gamescene.StageScene;
import utility.GameLoader;
import utility.ImageSpriteLoader;
public class Background implements IRenderable {

	private BufferedImage background;
	
	public Background(StageScene stage) {
		background = ImageSpriteLoader.BG[GameLoader.Stage-1][0];
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		graphics2d.drawImage(background, null, 0, 0);
	}
	@Override
	public boolean isVisible() {
		return true;
	}
	@Override
	public int getZ() {
		return -1;
	}
	@Override
	public void update() {}
	@Override
	public boolean isDestroyed() {
		return false;
	}
	@Override
	public BufferedImage getImage() {
		return background;
	}
	@Override
	public int getAct() {
		return 0;
	}
	@Override
	public int getFrame() {
		return 0;
	}
	
}
