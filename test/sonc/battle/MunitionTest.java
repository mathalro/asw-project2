package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.battle.Munition;
import sonc.battle.Ship;
import sonc.battle.World;
import sonc.battle.bots.SittingDuckBot;
import sonc.battle.bots.StalkerBot;

/**
 * Test munition, the abstract class common to all munitions fired by ships in a battle
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class MunitionTest extends TestData {
	TestMunition munition;
	
	private static final int 	 STATUS = 1000;
	private static final double HEADING =    0;
	private static final double 	  X =  500;
	private static final double 	  Y =  500;
	private static final double   SPEED =   10;
	private static final int 	  DELAY =    5;
	
	/**
	 * A mock class for creating testable instances
	 */
	class TestMunition extends Munition {

		TestMunition(int status, double heading, double speed) {
			super(status, heading, speed);
		}

		@Override
		double getMaxSpeed() {
			return SPEED;
		}
		
		@Override
		int getImpactDamage() {
			return 0;
		}

		@Override
		public int getSize() {
			return 0;
		}

		@Override
		public String getColor() {
			return null;
		}

		@Override
		int fireDelay() {
			return DELAY;
		}
		
	}
	
	
	/**
	 * Define initial speed of bullets and collision distance
	 */
	@BeforeClass
	public static void setUpClass() {
		World.setCollisionDistance(COLLISION_DISTANCE);
	}
	
	/**
	 * Creates a fixture with an instance of the tested class
	 */
	@Before
	public void setUp() {
		munition = new TestMunition(STATUS,HEADING,SPEED);
	}

	/**
	 * Check if fixture was created
	 */
	@Test
	public void testMunition() {
		assertNotNull("fixture munition",munition);
	}

	/**
	 * Check setter and getter of property origin (the ship that fired the munition)
	 */
	@Test
	public void testSetGetOrigin() {
		for(Ship ship: Arrays.asList(new StalkerBot(), new SittingDuckBot())) {
			munition.setOrigin(ship);
			assertEquals(ship,munition.getOrigin());
		}
	}

	/**
	 * A bullet needs to escape the ship that fires it.
	 * In the following round it must at a distance superior to the collision distance 
	 */
	@Test
	public void testEscape() {
		Ship ship = new StalkerBot();
		
		ship.setX(X);
		ship.setY(Y);
		ship.setHeading(HEADING);
		
		munition.setX(X);
		munition.setY(Y);
		munition.setHeading(HEADING);

		assertEquals(0,ship.distanceTo(munition),DELTA);
		assertEquals(0,munition.distanceTo(ship),DELTA);
		
		munition.escape();
		
		assertTrue(ship.distanceTo(munition) >= World.getCollisionDistance());
		assertTrue(munition.distanceTo(ship) >= World.getCollisionDistance());
	}
}
