package sonc.battle;

public class ChangeSpeedCommand implements ShipCommand
{
	//Attributes
	private Ship ship;
	private double delta;
	
	//Constructors
	ChangeSpeedCommand(Ship ship, double delta)
	{
		this.ship = ship;
		this.delta = delta;
	}
	
	//Methods
	public void execute()
	{
		this.ship.doChangeSpeed(this.delta);
	}
}
