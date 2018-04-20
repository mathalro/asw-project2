package sonc.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sonc.TestData;
import sonc.shared.Movie;
import sonc.shared.Movie.Frame;
import sonc.shared.SoncException;

/**
 * Template for a test class on Manager - YOU NEED TO IMPLEMENTS THESE TESTS!
 * 
 */
public class ManagerTest extends TestData {
	static Manager manager;
	
	@BeforeClass
	public static void setUpClass() throws SoncException {
		manager = Manager.getInstance();
		Manager.setPlayersFile(new File("C:\\users.ser"));
	}
	
	@Before
	public void setUp() throws Exception {
		manager.reset();
	}

	/**
	 * Check user registration with invalid nicks, duplicate nicks, multiple users
	 * 
	 * @throws SoncException on reading serialization file (not tested)
	 */
	@Test
	public void testRegister() throws SoncException {
		
		assertFalse(manager.register(INVALID_NICK, PASSWORDS[0]));		
		assertFalse(manager.register(NICKS[0], ""));
		
		for (int i = 0; i < NICKS.length; i++) {
			assertTrue(manager.register(NICKS[i], PASSWORDS[i]));
			assertFalse(manager.register(NICKS[i], PASSWORDS[i]));
		}
	}
	
	/**
	 * Check password update, with valid password, old password and wrong password
	 *    
	 * @throws SoncException on reading serialization file (not tested)
	 */
	@Test
	public void testUpdatePassword() throws SoncException {
				
		assertFalse(manager.updatePassword(NICKS[0], PASSWORDS[0], PASSWORDS[1]));		
		manager.register(NICKS[0], PASSWORDS[0]);		
		assertTrue(manager.updatePassword(NICKS[0], PASSWORDS[0], PASSWORDS[1]));				
		assertFalse(manager.updatePassword(NICKS[0], PASSWORDS[0], PASSWORDS[1]));		
		assertFalse(manager.updatePassword(NICKS[0], PASSWORDS[1], ""));					
	}

	/**
	 * Check authentication valid and invalid tokens and multiple users
	 * 
	 * @throws SoncException on reading serialization file (not tested)
	 */
	@Test
	public void testAuthenticate() throws SoncException {
		
		for (int i = 0; i < NICKS.length; i++) {
			manager.register(NICKS[i], PASSWORDS[i]);
			assertTrue(manager.authenticate(NICKS[i], PASSWORDS[i]));
		}
		
		assertFalse(manager.authenticate(NICKS[0], PASSWORDS[1]));
		assertFalse(manager.authenticate(NICKS[1], PASSWORDS[0]));		
	}

	/**
	 * Checks errors on compiling a ship 
	 * Checks exception handling on invalid passwords
	 * 
	 * @throws SoncException on reading serialization file (not tested)
	 */
	@Test
	public void testBuildShip() throws SoncException {
		
		manager.register(NICKS[0], PASSWORDS[0]);
				
		try {
			manager.buildShip(NICKS[0], PASSWORDS[0], INVALID_SHIP_CODE);
			fail("Exception expected");
		} catch (SoncException e) {
			assertNotNull("Invalid code, exception", e);
		}
		
		try {
			manager.buildShip(NICKS[0], PASSWORDS[0], EMPTY_SHIP_CODE);
		} catch (SoncException e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Check getting code from player
	 * 
	 * @throws SoncException on reading serialization file (not tested)
	 */
	@Test
	public void testGetCurrentCode() throws SoncException {
		
		try {
			assertNull(manager.getCurrentCode(NICKS[0], PASSWORDS[0]));
			fail("Exception expected");
		} catch (SoncException e) {
			assertNotNull(e);
		}
		
		manager.register(NICKS[0], PASSWORDS[0]);		
		manager.buildShip(NICKS[0], PASSWORDS[0], EMPTY_SHIP_CODE);
		
		assertSame(EMPTY_SHIP_CODE, manager.getCurrentCode(NICKS[0], PASSWORDS[0]));		
	}

	/**
	 * Check that number of players with ships increases only
	 * when a ship is build for a player (not just registering him/her),
	 * for more than a single player
	 */
	@Test
	public void testGetPlayersNamesWithShips() {
		
		try {
			
			manager.register(NICKS[0], PASSWORDS[0]);
			
			assertSame(0, manager.getPlayersNamesWithShips().size());
			
			manager.buildShip(NICKS[0], PASSWORDS[0], EMPTY_SHIP_CODE);
			
			assertSame(1, manager.getPlayersNamesWithShips().size());
			
			manager.register(NICKS[1], PASSWORDS[1]);
			
			assertSame(1, manager.getPlayersNamesWithShips().size());
			
			manager.buildShip(NICKS[1], PASSWORDS[1], EMPTY_SHIP_CODE);
			
			assertSame(2, manager.getPlayersNamesWithShips().size());
			
		} catch (SoncException e) {
			fail("Unexpected exception");
		}		
	}

	/**
	 * Check a simple battle with 2 empty ships. It should produce a
	 * movie with just two objects and scores for all frames
	 */
	@Test
	public void testBattle() {
			
		try {
			
			List<String> nicks = new ArrayList<>();
		
			for (int i = 0; i < NICKS.length; i++) {
				manager.register(NICKS[i], PASSWORDS[i]);
				manager.buildShip(NICKS[i], PASSWORDS[i], EMPTY_SHIP_CODE);
				nicks.add(NICKS[i]);
			}
			
			Movie movie = manager.battle(nicks);
			
			assertNotNull(movie);
			
			for (Frame frame : movie.getFrames()) {
				assertSame(2, frame.getOblongs().size());
				assertSame(2, frame.getScores().size());
			}
			
		} catch (SoncException e) {
			fail("Unexpected exception");
		}		
	}
}