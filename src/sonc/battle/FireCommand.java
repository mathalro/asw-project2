package sonc.battle;

public class FireCommand implements ShipCommand
{
	//Attributes
	private World world;
	private Ship ship;
	private Munition munition;
	
	//Constructors
	FireCommand(World world, Ship ship, Munition munition)
	{
		this.world = world;
		this.ship = ship;
		this.munition = munition;
	}
	
	//Methods
	public void execute()
	{
		if (this.ship.canFire(this.munition))
		{
			this.ship.setLastFireRound(this.world.getCurrentRound());
			this.munition.setX(this.ship.getX());
			this.munition.setY(this.ship.getY());
			this.munition.setOrigin(this.ship);
			this.munition.escape();
			this.world.addMovingObject(munition);
		}
	}
}