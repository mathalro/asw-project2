package sonc.battle;

import sonc.quad.HasPoint;

/**
 * Type of missile that that seeks the target
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public class GuidedMissile extends Munition implements HasPoint {
	private static int damage = 600;
	private static double maxMissileRotation;
	private static double initialSpeed;
	private static int fireDelay;
	private double maxSpeed;
	private int size;
	private String color;
	private MovingObject target;

	public static void setDamage(int damage) {
		GuidedMissile.damage = damage;
	}

	public static int getDamage() {
		return GuidedMissile.damage;
	}

	static double getMaxMissileRotation() {
		return GuidedMissile.maxMissileRotation;
	}

	static void setMaxMissileRotation(double maxMissileRotation) {
		GuidedMissile.maxMissileRotation = maxMissileRotation;
	}

	public static void setInitialSpeed(double speed) {
		GuidedMissile.initialSpeed = speed;
	}

	public static double getInitialSpeed() {
		return GuidedMissile.initialSpeed;
	}

	static int getFireDelay() {
		return GuidedMissile.fireDelay;
	}

	static void setFireDelay(int fireDelay) {
		GuidedMissile.fireDelay = fireDelay;
	}

	double getMaxSpeed() {
		return this.maxSpeed;
	}

	double getMaxRotation() {
		return GuidedMissile.maxMissileRotation;
	}

	int getImpactDamage() {
		return GuidedMissile.damage;
	}

	public int getSize() {
		return this.size;
	}

	public String getColor() {
		return this.color;
	}

	GuidedMissile(double heading, MovingObject target) {
		super(600, heading, GuidedMissile.initialSpeed);
		this.target = target;
	}

	void move() {
		double angle = this.headingTo(this.target) - this.getHeading();
		double difference = (2 * Math.PI) - angle;

		if (difference < angle)
			this.doRotate(-difference);
		else
			this.doRotate(angle);
	}

	int fireDelay() {
		return GuidedMissile.fireDelay;
	}
}