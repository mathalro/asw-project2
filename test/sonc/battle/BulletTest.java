package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;

/**
 * Test bullets fired from ships
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class BulletTest extends TestData {
	private static final int SPEED = 20;
	
	Bullet bullet;
	
	/**
	 * Define initial speed of bullets and collision distance
	 */
	@BeforeClass
	public static void setUpClass() {
		Bullet.setInitialSpeed(SPEED);
		World.setCollisionDistance(COLLISION_DISTANCE);
	}
	
	/**
	 * Create a bullet fired horizontally to the right as part of fixture 
	 */
	@Before
	public void setUp() {
		bullet = new Bullet(ANGLES[0]);
	}

	/**
	 * Check if fixture was instantiated
	 */
	@Test
	public void testBullet() {
		assertNotNull(bullet);
	}

	
	/**
	 * Check speed of fixture bullet
	 */
	@Test
	public void testGetMaxSpeedChange() {
		
		assertEquals(0,bullet.getMaxSpeedChange(),DELTA);
	}

	/**
	 * Bullets don't rotate 
	 */
	@Test
	public void testGetMaxRotation() {
		
		assertEquals(0,bullet.getMaxSpeedChange(),DELTA);
	}

	/**
	 * Check effect of the damage static property in the impact damage
	 */
	@Test
	public void testSetGetDamage() {
		for(int value: INTS) {
			Bullet.setDamage(value);
			assertEquals(value,Bullet.getDamage());
			
			assertEquals(value,bullet.getImpactDamage());
		}
		
	}

	/**
	 * Check the origin property (the ship that fires a bullet)
	 */
	@Test
	public void testSetGetOrigin() {	
		Ship ship = new Ship();
		
		bullet.setOrigin(ship);
		assertEquals(ship,bullet.getOrigin());
	}

	/**
	 * A bullet needs to escape the ship that fires it.
	 * In the following round it must at a distance superior to the collision distance 
	 * Double checking if it wasn't redefined
	 */
	@Test
	public void testEscape() {
		Ship ship = new Ship();
		ship.setX(100);
		ship.setY(100);
		ship.setHeading(0);
		
		for(double heading: ANGLES) {
			bullet.setX(ship.getX());
			bullet.setY(ship.getY());
			bullet.setHeading(heading);
		
			bullet.escape();
		
			assertEquals(World.getCollisionDistance(),ship.distanceTo(bullet),DELTA);
		}
	}

	/**
	 * Check the setter and getter of the the X property
	 */
	@Test
	public void testSetGetX() {
		for(double x: DOUBLES) {
			bullet.setX(x);
			assertEquals(x,bullet.getX(),DELTA);
		}
	}

	/**
	 * Check the setter and getter of the the Y property
	 */
	@Test
	public void testSetGetY() {
		for(double y: DOUBLES) {
			bullet.setY(y);
			assertEquals(y,bullet.getY(),DELTA);
		}
	}

	/**
	 * Check the setter and getter of the the heading property
	 */
	@Test
	public void testGetHeading() {
		for(double heading: DOUBLES) {
			Bullet bullet = new Bullet(heading);
			assertEquals(bullet.normalizeAngle(heading),bullet.getHeading(),DELTA);
		}

	}

	/**
	 * Check the speed (should be the initial one)
	 */
	@Test
	public void testGetSpeed() {
		assertEquals("Initial speed",SPEED,bullet.getSpeed(),DELTA);
	}

	/**
	 * Check distance between bullets. Double checking if it wasn't redefined
	 */
	@Test
	public void testDistanceTo() {
		Bullet b1= new Bullet(0), b2 = new Bullet(0);
		
		b1.setX(0); b1.setY(0);
		b2.setX(0); b2.setY(100);
		
		assertEquals(100, b1.distanceTo(b2),DELTA);
		assertEquals(100, b2.distanceTo(b1),DELTA);
		
		b2.setX(100); b2.setY(0);
		
		assertEquals(100, b1.distanceTo(b2),DELTA);
		assertEquals(100, b2.distanceTo(b1),DELTA);

		b2.setX(100); b2.setY(100);
		
		assertEquals(141.42, b1.distanceTo(b2),0.01);
		assertEquals(141.42, b2.distanceTo(b1),0.01);

	}

	/**
	 * Check heading between bullets. Double checking if it wasn't redefined
	 */
	@Test
	public void testHeadingTo() {
		Bullet b1= new Bullet(0), b2 = new Bullet(0);
		
		b1.setX(  0);	b1.setY(0);
		b2.setX(100);	b2.setY(0);
		
		assertEquals(0,b1.headingTo(b2),DELTA);
		assertEquals(Math.PI,b2.headingTo(b1),DELTA);
		
		b2.setX(0); b2.setY(100);
		
		assertEquals(Math.PI/2,b1.headingTo(b2),DELTA);
		assertEquals(3*Math.PI/2,b2.headingTo(b1),DELTA);
	}

	/**
	 * Check update position. Double checking if it wasn't redefined
	 */
	@Test
	public void testUpdatePosition() {
		double speed = bullet.getSpeed();
		
		for(Number value: Arrays.asList(0,Math.PI/4,Math.PI/3,Math.PI/2)) {
			double heading = (value).doubleValue();
			bullet = new Bullet(heading);
			bullet.setX(0); 
			bullet.setY(0);
		
			for(int c=0; c<10; c++) {
				assertEquals(c*speed*Math.sin(heading),bullet.getY(),DELTA);
				assertEquals(c*speed*Math.cos(heading),bullet.getX(),DELTA);
			
				bullet.updatePosition();
			}
		}		
	}

	/**
	 * Check effect of being hit by a bullet. Double checking if it wasn't redefined
	 */
	@Test
	public void testHitdBy() {
		Bullet other = new Bullet(0);
		int status = bullet.getStatus();
				
		bullet.hitdBy(other);
		
		assertEquals(status-other.getImpactDamage(),bullet.getStatus());
	}

	/**
	 * Check if bullet with 0 status is destroyed. Double checking if it wasn't redefined
	 */
	@Test
	public void testIsDestroyed() {
		Bullet other = new Bullet(0);
		
		assertEquals(false,bullet.isDestroyed());
		
		bullet.hitdBy(other);
				
		assertEquals(true,bullet.isDestroyed());
	}

}
