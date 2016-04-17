package entity;

import java.awt.Polygon;

public interface ICollidable {
	
	double getX();
	double getY();
	boolean collideWith(ICollidable obj);
	void setCollisionBox(Polygon[][] collisionBox);
	//void setCollisionBox(Polygon p, int act, int frame);
	Polygon getCollisionBox();
	
}
