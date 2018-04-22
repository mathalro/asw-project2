package sonc.quad;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A Trie that has no descendants. This class is the leaf in the composite
 * Pattern.
 * 
 * @author Matheus Rosa
 * @version 1.0
 */
public class LeafTrie<T extends HasPoint> extends Trie<T> {

	private ArrayList<T> content;

	public LeafTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		content = new ArrayList<T>();
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
			if (getDistance(it.getX(), it.getY(), x, y) <= radius)
				points.add(it);
		}
	}

	@Override
	void delete(T point) {
		content.remove(point);
	}

	@Override
	T find(T point) {
		if (content.contains(point))
			return point;
		return null;
	}

	@Override
	Trie<T> insert(T point) {
		if (isOnBoundary(point)) {
			this.content.add(point);
			if (this.content.size() > Trie.getCapacity()) {
				return this.divideNode(point);
			}
			return this;
		}
		throw new PointOutOfBoundException();
	}

	@Override
	Trie<T> insertReplace(T point) {
		ArrayList<T> newContent = new ArrayList<T>();
		for (T it : content) {
			if (it.getX() != point.getX() || it.getY() != it.getY()) {
				newContent.add(it);
			}
		}
		content.clear();
		content = newContent;
		content.add(point);
		if (content.size() > Trie.getCapacity()) {
			return divideNode(point);
		}
		return this;
	}

	private Trie<T> divideNode(T point) {
		NodeTrie<T> newNode = new NodeTrie<T>(this.topLeftX, this.topLeftY, this.bottomRightX, this.bottomRightY);
		double midX = this.topLeftX + (this.bottomRightX - this.topLeftX) / 2.0;
		double midY = this.bottomRightY + (this.topLeftY - this.bottomRightY) / 2.0;

		for (T entry : this.content) {
			double x = entry.getX();
			double y = entry.getY();
			if (x < midX && y > midY) {
				newNode.tries.put(Quadrant.NW, newNode.tries.get(Quadrant.NW).insert(entry));
				Set<T> tmp = new HashSet<T>();
				newNode.tries.get(Quadrant.NW).collectAll(tmp);
			} else if (x >= midX && y > midY) {
				newNode.tries.put(Quadrant.NE, newNode.tries.get(Quadrant.NE).insert(entry));
				Set<T> tmp = new HashSet<T>();
				newNode.tries.get(Quadrant.NE).collectAll(tmp);
			} else if (x < midX && y <= midY) {
				newNode.tries.put(Quadrant.SW, newNode.tries.get(Quadrant.SW).insert(entry));
				Set<T> tmp = new HashSet<T>();
				newNode.tries.get(Quadrant.SW).collectAll(tmp);
			} else {
				newNode.tries.put(Quadrant.SE, newNode.tries.get(Quadrant.SE).insert(entry));
				Set<T> tmp = new HashSet<T>();
				newNode.tries.get(Quadrant.SE).collectAll(tmp);
			}
		}
		content.clear();

		return newNode;
	}

	private boolean isOnBoundary(T point) {
		double x = point.getX();
		double y = point.getY();
		return !(y < this.bottomRightY || y > this.topLeftY || x < this.topLeftX || x > this.bottomRightX);
	}
}