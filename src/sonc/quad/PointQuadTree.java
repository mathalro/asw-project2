package quad;

import java.util.HashSet;
import java.util.Set;

/**
 * This class follows the Facade design pattern and presents a presents a single access point to manage quad trees. 
 * It provides methods for inserting, deleting and finding elements implementing HasPoint. 
 * This class corresponds to the Client in the Composite design pattern used in this package.
 * 
 * @author Matheus Rosa
 *
 * @param <T>
 */
public class PointQuadTree<T extends HasPoint> {
	
	Trie<T> root;
	
	public PointQuadTree(double topLeftX,
        double topLeftY,
        double bottomRightX,
        double bottomRightY) {
		root = new LeafTrie<T>(topLeftX, topLeftY, bottomRightX, bottomRightY);
	}
	
	/**
	 * Delete given point from QuadTree, if it exists there
	 * 
	 * @param point
	 */
	public void delete(T point) {
		root.delete(point);
	}
	
	/**
	 * Find a recorded point with the same coordinates of given point
	 * 
	 * @param point
	 * @return
	 */
	public T find(T point) {
		return root.find(point);
	}
	
	/**
	 * Returns a set of points at a distance smaller or equal to radius from point with given coordinates.
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return
	 */
	public Set<T> findNear(double x, double y, double radius) {
		HashSet<T> result = new HashSet<T>();
		root.collectNear(x, y, radius, result);
		return result;
	}
	
	/**
	 * A set with all points in the QuadTree
	 * 
	 * @return
	 */
	public Set<T> getAll() {
		HashSet<T> result = new HashSet<T>();
		root.collectAll(result);
		return result;
	}
	
	/**
	 * Insert given point in the QuadTree
	 * 
	 * @param point
	 */
	void insert(T point) {
		root.insert(point);
	}
	
	/**
	 * Insert point, replacing existing point in the same position
	 * 
	 * @param point
	 */
	void insertReplace(T point) {
		root.insertReplace(point);
	}
}
