package utility;

import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageSpriteLoader {

	public static BufferedImage[][] SpacecraftSprite = load("res/spacecraft.png", 120, 120);
	public static Polygon[][] SpacecraftCollision = generateSpacecraftBox(SpacecraftSprite);
	public static BufferedImage[][] BulletSprite = load("res/bullet.png", 36,90);
	public static Polygon[][] BulletCollision = generateBulletBox();
	public static Polygon[][] OneEnemyCollision = generateOneEnemy();
	
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
			e.printStackTrace();
			System.out.println("Error loading graphics.");
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
		Polygon[][] p = new Polygon[1][1];
		int plotX[] = {-5, 5, 5, -5};
		int plotY[] = {-12, -12, 12, 12};
		p[0][0] = new Polygon(plotX, plotY, plotX.length);
		return p;  
	 }
	 
	 private static Polygon[][] generateOneEnemy() {
		Polygon[][] p = new Polygon[1][1];
		int plotX[] = {-28, 27, 27, -28};
		int plotY[] = {-23, -23, 22, 22};
		p[0][0] = new Polygon(plotX, plotY, plotX.length);
		return p;  
	 }
	
}
