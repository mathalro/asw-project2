package sonc.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.utils.AgentBuilder;

/**
 * Tests on Players - a collection of Player indexed by nick
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class PlayersTest extends TestData {
	Players players ;
	
	@BeforeClass
	public static void setUpClass() {
		AgentBuilder.addToClassPath(MY_CLASS_PATH); // YOU MAY NEED TO CHANGE THE CLASS PATH!
	}
	
	@Before
	public void setUp() throws Exception {
		players = new Players();
	}

	/**
	 * Test if instance was created in fixture
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(players);
	}

	/**
	 * Check user registration with invalid nicks, duplicate nicks, multiple users
	 */
	@Test
	public void testRegister() {
		assertFalse("Invalid nick",players.register(INVALID_NICK, PASSWORDS[0]));
		assertTrue("Valid nick",players.register(NICKS[0], PASSWORDS[0]));
		assertFalse("Duplicate nick",players.register(NICKS[0], PASSWORDS[0]));
		assertTrue("Valid nick",players.register(NICKS[1], PASSWORDS[1]));
		assertFalse("Duplicate nick",players.register(NICKS[1], PASSWORDS[0]));
	}

	/**
	 * Check password update, with valid password, old password and wrong password
	 */
	@Test
	public void testUpdatePassword() {
		players.register(NICKS[0], PASSWORDS[0]);
		
		assertTrue(players.updatePassword(NICKS[0], PASSWORDS[0], PASSWORDS[1]));
		assertFalse(players.updatePassword(NICKS[0], PASSWORDS[0], PASSWORDS[1]));
		assertTrue(players.updatePassword(NICKS[0], PASSWORDS[1], PASSWORDS[0]));
	}

	/**
	 * Check authentication valid and invalid tokens and multiple users
	 */
	@Test
	public void testAuthenticate() {
		players.register(NICKS[0], PASSWORDS[0]);
		
		assertTrue(players.authenticate(NICKS[0], PASSWORDS[0]));
		assertFalse(players.authenticate(NICKS[0], PASSWORDS[1]));
		assertFalse(players.authenticate(NICKS[1], PASSWORDS[1]));
		assertFalse(players.authenticate(NICKS[1], PASSWORDS[0]));
	}

	/**
	 * Check obtaining a player by nick, when it is available or not,
	 * and with multiple players
	 */
	@Test
	public void testGetPlayer() {
		assertNull(players.getPlayer(NICKS[0]));
		assertNull(players.getPlayer(NICKS[1]));
		
		players.register(NICKS[0], PASSWORDS[0]);
		
		assertNotNull(players.getPlayer(NICKS[0]));
		assertNull(players.getPlayer(NICKS[1]));
		
		players.register(NICKS[1], PASSWORDS[1]);
		
		assertNotNull(players.getPlayer(NICKS[0]));
		assertNotNull(players.getPlayer(NICKS[1]));
	}

	/**
	 * Check getting players names with ships
	 */
	@Test
	public void testGetPlayersNamesWithShips() {
		assertEquals(0,players.getPlayersNamesWithShips().size());
		
		players.register(NICKS[0], PASSWORDS[0]);
		
		assertEquals(0,players.getPlayersNamesWithShips().size());
		
		players.getPlayer(NICKS[0]).setCode(EMPTY_SHIP_CODE);
		
		assertEquals(1,players.getPlayersNamesWithShips().size());
		
		players.register(NICKS[1], PASSWORDS[1]);
		
		assertEquals(1,players.getPlayersNamesWithShips().size());
		
		players.getPlayer(NICKS[1]).setCode(EMPTY_SHIP_CODE);
		
		assertEquals(2,players.getPlayersNamesWithShips().size());
		
	}

}
