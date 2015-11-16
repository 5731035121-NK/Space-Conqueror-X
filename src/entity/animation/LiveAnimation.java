package entity.animation;

import entity.Animation;

public class LiveAnimation extends Animation {
	
	public static final int BACKWARD = -1;
	public static final int HOLD = 0;
	public static final int FORWARD = 1;
	
	private int directionFrame;
	
	public LiveAnimation() {
		super();
		
		directionFrame = 0;
	}
	
	@Override
	public void update() {
		if (delay==-1) return;
		count++;
		if (count == delay) {
			if (directionFrame==0) {
				if (currentFrame<numFrames/2) {
					currentFrame = currentFrame+1;
				} else if (currentFrame>numFrames/2) {
					currentFrame = currentFrame-1;
				}
			} else if (0<=currentFrame+directionFrame && currentFrame+directionFrame < numFrames) {
				currentFrame = currentFrame + directionFrame;
				directionFrame = 0;
			}
		}
	}

	public int getDirectionFrame() {
		return directionFrame;
	}

	public void setDirectionFrame(int directionFrame) {
		if (directionFrame>=-1 && directionFrame<=1)
			this.directionFrame = directionFrame;
	}
	
}
