package entity;

import java.awt.Polygon;

public interface ICollidable {
	public Polygon getPolygon();
	public int getX();
	public int getY();
	public boolean collideWith(ICollidable obj);
}
