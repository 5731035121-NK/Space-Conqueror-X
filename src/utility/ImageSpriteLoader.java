package utility;

import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageSpriteLoader {

	public static BufferedImage Play1 = loadSingleImage("res/menu/Play1.png");
	public static BufferedImage Play2 = loadSingleImage("res/menu/Play2.png");
	public static BufferedImage Exit1 = loadSingleImage("res/menu/Exit1.png");
	public static BufferedImage Exit2 = loadSingleImage("res/menu/Exit2.png");
	public static BufferedImage Help1 = loadSingleImage("res/menu/Help1.png");
	public static BufferedImage Help2 = loadSingleImage("res/menu/Help2.png");
	public static BufferedImage Option1 = loadSingleImage("res/menu/Option1.png");
	public static BufferedImage Option2 = loadSingleImage("res/menu/Option2.png");
	public static BufferedImage CloseButton1 = loadSingleImage("res/menu/CloseButton1.png");
	public static BufferedImage CloseButton2 = loadSingleImage("res/menu/CloseButton2.png");
	public static BufferedImage[][] ChapterSelect = loadTitleScene();
	public static BufferedImage LV_MAX = loadSingleImage("res/menu/LV_MAX.png");
	public static BufferedImage Levelled = loadSingleImage("res/menu/leveled.png");
	public static BufferedImage PM = loadSingleImage("res/menu/PM.png");
	public static BufferedImage[] Completed = {loadSingleImage("res/menu/Completed0.png"),loadSingleImage("res/menu/Completed1.png"),loadSingleImage("res/menu/Completed2.png"),loadSingleImage("res/menu/Completed3.png")};
	public static BufferedImage[] Failed = {loadSingleImage("res/menu/Failed0.png"),loadSingleImage("res/menu/Failed1.png"),loadSingleImage("res/menu/Failed2.png"),loadSingleImage("res/menu/Failed3.png")};
	
	public static BufferedImage PlayerStatus = load("res/menu/PlayerStatus.png", 800, 51)[0][0];
	
	public static BufferedImage[][] BG = load("res/bg.png", 800, 600);
	public static BufferedImage[][] SpacecraftSprite = load("res/spacecraft.png", 120, 120);
	public static Polygon[][] SpacecraftCollision = generateSpacecraftBox(SpacecraftSprite);
	public static BufferedImage[][] BulletSprite = load("res/bullet.png", 18,45);
	public static BufferedImage[][] IceBulletSprite = load("res/icybullet.png", 18, 45);
	public static BufferedImage[][] PiecingBulletSprite = load("res/piecingbullet.png", 18,45);
	public static Polygon[][] BulletCollision = generateBulletBox();
	public static BufferedImage[][] OneEnemySprite = load("res/one.png", 90, 90);
	public static Polygon[][] OneEnemyCollision = generateOneEnemy();
	public static BufferedImage[][] BossSprite = load("res/boss.png", 350, 350);
	public static Polygon[][] BossCollision = generateBossCollisionBox();
	
	public static BufferedImage[] fireParticle = load("res/fire.png", 60, 90)[0];
	public static BufferedImage[] bombParticle = load("res/bomb.png", 90, 90)[0];
	public static BufferedImage[] bomb2Particle = load("res/bomb2.png", 192, 192)[0];
	
	public static BufferedImage[][] loadTitleScene() {
		BufferedImage[][] a = new BufferedImage[10][8];
		String load;
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 8; j++) {
				load = "res/menu/c" + i + "-" + j + ".png";
				a[i][j] = loadSingleImage(load);
			}
		for (int i = 7; i < 10; i++)
			for (int j = 0; j < 8; j++) {
				load = "res/menu/c" + i + ".png";
				a[i][j] = loadSingleImage(load);
			}
		return a;
	}

	public static BufferedImage loadSingleImage(String s) {
		BufferedImage bi;
		try {
			ClassLoader loader = ImageSpriteLoader.class.getClassLoader();
			bi = ImageIO.read(loader.getResource(s));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading graphics. Cant load file " + s);
			System.exit(0);
			return null;
		}
		return bi;
	}
	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			ClassLoader loader = ImageSpriteLoader.class.getClassLoader();
			BufferedImage spritesheet = ImageIO.read(loader.getResource(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error Loading Sprite \"" + s + "\". Terminate program.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return null;
	}

	 private static Polygon[][] generateSpacecraftBox(BufferedImage[][] img) {		
		Polygon[][] p = new Polygon[img.length][];
		for (int i=0;i<p.length;i++) {
			p[i] = new Polygon[img[i].length];
			for (int j=0;j<p[i].length;j++) {
				int plotX[] = {-15, -14, 14, 15, 22, 23, 53, 53, -53, -53, -23, -22};
				int plotY[] = {-40, -29, -29, -40, -36, -13, 14, 39, 39, 14, -13, -36};
				p[i][j] = new Polygon(plotX, plotY, plotX.length);
			}
		}
		return p;
	}
	 
	 private static Polygon[][] generateBulletBox() {
		Polygon[][] p = new Polygon[2][1];
		int plotX[] = {-2, 2, 2, -2};
		int plotY[] = {-13, -13, -6, -6};
		p[0][0] = new Polygon(plotX, plotY, plotX.length);
		p[1][0] = new Polygon(plotX, plotY, plotX.length);
		return p;  
	 }
	 
	 private static Polygon[][] generateOneEnemy() {
		Polygon[][] p = new Polygon[5][];
		p[0] = new Polygon[51];
		p[1] = new Polygon[51];
		p[2] = new Polygon[12];
		p[3] = new Polygon[16];
		for (int i=0;i<4;i++) {
			for (int j=0;j<p[i].length;j++) {
				int plotX[] = {-28, 27, 27, -28};
				int plotY[] = {-23, -23, 22, 22};
				p[i][j] = new Polygon(plotX, plotY, plotX.length);
			}
		}
		p[4] = new Polygon[0];
		return p;  
	 }
	
	 private static Polygon[][] generateBossCollisionBox() {
		Polygon[][] p = new Polygon[5][];
		p[0] = new Polygon[51];
		p[1] = new Polygon[51];
		p[2] = new Polygon[12];
		p[3] = new Polygon[16];
		for (int i=0;i<4;i++) {
			for (int j=0;j<p[i].length;j++) {
				int plotX[] = {-140, 140, 140, -140};
				int plotY[] = {-150, -150, 100, 100};
				p[i][j] = new Polygon(plotX, plotY, plotX.length);
			}
		}
		p[4] = new Polygon[0];
		return p;  
	 }
	 
}
