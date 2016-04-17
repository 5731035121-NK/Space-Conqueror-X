package entity.enemy;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.PlayerConstant;

public class EnemyGroup implements Cloneable {
	
	public static int MOVE = 1;
	public static int HOLD = 2;

	private ArrayList<Enemy> enemies;
	private ArrayList<Point> position;
	private int direction, numEnemy;
	private boolean looped, ready;
	private int state;
	
	private int shootCounter, shootDelay = 400;
	private int holdPositionCounter, holdDuration = 100;
	private int slowCounter;
	
	public EnemyGroup(ArrayList<Enemy> enemies, ArrayList<Point> position, int moveDuration, boolean looped) {
		super();
		
		this.enemies = enemies;
		this.position = position;
		direction = 1;
		this.looped = looped;
		this.holdDuration = moveDuration;
		
		shootCounter = 0;
		
		numEnemy = 0;
		for (Enemy enemy : enemies) {
			if (enemies != null)
				numEnemy++;
		}
	}
	
	public void update() {
		if (!ready) {
			ready = true;
			// Check ready
			for (Enemy enemy : enemies) {
				if (enemy != null && !enemy.isReady()) {
					ready = false;
					break;
				}
			}
			
			if (ready) {
				state = MOVE;
			}
		} else {
			if (state == MOVE) {
				boolean finishMoving = true;
				for (Enemy enemy : enemies) {
					if (enemy != null && !enemy.isInRightPos()) {
						finishMoving = false;
						break;
					}
				}
				
				// Wait for all enemies to in the right position
				if (finishMoving) {
					state = HOLD;
					holdPositionCounter = holdDuration;
				}
			} else if (state == HOLD) {
				hold();
			}
			
			doShoot();
		}
		
		// Reduce slow
		if (slowCounter > 0) {
			slowCounter--;
			if (slowCounter <= 0) {
				// Clear Slow
				for (Enemy enemy : enemies) {
					if (enemy != null)
						enemy.deSlow();
				}
			}
		}
		// Update enemy
		for (Enemy enemy : enemies) {
			if (enemy != null) {
				enemy.update();
			}
		}
	}
	
	private void hold() {
		if (holdPositionCounter > 0) { 
			holdPositionCounter--;
			return;
		}
		
		state = MOVE;
		// Generate next position to enemies 
		if (looped) {
			ArrayList<Enemy> temp = new ArrayList<Enemy>(enemies);
			for (int i=0; i<enemies.size(); i++) {
				temp.set((i + direction)%enemies.size(), enemies.get(i));
			}
			enemies = temp;
		} else {
			int cantBackward = 0, cantForward = 0;
			for (int i=0; i<enemies.size(); i++) {
				if (enemies.get(i) != null) {
					if (i+direction >= enemies.size() || i+direction < 0) {
						cantForward++;
					}
					if (i-direction >= enemies.size() || i-direction < 0) {
						cantBackward++;
					}
				}
			}
			
			if (cantForward > 0 && cantBackward > 0) {
				// Hold
			} else {
				if (cantForward > 0) {
					// Backward
					direction = -direction;
				}
				
				ArrayList<Enemy> temp = new ArrayList<Enemy>(enemies);
				for (int i=0; i<enemies.size(); i++) {
					temp.set((i + direction + enemies.size())%enemies.size(), enemies.get(i));
				}
				enemies = temp;
			}
		}
		
		// Set speed
		for (int i=0; i<enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			if (enemy != null) {
				enemy.setNextX(position.get(i).x);
				enemy.setNextY(position.get(i).y);
				
				enemy.move();
			}
		}
	}

	private void doShoot() {
		if (shootCounter>0) {
			shootCounter--;
			return;
		}
		
		shootCounter = shootDelay;
		Random rand = new Random();
		Double d = new Double(rand.nextDouble()*142790);
		int x = d.intValue() % numEnemy;
		
		for (int i=0;i<enemies.size();i++) {
			if (enemies.get(i) != null) {
				if (x == 0) {
					enemies.get(i).willAttack();
					break;
				} else {
					x--;
				}
			}
		}
	}

	public void shifGroup(int dx, int dy) {
		for (Point point : position) {
			point.translate(dx, dy);
		}
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void draw(Graphics2D g) {
		for (Enemy enemy : enemies) {
			if (enemy != null) {
				enemy.draw(g);
			}
		}
	}

	public boolean clearDestroyed() {
		numEnemy = 0;
		ArrayList<Enemy> temp = new ArrayList<>();
		for (Enemy enemy : enemies) {
			if (enemy != null) {
				if (enemy.isDestroyed()) {
					temp.add(null);
				} else {
					numEnemy++;
					temp.add(enemy);
				}
			} else {
				temp.add(null);
			}
		}
		
		enemies = temp;
		return numEnemy == 0;
	}

	public void slow(double slow, int slowTime) {
		if (slowCounter > 0)
			return;
		
		slowCounter = PlayerConstant.SLOW_DURATION;
		
		for (Enemy enemy : enemies) {
			if (enemy != null)
				enemy.slow();
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		ArrayList<Enemy> clone = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			if (enemy != null)
				clone.add((Enemy) enemy.clone());
			else 
				clone.add(null);
		}
		ArrayList<Point> clonePos = new ArrayList<>(position);
		return new EnemyGroup(clone, clonePos, holdDuration, looped);
	}
	
}
