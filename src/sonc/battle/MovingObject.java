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
		this.heading = this.normalizeAngle(heading);
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
		this.heading = this.normalizeAngle(heading);
		this.speed = speed;
	}
		
	//Methods
	protected double normalizeAngle(double angle)
	{
		double mod = angle % (2 * Math.PI);
		return (mod >= 0) ? mod : (2 * Math.PI) + mod;
	}
	
	protected double distanceTo(MovingObject other)
	{
		double catetoX = Math.abs(this.x - other.getX());
		double catetoY = Math.abs(this.y - other.getY());
		return Math.sqrt(Math.pow(catetoX, 2) + Math.pow(catetoY, 2));
	}
	
	protected double headingTo(MovingObject other)
	{
		double heading = Math.atan2(other.y - this.y, other.x - this.x) - this.heading;
		return (heading >= 0) ? heading : heading + (2 * Math.PI);
	}
	
	final void updatePosition()
	{
		this.x += this.speed * Math.cos(this.heading);
		this.y += this.speed * Math.sin(this.heading);
	}
	
	final void doRotate(double delta)
	{
		delta = Math.min(delta, this.getMaxRotation());
		delta = Math.max(delta, -this.getMaxRotation());
		this.heading = this.normalizeAngle(this.heading + delta);		
	}
	
	final void doChangeSpeed(double delta)
	{	
		delta = Math.min(delta, this.getMaxSpeedChange());
		delta = Math.max(delta, -this.getMaxSpeedChange());		
		double speed = this.speed + delta;		
		speed = Math.min(speed, this.getMaxSpeed());
		speed = Math.max(speed, -this.getMaxSpeed());		
		this.speed = speed;		
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