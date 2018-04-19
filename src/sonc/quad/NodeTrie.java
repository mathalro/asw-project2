package quad;

import java.util.Set;


/**
 * Trie with 4 sub tries with equal dimensions covering all its area. 
 * This class corresponds to the Composite in the Composite design pattern.
 * 
 * @author Matheus Rosa
 *
 * @param <T>
 */
public class NodeTrie<T extends HasPoint> extends Trie<T>{

	protected NodeTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
	}

	@Override
	void collectAll(Set<T> points) {
		// NW.collectAll(points);
		// NE.collectAll(points);
		// SW.collectAll(points);
		// SW.collectAll(points);
	}
	
	@Override
	void collectNear(double x, double y, double radius, Set<T> points) {
		// test intersection and call collectNear for each quadrant
		// if (NW.overlaps(x, y, radius)) NW.collectNear(x, y, radius, points);
		// if (NE.overlaps(x, y, radius)) NE.collectNear(x, y, radius, points);
		// if (SW.overlaps(x, y, radius)) SW.collectNear(x, y, radius, points);
		// if (SE.overlaps(x, y, radius)) SE.collectNear(x, y, radius, points);
	}

	@Override
	void delete(T point) {
		// quadrantOf(point).delete();
	}

	@Override
	T find(T point) {
		// return quadrantOf(point).find(point);
		return null;
	}

	@Override
	Trie<T> insert(T point) {
		// quadrantOf(point).insert(point);
		return this;
	}

	@Override
	Trie<T> insertReplace(T point) {
		// quadrantOf(point).insertReplace(point);
		return this;
	}
	
	void quadrantOf(T point) {
		double midX = this.topLeftX + (this.bottomRightX - this.topLeftX) / 2;
		double midY = this.topLeftY + (this.bottomRightY - this.topLeftY) / 2;
		double x = point.getX();
		double y = point.getY();
		
		if (x < midX && y < midY); 					// return the quadrant NW
		else if (x >= midX && y < midY);			// return the quadrant NE
		else if (x < midX && y >= midY);			// return the quadrant SW
		else;										// return the quadrant SE
		
	}
	
	public String toString() {
		return "";
	}
}
