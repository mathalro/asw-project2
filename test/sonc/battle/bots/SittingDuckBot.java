package sonc.battle.bots;

import sonc.battle.Ship;

/**
 * Ship that neither moves nor responds to attacks 
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class SittingDuckBot extends Ship {

	@Override
	public String getColor() {
		return "#FFFFFF";
	}
	
	@Override
	public String getName() {
		return "Sitting Duck";
	}
}
