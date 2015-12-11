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
	
	public static int HEALTH = 1;
	public static double DAMAGE = 1.0;
	public static int SPEED = 2; 
	
	private static boolean readAndParseFile(File f){
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
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
				SPEED = Integer.parseInt(status[2]);
			} catch (NumberFormatException e) {
				System.err.println("Player Status file format wrong.");
			}
			
			return true;
		} catch (Exception e) {
			// TODO Display popup
			System.err.println("Error: Read Player Status");
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

	private static boolean createDefaultPlayerStatusFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("playerstatus"));
			String str = "";
			str += HEALTH + "\n";
			str += DAMAGE + "\n";
			str += SPEED + "\n";
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
		int i = 0;
		for (byte b : inData) {
			b = (byte) (b^key[i%key.length]);
			i++;
		}
		return new String(inData);
	}
	
}
