package utility;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import entity.enemy.Boss;
import entity.enemy.Enemy;
import entity.enemy.EnemyGroup;
import entity.enemy.OneEnemy;
import exception.FileFormatException;
import exception.FileMissingException;
import game.gamescene.WaveGameState;

public class GameLoader {

	public static class WaveLoader {
		private String waveName;
		private CopyOnWriteArrayList<EnemyGroup> enemyGroups;
		
		/* 
		 * Parse the given string "record"
		 * record format is name:score
		 */
		public WaveLoader(String str) throws FileFormatException {
			enemyGroups = new CopyOnWriteArrayList<EnemyGroup>();
			
			String[] group = str.split("/");
			waveName = group[0];
			
			try {
				for (int i = 1; i < group.length; i++) {
					String enemy[] = group[i].split(":");
					int groupDuration = Integer.parseInt(enemy[0]);
					boolean groupLooped = Boolean.getBoolean(enemy[1]);

					ArrayList<Enemy> enemies = new ArrayList<Enemy>();
					ArrayList<Point> points = new ArrayList<Point>();
					for (int j = 2; j < enemy.length; j++) {
						String detail[] = enemy[j].split(" ");
						int type = Integer.parseInt(detail[0]);
						if (type == 0) {
							int x = Integer.parseInt(detail[1]);
							int y = Integer.parseInt(detail[2]);
							
							enemies.add(null);
							points.add(new Point(x, y));
						} else if (type == 1) {
							int health = Integer.parseInt(detail[1]);
							int sx = Integer.parseInt(detail[2]);
							int sy = Integer.parseInt(detail[3]);
							int x = Integer.parseInt(detail[4]);
							int y = Integer.parseInt(detail[5]);
							
							enemies.add(new OneEnemy(health, sx, sy, x, y));
							points.add(new Point(x, y));
						} else if (type == 2) {
							int health = Integer.parseInt(detail[1]);
							int sx = Integer.parseInt(detail[2]);
							int sy = Integer.parseInt(detail[3]);
							int x = Integer.parseInt(detail[4]);
							int y = Integer.parseInt(detail[5]);
							ArrayList<Enemy> bossEnemies = new ArrayList<Enemy>();
							ArrayList<Point> bossPoints = new ArrayList<Point>();
							for (int k=6; k<detail.length; k++) {
								String detailBoss[] = detail[k].split("!");
								int typeE = Integer.parseInt(detailBoss[0]);
								if (typeE == 0) {
									int xE = Integer.parseInt(detailBoss[1]);
									int yE = Integer.parseInt(detailBoss[2]);
									
									bossEnemies.add(null);
									bossPoints.add(new Point(xE, yE));
								} else if (typeE == 1) {
									int healthE = Integer.parseInt(detailBoss[1]);
									int sxE = Integer.parseInt(detailBoss[2]);
									int syE = Integer.parseInt(detailBoss[3]);
									int xE = Integer.parseInt(detailBoss[4]);
									int yE = Integer.parseInt(detailBoss[5]);
									
									bossEnemies.add(new OneEnemy(healthE, sxE, syE, xE, yE));
									bossPoints.add(new Point(xE, yE));
								}
							}
							enemies.add(new Boss(health, new EnemyGroup(bossEnemies, bossPoints, 50, false), sx, sy, x, y));
							points.add(new Point(x, y));
						}
					}

					enemyGroups.add(new EnemyGroup(enemies, points, groupDuration, groupLooped));
				}
			} catch (NumberFormatException e) {
				throw new FileFormatException();
			}
		}
		
		public String getWaveName() {
			return waveName;
		}
		
		public CopyOnWriteArrayList<EnemyGroup> getEnemyGroups() {
			return enemyGroups;
		}
		
	}
	
	public static int numberWave = 0;
	public static WaveLoader[] wave;
	public static String StageName;
	public static int Stage, SubStage;
	
	public static void loadStage(int stage, int substage) throws FileMissingException, FileFormatException {
		wave = null;
		
		ClassLoader loader = GameLoader.class.getClassLoader();
		String url = "res/stage/"+stage+"-"+substage;
		Stage = stage;
		SubStage = substage;
		
		Scanner file = null;
		
		try {
			file = new Scanner(loader.getResourceAsStream(url));
		} catch (Exception e) {
			throw new FileMissingException("Stage file missing");
		}
		
		if (file.hasNext()) {
			try {
				StageName = file.nextLine();
				numberWave = Integer.parseInt(file.nextLine());
			} catch (Exception e) {
				throw new FileFormatException();
			}
			
			wave = new WaveLoader[numberWave];
			
			for (int i=0;i<numberWave;i++) {
				wave[i] = new WaveLoader(file.nextLine());
			}
		}
	}
	
	private static boolean readAndParseKeyBind(File f){
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			InputUtility.initKeyBind();
			String str = "";
			int c;
			while ((c = in.read()) != -1) {
				str += (char)c;
			}
			in.close();
			String status[] = str.split("\n");
			try {
				InputUtility.setKeyBind(0, Integer.parseInt(status[0]));
				InputUtility.setKeyBind(1, Integer.parseInt(status[1]));
				InputUtility.setKeyBind(2, Integer.parseInt(status[2]));
				InputUtility.setKeyBind(3, Integer.parseInt(status[3]));
				InputUtility.setKeyBind(4, Integer.parseInt(status[4]));
				InputUtility.setKeyBind(5, Integer.parseInt(status[5]));
			} catch (NumberFormatException e) {
				System.err.println("Key bind file format wrong.");
			}
			
			return true;
		} catch (Exception e) {
			System.err.println("Error: Read Key bind");
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean loadKeyBind() {
		File f = new File("key");
		
		if (!f.exists()) {
			if (!createDefaultKeyBindFile()) return false;
		}
		if (!readAndParseKeyBind(f)) {
			f.delete();
			if(!createDefaultKeyBindFile()) return false;
			return readAndParseKeyBind(f);
		}
		return true;
	}
	
	public static boolean createDefaultKeyBindFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("key"));
			String str = "";
			str += KeyEvent.VK_LEFT + "\n";
			str += KeyEvent.VK_RIGHT + "\n";
			str += KeyEvent.VK_Z + "\n";
			str += KeyEvent.VK_X + "\n";
			str += KeyEvent.VK_C + "\n";
			str += KeyEvent.VK_ENTER + "\n";
			str = str.trim();
			out.write(str);
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean saveKeyBind() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("key"));
			String str = "";
			
			for (int i=0;i<6;i++) {
				str += InputUtility.getKeyBind(i)+"\n";
			}
			str = str.trim();
			out.write(str);
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
