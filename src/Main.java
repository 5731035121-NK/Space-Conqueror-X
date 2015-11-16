

import game.MainGame;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Space Conqueror X");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		MainGame game = new MainGame();
		frame.add(game);
		frame.pack();
		game.requestFocus();
		
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
			game.logicUpdate();
			game.repaint();
		}
	}

}
