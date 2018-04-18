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
		
	}
	
	//Abstract methods
	abstract int fireDelay();	
}
