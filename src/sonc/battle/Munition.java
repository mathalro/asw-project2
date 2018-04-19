package sonc.battle;

import sonc.quad.HasPoint;

public abstract class Munition extends MovingObject implements HasPoint
{
	//Attributes
	private double maxSpeedChange;
	private double maxRotation;
	private Ship origin;
	
	//Getters and setters
	double getMaxSpeedChange()
	{
		return this.maxSpeedChange;
	}	
	double getMaxRotation()
	{
		return this.maxRotation;
	}	
	void setOrigin(Ship origin)
	{
		this.origin = origin;
	}	
	Ship getOrigin()
	{
		return this.origin;
	}
		
	//Constructors
	Munition(int status, double heading, double speed) 
	{
		super(status, heading, speed);
	}
	
	//Methods
	void escape()
	{
		this.setX(this.getX() + World.getCollisionDistance() * Math.cos(getHeading()));
		this.setY(this.getY() + World.getCollisionDistance() * Math.sin(getHeading()));
	}
	
	//Abstract methods
	abstract int fireDelay();
}