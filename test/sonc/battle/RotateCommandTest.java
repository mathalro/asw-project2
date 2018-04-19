package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.battle.RotateCommand;
import sonc.battle.Ship;
import sonc.battle.World;

/**
 * Test command for rotating ships
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class RotateCommandTest extends TestData {
	public static final double MAX_ROTATION = Math.PI/6;
	
	RotateCommand rotateCommand;
	World world;
	Ship ship;
	
	@BeforeClass
	public static void SetUpClass() {
		Ship.setMaxShipRotation(MAX_ROTATION);
	}
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship();
		world = new World();
		rotateCommand = new RotateCommand(ship,MAX_ROTATION);
	}

	@Test
	public void testRotateCommand() {
		assertNotNull(rotateCommand);
	}

	@Test
	public void testExecute() {
		assertEquals(ANGLES[0],ship.getHeading(),DELTA);
		
		rotateCommand.execute();
		
		assertEquals(MAX_ROTATION,ship.getHeading(),DELTA);
	}

}
