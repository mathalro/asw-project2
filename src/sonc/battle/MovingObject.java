package sonc.battle;

import sonc.quad.HasPoint;

/**
 * Common class to all moving objects in the game, including ships and the munitions they throw at each other
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public abstract class MovingObject implements HasPoint {
	private double x;
	private double y;
	private double heading;
	private double speed;
	private int status;

	public double getX() {
		return this.x;
	}

	void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	void setY(double y) {
		this.y = y;
	}

	public double getHeading() {
		return this.heading;
	}

	void setHeading(double heading) {
		this.heading = this.normalizeAngle(heading);
	}

	public double getSpeed() {
		return this.speed;
	}

	public int getStatus() {
		return this.status;
	}

	MovingObject(int status, double heading, double speed) {
		this.status = status;
		this.heading = this.normalizeAngle(heading);
		this.speed = speed;
	}

	/**
	 * Normalize angles in range [0,2*PI[ in radians. The value is added, or subtracted 2xPI, 
	 * respectively while it is less than 0, or greater or equal than 2xPI. 
	 * The method is available to concrete ships
	 * 
	 * @param angle - to normalize
	 * @return normalized angle in range [0,2*PI[ 
	 */
	protected double normalizeAngle(double angle) {
		double mod = angle % (2 * Math.PI);
		return (mod >= 0) ? mod : (2 * Math.PI) + mod;
	}

	/**
	 * Distance from this moving object to another given as parameter
	 * 
	 * @param moving object
	 * @return distance to the other
	 */
	protected double distanceTo(MovingObject other) {
		double catetoX = Math.abs(this.x - other.getX());
		double catetoY = Math.abs(this.y - other.getY());
		return Math.sqrt(Math.pow(catetoX, 2) + Math.pow(catetoY, 2));
	}

	/**
	 * Angle from this moving object to another given as parameter. 
	 * Angles are in radians in the range [0,2*PI: 0 is right, PI/2 is down, PI is left and 3/2*PI is up
	 * 
	 * @param moving object
	 * @return angle to other object or NaN if some coordinates is not defined
	 */
	protected double headingTo(MovingObject other) {
		double heading = Math.atan2(other.y - this.y, other.x - this.x); // - this.heading;
		return (heading >= 0) ? heading : heading + (2 * Math.PI);
	}

	/**
	 * Update the position - (x,y) coordinates - of this moving object 
	 * taking in consideration the current speed and heading. 
	 * This method cannot be invoked by a concrete ship.
	 */
	final void updatePosition() {
		this.x += this.speed * Math.cos(this.heading);
		this.y += this.speed * Math.sin(this.heading);
	}

	/**
	 * Change heading of this moving object by given variation. 
	 * Positive variation correspond to clockwise rotations and negative variations 
	 * to counterclockwise rotations. If the absolute value of variation exceeds the predefined 
	 * maximum rotation than it is limited to that value (with the corresponding signal). 
	 * This method cannot be invoked by a concrete ship.
	 * 
	 * @param delta - angle in radians
	 */
	final void doRotate(double delta) {
		delta = Math.min(delta, this.getMaxRotation());
		delta = Math.max(delta, -this.getMaxRotation());
		this.heading = this.normalizeAngle(this.heading + delta);
	}

	/**
	 * Change speed of this moving object. Positive values increase the speed and negative values decrease it. 
	 * If either the absolute value of variation, or the absolute value of the changed speed, 
	 * exceeds their respective predefined maximums (getMaxSpeedChange() and getMaxSpeed() then they are 
	 * limited to that value (with the corresponding signal). This method cannot be invoked by a concrete ship.
	 * 
	 * @param delta - speed variation
	 */
	final void doChangeSpeed(double delta) {
		delta = Math.min(delta, this.getMaxSpeedChange());
		delta = Math.max(delta, -this.getMaxSpeedChange());
		double speed = this.speed + delta;
		speed = Math.min(speed, this.getMaxSpeed());
		speed = Math.max(speed, -this.getMaxSpeed());
		this.speed = speed;
	}

	/**
	 * Override this method to define the movement of this object. 
	 * Concrete ships will need to do it to implement their strategies.
	 */
	void move() {

	}

	/**
	 * Change status to reflect damage inflicted by given moving object
	 * 
	 * @param moving - object that hit this one
	 */
	void hitdBy(MovingObject moving) {
		this.status -= moving.getImpactDamage();
	}

	/**
	 * Check if this moving object was destroyed
	 * 
	 * @return true is this object is destroyed, false otherwise
	 */
	public boolean isDestroyed() {
		return (this.status <= 0);
	}

	abstract double getMaxSpeed();

	abstract double getMaxSpeedChange();

	abstract double getMaxRotation();

	abstract int getImpactDamage();

	abstract Ship getOrigin();

	public abstract int getSize();

	public abstract String getColor();
}