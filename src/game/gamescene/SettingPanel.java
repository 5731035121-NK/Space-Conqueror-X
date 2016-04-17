package game.gamescene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;

import utility.AudioUtility;
import utility.ImageSpriteLoader;
import utility.InputUtility;

public class SettingPanel extends JComponent implements GameScene {
	
	private static Font header = new Font("Aldo the Apache", Font.BOLD, 64);
	private static Font other = new Font("Aldo the Apache", Font.BOLD, 30);
	
	private String leftkey, rightkey, normalbullet, piecingbullet, icybullet, pausekey;
	private boolean[] set;

	public SettingPanel() {
		//this.setPreferredSize(new Dimension(660, 488));
		set = new boolean[6];
		for (int i = 0; i < 6; i++) {
			set[i] = false;
		}
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
		g2.drawString("MUTE : ", 185, 470);
		
		g2.setBackground(Color.BLACK);
		g2.fillRect(285, 451, 20, 20);
		g2.clearRect(287, 453, 16, 16);
		if (AudioUtility.isMUTED())
			g2.fillRect(290, 456, 10, 10);
		
		int x = InputUtility.getMouseX();
		int y = InputUtility.getMouseY();
		if (x >= 660 && x <= 710 && y >= 85 && y <= 125) {
			g2.drawImage(ImageSpriteLoader.CloseButton2, null, 658, 85);
		}

	}

	@Override
	public void updateLogic() {
		int x = InputUtility.getMouseX();
		int y = InputUtility.getMouseY();
		
		if (InputUtility.isMouseLeftClicked()) {
			if (x >= 128 && x <= 533) {
				if (180 <= y && y <= 220) {
					boolean flag = !set[0];
					set = new boolean[6];
					set[0] = flag;
				} else if (220 <= y && y <= 260) {
					boolean flag = !set[1];
					set = new boolean[6];
					set[1] = flag;
				} else if (290 <= y && y <= 330) {
					boolean flag = !set[2];
					set = new boolean[6];
					set[2] = flag;
				} else if (330 <= y && y <= 370) {
					boolean flag = !set[3];
					set = new boolean[6];
					set[3] = flag;
				} else if (370 <= y && y <= 410) {
					boolean flag = !set[4];
					set = new boolean[6];
					set[4] = flag;
				}  else if (410 <= y && y <= 450) {
					boolean flag = !set[5];
					set = new boolean[6];
					set[5] = flag;
				}
			}
			if (285 <= x && x <= 305 && 450 <= y && y <= 470) {
				AudioUtility.toggleMUTED();
			}
		}
		
		for (int i = 0; i < set.length; i++) {
			if (set[i]) {
				for (int k = 0; k < 256; k++) {
					if (InputUtility.getKeyPressed(k)) {
						InputUtility.setKeyBind(i, k);
						set[i] = false;
						break;
					}
				}
			}
		}

		if (set[0])
			leftkey = "";
		else 
			leftkey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(0)));
		
		if (set[1])
			rightkey = "";
		else 
			rightkey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(1)));
		
		if (set[2])
			normalbullet = "";
		else 
			normalbullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(2)));
		
		if (set[3])
			piecingbullet = "";
		else 
			piecingbullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(3)));

		if (set[4])
			icybullet = "";
		else 
			icybullet = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(4)));
		
		if (set[5])
			pausekey = "";
		else 
			pausekey = KeyEvent.getKeyText(new Integer(InputUtility.getKeyBind(5)));
	}

	@Override
	public int getState() {
		return 0;
	}
}
