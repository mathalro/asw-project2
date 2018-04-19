package sonc.battle.bots;

import static java.lang.Math.PI;

import sonc.battle.Bullet;
import sonc.battle.Ship;

/**
 * Ship that stalks the nearest enemy while shoting it
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class StalkerBot extends BasicShip {
	final static int CRUISING_SPEED = 10;
	final static int SAFE_DISTANCE = 300;

	@Override
	protected void move() {
		double speed = getSpeed();
		Ship target =  nearest(aliveShips());
		double rotation = normalizeAngle(headingTo(target) - getHeading()); 
		Bullet bullet = new Bullet(headingTo(target));
				
		if(speed < CRUISING_SPEED)
			changeSpeed(CRUISING_SPEED);
		else if(canFire(bullet)) 
			fire(bullet);
		else if(distanceTo(target) < SAFE_DISTANCE  && Math.abs(headingTo(target)) < PI/2)
			rotate(rotation-Math.atan2(SAFE_DISTANCE, distanceTo(target)));
		else 
			rotate(rotation);
	}
	
	@Override
	public String getColor() {
		return "#0000FF";
	}
	
	@Override
	public String getName() {
		return "Stalker";
	}
	
}
