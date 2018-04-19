package sonc.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.battle.Ship;
import sonc.shared.SoncException;
import sonc.utils.AgentBuilder;

/**
 * Tests on Player - a participant on SonC games, with nick, password and code
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class PlayerTest extends TestData {
	Player player;
	
	@BeforeClass
	public static void setUpClass() {
		AgentBuilder.addToClassPath(MY_CLASS_PATH); // YOU MAY NEED TO CHANGE THE CLASS PATH!
	}
	
	@Before
	public void setUp() throws Exception {
		player = new Player(NICKS[0],PASSWORDS[0]);
	}

	/**
	 *  Test if instance was created in fixture
	 */
	@Test
	public void testPlayer() {
		assertNotNull(new Player(NICKS[1],PASSWORDS[1]));
	}

	/**
	 * Check getter and setter of property nick
	 */
	@Test
	public void testSetGetNick() {
	
		for(String nick: NICKS) {
			player.setNick(nick);
			assertEquals(nick,player.getNick());
		}
		
	}

	/**
	 * Check getter and setter of property password
	 */
	@Test
	public void testSetGetPassword() {
		for(String nick: NICKS) {
			player.setPassword(nick);
			assertEquals(nick,player.getPassword());
		}
	}

	/**
	 * Check getter and setter of property code
	 */
	@Test
	public void testSetGetCode() {
		assertNull(player.getCode());
		
		player.setCode(EMPTY_SHIP_CODE);
		assertEquals(EMPTY_SHIP_CODE,player.getCode());
	}

	/**
	 * Check exception returned when compiling invalid code
	 * and their absence with valid one.
	 */
	@Test
	public void testCheckCode() {
		
		try {
			player.checkCode();
			fail("Exception expected ");
		} catch (SoncException cause) {
			assertNotNull("No code, exception",cause);
		}
		
		player.setCode(INVALID_SHIP_CODE);
		
		try {
			player.checkCode();
			fail("Exception expected ");
		} catch (SoncException cause) {
			assertNotNull("Invalid code, exception",cause);
		}
	
		player.setCode(EMPTY_SHIP_CODE);
		
		try {
			player.checkCode();
		} catch (SoncException cause) {
			fail("Unexpected exception");
		}
	}

	/**
	 * Check instantiation of a ship
	 */
	@Test
	public void testInstanceShip() {
		Ship ship;
		
		assertNull("No code, no ship",player.instanceShip());
		
		player.setCode(INVALID_SHIP_CODE);
		
		assertNull("Invalid code, no ship",player.instanceShip());
		
		player.setCode(EMPTY_SHIP_CODE);
		
		ship = player.instanceShip();
		
		assertNotNull(ship);
				
		assertEquals(0,ship.getX(),DELTA);
		assertEquals(0,ship.getY(),DELTA);
	}

	/**
	 * Check player authentication with valid and invalid passwords
	 */
	@Test
	public void testAuthenticate() {
		assertTrue(player.authenticate(PASSWORDS[0]));
		
		assertFalse(player.authenticate(PASSWORDS[1]));
		
		player.setPassword(PASSWORDS[1]);
		
		assertTrue(player.authenticate(PASSWORDS[1]));
	}

}
