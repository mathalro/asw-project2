package sonc.quad;

/**
 * A class that represent the circle structure, with a point (center) and a radius
 * 
 * @author Matheus Rosa
 *
 * @param <T>
 */
public class Circle<T> {
	Point<T> center;
	double radius;
	
	public Circle(Point<T> center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
	}
	
	public Point<T> getCenter() {
		return center;
	}
	public void setCenter(Point<T> center) {
		this.center = center;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadio(double radius) {
		this.radius = radius;
	}
}
