package sonc.quad;

import java.util.Set;

/**
 * Abstract class common to all classes implementing the trie structure. Defines
 * methods required by those classes and provides general methods for checking
 * overlaps and computing distances. This class corresponds to the Component in
 * the Composite design pattern.
 * 
 * @author Matheus Rosa
 * @version 1.0
 */
public abstract class Trie<T extends HasPoint> {

	protected double bottomRightX;
	protected double bottomRightY;
	protected double topLeftX;
	protected double topLeftY;
	private static int capacity;

	public void setBottomRightX(double bottomRightX) {
		this.bottomRightX = bottomRightX;
	}

	public double getBottomRightY() {
		return bottomRightY;
	}

	public void setBottomRightY(double bottomRightY) {
		this.bottomRightY = bottomRightY;
	}

	public double getTopLeftX() {
		return topLeftX;
	}

	public void setTopLeftX(double topLeftX) {
		this.topLeftX = topLeftX;
	}

	public double getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(double topLeftY) {
		this.topLeftY = topLeftY;
	}

	public static int getCapacity() {
		return capacity;
	}

	public static void setCapacity(int capacity) {
		Trie.capacity = capacity;
	}

	Trie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		this.bottomRightX = bottomRightX;
		this.bottomRightY = bottomRightY;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
	}

	/**
	 * Collect points in this node and its descendants in given set
	 * 
	 * @param points
	 *            - set of {@link HasPoint} for collecting points
	 */
	abstract void collectAll(Set<T> points);

	/**
	 * Collect points at a distance smaller or equal to radius from (x,y) and place
	 * then in given list
	 * 
	 * @param x
	 *            - coordinate of point
	 * @param y
	 *            - coordinate of point
	 * @param radius
	 *            - from given point
	 * @param points
	 *            - set for collecting points
	 */
	abstract void collectNear(double x, double y, double radius, Set<T> points);

	/**
	 * Delete a given point
	 * 
	 * @param point
	 *            - to delete
	 */
	abstract void delete(T point);

	/**
	 * Find a point with the same coordinate of a given point
	 * 
	 * @param point
	 *            - with requested coordinates
	 * @return recorded point, if found; null otherwise
	 */
	abstract T find(T point);

	/**
	 * Insert a given point
	 * 
	 * @param point
	 *            - to be inserted
	 * @return changed parent node
	 */
	abstract Trie<T> insert(T point);

	/**
	 * Insert a given point and remove existing points in same location
	 * 
	 * @param point
	 *            - point to be inserted
	 * @return changed parent node
	 */
	abstract Trie<T> insertReplace(T point);

	/**
	 * Return the Euclidean distance between two pair of coordinates of given points
	 * 
	 * @param x1
	 *            - x coordinate of first point
	 * @param y1
	 *            - y coordinate of first point
	 * @param x2
	 *            - x coordinate of second point
	 * @param y2
	 *            - y coordinate of second point
	 * @return distance between given points
	 */
	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	/**
	 * Check if this quadrant overlaps with a given circle Test for each segment of
	 * the total quadrant, whether it intersects with the circle or not If any
	 * segment intersects with the circle, then the circle overlaps this quadrant
	 * 
	 * @param x
	 *            - coordinate of circle
	 * @param y
	 *            - coordinate of circle
	 * @param radius
	 *            - of circle
	 * @return true if overlaps and false otherwise
	 */
	boolean overlaps(double x, double y, double radius) {
		Point<Double> p = new Point<Double>(x, y);
		if (inRectangle(p))
			return true;

		Circle<Double> circle = new Circle<Double>(new Point<Double>(x, y), radius);
		if (intersectCircle(new Point<Double>(topLeftX, bottomRightY), new Point<Double>(bottomRightX, bottomRightY),
				circle)
				|| intersectCircle(new Point<Double>(bottomRightX, bottomRightY),
						new Point<Double>(bottomRightX, topLeftY), circle)
				|| intersectCircle(new Point<Double>(topLeftX, topLeftY), new Point<Double>(bottomRightX, topLeftY),
						circle)
				|| intersectCircle(new Point<Double>(topLeftX, bottomRightY), new Point<Double>(topLeftX, topLeftY),
						circle))
			return true;

		return false;
	}

	@Override
	public String toString() {
		return this.toString();
	}

	private boolean inRectangle(Point<Double> p) {
		if (p.getX() >= this.topLeftX && p.getX() <= this.bottomRightX && p.getY() >= this.bottomRightY
				&& p.getY() <= this.topLeftY)
			return true;
		return false;
	}

	private boolean intersectCircle(Point<Double> p1, Point<Double> p2, Circle<Double> circle) {

		double x1 = Math.min(p1.getX(), p2.getX());
		double y1 = Math.min(p1.getY(), p2.getY());
		double x2 = Math.max(p1.getX(), p2.getX());
		double y2 = Math.max(p1.getY(), p2.getY());

		if (x1 > circle.center.getX() + circle.getRadius() || x2 < circle.center.getX() - circle.getRadius()
				|| y1 > circle.center.getY() + circle.getRadius() || y2 < circle.center.getY() - circle.getRadius())
			return false;

		return true;
	}

	static enum Quadrant {
		NE, NW, SE, SW;
	}
}
