package quad;

import java.util.HashSet;
import java.util.Set;


/**
 * A Trie that has no descendants. This class is the leaf in the composite Pattern.
 * 
 * @author Matheus Rosa
 *
 * @param <T>
 */
public class LeafTrie<T extends HasPoint> extends Trie<T> {

	private HashSet<T> content;
	
	public LeafTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		content = new HashSet<T>();
	}

	@Override
	void collectAll(Set<T> points) {
		for (T it : content) {
			points.add(it);
		}
	}

	@Override
	void collectNear(double x, double y, double radius, Set<T> points) { 
		for (T it : content) {
			if (distance(it.getX(), it.getY(), x, y) <= radius) points.add(it); 
		}
	}

	@Override
	void delete(T point) {
		content.remove(point);
	}

	@Override
	T find(T point) {
		if (content.contains(point)) return point;
		return null;
	}

	@Override
	Trie<T> insert(T point) {
		this.content.add(point);
		if (this.content.size() > Trie.getCapacity()) {
			return this.divideNode(point);
		}
		return this;
	}

	@Override
	Trie<T> insertReplace(T point) {
		while (content.contains(point)) content.remove(point);
		content.add(point);
		if (content.size() > Trie.getCapacity()) {
			return divideNode(point);
		}
		return this;
	}
	
	
	/**
	 * Divide the points in this node between its children
	 * There are four quadrants, so the quadrants are separated by the middle x and middle y of current quadrant
	 *  
	 * @param point
	 */
	private Trie<T> divideNode(T point) {
		// insertReplace(point);
		NodeTrie<T> newNode = new NodeTrie<T>(this.topLeftX, this.topLeftY, this.bottomRightX, this.bottomRightY);
		double midX = this.topLeftX + (this.bottomRightX - this.topLeftX) / 2;
		double midY = this.topLeftY + (this.bottomRightY - this.topLeftY) / 2;
		for (T entry : this.content) {
			double x = entry.getX();
			double y = entry.getY();
			if (x < midX && y < midY) newNode.tries.get(Quadrant.NW).insert(entry); 
			else if (x >= midX && y < midY) newNode.tries.get(Quadrant.NE).insert(entry);
			else if (x < midX && y >= midY) newNode.tries.get(Quadrant.SW).insert(entry);
			else newNode.tries.get(Quadrant.SE).insert(entry);
		}
		content.clear();
		
		return newNode;
	}
	
}
