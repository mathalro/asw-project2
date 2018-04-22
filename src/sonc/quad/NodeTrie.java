package sonc.quad;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Trie with 4 sub tries with equal dimensions covering all its area. This class
 * corresponds to the Composite in the Composite design pattern.
 * 
 * @author Matheus Rosa
 * @version 1.0
 */
public class NodeTrie<T extends HasPoint> extends Trie<T> {

	Map<Trie.Quadrant, Trie<T>> tries;

	protected NodeTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		tries = new HashMap<>();
		double midX = this.topLeftX + (this.bottomRightX - this.topLeftX) / 2.0;
		double midY = this.bottomRightY + (this.topLeftY - this.bottomRightY) / 2.0;

		LeafTrie<T> nw = new LeafTrie<T>(topLeftX, topLeftY, midX, midY);
		this.tries.put(Quadrant.NW, nw);
		LeafTrie<T> ne = new LeafTrie<T>(midX, topLeftY, bottomRightX, midY);
		this.tries.put(Quadrant.NE, ne);
		LeafTrie<T> sw = new LeafTrie<T>(topLeftX, midY, midX, bottomRightY);
		this.tries.put(Quadrant.SW, sw);
		LeafTrie<T> se = new LeafTrie<T>(midX, midY, bottomRightX, bottomRightY);
		this.tries.put(Quadrant.SE, se);
	}

	@Override
	void collectAll(Set<T> points) {
		tries.get(Trie.Quadrant.NW).collectAll(points);
		tries.get(Trie.Quadrant.NE).collectAll(points);
		tries.get(Trie.Quadrant.SW).collectAll(points);
		tries.get(Trie.Quadrant.SE).collectAll(points);
	}

	@Override
	void collectNear(double x, double y, double radius, Set<T> points) {
		// test intersection and call collectNear for each quadrant
		if (tries.get(Trie.Quadrant.NW).overlaps(x, y, radius)) {
			tries.get(Trie.Quadrant.NW).collectNear(x, y, radius, points);
		}
		if (tries.get(Trie.Quadrant.NE).overlaps(x, y, radius)) {
			tries.get(Trie.Quadrant.NE).collectNear(x, y, radius, points);
		}
		if (tries.get(Trie.Quadrant.SW).overlaps(x, y, radius)) {
			tries.get(Trie.Quadrant.SW).collectNear(x, y, radius, points);
		}
		if (tries.get(Trie.Quadrant.SE).overlaps(x, y, radius)) {
			tries.get(Trie.Quadrant.SE).collectNear(x, y, radius, points);
		}
	}

	@Override
	void delete(T point) {
		tries.get(quadrantOf(point)).delete(point);
	}

	@Override
	T find(T point) {
		return tries.get(quadrantOf(point)).find(point);
	}

	@Override
	Trie<T> insert(T point) {
		Trie.Quadrant quadrant = quadrantOf(point);
		tries.put(quadrant, tries.get(quadrant).insert(point));
		return this;
	}

	@Override
	Trie<T> insertReplace(T point) {
		Trie.Quadrant quadrant = quadrantOf(point);
		tries.put(quadrant, tries.get(quadrant).insertReplace(point));
		return this;
	}

	@Override
	public String toString() {
		return "Node Trie";
	}
	
	/**
	 * Checks the quadrant of a given point.
	 * 
	 * @param point - to be checked
	 * @return Trie.Quadrant - the quadrant of this point
	 */
	Trie.Quadrant quadrantOf(T point) {
		double midX = this.topLeftX + (this.bottomRightX - this.topLeftX) / 2;
		double midY = this.bottomRightY + (this.topLeftY - this.bottomRightY) / 2;
		double x = point.getX();
		double y = point.getY();

		if (x < midX && y > midY)
			return Trie.Quadrant.NW;
		else if (x >= midX && y > midY)
			return Trie.Quadrant.NE;
		else if (x < midX && y <= midY)
			return Trie.Quadrant.SW;
		else if (x >= midX && y <= midY)
			return Trie.Quadrant.SE;

		throw new PointOutOfBoundException();
	}
}
