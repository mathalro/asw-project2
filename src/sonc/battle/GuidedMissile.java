package sonc.battle;

import sonc.quad.HasPoint;

public class GuidedMissile extends Munition implements HasPoint
{
	//Attributes
	private static int damage;
	private static double maxMissileRotation;
	private static double initialSpeed;
	private static int fireDelay;
	private double maxSpeed;
	private double maxRotation;
	private int impactDamage;
	private int size;
	private String color;
	private MovingObject target; 
	
	//Getters and setters
	public static void setDamage(int damage)
	{
		GuidedMissile.damage = damage;
	}	
	public static int getDamage()
	{
		return GuidedMissile.damage;
	}	
	static double getMaxMissileRotation()
	{
		return GuidedMissile.maxMissileRotation;
	}	
	static void setMaxMissileRotation(double maxMissileRotation)
	{
		GuidedMissile.maxMissileRotation = maxMissileRotation;
	}	
	public static void setInitialSpeed(double speed)
	{
		GuidedMissile.initialSpeed = speed;
	}	
	public static double getInitialSpeed()
	{
		return GuidedMissile.initialSpeed;
	}	
	static int getFireDelay()
	{
		return GuidedMissile.fireDelay;
	}	
	static void setFireDelay(int fireDelay)
	{
		GuidedMissile.fireDelay = fireDelay;
	}	
	double getMaxSpeed()
	{
		return this.maxSpeed;
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
	GuidedMissile(double heading, MovingObject target)
	{
		super(600, heading, GuidedMissile.initialSpeed);
		this.target = target;
	}
	
	void move()
	{
		
	}
	
	int fireDelay()
	{
		return 0;
	}	
}