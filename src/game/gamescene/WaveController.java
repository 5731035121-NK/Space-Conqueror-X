package game.gamescene;

import game.GameManager;

public class WaveController implements Runnable {
	
	private WaveGameState wave;
	private Thread prevThread;
	
	public WaveController(WaveGameState wave, Thread prevThread) {
		this.wave = wave;
		this.prevThread = prevThread;
	}
	
	@Override
	public void run() {
		try {
			if (prevThread != null)
				prevThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		wave.setPause(true);
		while (true) {
			try {
				Thread.sleep(GameManager.REFRESH_DELAY);
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (wave) {
				if (!wave.isPause()) {
					try {
						wave.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			wave.updateLogic();
			GameManager.getGameScene().repaint();
			if (wave.clearDestroyed()) {
				break;
			}
		}
	}

}
