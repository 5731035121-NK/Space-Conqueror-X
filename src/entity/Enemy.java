package entity;

public abstract class Enemy extends Entity {

	public Enemy(int health, int x, int y) {
		super(health, x, y);
	}

	@Override
	public int getZ() {
		return 3;
	}
	
}
