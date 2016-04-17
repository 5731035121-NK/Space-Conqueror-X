package utility;

import java.applet.Applet;
import java.applet.AudioClip;

public class AudioUtility {

	private static boolean MUTED;
	private static AudioClip acShoot = loadSound("res/sound/Laser.wav");
	private static AudioClip acExplosion = loadSound("res/sound/Explosion.wav");
	private static AudioClip acClick = loadSound("res/sound/Click.wav");
	private static AudioClip acLaserSound = loadSound("res/sound/Assaulted.wav");
	private static AudioClip acThemeSong = loadSound("res/sound/ThemeSong.wav");
	
	public static AudioClip loadSound(String s) {
		ClassLoader loader = AudioUtility.class.getClassLoader();
		
		try {
			//System.out.println(Applet.newAudioClip((loader.getResource(s)).toURI().toURL()));
			return Applet.newAudioClip((loader.getResource(s)).toURI().toURL());
		} catch (Exception e) {
			System.err.println("Error load sound " + s);
		}
		return null;
	}
	
	public static void playSound(String identifier){
		if (MUTED) {
			return;
		}
		
		if (identifier.equalsIgnoreCase("shoot")) {
			acShoot.play();
		} else if (identifier.equalsIgnoreCase("clicked")) {
			acClick.play();
		} else if (identifier.equalsIgnoreCase("explosion")) {
			acExplosion.play();
		} else if (identifier.equalsIgnoreCase("laser")) {
			acLaserSound.play();
		} else if (identifier.equalsIgnoreCase("theme")) {
			acThemeSong.loop();
		}
	}
	
	public static boolean isMUTED() {
		return MUTED;
	}
	
	public static void toggleMUTED() {
		MUTED = !MUTED;
		if (MUTED) {
			try {
				acShoot.stop();
			} catch (Exception e) {
				System.err.println("ERROR WHILE MUTED");
			}
			try {
				acClick.stop();
			} catch (Exception e) {
				System.err.println("ERROR WHILE MUTED");
			}
			try {
				acExplosion.stop();
			} catch (Exception e) {
				System.err.println("ERROR WHILE MUTED");
			}
			try {
				acLaserSound.stop();
			} catch (Exception e) {
				System.err.println("ERROR WHILE MUTED");
			}
			try {
				acThemeSong.stop();
			} catch (Exception e) {
				System.err.println("ERROR WHILE MUTED");
			}
		} else {
			acThemeSong.loop();
		}
	}
}
