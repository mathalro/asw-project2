package sonc.battle;

/**
 * This class integrates the concrete part of the Command design pattern. It
 * defines the firing of a munition executed by the fire() method, that is
 * delayed until the Ship.move() command is completed. This ensures that a
 * single command is executed per turn. The execute command in this class is
 * responsible to enforce a delay between consecutive firing from the same ship.
 * The delay between consecutive firing from the same ship must be superior the
 * munition Munition.fireDelay()
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public class FireCommand implements ShipCommand {
	private World world;
	private Ship ship;
	private Munition munition;

	FireCommand(World world, Ship ship, Munition munition) {
		this.world = world;
		this.ship = ship;
		this.munition = munition;
	}

	public void execute() {
		this.ship.doFire(this.world, this.munition);
	}
}