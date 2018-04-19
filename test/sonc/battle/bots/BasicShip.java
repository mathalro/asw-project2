package sonc.battle.bots;

import java.util.HashSet;
import java.util.Set;

import sonc.battle.MovingObject;
import sonc.battle.Ship;

/**
 * Abstract ship with a couple of useful methods
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt }
 */
public abstract class BasicShip extends Ship {

	/**
	 * Find nearest moving object from given list
	 * @param all list of moving objects
	 * @return nearest or {@code null} on empty set
	 * @param <T> moving object specialization
	 */
	<T extends MovingObject> T nearest(Set<T> all) {
		T nearest = null;
		double minimun = Double.MAX_VALUE;
		
		for(T other: all) {
			double distance = distanceTo(other);
			
			if(distance < minimun) {
				minimun = distance;
				nearest = other;
			}
		}
			
		return nearest;
	}
	
	/**
	 * Set of non destroyed ships in current world
	 * @return set of {@link sonc.battle.Ship}
	 */
	Set<Ship> aliveShips() {
		Set<Ship> alive = new HashSet<>();
		
		for(Ship ship: getOtherShips())
			if(! ship.isDestroyed())
				alive.add(ship);
		
		return alive;
	}
}
