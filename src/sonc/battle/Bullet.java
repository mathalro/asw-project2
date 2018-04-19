package sonc.battle;

import sonc.quad.HasPoint;

public class Bullet extends Munition implements HasPoint
{
	//Attributes
	private static int damage;
	private static double initialSpeed;
	private static int fireDelay;
	private double maxSpeed;
	private double maxSpeedChange;
	private double maxRotation;
	private int impactDamage;
	private int size;
	private String color;
		
	//Getters and setters
	static void setDamage(int damage)
	{
		Bullet.damage = damage;
	}	
	static int getDamage()
	{
		return Bullet.damage;
	}	
	static void setInitialSpeed(double speed)
	{
		Bullet.initialSpeed = speed;
	}	
	static double getInitialSpeed()
	{
		return Bullet.initialSpeed;
	}	
	static int getFireDelay()
	{
		return Bullet.fireDelay;
	}	
	static void setFireDelay(int fireDelay)
	{
		Bullet.fireDelay = fireDelay;
	}	
	double getMaxSpeed()
	{
		return this.maxSpeed;
	}	
	double getMaxSpeedChange()
	{
		return this.maxSpeedChange;
	}	
	double getMaxRotation()
	{
		return this.maxRotation;
	}	
	int getImpactDamage()
	{
		return this.impactDamage;
	}	
	public int getSize()
	{
		return this.size;
	}	
	public String getColor()
	{
		return this.color;
	}
		
	//Constructors
	public Bullet(double heading)
	{
		super(10, heading, Bullet.initialSpeed);
	}
	
	//Methods
	int fireDelay()
	{
		return 0;
	}
}