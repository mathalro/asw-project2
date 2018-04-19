package sonc.battle;

public class RotateCommand implements ShipCommand
{
	//Attributes
	private Ship ship;
	private double delta;
	
	//Constructors
	RotateCommand(Ship ship, double delta)
	{
		this.ship = ship;
		this.delta = delta;
	}
	
	//Methods
	public void execute()
	{
		this.ship.doRotate(this.delta);
	}
}