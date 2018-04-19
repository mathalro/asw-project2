package sonc.quad;

import java.util.Set;


/**
 * Abstract class common to all classes implementing the trie 
 * structure. Defines methods required by those classes and provides 
 * general methods for checking overlaps and computing distances. 
 * This class corresponds to the Component in the Composite design pattern.
 * 
 * @author Matheus Rosa
 *
 * @param <T>
 */
public abstract class Trie<T extends HasPoint> {
	
	protected double bottomRightX;
	protected double bottomRightY;
	protected double topLeftX;
	protected double topLeftY;
	private static int capacity;

	static enum Quadrant {
		NE,
		NW,
		SE,
		SW;
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
	 */
	abstract void collectAll(Set<T> points);
	
	/**
	 * Collect points at a distance smaller or equal to radius from (x,y) and place then in given list
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @param points
	 */
	abstract void collectNear(double x, double y, double radius, Set<T> points);
	
	
	/**
	 * Delete a given point
	 * 
	 * @param point
	 */
	abstract void delete(T point);
	
	
	/**
	 * Find a point with the same coordinate of a given point
	 * 
	 * @param point
	 * @return
	 */
	abstract T find(T point);
	
	/**
	 * Insert a given point 
	 * 
	 * @param point
	 * @return
	 */
	abstract Trie<T> insert(T point);
	
	/**
	 * Insert a given point and remove existing points in same location
	 * 
	 * @param point
	 * @return
	 */
	abstract Trie<T> insertReplace(T point);
	
	
	/**
	 * Return the Euclidean distance between two pair of coordinates of given points
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	/**
	 * Check if this quadrant overlaps with a given circle
	 * Test for each segment of the total quadrant, whether it intersects with the circle or not 
	 * If any segment intersects with the circle, then the circle overlaps this quadrant
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return
	 */
	boolean overlaps(double x, double y, double radius) {
		Point<Double> p = new Point<Double>(x, y);
		if (inRectangle(p)) return true;
		
		Circle<Double> circle = new Circle<Double>(new Point<Double>(x, y), radius);
		if (intersectCircle(new Point<Double>(topLeftX, bottomRightY), new Point<Double>(bottomRightX, bottomRightY), circle) ||
				intersectCircle(new Point<Double>(bottomRightX, bottomRightY), new Point<Double>(bottomRightX, topLeftY), circle) ||
				intersectCircle(new Point<Double>(topLeftX, topLeftY), new Point<Double>(bottomRightX, topLeftY), circle) ||
				intersectCircle(new Point<Double>(topLeftX, bottomRightY), new Point<Double>(topLeftX, topLeftY), circle)) return true;
		
		return false;
	}
	
	public String toString() {
		return this.toString();
	}
		
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
	
	/**
	 * Verify if a point is inside a rectangle
	 * 
	 * @param p
	 * @return
	 */
	private boolean inRectangle(Point<Double> p) {
		if (p.getX() >= this.topLeftX && p.getX() <= this.bottomRightX &&
				p.getY() >= this.bottomRightY && p.getY() <= this.topLeftY) return true;
		return false;
	}
	
	/**
	 * Verify if a vertical or horizontal segment intersects a circle
	 * 
	 * @param p1
	 * @param p2
	 * @param circle
	 * @return
	 */
	private boolean intersectCircle(Point<Double> p1, Point<Double> p2, Circle<Double> circle) {
		
		double x1 = Math.min(p1.getX(), p2.getX());
		double y1 = Math.min(p1.getY(), p2.getY());
		double x2 = Math.max(p1.getX(), p2.getX());
		double y2 = Math.max(p1.getY(), p2.getY());
		
		if (x1 > circle.center.getX() + circle.getRadius() || 
				x2 < circle.center.getX() - circle.getRadius() ||
					y1 > circle.center.getY() + circle.getRadius() || 
						y2 < circle.center.getY() - circle.getRadius()) 
			return false;
		
		return true;
	}
}
