package game.gamescene;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entity.PlayerStatus;
import game.GameManager;
import utility.AudioUtility;
import utility.GameLoader;
import utility.ImageSpriteLoader;
import utility.InputUtility;
import utility.PlayerConstant;

public class TitleScene extends JPanel implements GameScene {

	public static final int CHAPTER_SELECT = 0;
	public static final int SHOP = 6;
	public static final int MAINSCENE = 7;
	public static final int HELP = 8;
	public static final int OPTION = 9;
	private static final TitleScene instance = new TitleScene();
	
	public static TitleScene getInstance() {
		return instance;
	}
	
	private static AlphaComposite notOver = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	private static AlphaComposite Over = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	private static Font goldFont = new Font("Aldo the Apache", Font.BOLD, 28);
	private static Font itemFont = new Font("Aldo the Apache", Font.BOLD, 20);
	
	private int upgradeBarY[], itemX[], itemY[];
	private String itemName[];
	private boolean shop[];
	private SettingPanel OptionPanel;
	private HelpPanel HelpPanel;
	
	public int state = 7;
	
	private BufferedImage s[][], cs, BG, Play1, Play2, Exit1, Exit2, Help1, Help2, Option1, Option2, Play, Exit, 
				Help, Option, LV_MAX, Levelled;

	public TitleScene() {

		state = 7;
		s = ImageSpriteLoader.ChapterSelect;
		cs = s[state][0];
		Play = ImageSpriteLoader.Play1;
		Play1 = ImageSpriteLoader.Play1;
		Play2 = ImageSpriteLoader.Play2;
		Exit = ImageSpriteLoader.Exit1;
		Exit1 = ImageSpriteLoader.Exit1;
		Exit2 = ImageSpriteLoader.Exit2;
		Help = ImageSpriteLoader.Help1;
		Help1 = ImageSpriteLoader.Help1;
		Help2 = ImageSpriteLoader.Help2;
		Option = ImageSpriteLoader.Option1;
		Option1 = ImageSpriteLoader.Option1;
		Option2 = ImageSpriteLoader.Option2;
		LV_MAX = ImageSpriteLoader.LV_MAX;
		Levelled = ImageSpriteLoader.Levelled;
		
		upgradeBarY = new int[6];
		upgradeBarY[0] = 175;
		upgradeBarY[1] = upgradeBarY[0] + 55;
		upgradeBarY[2] = upgradeBarY[1] + 55;
		upgradeBarY[3] = upgradeBarY[2] + 85;
		upgradeBarY[4] = upgradeBarY[3] + 85;
		upgradeBarY[5] = upgradeBarY[4] + 85;
		itemX = new int[6];
		itemX[0] = 115;
		itemX[1] = itemX[0] + 11;
		itemX[2] = itemX[0] - 36;
		itemX[3] = itemX[0] + 3;
		itemX[4] = itemX[0] + 3;
		itemX[5] = itemX[0] + 3;
		itemY = new int[6];
		itemY[0] = 158;
		itemY[1] = itemY[0] + 55;
		itemY[2] = itemY[1] + 55;
		itemY[3] = itemY[2] + 85;
		itemY[4] = itemY[3] + 85;
		itemY[5] = itemY[4] + 85;
		itemName = new String[6];
		itemName[0] = "HEALTH";
		itemName[1] = "SPEED";
		itemName[2] = "FIRING RATE";
		itemName[3] = "POWER";
		itemName[4] = "POWER";
		itemName[5] = "POWER";
		shop = new boolean[6];
		for (int i = 0; i < 6; i++) {
			shop[i] = false;
		}
		
		setPreferredSize(new Dimension(800, 600));
		
		Play = ImageSpriteLoader.Play1;
		Exit = ImageSpriteLoader.Exit1;
		Help = ImageSpriteLoader.Help1;
		Option = ImageSpriteLoader.Option1;

		OptionPanel = new SettingPanel();
		HelpPanel = new HelpPanel();
		
		AudioUtility.playSound("theme");
	}

	public void buy(int g, int i) {
		if (g >= Math.pow(2, PlayerConstant.UPGRADE_LEVEL[i]) * 100 & PlayerConstant.UPGRADE_LEVEL[i] < 10 && shop[i]) {
			PlayerConstant.GOLD -= Math.pow(2, PlayerConstant.UPGRADE_LEVEL[i]) * 100;
			PlayerConstant.UPGRADE_LEVEL[i]++;
			if (i == 0)
				PlayerConstant.HEALTH = PlayerConstant.UPGRADE_LEVEL[i];
			if (i == 1)
				PlayerConstant.SPEED = 0.4 * PlayerConstant.UPGRADE_LEVEL[i] + 1.8;
			if (i == 2)
				PlayerConstant.FIRERATE = 100 - 9 * PlayerConstant.UPGRADE_LEVEL[i];
			if (i == 3)
				PlayerConstant.DAMAGE = Math.pow(2, PlayerConstant.UPGRADE_LEVEL[i]);
			if (i == 4)
				PlayerConstant.DAMAGE_PIECE = Math.pow(2, PlayerConstant.UPGRADE_LEVEL[i]);
			if (i == 5){
				PlayerConstant.DAMAGE_ICE = Math.pow(2, PlayerConstant.UPGRADE_LEVEL[i]);
				PlayerConstant.SLOW_ICE = 0.3 + PlayerConstant.UPGRADE_LEVEL[i] * 0.05;
			}
			PlayerConstant.savePlayerStatusFile();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(cs, null, 0, 0);
		if (state == 6) {
			g2.setColor(Color.WHITE);
			g2.setFont(goldFont);
			g2.drawString("GOLD : $" + PlayerConstant.GOLD, 53, 93);
			g2.setComposite(notOver);
			g2.setFont(itemFont);
			for (int i = 0; i < 6; i++) {
				drawCost(g2, PlayerConstant.UPGRADE_LEVEL[i], upgradeBarY[i]);
				g2.drawString(itemName[i], itemX[i], itemY[i]);
			}
			g2.setComposite(Over);
			for (int i = 0; i < 6; i++) {
				if (shop[i]) {
					drawCost(g2, PlayerConstant.UPGRADE_LEVEL[i], upgradeBarY[i]);
					g2.drawString(itemName[i], itemX[i], itemY[i]);
				}
			}
			for (int i = 0; i < 6; i++)
				if (PlayerConstant.UPGRADE_LEVEL[i] == 10) {
					g2.drawImage(LV_MAX, null, 70, upgradeBarY[i] - 40);
					g2.drawString(itemName[i], itemX[i], itemY[i]);
				}
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < PlayerConstant.UPGRADE_LEVEL[i]; j++) {
					g2.drawImage(Levelled, null, 190 + 20 * j, itemY[i] - 13);
				}
			}
		}
		if (state == 7) {
			g2.drawImage(BG, null, 0, 0);
			g2.drawImage(Play, null, 277, 475);
			g2.drawImage(Exit, null, 187, 540);
			g2.drawImage(Help, null, 308, 540);
			g2.drawImage(Option, null, 443, 540);
		}
		if (state == 8) {
			HelpPanel.paint(g2);
		}
		if (state == 9) {
			OptionPanel.paint(g2);
		}
	}

	public void drawCost(Graphics2D g, int level, int y) {
		int a = (int) (Math.pow(2, level) * 100);
		if (level <= 3)
			g.drawString("$" + a, 136, y);
		else if (level <= 6)
			g.drawString("$" + a, 127, y);
		else if (level <= 9)
			g.drawString("$" + a, 119, y);
	}

	@Override
	public void updateLogic() {
		// TODO Auto-generated method stub
		int x = InputUtility.getMouseX();
		int y = InputUtility.getMouseY();
		if (x >= 50 && x <= 270 && y >= 50 && y <= 240 && PlayerConstant.reachStage(state, 5))
			cs = s[state][5];
		else if (x >= 290 && x <= 510 && y >= 50 && y <= 240 && PlayerConstant.reachStage(state, 4))
			cs = s[state][4];
		else if (x >= 530 && x <= 750 && y >= 50 && y <= 240 && PlayerConstant.reachStage(state, 3))
			cs = s[state][3];
		else if (x >= 530 && x <= 750 && y >= 360 && y <= 550 && PlayerConstant.reachStage(state, 2))
			cs = s[state][2];
		else if (x >= 290 && x <= 510 && y >= 360 && y <= 550 && PlayerConstant.reachStage(state, 1))
			cs = s[state][1];
		else if (x >= 50 && x <= 270 && y >= 360 && y <= 445)
			cs = s[state][6];
		else if (x >= 50 && x <= 270 && y >= 465 && y <= 550)
			cs = s[state][7];
		else
			cs = s[state][0];
		if (x >= 277 && x <= 517 && y >= 475 && y <= 525)
			Play = Play2;
		else
			Play = Play1;
		if (y >= 540 && y <= 580) {
			if (x >= 187 && x <= 292)
				Exit = Exit2;
			else
				Exit = Exit1;
			if (x >= 308 && x <= 424)
				Help = Help2;
			else
				Help = Help1;
			if (x >= 443 && x <= 614)
				Option = Option2;
			else
				Option = Option1;
		} else {
			Exit = Exit1;
			Help = Help1;
			Option = Option1;
		}
		if (state == 6) {
			cs = s[state][0];
			for (int i = 0; i < 6; i++)
				shop[i] = false;
			if (x >= 70 && x <= 175) {
				if (y >= 135 && y <= 185)
					shop[0] = true;
				if (y >= 190 && y <= 240)
					shop[1] = true;
				if (y >= 245 && y <= 295)
					shop[2] = true;
				if (y >= 330 && y <= 380)
					shop[3] = true;
				if (y >= 415 && y <= 465)
					shop[4] = true;
				if (y >= 500 && y <= 550)
					shop[5] = true;
			}
			for (int i = 0; i < 6; i++)
				if (shop[i])
					cs = s[state][i + 1];
			if (x >= 690 && x <= 780 && y >= 20 && y <= 70)
				cs = s[state][7];

		}
		if (InputUtility.isMouseLeftClicked()) {
			int cx = InputUtility.getMouseX();
			int cy = InputUtility.getMouseY();
			if (state == 0) {
				if (cx >= 50 && cx <= 270 && cy >= 50 && cy <= 240 && PlayerConstant.reachChapter(5)) {
					AudioUtility.playSound("clicked");
					state = 5;
					cs = s[state][0];
				}
				if (cx >= 290 && cx <= 510 && cy >= 50 && cy <= 240 && PlayerConstant.reachChapter(4)) {
					AudioUtility.playSound("clicked");
					state = 4;
					cs = s[state][0];
				}
				if (cx >= 530 && cx <= 750 && cy >= 50 && cy <= 240 && PlayerConstant.reachChapter(3)) {
					AudioUtility.playSound("clicked");
					state = 3;
					cs = s[state][0];
				}
				if (cx >= 530 && cx <= 750 && cy >= 360 && cy <= 550 && PlayerConstant.reachChapter(2)) {
					AudioUtility.playSound("clicked");
					state = 2;
					cs = s[state][0];
				}
				if (cx >= 290 && cx <= 510 && cy >= 360 && cy <= 550 && PlayerConstant.reachChapter(1)) {
					AudioUtility.playSound("clicked");
					state = 1;
					cs = s[state][0];
				}
				if (cx >= 50 && cx <= 270 && cy >= 360 && cy <= 445) {
					AudioUtility.playSound("clicked");
					state = 6;
					cs = s[state][0];
				}
				if (cx >= 50 && cx <= 270 && cy >= 465 && cy <= 550) {
					AudioUtility.playSound("clicked");
					state = 7;
					cs = s[state][0];
				}
			} else if (state >= 1 && state <= 5) {
				if (cx >= 50 && cx <= 270 && cy >= 50 && cy <= 240 && PlayerConstant.reachStage(state, 5)) {
					//Gamstate-5);
					AudioUtility.playSound("clicked");
					StageScene.instance = new StageScene(state, 5);
					GameManager.switchScene(StageScene.instance);
					cs = s[state][0];
				}
				if (cx >= 290 && cx <= 510 && cy >= 50 && cy <= 240 && PlayerConstant.reachStage(state, 4)) {
					// state = 4;
					AudioUtility.playSound("clicked");
					StageScene.instance = new StageScene(state, 4);
					GameManager.switchScene(StageScene.instance);
					cs = s[state][0];
				}
				if (cx >= 530 && cx <= 750 && cy >= 50 && cy <= 240 && PlayerConstant.reachStage(state, 3)) {
					// state = 3;
					AudioUtility.playSound("clicked");
					StageScene.instance = new StageScene(state, 3);
					GameManager.switchScene(StageScene.instance);
					cs = s[state][0];
				}
				if (cx >= 530 && cx <= 750 && cy >= 360 && cy <= 550 && PlayerConstant.reachStage(state, 2)) {
					// state = 2;
					AudioUtility.playSound("clicked");
					StageScene.instance = new StageScene(state, 2);
					GameManager.switchScene(StageScene.instance);
					cs = s[state][0];
				}
				if (cx >= 290 && cx <= 510 && cy >= 360 && cy <= 550 && PlayerConstant.reachStage(state, 1)) {
					// state = 1;
					AudioUtility.playSound("clicked");
					StageScene.instance = new StageScene(state, 1);
					GameManager.switchScene(StageScene.instance);
					cs = s[state][0];
				}
				if (cx >= 50 && cx <= 270 && cy >= 465 && cy <= 550) {
					AudioUtility.playSound("clicked");
					state = 0;
					cs = s[state][0];
				}
			}
			if (state == 6) {
				for (int i = 0; i < 6; i++)
					buy(PlayerConstant.GOLD, i);
				if (cx >= 690 && cx <= 780 && cy >= 20 && cy <= 70) {
					state = 0;
					cs = s[state][0];
				}
			}
			if (state == 7) {
				if (cx >= 277 && cx <= 517 && y >= 475 && y <= 525) {
					AudioUtility.playSound("clicked");
					state = 0;
					cs = s[state][0];
				}
				if (y >= 540 && y <= 580) {
					if (cx >= 187 && cx <= 292) {
						Exit = Exit2;
						AudioUtility.playSound("clicked");
						System.exit(0);
					} if (cx >= 308 && cx <= 424) {
						AudioUtility.playSound("clicked");
						state = 8;
						cs = s[state][0];
					}
					if (cx >= 443 && cx <= 614) {
						AudioUtility.playSound("clicked");
						state = 9;
						cs = s[state][0];
					}
				}
			}
			if (state == 8) {
				if (cx >= 660 && cx <= 710 && cy >= 85 && cy <= 125) {
					AudioUtility.playSound("clicked");
					state = 7;
				}
				cs = s[state][0];
			}
			if (state == 9) {
				if (cx >= 660 && cx <= 710 && cy >= 85 && cy <= 125) {
					AudioUtility.playSound("clicked");
					state = 7;
				}
				cs = s[state][0];
			}
		}
		
		if (state == 8) {
			HelpPanel.updateLogic();
		} else if (state == 9) {
			OptionPanel.updateLogic();
		}
	}

	@Override
	public int getState() {
		return 0;
	}
}
