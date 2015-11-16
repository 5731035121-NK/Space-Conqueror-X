package entity;

import java.awt.image.BufferedImage;

public class Animation {

	protected BufferedImage[] frames;
	protected int currentFrame;
	protected int numFrames;
	
	protected int count;
	protected int delay;
	private boolean finished;
	
	public Animation() {
		super();
	}
	
	public Animation(BufferedImage[] frames, int delay) {
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		this.delay = delay;
		numFrames = frames.length;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		delay = 2;
		numFrames = frames.length;
	}
	
	public void setDelay(int i) { delay = i; }
	public void setFrame(int i) { currentFrame = i; }
	public void setNumFrames(int i) { numFrames = i; }
	
	public void update() {
		if (delay==-1) return;
		count++;
		if (count == delay) {
			if (currentFrame+1 < numFrames)
				currentFrame++;
			else {
				finished = true;
			}
			count = 0;
		}
	}
	
	public int getFrame() { return currentFrame; }
	public int getCount() { return count; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean isFinished() { return finished;  }
	
}
