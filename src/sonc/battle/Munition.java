package sonc.battle;

import sonc.quad.HasPoint;

/**
 * Common class to all munitions in the game, including bullets and guided
 * missiles
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public abstract class Munition extends MovingObject implements HasPoint {
	private double maxSpeedChange;
	private double maxRotation;
	private Ship origin;

	double getMaxSpeedChange() {
		return this.maxSpeedChange;
	}

	double getMaxRotation() {
		return this.maxRotation;
	}

	void setOrigin(Ship origin) {
		this.origin = origin;
	}

	Ship getOrigin() {
		return this.origin;
	}

	Munition(int status, double heading, double speed) {
		super(status, heading, speed);
	}

	/**
	 * Initial movement from its origin, to avoid being considered as hitting it
	 */
	void escape() {
		this.setX(this.getX() + World.getCollisionDistance() * Math.cos(getHeading()));
		this.setY(this.getY() + World.getCollisionDistance() * Math.sin(getHeading()));
	}

	/**
	 * Number of rounds a ship must wait to fire this munition since it fired the
	 * last time
	 * 
	 * @return delay in number of rounds
	 */
	abstract int fireDelay();
}