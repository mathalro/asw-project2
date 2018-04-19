package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.utils.WorldViewer;

/**
 * Test command for firing munitions
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class FireCommandTest extends TestData {
	FireCommand fireCommand;
	World world;
	Ship ship;
	Bullet bullet;
	
	@BeforeClass
	public static void setUpClass() {
		World.setWidth(SIDE);
		World.setHeight(SIDE);
		World.setMargin(MARGIN);
		World.setCollisionDistance(COLLISION_DISTANCE);
		
		WorldViewer.setStopFrequency(0);
		WorldViewer.setScale(SCALE);
		
		Ship.setMaxStatus(STATUS);
		Ship.setMaxShipRotation(Math.PI/32);
		
		Bullet.setInitialSpeed(BULLET_SPEED);
	}

	/**
	 * Create instance of {@code FireCommand} as part of fixture
	 * an well as instances of other classes needed for tests
	 */
	@Before
	public void setUp() {
		ship = new Ship();
		bullet = new Bullet(ANGLES[0]);
		world = new World();
		fireCommand = new FireCommand(world, ship, bullet);
		
		
		ship.setWorld(world);
		ship.setX(SIDE/2);
		ship.setY(SIDE/2);
	}
	
	
	/**
	 * Check if {@code FireCommand} of fixture was instantiated
	 */
	@Test
	public void testFireCommand() {
		assertNotNull(fireCommand);
	}

	/**
	 * Check that firing a bullet increases the number of objects in the world
	 */
	@Test
	public void testSimpleExecute() {
		assertEquals("Empty world",0,world.getMovingObjects().size());
		
		fireCommand.execute();
		
		assertEquals("bullet in the world",1,world.getMovingObjects().size());
		
	}
	
	/**
	 * Check that two bullets cannot be fired without a delay
	 */
	@Test
	public void testExecuteWithDelay() {
		assertEquals("Empty world",0,world.getMovingObjects().size());
		
		world.setCurrentRound(0);
		
		fireCommand.execute();
		
		assertEquals("bullet in the world",1,world.getMovingObjects().size());
		
		Bullet otherBullet = new Bullet(ANGLES[0]);
		FireCommand otherFireCommand = new FireCommand(world, ship, otherBullet);
		
		world.update();
		
		assertEquals("bullet in the world",1,world.getMovingObjects().size());
		
		world.setCurrentRound(1);
	
		otherFireCommand.execute();
		
		assertEquals("no other bullet in the world",1,world.getMovingObjects().size());
		
		// rounds pass ...
		world.setCurrentRound(ship.getLastFireRound() + otherBullet.fireDelay() + 1);
		
		otherFireCommand.execute();
		
		assertEquals("another bullet in the world",2,world.getMovingObjects().size());
		
	}

}
