package sonc.battle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import sonc.quad.PointQuadtree;
import sonc.shared.Movie;
import sonc.utils.SafeExecutor;

public class World
{
	//Attributes
	private static int rounds;
	private static double margin;
	private static double width;
	private static double height;
	private static double collisionDistance;
	private int currentRound;
	private PointQuadtree<MovingObject> pointQuadTree; 
	
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
	public int getCurrentRound()
	{
		return this.currentRound;
	}	
	void setCurrentRound(int currentRound)
	{
		this.currentRound = currentRound;
	}
	
	//Constructors
	public World()
	{
		this.pointQuadTree = new PointQuadtree<>(0, 1000, 1000, 0);
	}
		
	void addShipAtRandom(Ship ship)
	{
		ship.setWorld(this);
		ship.setX(Math.random() * World.width);
		ship.setY(Math.random() * World.height);
		ship.setHeading(Math.random() * (2 * Math.PI));
		ship.resetPoints();
		this.addMovingObject(ship);
	}
	
	void addShipAt(Ship ship, double x, double y, double heading)
	{
		ship.setWorld(this);
		ship.setX(x);
		ship.setY(y);
		ship.setHeading(heading);		
		ship.init();
		ship.resetPoints();
		this.addMovingObject(ship);
	}
	
	public Movie battle(List<Ship> ships)
	{
		Movie movie = new Movie();		
		for (Iterator<Ship> iterator = ships.iterator(); iterator.hasNext();)
		{
			try
			{
				Ship ship = iterator.next();
				SafeExecutor.executeSafelly(() -> ship.init());
			} catch (Exception e)
			{
				iterator.remove();
			}
		}		
		for (int round = 1; round <= World.getRounds(); round++)
		{			
			this.setCurrentRound(round);
			this.pointQuadTree = new PointQuadtree<>(0, 1000, 1000, 0);
			for (Ship ship : ships)
				this.addShipAtRandom(ship);									
			while (this.getShips().size() > 1)
			{								
				for (MovingObject movingObject : this.pointQuadTree.getAll())
				{
					try
					{
						SafeExecutor.executeSafelly(() -> movingObject.move());
					} catch (Exception e)
					{
						this.pointQuadTree.delete(movingObject);
					}
				}								
				movie.newFrame();				
				for (MovingObject movingObject : this.pointQuadTree.getAll())
				{					
					int x = ((Double) movingObject.getX()).intValue();
					int y = ((Double) movingObject.getY()).intValue();
					float heading = ((Double) movingObject.getHeading()).floatValue();
					int size = movingObject.getSize();
					String color = movingObject.getColor();					
					movie.addOblong(x, y, heading, size, color);										
					if (movingObject instanceof Ship)
					{
						Ship ship = (Ship) movingObject;						
						String name = ship.getName();
						int points = ship.getPoints();
						int status = ship.getStatus();						
						movie.addScore(name, points, status);						
					}					
				}				
				this.update();
			}
		}
		this.setCurrentRound(0);
		return movie;
	}
	
	void update()
	{
		Set<MovingObject> movingObjects = this.pointQuadTree.getAll();		
		for (Iterator<MovingObject> iterator = movingObjects.iterator(); iterator.hasNext();)
		{				
			MovingObject movingObject = iterator.next();			
			movingObject.updatePosition();			
			double x = movingObject.getX();
			double y = movingObject.getY();
			double collisionDistance = World.getCollisionDistance();
			double worldWidth = World.getWidth();
			double worldHeight = World.getHeight();
			
			if ((x < 0) || (x > worldWidth) || (y < 0) || (y > worldHeight))
			{				
				if (movingObject instanceof Ship)
				{
					Ship currentShip = (Ship) movingObject;
					Set<Ship> otherShips = currentShip.getOtherShips();
					int points =  currentShip.getPoints() / otherShips.size();					
					for (Ship otherShip : otherShips)
						otherShip.addPoints(points);
				}
				iterator.remove();				
			} else
			{					
				Set<MovingObject> nearMovingObjects = this.pointQuadTree.findNear(x, y, collisionDistance);				
				for (MovingObject nearMovingObject : nearMovingObjects)
				{															
					if (movingObject != nearMovingObject)
					{
						movingObject.hitdBy(nearMovingObject);
						if (nearMovingObject instanceof Ship)
							((Ship) nearMovingObject).addPoints(nearMovingObject.getImpactDamage());
						else
							((Munition) nearMovingObject).getOrigin().addPoints(nearMovingObject.getImpactDamage());
					}
				}
				if (movingObject.isDestroyed())
					iterator.remove();								
			}			
		}				
		this.pointQuadTree = new PointQuadtree<>(0, 1000, 1000, 0);
		for (MovingObject movingObject : movingObjects)
			this.pointQuadTree.insert(movingObject);
	}
	
	void addMovingObject(MovingObject added)
	{
		this.pointQuadTree.insert(added);
	}
	
	public Set<Ship> getShips()
	{
		Set<MovingObject> movingObjects = this.pointQuadTree.getAll();
		Set<Ship> ships = new HashSet<>();
		for (MovingObject movingObject : movingObjects)
			if (movingObject instanceof Ship)
				ships.add((Ship) movingObject);
		return ships;
	}
	
	Set<MovingObject> getMovingObjects()
	{
		return this.pointQuadTree.getAll();
	}
}