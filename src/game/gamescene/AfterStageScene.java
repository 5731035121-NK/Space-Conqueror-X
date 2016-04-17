package game.gamescene;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import game.GameManager;
import utility.GameLoader;
import utility.ImageSpriteLoader;
import utility.InputUtility;
import utility.PlayerConstant;

public class AfterStageScene extends JComponent implements GameScene {

	private static AlphaComposite halpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
	private static Font font = new Font("Aldo the Apache", Font.BOLD, 32);
	
	private BufferedImage[] ending;
	private int GoldLoot, state;
	private boolean Pass;
	private int x, y;

	public AfterStageScene(boolean Pass) {
		this.Pass = Pass;
		if (Pass)
			ending = ImageSpriteLoader.Completed;
		else
			ending = ImageSpriteLoader.Failed;
		
		if (GameLoader.Stage == 5)
			GoldLoot = (int) Math.pow(2, GameLoader.Stage) * 1000;
		else
			GoldLoot = (int) Math.pow(2, GameLoader.Stage) * (GameLoader.SubStage + 1) * 100;
		
		if (Pass) {
			PlayerConstant.GOLD = GoldLoot + PlayerConstant.GOLD;
		} else {
			PlayerConstant.GOLD = PlayerConstant.GOLD - GoldLoot;
			PlayerConstant.HEALTH = 1;
			PlayerConstant.UPGRADE_LEVEL[0] = HEIGHT;
		}
		this.state = 0;
		if (GameLoader.Stage == PlayerConstant.CHAPTER) {
			if (GameLoader.SubStage == 5) {
				if (PlayerConstant.CHAPTER < GameLoader.Stage+1) {
					// Change Chapter
					PlayerConstant.CHAPTER = GameLoader.Stage+1;
					PlayerConstant.STAGE = 1;
				}
			} else {
				PlayerConstant.STAGE = Math.max(PlayerConstant.STAGE, GameLoader.SubStage+1);
			}
		}
		
		PlayerConstant.savePlayerStatusFile();
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(ending[state], null, 0, 0);
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString("STAGE : " + GameLoader.Stage + " - " + GameLoader.SubStage, 305, 127);
		if (Pass) {
			g2.drawString("GOLD LOOT : " + GoldLoot, 251, 187);
			g2.drawString("TOTAL GOLD : " + PlayerConstant.GOLD, 236, 247);
			g2.drawString("REMAINING HEALTH : ", 151, 307);
			for (int i = 0; i < PlayerConstant.HEALTH; i++)
				g2.fillRect(435 + 20 * i, 281, 15, 30);
			g2.setComposite(halpha);
			g2.setColor(Color.BLACK);
			for (int i = 0; i < PlayerConstant.HEALTH; i++)
				g2.fillRect(435 + 20 * i, 281, 15, 25);
		} else {
			g2.drawString("GOLD PENALTY : " + GoldLoot, 211, 187);
			g2.drawString("REMAINING GOLD : " + PlayerConstant.GOLD, 181, 247);
			g2.drawString("REMAINING HEALTH : ", 151, 307);
			g2.fillRect(435, 281, 15, 30);
			g2.setComposite(halpha);
			g2.setColor(Color.BLACK);
			g2.fillRect(435, 281, 15, 25);
		}
	}

	@Override
	public void updateLogic() {
		x = InputUtility.getMouseX();
		y = InputUtility.getMouseY();
		if (x >= 143 && x <= 225 && y >= 492 && y <= 542)
			state = 1;
		if (x >= 294 && x <= 506 && y >= 492 && y <= 542)
			state = 2;
		if (x >= 575 && x <= 714 && y >= 492 && y <= 542)
			state = 3;
		if (x >= 575 && x <= 732 && y >= 492 && y <= 542 && Pass)
			state = 3;

		if (InputUtility.isMouseLeftClicked()) {
			if (x >= 143 && x <= 225 && y >= 492 && y <= 542) {
				TitleScene.getInstance().state = TitleScene.SHOP;
				GameManager.switchScene(TitleScene.getInstance());
			}
			if (x >= 294 && x <= 506 && y >= 492 && y <= 542) {
				TitleScene.getInstance().state = TitleScene.CHAPTER_SELECT;
				GameManager.switchScene(TitleScene.getInstance());
			}
			if (x >= 575 && x <= 714 && y >= 492 && y <= 542) {
				if (!Pass) {
					StageScene.instance = new StageScene(GameLoader.Stage, GameLoader.SubStage);
					GameManager.switchScene(StageScene.instance);
					state = 3;
				} else {
					GameManager.switchScene(TitleScene.getInstance());
				}
			}
		}
	}

	@Override
	public int getState() {
		return state;
	}

}
