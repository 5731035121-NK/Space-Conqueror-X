package game.gamescene;

import java.awt.Color;

import game.GameManager;

public class WaveController implements Runnable {
	
	private WaveGameState wave;
	private Thread prevThread;
	
	public WaveController(WaveGameState wave, Thread prevThread) {
		this.wave = wave;
		this.prevThread = prevThread;
	}

	public synchronized void togglePause() {
		if (prevThread == null || !prevThread.isAlive()) {
			wave.setPause(!wave.isPause());
		}
		
		notifyAll();
	}
	
	@Override
	public void run() {
		try {
			if (prevThread != null)
				prevThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				Thread.sleep(GameManager.REFRESH_DELAY);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (wave.getState() == WaveGameState.DEAD) {
				break;
			}
			synchronized (this) {
				if (wave.isPause()) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			GameManager.getGameScene().repaint();
			wave.updateLogic();
			wave.clearDestroyed();
			/*if (wave.clearDestroyed()) {
				break;
			}*/
		}
	}

}
