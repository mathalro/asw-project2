package sonc.battle;

/**
 * This class integrates the concrete part of the Command design pattern. It
 * defines a rotation executed by the rotate() method, that is delayed until the
 * Ship.move() command is completed. This ensures that a single command is
 * executed per turn.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public class RotateCommand implements ShipCommand {
	private Ship ship;
	private double delta;

	RotateCommand(Ship ship, double delta) {
		this.ship = ship;
		this.delta = delta;
	}

	public void execute() {
		this.ship.doRotate(this.delta);
	}
}