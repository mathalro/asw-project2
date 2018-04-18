package sonc.battle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class World
{
	//Attributes
	private static int rounds;
	private static double margin;
	private static double width;
	private static double height;
	private static double collisionDistance;
	private Set<Ship> ships;
	private int currentRound;
	private Set<MovingObject> movingObjects;
	
	//Getters and setters
	public static int getRounds()
	{
		return World.rounds;
	}	
	static void setRounds(int rounds)
	{
		World.rounds = rounds;
	}	
	public static double getMargin()
	{
		return World.margin;
	}	
	static void setMargin(double margin)
	{
		World.margin = margin;
	}	
	public static double getWidth()
	{
		return World.width;
	}	
	static void setWidth(double width)
	{
		World.width = width;
	}	
	public static double getHeight()
	{
		return World.height;
	}	
	static void setHeight(double height)
	{
		World.height = height;
	}	
	public static double getCollisionDistance()
	{
		return World.collisionDistance;
	}	
	static void setCollisionDistance(double collisionDistance)
	{
		World.collisionDistance = collisionDistance;
	}	
	public Set<Ship> getShips()
	{
		return this.ships;
	}	
	public int getCurrentRound()
	{
		return this.currentRound;
	}	
	void setCurrentRound(int currentRound)
	{
		this.currentRound = currentRound;
	}
	Set<MovingObject> getMovingObjects()
	{
		return this.movingObjects;
	}
	
	//Constructors
	public World()
	{
		this.ships = new HashSet<>();
		this.movingObjects = new HashSet<>();
	}
		
	void addShipAtRandom(Ship ship)
	{
		
	}
	
	void addShipAt(Ship ship, double x, double y, double heading)
	{
		
	}
		
	public Movie battle(List<Ship> ships)
	{
		
	}
		
	void update()
	{
		
	}
	
	void addMovingObject(MovingObject added)
	{
		
	}	
}