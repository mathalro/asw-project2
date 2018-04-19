package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;

/**
 * Test command for changing speed
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class ChangeSpeedCommandTest extends TestData {
	private static final int MAX_SPEED_CHANGE=2;
	
	ChangeSpeedCommand changeSpeedCommand;
	Ship ship;
	
	/**
	 * Set max speed change to adjust tests
	 */
	@BeforeClass
	public static void setUpClass() {
		Ship.setMaxShipSpeedChange(MAX_SPEED_CHANGE);	
	}
	
	/**
	 * Create instance of ship as fixture
	 */
	@Before
	public void setUp() {
		ship = new Ship();
	}
	
	/**
	 * Check if fixture was instantiated
	 */
	@Test
	public void testFireCommand() {
		assertNotNull(new ChangeSpeedCommand(ship, MAX_SPEED_CHANGE));
	}

	/**
	 * Check the impact of executing a {@code ChangeSpeedCommand} bellow MAX_SPEED_CHANGE
	 */
	@Test
	public void testExecuteBellowMax() {
		int speed = MAX_SPEED_CHANGE / 2;
		assertEquals("Resting ship",0,ship.getSpeed(),DELTA);
		
		new ChangeSpeedCommand(ship, speed).execute();
		
		assertEquals("Ship in motion",speed,ship.getSpeed(),DELTA);
		
	}
	
	/**
	 * Check the impact of executing a {@code ChangeSpeedCommand} at the MAX_SPEED_CHANGE
	 */
	@Test
	public void testExecuteExatlyMax() {
		int speed = MAX_SPEED_CHANGE;
		assertEquals("Resting ship",0,ship.getSpeed(),DELTA);
		
		new ChangeSpeedCommand(ship, speed).execute();
		
		assertEquals("Ship in motion",MAX_SPEED_CHANGE,ship.getSpeed(),DELTA);
		
	}
	
	/**
	 * Check the impact of executing a {@code ChangeSpeedCommand} above MAX_SPEED_CHANGE
	 */
	@Test
	public void testExecuteAboveMax() {
		int speed = 2* MAX_SPEED_CHANGE;
		assertEquals("Resting ship",0,ship.getSpeed(),DELTA);
		
		new ChangeSpeedCommand(ship, speed).execute();
		
		assertEquals("Ship in motion",MAX_SPEED_CHANGE,ship.getSpeed(),DELTA);
		
	}
}
