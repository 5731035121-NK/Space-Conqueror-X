package game.gamescene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;

import utility.ImageSpriteLoader;
import utility.InputUtility;

public class HelpPanel extends JComponent implements GameScene {
	
	private static Font header = new Font("Aldo the Apache", Font.BOLD, 64);
	private static Font other = new Font("Aldo the Apache", Font.BOLD, 30);
	
	private String leftkey, rightkey, normalbullet, piecingbullet, icybullet, pausekey;

	public HelpPanel() {
		//this.setPreferredSize(new Dimension(660, 488));
		leftkey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(0)));
		rightkey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(1)));
		normalbullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(2)));
		piecingbullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(3)));
		icybullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(4)));
		pausekey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(5)));
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(ImageSpriteLoader.PM, null, 69, 67);
		g2.setFont(header);
		g2.setColor(Color.WHITE);
		
		g2.setFont(other);
		g2.drawString("MOVE LEFT : " + leftkey, 185, 206);
		g2.drawString("MOVE RIGHT : " + rightkey, 185, 244);
		g2.drawString("SHOOT :", 185, 282);
		g2.drawString("FOR NORMAL BULLET : " + normalbullet, 228, 320);
		g2.drawString("FOR PIECING BULLET : " + piecingbullet, 228, 358);
		g2.drawString("FOR ICY BULLET : " + icybullet, 228, 396);
		g2.drawString("PAUSE : " + pausekey, 185, 432);
		
		int x = InputUtility.getMouseX();
		int y = InputUtility.getMouseY();
		if (x >= 660 && x <= 710 && y >= 85 && y <= 125) {
			g2.drawImage(ImageSpriteLoader.CloseButton2, null, 658, 85);
		}

	}

	@Override
	public void updateLogic() {
		leftkey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(0)));
		rightkey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(1)));
		normalbullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(2)));
		piecingbullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(3)));
		icybullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(4)));
		pausekey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(5)));
	}

	@Override
	public int getState() {
		return 0;
	}
}
