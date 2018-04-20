package sonc.battle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sonc.quad.HasPoint;

public class Ship extends MovingObject implements HasPoint
{
	//Attributes
	private static int damage;
	private static double maxShipRotation;
	private static double maxShipSpeedChange;
	private static int maxStatus;
	private World world;
	private int lastFireRound;
	private ShipCommand command;
	private int points;
	private double maxSpeed;
	private double maxSpeedChange;
	private double maxRotation;
	private int impactDamage;
	private Ship origin;
	private int size;
	private String color;
	private String name;
	
	//Getters and setters
	static void setDamage(int damage)
	{
		Ship.damage = damage;
	}	
	static int getDamage()
	{
		return Ship.damage;
	}	
	static double getMaxShipRotation()
	{
		return Ship.getMaxShipRotation();
	}	
	static void setMaxShipRotation(double maxShipRotation)
	{
		Ship.maxShipRotation = maxShipRotation;
	}	
	static double getMaxShipSpeedChange()
	{
		return Ship.maxShipSpeedChange;
	}	
	public static void setMaxShipSpeedChange(double maxShipSpeedChange)
	{
		Ship.maxShipSpeedChange = maxShipSpeedChange;
	}	
	static int getMaxStatus()
	{
		return Ship.maxStatus;
	}	
	static void setMaxStatus(int maxStatus)
	{
		Ship.maxStatus = maxStatus;
	}	
	protected World getWorld()
	{
		return this.world;
	}	
	void setWorld(World world)
	{
		this.world = world;
	}	
	protected int getLastFireRound()
	{
		return this.lastFireRound;
	}
	void setLastFireRound(int lastFireRound)
	{
		this.lastFireRound = lastFireRound;
	}	
	ShipCommand getCommand()
	{
		return this.command;
	}	
	void setCommand(ShipCommand command)
	{
		this.command = command; 
	}	
	public int getPoints()
	{
		return this.points;
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
	final int getImpactDamage()
	{
		return this.impactDamage;
	}	
	final Ship getOrigin()
	{
		return this.origin;
	}	
	public final int getSize()
	{
		return this.size;
	}	
	public String getColor()
	{
		return this.color;
	}	
	public String getName()
	{
		return this.name;
	}
	
	//Constructor
	public Ship()
	{
		super(1000, 0, 0);
	}
	
	//Methods
	protected boolean canFire(Munition munition)
	{
		return (this.world.getCurrentRound() - this.lastFireRound) > munition.fireDelay();
	}
	
	void resetPoints()
	{
		this.points = 0;
	}
	
	void addPoints(int points)
	{
		this.points += points;
	}	
	
	void execute()
	{
		if (this.command != null)
			this.command.execute();
	}

	protected final void changeSpeed(double delta)
	{
		this.command = new ChangeSpeedCommand(this, delta);		
	}
	
	protected final void rotate(double delta)
	{
		this.command = new RotateCommand(this, delta);
	}
	
	protected final void fire(Munition munition)
	{
		this.command = new FireCommand(this.world, this, munition);
	}
	
	protected final Set<Ship> getOtherShips()
	{
		Set<Ship> otherShips = new HashSet<>();
		for (Ship ship : this.world.getShips())
			if (ship != this)
				otherShips.add(ship);
		return otherShips;
	}
	
	protected void init()
	{

	}
	
	protected void move()
	{

	}	
}