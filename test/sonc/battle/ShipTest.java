package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.battle.bots.ShooterBot;
import sonc.battle.bots.StalkerBot;

/**
* Test super class common to all ships
* 
* @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
*/

public class ShipTest extends TestData {
	Ship ship;
	
	@BeforeClass
	public static void setUpClass() {
		Ship.setMaxShipRotation(2*Math.PI);
	}
	
	
	/**
	 * Creates a fixture with an instance of the tested class
	 */
	@Before
	public void setUp() {
		ship = new Ship();
		
		ship.setWorld(new World());
		ship.setX(MARGIN);
		ship.setY(MARGIN);
	}
	
	/**
	 * Check if fixture was created
	 */
	@Test
	public void testShip() {
		assertNotNull("fixture ship",ship);
	}
	
	/**
	 * Test setter and getter of static property damage
	 * and their influence on impact damage 
	 */
	@Test
	public void testGetImpactDamage() {
		for(int value: INTS) {
			Ship.setDamage(value);
			assertEquals(value,Ship.getDamage());
			
			assertEquals(value,ship.getImpactDamage());
		}
	}

	@Test
	public void testGetOrigin() {
		assertNull("Ship has no origin",ship.getOrigin());
	}

	/**
	 * Check that ships have a positive size
	 */
	@Test
	public void testGetSize() {
		assertTrue("ships with positive size",ship.getSize() > 0);
	}

	/**
	 * Check if the ship has a color
	 */
	@Test
	public void testGetColor() {
		assertNotNull("ship's color",ship.getColor());
	}

	/**
	 * Check setter and getter of property world (where the ships sails)
	 */
	@Test
	public void testSetGetWorld() {
		World world = new World();
		
		assertFalse("Some other initial world",world.equals(ship.getWorld()));
		
		ship.setWorld(world);
		
		assertEquals("Previously set World",world,ship.getWorld());
	}

	/**
	 * Check if list of other ships includes only those that are added 
	 */
	@Test
	public void testGetOtherShips() {
		Set<Ship> expected = new HashSet<>();
		
		assertEquals(expected,ship.getOtherShips());
		
		Ship other = new ShooterBot();
		
		expected.add(other);
		ship.getWorld().addShipAtRandom(other);
		
		assertEquals(expected,ship.getOtherShips());
		
		Ship another = new StalkerBot();
		
		expected.add(another);
		ship.getWorld().addShipAtRandom(another);
		
		assertEquals(expected,ship.getOtherShips());
		
	}
	
	/**
	 * Check setter and getter of property last fire round
	 * (round where last munition was fired)
	 */
	@Test
	public void testLastFireRound() {
		assertEquals("Default value",Integer.MIN_VALUE,ship.getLastFireRound());
		
		for(int value: INTS) {
			ship.setLastFireRound(value);
			
			assertEquals(value,ship.getLastFireRound());
		}
	}

	/**
	 * Check that if ship is rotated twice, only the second rotations is effective
	 */
	@Test
	public void testDoubleRotate() {
		assertEquals("Default  heading",ANGLES[0],ship.getHeading(),DELTA);
		
		ship.rotate(ANGLES[1]); 
		ship.rotate(ANGLES[2]); // this will supersede the previous
		
		assertEquals("Default heading",ANGLES[0],ship.getHeading(),DELTA);
		
		ship.execute();
		
		assertEquals("Second heading efective after execution",
				ANGLES[2],ship.getHeading(),DELTA);		
	}

	/**
	 * Check that if ship is rotated immediately after firing then it will just rotate
	 */
	@Test
	public void testRotateAfterFire() {
		assertEquals("Default  heading",ANGLES[0],ship.getHeading(),DELTA);
		
		ship.fire(new Bullet(ANGLES[1]));
		ship.rotate(ANGLES[2]);
		
		assertEquals("Default heading",0,ship.getHeading(),DELTA);
		
		ship.execute();
		
		assertEquals("Rotation efective after execution",
				ANGLES[2],ship.getHeading(),DELTA);		
	}

	/**
	 * Check that if ship fires immediately after rotating then it will just fire
	 */
	@Test
	public void testFireAfterRotate() {
		
		assertEquals("Default  heading",ANGLES[0],ship.getHeading(),DELTA);
		
		ship.rotate(ANGLES[2]);
		ship.fire(new Bullet(ANGLES[1]));
		
		assertEquals("Default heading",ANGLES[0],ship.getHeading(),DELTA);
		
		ship.execute();
		
		assertEquals("Rotate() should be nulled by fire()",ANGLES[0],ship.getHeading(),DELTA);		
	}

	
	
	@Test
	public void testFire() {
		World world = new World();
		
		ship.setWorld(world);
		ship.fire(new Bullet(0));
		
		ship.execute();
	}


	/**
	 * Check setter and getter of static property damage
	 */
	@Test
	public void testSetGetDamage() {
	
		for(int damage: INTS) {
			Ship.setDamage(damage);
			assertEquals(damage,Ship.getDamage());
		}
	}

	/**
	 * Check if points are added correctly
	 */
	@Test
	public void testAddGetPoints() {
		int total = 0;
		
		assertEquals("No points defined",total,ship.getPoints());
		
		for(int count=0; count < REPETITIONS; count++) {
			int points = INTS[count % INTS.length];
			
			ship.addPoints(points);
			total += points;
			
			assertEquals(total,ship.getPoints());
		}
	}
}
