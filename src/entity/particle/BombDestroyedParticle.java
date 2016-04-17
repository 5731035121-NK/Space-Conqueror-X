package entity.particle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import utility.AudioUtility;
import utility.ImageSpriteLoader;

public class BombDestroyedParticle extends Particle {
	
	private int counter;

	public BombDestroyedParticle(double x, double y) {
		super(x, y);
		AudioUtility.playSound("bomb");
		
		animation = ImageSpriteLoader.bomb2Particle;
		frameDuration = new int[20];
		for (int i=0;i<frameDuration.length;i++)
			frameDuration[0] = 20;
		counter = 0;
	}

	@Override
	public void draw(Graphics2D graphics2d) {
		graphics2d.drawImage(animation[frame], null, (int) (x-92), (int) (y-92));
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public int getZ() {
		return 1;
	}

	@Override
	public void update() {
		if (counter > 0) {
			counter--;
			return;
		}
		
		if (frame + 1 < frameDuration.length && frame + 1 >= 0) {
			counter = frameDuration[frame+1];
			frame = frame+1;;
		} else {
			state = Entity.DESTROYED;
		}
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

	@Override
	public int getAct() {
		return 0;
	}

	@Override
	public int getFrame() {
		return frame;
	}

}
