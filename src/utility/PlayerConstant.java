package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerConstant {

	public static final double MAX_HEALTH = 10.0;
	public static final int MAX_SPEED = 10;
	public static final double MAX_DAMAGE = 90.0;
	
	public static int HEALTH;
	public static double DAMAGE;
	public static double DAMAGE_PIECE;
	public static double DAMAGE_ICE;
	public static double SLOW_ICE;
	public static double SPEED; 
	public static int FIRERATE;
	public static int GOLD;
	public static int UPGRADE_LEVEL[];
	public static int SLOW_DURATION;
	public static int CHAPTER;
	public static int STAGE;
	
	public static void decreaseHealth() {
		if (HEALTH > 0) {
			HEALTH--;
			UPGRADE_LEVEL[0] = HEALTH;
		}
	}
	
	private static boolean readAndParseFile(File f){
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			UPGRADE_LEVEL = new int[6];
			String str = "";
			int c;
			while ((c = in.read()) != -1) {
				str += (char)c;
			}
			in.close();
			String status[] = getXORed(str).split("\n");
			try {
				HEALTH = Integer.parseInt(status[0]);
				DAMAGE = Double.parseDouble(status[1]);
				DAMAGE_PIECE = Double.parseDouble(status[2]);
				DAMAGE_ICE = Double.parseDouble(status[3]);
				SLOW_ICE = Double.parseDouble(status[4]);
				SPEED = Double.parseDouble(status[5]);
				FIRERATE = Integer.parseInt(status[6]);
				GOLD = Integer.parseInt(status[7]);
				for (int i = 0; i < 6; i++) {
					UPGRADE_LEVEL[i] = Integer.parseInt(status[8+i]);
				}
				SLOW_DURATION = Integer.parseInt(status[14]);
				CHAPTER = Integer.parseInt(status[15]);
				STAGE = Integer.parseInt(status[16]);
			} catch (NumberFormatException e) {
				System.err.println("Player Status file format wrong.");
			}
			
			return true;
		} catch (Exception e) {
			// TODO Display popup
			System.err.println("Error: Read Player Status");
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean loadPlayerStatus() {
		File f = new File("playerstatus");
		
		if (!f.exists()) {
			if (!createDefaultPlayerStatusFile()) return false;
		}
		if (!readAndParseFile(f)) {
			f.delete();
			if(!createDefaultPlayerStatusFile()) return false;
			return readAndParseFile(f);
		}
		return true;
	}

	public static boolean createDefaultPlayerStatusFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("playerstatus"));
			String str = "";
			str += 1 + "\n";
			str += 1 + "\n";
			str += 1 + "\n";
			str += 1 + "\n";
			str += 0.4 + "\n";
			str += 4 + "\n";
			str += 100 + "\n";
			str += 300 + "\n";
			for (int i=0; i<6; i++) {
				str += 0 + "\n";
			}
			str += 400 + "\n";
			str += 1 + "\n";
			str += 1 + "\n";
			str = str.trim();
			out.write(getXORed(str));
			out.close();
			return true;
		} catch (IOException e1) {
			return false;
		}
	}
	
	public static boolean savePlayerStatusFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("playerstatus"));
			String str = "";
			str += HEALTH + "\n";
			str += DAMAGE + "\n";
			str += DAMAGE_PIECE + "\n";
			str += DAMAGE_ICE + "\n";
			str += SLOW_ICE + "\n";
			str += SPEED + "\n";
			str += FIRERATE + "\n";
			str += GOLD + "\n";
			for (int i=0; i<6; i++) {
				str += UPGRADE_LEVEL[i] + "\n";
			}
			str += SLOW_DURATION + "\n";
			str += CHAPTER + "\n";
			str += STAGE + "\n";
			str = str.trim();
			out.write(getXORed(str));
			out.close();
			return true;
		} catch (IOException e1) {
			return false;
		}
	}
	
	private static final byte[] key = "RmAAq2b5d8fjgu9dhher".getBytes();
	
	//This method does both encryption and decryption 
	private static String getXORed(String in){
		byte[] inData = in.getBytes();
		int l=0;
		for(int i =0;i<inData.length;i++){
			inData[i]=(byte) (inData[i]^key[l]);
			l++;
			if(l==key.length)
				l=0;
		}
		return new String(inData);
	}

	public static boolean reachStage(int state, int i) {
		if (state == 0)
			return i <= CHAPTER;
		if (state < CHAPTER)
			return true;
		if (state > CHAPTER)
			return false;
		return i <= STAGE;
	}

	public static boolean reachChapter(int state) {
		return state <= CHAPTER;
	}
	
}
