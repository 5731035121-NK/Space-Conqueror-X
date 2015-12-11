package utility;

import java.text.ParseException;
import java.util.List;

import entity.enemy.Enemy;

public class GameLoader {

	public static class WaveLoader {
		
		private List<Enemy> enemies;
		
		public WaveLoader(List<Enemy> enemies) {
			this.enemies = enemies;
		}
		/* 
		 * Parse the given string "record"
		 * record format is name:score
		 */
		public WaveLoader(String str) throws ParseException {
			while( str.indexOf(" ")>=0 ) {
				String monster[] = str.split(":");
				int type = Integer.parseInt(monster[0]);
				// 
				if (type == 0) {
					
				}
				str = str.substring(str.indexOf(" ")+1);
			}
		}

		public List<Enemy> getEnemies() {
			return enemies;
		}
		
	}
	
	public static void loadStage(int stage) {
		
	}
	
}
