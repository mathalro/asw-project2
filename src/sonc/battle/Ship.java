package sonc.battle;

import java.util.HashSet;
import java.util.Set;
import sonc.quad.HasPoint;

/**
 * The class common to all ships. It is meant to be specialized by concrete
 * ships, those submitted by players. Concrete ships are expected to be in the
 * default package (no package declaration) and provide an implementation of the
 * method {@link #move()}. This method is invoked once per round by the game
 * engine (the {@link World}) and will be able to execute actions such as:
 * 
 * <ul>
 * <li>rotate(double) - the ship by given angle in radians</li>
 * <li>changeSpeed(double) - the change the speed of ship and sail around the
 * world</li>
 * <li>fire({@link Munition}) - to fire a munition against other ships</li>
 * </ul>
 * 
 * These methods create instances of the corresponding implementation of the
 * {@link ShipCommand} interface and store them, so that latter on the
 * {@link #execute()} method may be invoked to execute them. With this approach,
 * if the methods in the list above are called more than once, only the last is
 * effectively executed.
 * 
 * <br>
 * <br>
 * Other methods can be override, such as:<br>
 * <br>
 * 
 * <ul>
 * <li>init() - to initialize the ship</li>
 * <li>getName() - to assign a name to the ship</li>
 * <li>getColor() - to assign an HTML color to the ship</li>
 * </ul>
 * 
 * This can be instantiated as it provides default implementations to all
 * methods that can be provided by concrete ships. The default definitions are
 * always empty instruction blocks.
 * 
 * @author Ricardo Giovani
 * @version 1.0
 */
public class Ship extends MovingObject implements HasPoint {
	private static int damage = 1000;
	private static double maxShipRotation;
	private static double maxShipSpeedChange;
	private static int maxStatus;
	private World world;
	private int lastFireRound;
	private ShipCommand command;
	private int points;
	private Ship origin;
	private int size;
	private String color;
	private String name;

	static void setDamage(int damage) {
		Ship.damage = damage;
	}

	static int getDamage() {
		return Ship.damage;
	}

	static double getMaxShipRotation() {
		return Ship.getMaxShipRotation();
	}

	static void setMaxShipRotation(double maxShipRotation) {
		Ship.maxShipRotation = maxShipRotation;
	}

	static double getMaxShipSpeedChange() {
		return Ship.maxShipSpeedChange;
	}

	public static void setMaxShipSpeedChange(double maxShipSpeedChange) {
		Ship.maxShipSpeedChange = maxShipSpeedChange;
	}

	static int getMaxStatus() {
		return Ship.maxStatus;
	}

	static void setMaxStatus(int maxStatus) {
		Ship.maxStatus = maxStatus;
	}

	protected World getWorld() {
		return this.world;
	}

	void setWorld(World world) {
		this.world = world;
	}

	protected int getLastFireRound() {
		return this.lastFireRound;
	}

	void setLastFireRound(int lastFireRound) {
		this.lastFireRound = lastFireRound;
	}

	ShipCommand getCommand() {
		return this.command;
	}

	void setCommand(ShipCommand command) {
		this.command = command;
	}

	public int getPoints() {
		return this.points;
	}

	double getMaxSpeed() {
		return Ship.maxShipSpeedChange;
	}

	double getMaxSpeedChange() {
		return Ship.maxShipSpeedChange;
	}

	double getMaxRotation() {
		return Ship.maxShipRotation;
	}

	final int getImpactDamage() {
		return Ship.damage;
	}

	final Ship getOrigin() {
		return this.origin;
	}

	public final int getSize() {
		return this.size;
	}

	public String getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
	}

	public Ship() {
		super(1000, 0, 0);
		this.color = "";
		this.size = 5;
		this.lastFireRound = Integer.MIN_VALUE;
		this.setHeading(0);
	}

	/**
	 * Check if this ship can fire the given munition.
	 * 
	 * @param munition
	 *            - to be fired
	 * @return true if munition can be fired or false otherwise
	 */
	protected boolean canFire(Munition munition) {
		return (this.lastFireRound == Integer.MIN_VALUE)
				|| (this.world.getCurrentRound() - this.lastFireRound) > munition.fireDelay();
	}

	/**
	 * Reset points of this ship to zero
	 */
	void resetPoints() {
		this.points = 0;
	}

	/**
	 * Add given points to this ship
	 * 
	 * @param points
	 *            - to add to this ship
	 */
	void addPoints(int points) {
		this.points += points;
	}

	/**
	 * Execute the latest command defined by the concrete ship. This method should
	 * only be invoked from a World instance and cannot be invoked by concrete
	 * ships.
	 */
	void execute() {
		if (this.command != null)
			this.command.execute();
	}

	/**
	 * Change the speed of this ship by given delta. This command will be effective
	 * only if it is the last executed in a round
	 * 
	 * @param delta
	 *            - variation of speed
	 */
	protected final void changeSpeed(double delta) {
		this.command = new ChangeSpeedCommand(this, delta);
	}

	/**
	 * Rotate the ship by given angle. This command will be effective only if it is
	 * the last executed in a round. This command cannot be override by concrete
	 * ships.
	 * 
	 * @param delta
	 *            - the rotation angle
	 */
	protected final void rotate(double delta) {
		this.command = new RotateCommand(this, delta);
	}

	/**
	 * Fire a munition given as parameter start in current position (e.g. {code
	 * fire(new Bullet(headingTo(enemy)))}. This command will be effective only if
	 * it is the last executed in a round. This command cannot be override by
	 * concrete ships.
	 * 
	 * @param munition
	 *            - to be fired from ship
	 */
	protected final void fire(Munition munition) {
		this.command = new FireCommand(this.world, this, munition);
	}

	protected final Set<Ship> getOtherShips() {
		Set<Ship> otherShips = new HashSet<>();
		for (Ship ship : this.world.getShips())
			if (ship != this)
				otherShips.add(ship);
		return otherShips;
	}

	/**
	 * Create a new munition and add it to the world
	 * 
	 * @param world
	 *            - where the munition is added
	 * @param munition
	 *            - to be added
	 */
	final void doFire(World world, Munition munition) {
		if (canFire(munition)) {
			setLastFireRound(world.getCurrentRound());
			munition.setX(getX());
			munition.setY(getY());
			munition.setOrigin(this);
			munition.escape();
			world.addMovingObject(munition);
		}
	}

	/**
	 * Initialize you ship. This method is called when the ship starts sailing. Use
	 * this method
	 */
	protected void init() {

	}

	protected void move() {

	}
}