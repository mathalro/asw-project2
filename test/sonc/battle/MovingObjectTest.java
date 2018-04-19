package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import sonc.TestData;

/**
 * Test moving objects, the abstract class common to all objects in a battle
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class MovingObjectTest extends TestData {
	private static final int STATUS = 1000;
	private static final double MAX_SPEED = 10;
	private static final double MAX_SPEED_CHANGE = 2;
	private static final double MAX_ROTATION = Math.PI/4;
	private static final int IMPACT_DEMAGE = 10;
	
	TestMovingObject moving;
	
	/**
	 * A mock class for creating testable instances
	 */
	class TestMovingObject extends MovingObject {
		
		TestMovingObject() {
			super(STATUS,0,0);
		}

		@Override
		double getMaxSpeed() {return MAX_SPEED;}
		
		@Override
		double getMaxSpeedChange() {return MAX_SPEED_CHANGE;}

		@Override
		double getMaxRotation() { return MAX_ROTATION; }

		@Override
		int getImpactDamage() {	return IMPACT_DEMAGE; }

		@Override
		Ship getOrigin() {  return null; }

		@Override
		public int getSize() {	return 0; }

		@Override
		public String getColor() {	return null; }
	}

	/**
	 * Creates a fixture with an instance of the tested class
	 */
	@Before
	public void setUp() {
		moving = new TestMovingObject();
	}

	/**
	 * Check if fixture was created
	 */
	@Test
	public void testMovingObject() {
		assertNotNull(moving);
	}

	/**
	 * Check setter and getter of property X
	 */
	@Test
	public void testSetGetX() {
		assertEquals("Default",0,moving.getX(),DELTA);
		
		for(double x: DOUBLES) {
			moving.setX(x);
			assertEquals(x,moving.getX(),DELTA);
		}
	}

	/**
	 * Check setter and getter of property Y
	 */
	@Test
	public void testSetGetY() {
		assertEquals("Default",0,moving.getY(),DELTA);
		
		for(double y: DOUBLES) {
			moving.setY(y);
			assertEquals(y,moving.getY(),DELTA);
		}
	}

	/**
	 * Check setter and getter of property heading.
	 * Depends on the angle normalization method from tested class 
	 */
	@Test
	public void testSetGetHeading() {
		assertEquals("Default",0,moving.getHeading(),DELTA);
		
		for(double heading: DOUBLES) {
			moving.setHeading(heading);
			assertEquals(moving.normalizeAngle(heading),moving.getHeading(),DELTA);
		}
	}

	/**
	 * Check default speed
	 */
	@Test
	public void testGetSpeed() {
		assertEquals("Default",0,moving.getSpeed(),DELTA);
	}

	/**
	 * Check distances between objects in different relative positions.
	 * Make sure that distance is symmetrical.
	 */
	@Test
	public void testDistanceTo() {
		TestMovingObject other = new TestMovingObject();
		
		other.setX(100);
		other.setY(0);
		
		assertEquals(100,moving.distanceTo(other),DELTA);
		assertEquals(100,other.distanceTo(moving),DELTA);
		
		other.setX(0);
		other.setY(100);
		
		assertEquals(100,moving.distanceTo(other),DELTA);
		assertEquals(100,other.distanceTo(moving),DELTA);
		
		other.setX(100);
		other.setY(100);
		
		double dist = Math.sqrt(2*100*100);
		
		assertEquals(dist,moving.distanceTo(other),DELTA);
		assertEquals(dist,other.distanceTo(moving),DELTA);
	}
	
	/**
	 * Check if angles are correctly normalized in the range [0,2xPI[
	 * Double check that 2xPI is normalized in 0  
	 */
	@Test
	public void testNormalizeAngle() {
		assertEquals("2xPI equivalent to 0",0,moving.normalizeAngle(2*Math.PI),DELTA);
		for(double angle: ANGLES) {
			assertEquals(angle,moving.normalizeAngle(angle),DELTA);
			for(int n: Arrays.asList(2,3,4,5,6,7)) {
				assertEquals(angle,moving.normalizeAngle(angle+n*2*Math.PI),DELTA);
				assertEquals(angle,moving.normalizeAngle(angle-n*2*Math.PI),DELTA);
			}
		}
	}

	/**
	 * Check heading to an object in the same horizontal line
	 * The reverse angle should have a difference of PI 
	 */
	@Test
	public void testHeadingToHorizontal() {
		TestMovingObject other = new TestMovingObject();
		
		other.setX(100);
		other.setY(0);
		
		assertEquals(0,moving.headingTo(other),DELTA);
		assertEquals(Math.PI,other.headingTo(moving),DELTA);
	}
	
	/**
	 * Check heading to an object in the same vertical line
	 * The reverse angle should have a difference of PI 
	 */
	@Test
	public void testHeadingToVertical() {
		TestMovingObject other = new TestMovingObject();
		
		other.setX(0);
		other.setY(100);
		
		assertEquals(Math.PI/2,moving.headingTo(other),DELTA);
		assertEquals(3*Math.PI/2,other.headingTo(moving),DELTA);
	}
	
	/**
	 * Check heading to an object in a diagonal line
	 * The reverse angle should have a difference of PI 
	 */
	@Test
	public void testHeadingToDiagonal() {
		TestMovingObject other = new TestMovingObject();
		
		other.setX(100);
		other.setY(100);
		
		assertEquals(Math.PI/4,moving.headingTo(other),DELTA);
		assertEquals(5*Math.PI/4,other.headingTo(moving),DELTA);
	}


	/**
	 * Check rotation is not greater than MAX_ROTATION
	 */
	@Test
	public void testDoRotate() {
		for(double angle: ANGLES) {
			moving.setHeading(0);
			moving.doRotate(angle);
			assertEquals(Math.min(angle,MAX_ROTATION),moving.getHeading(),DELTA);
		}
	}
	
	/**
	 * Check rotation equal to MAX_ROTATION
	 */
	@Test
	public void testDoRotateMax() {
		moving.doRotate(MAX_ROTATION);
		assertEquals(MAX_ROTATION,moving.getHeading(),DELTA);
	}
	
	/**
	 * Check rotation larger than MAX_ROTATION
	 */
	@Test
	public void testDoRotateMoreThanMax() {
		moving.doRotate(2*MAX_ROTATION);
		
		assertEquals(MAX_ROTATION,moving.getHeading(),DELTA);
	}
	
	/**
	 * Check rotation less than MAX_ROTATION
	 */
	@Test
	public void testDoRotateLessThanMax() {
		moving.doRotate(MAX_ROTATION/2);
		
		assertEquals(MAX_ROTATION/2,moving.getHeading(),DELTA);
	}
	
	/**
	 * Check with speeds larger than  MAX_SPEED_CHANGE
	 */
	@Test
	public void testSpeedMoreThanMax() {
		
		moving.setHeading(Math.PI/4);
		
		assertEquals(0,moving.getSpeed(),DELTA);
		
		moving.doChangeSpeed(2*MAX_SPEED_CHANGE);
		
		assertEquals(MAX_SPEED_CHANGE,moving.getSpeed(),DELTA);
	}
	
	/**
	 * Check with speeds less than  MAX_SPEED_CHANGE
	 */
	@Test
	public void testSpeedLessThanMax() {
		
		moving.setHeading(Math.PI/4);
		
		assertEquals(0,moving.getSpeed(),DELTA);
		
		moving.doChangeSpeed(MAX_SPEED_CHANGE/2);
		
		assertEquals(MAX_SPEED_CHANGE/2,moving.getSpeed(),DELTA);
	}

	/**
	 * Test update when object moves horizontally
	 */
	@Test
	public void testSpeedUpdatePositionHorizontal() {
		moving.setHeading(0);
		
		assertEquals(0,moving.getSpeed(),DELTA);
		
		moving.doChangeSpeed(MAX_SPEED_CHANGE);
		
		assertEquals(MAX_SPEED_CHANGE,moving.getSpeed(),DELTA);
		
		moving.updatePosition();
		
		assertEquals(MAX_SPEED_CHANGE,moving.getX(),DELTA);
		assertEquals(0,moving.getY(),DELTA);
		assertEquals(0,moving.getHeading(),DELTA);
	}

	/**
	 * Test update when object moves vertically
	 */
	@Test
	public void testSpeedUpdatePositionVertical() {
		
		moving.setHeading(Math.PI/2);
		
		assertEquals(0,moving.getSpeed(),DELTA);
		
		moving.doChangeSpeed(MAX_SPEED_CHANGE);
		
		assertEquals(MAX_SPEED_CHANGE,moving.getSpeed(),DELTA);
		
		moving.updatePosition();
		
		assertEquals(0,moving.getX(),DELTA);
		assertEquals(MAX_SPEED_CHANGE,moving.getY(),DELTA);
		assertEquals(Math.PI/2,moving.getHeading(),DELTA);
	}
	
	/**
	 * Test speed and update when object moves in a diagonal
	 */
	@Test
	public void testSpeedUpdatePositionDiagonal() {
		
		moving.setHeading(Math.PI/4);
		
		assertEquals(0,moving.getSpeed(),DELTA);
		
		moving.doChangeSpeed(MAX_SPEED_CHANGE);
		
		assertEquals(MAX_SPEED_CHANGE,moving.getSpeed(),DELTA);
		
		moving.updatePosition();
		
		double dist = MAX_SPEED_CHANGE/Math.sqrt(2);
		
		assertEquals(dist,moving.getX(),DELTA);
		assertEquals(dist,moving.getY(),DELTA);
		assertEquals(Math.PI/4,moving.getHeading(),DELTA);
	}

	/**
	 * Check initial status 
	 */
	@Test
	public void testGetStatus() {
		assertEquals(STATUS,moving.getStatus(),DELTA);
	}
	
	/**
	 * Check status after being hit by another moving object
	 */
	@Test
	public void testHitdBy() {
		TestMovingObject other = new TestMovingObject();
		
		moving.hitdBy(other);
		
		assertEquals(STATUS-IMPACT_DEMAGE,moving.getStatus(),DELTA);
	}

	/**
	 * Check if object is destroyed when status reaches 0
	 */
	@Test
	public void testIsDestroyed() {
		TestMovingObject other = new TestMovingObject();
		
		assertEquals(STATUS,moving.getStatus(),DELTA);
		
		while(moving.getStatus() > 0) {
			assertFalse(moving.isDestroyed());
			moving.hitdBy(other);
		}
		
		assertTrue(moving.isDestroyed());
	}		
}
