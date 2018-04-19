package sonc.battle;

import sonc.quad.HasPoint;

public abstract class MovingObject implements HasPoint
{
	//Attributes
	private double x;
	private double y;
	private double heading;
	private double speed;
	private int status;
	
	//Getters and setters
	public double getX()
	{
		return this.x;
	}	
	void setX(double x)
	{
		this.x = x;
	}	
	public double getY()
	{
		return this.y;
	}	
	void setY(double y)
	{
		this.y = y;
	}	
	public double getHeading()
	{
		return this.heading;
	}	
	void setHeading(double heading)
	{
		this.heading = heading;
	}	
	public double getSpeed()
	{
		return this.speed;
	}
	public int getStatus()
	{
		return this.status;
	}
	
	//Constructors
	MovingObject(int status, double heading, double speed)
	{
		this.status = status;
		this.heading = heading;
		this.speed = speed;
	}
		
	//Methods
	protected double normalizeAngle(double angle)
	{
		
	}
	
	protected double distanceTo(MovingObject other)
	{
		double catetoX = Math.abs(this.x - other.getX());
		double catetoY = Math.abs(this.y - other.getY());
		return Math.sqrt(Math.pow(catetoX, 2) + Math.pow(catetoY, 2));
	}
	
	protected double headingTo(MovingObject other)
	{
		
	}
	
	final void updatePosition()
	{
		
	}
	
	final void doRotate(double delta)
	{
		
	}
	
	final void doChangeSpeed(double delta)
	{
		
	}
	
	void move()
	{
		
	}
	
	void hitdBy(MovingObject moving)
	{
		this.status -= moving.getImpactDamage();
	}
	
	public boolean isDestroyed()
	{
		return (this.status <= 0);
	}
	
	//Abstract methods
	abstract double getMaxSpeed();
	
	abstract double getMaxSpeedChange();
	
	abstract double getMaxRotation();
	
	abstract int getImpactDamage();
	
	abstract Ship getOrigin();
	
	public abstract int getSize();

	public abstract String getColor();
}
