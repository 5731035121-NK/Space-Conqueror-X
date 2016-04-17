package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Label implements IRenderable {

	public static Font header = new Font("Aldo the Apache", Font.BOLD, 64);
	
	private String displayStr;
	private int act, frame;
	private int frameCounter;
	private int[][] frameDuration;
	private boolean play;
	
	public Label(String displayStr) {
		this.displayStr = displayStr;
		frameDuration = new int[2][10];
		for (int i=0;i<frameDuration[0].length;i++) {
			frameDuration[0][i] = 3;
			frameDuration[1][i] = 3;
		}
		frame = 0;
		frameCounter = frameDuration[0][0];
		act = 0;
		play = true;
	}
	
	public void update() {
		if (isDestroyed())
			return;
		
		if (frameCounter > 0) {
			frameCounter--;
			return;
		}
		if (play == false)
			return;
			
		if (frame+1>=frameDuration[0].length) {
			play = false;
			return;
		}
		frame++;
		frameCounter = frameDuration[0][frame];
	}
	
	@Override
	public void draw(Graphics2D graphics2d) {
		if (isVisible()) {

			graphics2d.setColor(Color.WHITE);
			if (act == 0) {
				graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
			}
			else {
				graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (0.7-0.07*frame)));
			}
			graphics2d.fillRect(0, 250, 800, 100);
			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			graphics2d.setColor(Color.BLACK);
			graphics2d.setFont(header);
			graphics2d.drawString(" " + displayStr, 300, 300);
		}
	}

	@Override
	public boolean isVisible() {
		return !isDestroyed();
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isDestroyed() {
		return act == 1 && !play;
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAct() {
		return act;
	}

	@Override
	public int getFrame() {
		return frame;
	}
	
	public String getDisplayString() {
		return displayStr;
	}

	public void nextAnimation() {
		// TODO Auto-generated method stub
		if (act == 0 && play == false) {
			act = 1;
			frame = 0;
			play = true;
		}
	}

}
