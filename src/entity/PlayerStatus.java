package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import game.gamescene.GameScene;
import game.gamescene.StageScene;
import utility.GameLoader;
import utility.ImageSpriteLoader;
import utility.InputUtility;

public class PlayerStatus implements IRenderable {

	private static AlphaComposite halpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
	private static AlphaComposite notOver = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	private static Font font = new Font("Aldo the Apache", Font.BOLD, 28);
	
	private StageScene stage;
	boolean isOver;
	int x, y;

	public PlayerStatus(StageScene stage) {
		this.stage = stage;
		isOver = false;
	}

	public void draw(Graphics2D graphics2d) {
		graphics2d.setFont(font);
		graphics2d.setColor(Color.WHITE);
		graphics2d.drawImage(ImageSpriteLoader.PlayerStatus, null, 0, 549);
		graphics2d.setBackground(Color.BLACK);
		graphics2d.drawString(GameLoader.StageName + " - " + stage.getCurrentWave().getWaveName(), 15, 585);
		graphics2d.drawString("HEALTH", 391, 585);
		for (int i = 0; i < stage.getPlayer().getHealth(); i++)
			graphics2d.fillRect(485 + 20 * i, 560, 15, 30);
		if (!isOver) {
			graphics2d.setComposite(notOver);
		}
		graphics2d.fillRect(687, 554, 102, 42);
		graphics2d.clearRect(688, 555, 100, 40);
		graphics2d.drawString("PAUSE", 705, 585);
		graphics2d.setComposite(halpha);
		graphics2d.setColor(Color.BLACK);
		for (int i = 0; i < stage.getPlayer().getHealth(); i++)
			graphics2d.fillRect(485 + 20* i, 565, 15,25);

	}

	@Override
	public void update() {
		x = InputUtility.getMouseX();
		y = InputUtility.getMouseY();
		if (x >= 687 && x <= 789 && y >= 554 && y <= 596)
			isOver = true;
		else
			isOver = false;
		
		if (InputUtility.isMouseLeftDown() && isOver) {
			stage.togglePause();
		}
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE - 1;
	}

	@Override
	public boolean isDestroyed() {
		return false;
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}

	@Override
	public int getAct() {
		return 0;
	}

	@Override
	public int getFrame() {
		return 0;
	}

}
