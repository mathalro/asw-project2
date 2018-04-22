package sonc.battle;

/**
 * This class integrates the concrete part of the Command design pattern. It
 * defines a change in speed of the ship executed by the changeSpeed() method,
 * that is delayed until the MovingObject.move() command is completed. This
 * ensures that a single command is executed per turn.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public class ChangeSpeedCommand implements ShipCommand {
	private Ship ship;
	private double delta;

	ChangeSpeedCommand(Ship ship, double delta) {
		this.ship = ship;
		this.delta = delta;
	}

	public void execute() {
		this.ship.doChangeSpeed(this.delta);
	}
}
