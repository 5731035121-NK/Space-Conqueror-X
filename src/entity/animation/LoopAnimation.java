package entity.animation;

import java.awt.image.BufferedImage;

import entity.Animation;

public class LoopAnimation extends Animation {
	
	private int timesPlayed;
	
	public LoopAnimation() {
		super();
		
		timesPlayed = 0;
	}
	
	@Override
	public void setFrames(BufferedImage[] frames) {
		super.setFrames(frames);
		timesPlayed = 0;
	};
	
	@Override
	public void update() {
		if (delay==-1) return;
		count++;
		if (count == delay) {
			currentFrame++;
			count = 0;
		}
		
		if (currentFrame == numFrames) {
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public boolean hasPlayed(int i) { return timesPlayed == i; }
	
}
