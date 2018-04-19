package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.TestViewer;
import sonc.battle.bots.SittingDuckBot;
import sonc.utils.WorldViewer;

/**
 * Test guided missiles fired from ships
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class GuidedMissileTest extends TestData {
	private static final int SPEED = 5;
	private static final int DAMAGE = 600;
	private static final double MAX_ROTATION = Math.PI/64;
	
    GuidedMissile guidedMissile;
	Ship ship;
    
	/**
	 * Set static properties to adjust to tests
	 */
	@BeforeClass
	public static void setUpClass() {
		GuidedMissile.setMaxMissileRotation(MAX_ROTATION);
		GuidedMissile.setDamage(DAMAGE);
		GuidedMissile.setInitialSpeed(SPEED);
		

		/*--------------------------------------------------------------*\
		 *                 Change this to visualize tests               *
		\*--------------------------------------------------------------*/
		TestViewer.setVisible(false);	// true to make view animation
		TestViewer.setDebugging(false); // if you are using the debugger

	}
	
	/**
	 * Create guided missile and ship as fixture
	 */
	@Before
	public void setUp() {
		ship = new SittingDuckBot();
		guidedMissile = new GuidedMissile(ANGLES[0],ship);
	}
	
	/**
	 * Check if missile in fixture was instantiated 
	 */
	@Test
	public void testGuidedMissile() {
		assertNotNull(guidedMissile);
	}

	/**
	 * Check if speed change is blocked 
	 */
	@Test
	public void testGetMaxSpeedChange() {
		
		assertEquals(0,guidedMissile.getMaxSpeedChange(),DELTA);
	}

	/**
	 * Check if max rotation is is was was defined as static property
	 */
	@Test
	public void testGetMaxRotation() {
		
		assertEquals(MAX_ROTATION,guidedMissile.getMaxRotation(),DELTA);
	}


	/**
	 * Test setter and getter of static property damage
	 * and their influence on impact damage 
	 */
	@Test
	public void testSetGetDamage() {
		for(int value: INTS) {
			GuidedMissile.setDamage(value);
			assertEquals(value,GuidedMissile.getDamage());
			
			assertEquals(value,guidedMissile.getImpactDamage());
		}
		
	}

	/**
	 * Check property origin (the ship that fired the missile)
	 */
	@Test
	public void testSetGetOrigin() {	
		Ship ship = new Ship();
		
		guidedMissile.setOrigin(ship);
		assertEquals(ship,guidedMissile.getOrigin());
	}

	/**
	 * Check setter and getter of property X coordinate
	 */
	@Test
	public void testSetGetX() {
		for(double x: DOUBLES) {
			guidedMissile.setX(x);
			assertEquals(x,guidedMissile.getX(),DELTA);
		}
	}

	/**
	 * Check setter and getter of property Y coordinate
	 */
	@Test
	public void testSetGetY() {
		for(double y: DOUBLES) {
			guidedMissile.setY(y);
			assertEquals(y,guidedMissile.getY(),DELTA);
		}
	}

	/**
	 * Check setter and getter of property heading
	 */
	@Test
	public void testGetHeading() {
		for(double heading: DOUBLES) {
			guidedMissile = new GuidedMissile(heading,ship);
			assertEquals(guidedMissile.normalizeAngle(heading),guidedMissile.getHeading(),DELTA);
		}

	}

	/**
	 * Check speed of missile that  is controlled by static property
	 */
	@Test
	public void testGetSpeed() {
		assertEquals("Initial speed",SPEED,guidedMissile.getSpeed(),DELTA);
	}

	
	/**
	 *  Check heading to between missiles: Double check if they were redefined
	 */
	@Test
	public void testHeadingTo() {
		GuidedMissile m1= new GuidedMissile(0,ship), m2 = new GuidedMissile(0,ship);
		
		m1.setX(0); m1.setY(0);
		m2.setX(100); m2.setY(0);
		
		assertEquals(0,m1.headingTo(m2),DELTA);
		assertEquals(Math.PI,m2.headingTo(m1),DELTA);
		
		m2.setX(0); m2.setY(100);
		
		assertEquals(Math.PI/2,m1.headingTo(m2),DELTA);
		assertEquals(3*Math.PI/2,m2.headingTo(m1),DELTA);
	}

	/**
	 * Check position update for missiles along a straight path path
	 * (without using move)
	 */
	@Test
	public void testUpdatePosition() {
		double speed = guidedMissile.getSpeed();
		
		for(Number value: Arrays.asList(0,Math.PI/4,Math.PI/3,Math.PI/2)) {
			double heading = (value).doubleValue();
			guidedMissile = new GuidedMissile(heading,ship);
			guidedMissile.setX(0); 
			guidedMissile.setY(0);
		
			for(int c=0; c<10; c++) {
				assertEquals(c*speed*Math.sin(heading),guidedMissile.getY(),DELTA);
				assertEquals(c*speed*Math.cos(heading),guidedMissile.getX(),DELTA);
			
				
				guidedMissile.updatePosition();
			}
		}
	}

	/**
	 * Check is guided missile fired horizontally goes towards a stationary ship
	 */
	@Test
	public void testMoveMissiletoStationaryShip() {
		double previous, distance; 
		ship.setX(SIDE/2);
		ship.setY(SIDE-MARGIN);
		
		guidedMissile = new GuidedMissile(0,ship);
		guidedMissile.setX(MARGIN); 
		guidedMissile.setY(MARGIN);
	
		TestViewer viewer = new TestViewer();
		
		distance = guidedMissile.distanceTo(ship);
		do {
			previous = distance;
			
			guidedMissile.move();
			guidedMissile.updatePosition();
		
			distance = guidedMissile.distanceTo(ship);
	
			viewer.frameWith(ship,guidedMissile);
			
			
			assertTrue(distance < previous);
		} while(distance > MARGIN);
		
		viewer.terminate();;
	}
	
	/**
	 * Check is guided missile fired horizontally goes towards a moving ship 
	 */
	@Test
	public void testMoveMissiletoMovingShip() {
		double previous, distance; 
		ship.setX(SIDE/2);
		ship.setY(SIDE-MARGIN);
		ship.setHeading(Math.PI);
		ship.doChangeSpeed(SPEED);
		
		guidedMissile = new GuidedMissile(0,ship);
		guidedMissile.setX(MARGIN); 
		guidedMissile.setY(MARGIN);
	
		TestViewer viewer = new TestViewer();
		
		distance = guidedMissile.distanceTo(ship);
		do {
			previous = distance;
			
			guidedMissile.move();
			guidedMissile.updatePosition();
			
			ship.move();
			ship.updatePosition();
		
			distance = guidedMissile.distanceTo(ship);
	
	
			viewer.frameWith(ship,guidedMissile);
			
			assert(distance < previous);
		} while(distance > MARGIN);
		
		viewer.terminate();
	}

	
	void view(WorldViewer viewer, MovingObject moving) {
		final int x = (int) moving.getX();
		final int y = (int) moving.getY();
		final float heading = (float) moving.getHeading();
		final int size = moving.getSize();
		final String color = moving.getColor();
		
		viewer.addOblong(x,y,heading,size,color);
	}
	

	@Test
	public void testHitdBy() {
		Bullet other = new Bullet(0);
		int status = guidedMissile.getStatus();
				
		guidedMissile.hitdBy(other);
		
		assertEquals(status-other.getImpactDamage(),guidedMissile.getStatus());
	}

	@Test
	public void testIsDestroyed() {
		GuidedMissile other = new GuidedMissile(0,guidedMissile);
		
		assertEquals(false,guidedMissile.isDestroyed());
		
		guidedMissile.hitdBy(other);
				
		assertEquals(true,guidedMissile.isDestroyed());
	}


}
