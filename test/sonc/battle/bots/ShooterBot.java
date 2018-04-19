package sonc.battle.bots;

import sonc.battle.Bullet;
import sonc.battle.Ship;

/**
 * Stationary ships that shoots to the nearest enemy
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class ShooterBot extends BasicShip {
	
	@Override
	protected void move() {
		Ship target =  nearest(aliveShips());
		
		if(target != null) {
			Bullet bullet = new Bullet(headingTo(target));
				
			if(canFire(bullet)) 
				fire(bullet);
		}
	}
	
	@Override
	public String getColor() {
		return "#00FF00";
	}
	
	@Override
	public String getName() {
		return "Shooter";
	}
}
