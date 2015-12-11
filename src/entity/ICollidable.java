package entity;

import java.awt.Polygon;

public interface ICollidable {
	
	int getX();
	int getY();
	boolean collideWith(ICollidable obj);
	void setCollisionBox(Polygon[][] collisionBox);
	//void setCollisionBox(Polygon p, int act, int frame);
	Polygon getCollisionBox();
	
}
