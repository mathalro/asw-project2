package sonc.battle;

import sonc.quad.HasPoint;

/**
 * A simple munition that moves in a straight line.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public class Bullet extends Munition implements HasPoint {
	private static int damage = 10;
	private static double initialSpeed;
	private static int fireDelay;
	private double maxSpeed;
	private double maxSpeedChange;
	private double maxRotation;
	private int size;
	private String color;

	static void setDamage(int damage) {
		Bullet.damage = damage;
	}

	static int getDamage() {
		return Bullet.damage;
	}

	static void setInitialSpeed(double speed) {
		Bullet.initialSpeed = speed;
	}

	static double getInitialSpeed() {
		return Bullet.initialSpeed;
	}

	static int getFireDelay() {
		return Bullet.fireDelay;
	}

	static void setFireDelay(int fireDelay) {
		Bullet.fireDelay = fireDelay;
	}

	double getMaxSpeed() {
		return this.maxSpeed;
	}

	double getMaxSpeedChange() {
		return this.maxSpeedChange;
	}

	double getMaxRotation() {
		return this.maxRotation;
	}

	int getImpactDamage() {
		return Bullet.damage;
	}

	public int getSize() {
		return this.size;
	}

	public String getColor() {
		return this.color;
	}

	public Bullet(double heading) {
		super(10, heading, Bullet.initialSpeed);
	}

	int fireDelay() {
		return Bullet.fireDelay;
	}
}