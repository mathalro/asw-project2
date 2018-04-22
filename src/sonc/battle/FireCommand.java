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
		this.ship.doFire(this.world, this.munition);
	}
}