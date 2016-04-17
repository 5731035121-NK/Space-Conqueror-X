package entity.particle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Player;
import utility.ImageSpriteLoader;

public class fireParticle extends Particle {

	private Player player;
	private int counter;
	private int direction;
	
	public fireParticle(Player player, double x, double y) {
		super(x, y);
		
		this.player = player;
		direction = 1;
		animation = ImageSpriteLoader.fireParticle;
		frameDuration = new int[24];
		for (int i=0;i<frameDuration.length;i++)
			frameDuration[0] = 3;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public void update() {
		if (counter > 0) {
			counter--;
			return;
		}
		
		if (frame + direction < frameDuration.length && frame + direction >= 0) {
			counter = frameDuration[frame+direction];
			frame = frame+direction;;
		} else {
			direction = -direction;
			frame = frame+direction;
		}
	}

	@Override
	public void draw(Graphics2D graphics2d) {
		graphics2d.drawImage(animation[frame], null, (int) (player.getX()-30), (int) (player.getY()+10));
	}

	@Override
	public BufferedImage getImage() {
		return animation[frame];
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
