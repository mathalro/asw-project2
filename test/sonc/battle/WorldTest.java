package sonc.battle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.TestViewer;
import sonc.battle.bots.ShooterBot;
import sonc.battle.bots.SittingDuckBot;
import sonc.battle.bots.StalkerBot;
import sonc.shared.Movie;
import sonc.shared.Movie.Frame;
import sonc.shared.Movie.Score;
import sonc.utils.WorldViewer;

/**
 * Tests on the world were battles take place
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt }
 */
public class WorldTest extends TestData {
	
	World world;
	Ship ship;
	
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
		
		/*--------------------------------------------------------------*\
		 *                 Change this to visualize tests               *
		\*--------------------------------------------------------------*/
		TestViewer.setVisible(false);	// true to make view animation
		TestViewer.setDebugging(false); // if you are using the debugger
	}
		
	@Before
	public void setUp() throws Exception {
		world = new World();
		ship = new Ship();
	}

	@Test
	public void testWorld() {
		assertNotNull(world);
	}

	@Test
	public void testAddGetShipsAtRandom() {
		assertEquals("No ships on start ",0,world.getShips().size());
				
		for(int count=1; count <= REPETITIONS; count++) {
			world.addShipAtRandom(new Ship());
			assertEquals(count,world.getShips().size());
		}
	}
	
	@Test
	public void testAddGetShipsAtGivenPositions() {
		assertEquals("No ships on start ",0,world.getShips().size());
				
		for(int count=1; count <= REPETITIONS; count++) {
			world.addShipAt(new Ship(),count*10,count*10,count*2*Math.PI/REPETITIONS);
			assertEquals(count,world.getShips().size());
		}
	}

	/**
	 * Check adding single moving object to world
	 */
	@Test
	public void testAddGetMovingObject() {
		Bullet bullet = new Bullet(ANGLES[0]);
		
		assertEquals(0,world.getMovingObjects().size());
		
		world.addMovingObject(bullet);
		
		assertEquals(1,world.getMovingObjects().size());
		assertTrue(world.getMovingObjects().contains(bullet));
	}

	/**
	 * Check adding multiple moving object to world
	 */
	@Test 
	public void testgetAddMovingObjects() {
		assertEquals("No moving objects on start ",0,world.getMovingObjects().size());
		
		for(int count=1; count <= REPETITIONS; count++) {
			world.addShipAt(new Ship(),count*10,count*10,count*2*Math.PI/REPETITIONS);
			assertEquals(count,world.getMovingObjects().size());
		}
		
		for(int count=1; count <= REPETITIONS; count++) {
			Bullet bullet = new Bullet(0);
			
			bullet.setX(10+count*10);
			bullet.setY(0);
			world.addMovingObject(bullet);
			assertEquals(REPETITIONS+count,world.getMovingObjects().size());
		}
	}


	/**
	 * Trying to place a ship out of world boundaries
	 */
	@Test
	public void testUpdateOutOfBound() {
		Ship duck = new SittingDuckBot();
		
		try {
			world.addShipAt(duck,SIDE+1,SIDE+1,0);
			fail("Exception expected");
		} catch(Exception cause) {
			assertNotNull(cause);
		}
	}

	/**
	 * Check the world update method with a {@link sonc.battle.bots.StalkerBot}
	 * and a {@link sonc.battle.bots.SittingDuckBot}.
	 * Since the duck does not reply, the points received by the stalker
	 * and those lost by the first.
	 */
	@Test
	public void testUpdateWithStalkerAndDuck() {
		TestViewer viewer = new TestViewer();
		
		Ship stalker = new StalkerBot();
		Ship duck = new SittingDuckBot();
	
		world.addShipAt(stalker,SIDE/4,SIDE/2,Math.PI);
		world.addShipAt(duck,3*SIDE/4,SIDE/2,0);
		
		int count = 0;
		
		while(! duck.isDestroyed()) {
			
			world.setCurrentRound(count++);
			
			for(Ship ship: world.getShips()) {
				ship.move();
				ship.execute();
			}
			
			world.update();
			
			viewer.frameWith(world.getMovingObjects());
			viewer.showScores(world.getShips());
				
		}
		
		viewer.terminate();
		
		assertEquals(STATUS,stalker.getStatus());
		
		assertEquals(STATUS - stalker.getPoints(),duck.getStatus());
		assertTrue(duck.getStatus() <= 0);
		assertEquals(0,duck.getPoints());
	}

	/**
	 * Check the world update method with 2 {@link sonc.battle.bots.StalkerBot}
	 * Since they both fire, they get points from hitting opponent bullets.
	 */
	@Test
	public void testUpdateWith2Stalkers() {
		Ship stalker1 = new StalkerBot();
		Ship stalker2 = new StalkerBot();
	
		world.addShipAt(stalker1,SIDE/4,SIDE/2,Math.PI);
		world.addShipAt(stalker2,3*SIDE/4,SIDE/2,0);
		
		int count = 0;
		
		TestViewer viewer = new TestViewer();
		while(stalker2.getStatus() == STATUS) {
			
			world.setCurrentRound(count++);
			
			for(Ship ship: world.getShips()) {
				ship.move();
				ship.execute();
			}
			
			world.update();
			
			viewer.frameWith(world.getMovingObjects());
			viewer.showScores(world.getShips());
		}

		viewer.terminate();
		
		assertTrue(STATUS - stalker2.getPoints() <= stalker1.getStatus());
		assertTrue(STATUS - stalker1.getPoints() <= stalker2.getStatus());

	}
	
	/**
	 * Check as simple battle between an instance of 
	 * {@link sonc.battle.bots.ShooterBot} and a
	 * {@link sonc.battle.bots.SittingDuckBot}
	 */
	@Test
	public void testBattle1() {
		TestViewer viewer = new TestViewer();
		Ship shooter = new ShooterBot();
		Ship duck = new SittingDuckBot();
		List<Ship> ships = new ArrayList<Ship>() {
			private static final long serialVersionUID = 1L;
			{ add(shooter); add(duck); }
		};
		
		Movie movie = world.battle(ships);
		
		viewer.show(movie);
		
		checkMovie(movie,ships);
	}
	
	/**
	 * Check as simple battle between a 
	 * {@link sonc.battle.bots.ShooterBot} and 2 instances of 
	 * {@link sonc.battle.bots.SittingDuckBot}
	 */
	@Test
	public void testBattle2() {
		TestViewer viewer = new TestViewer();
		Ship shooter = new ShooterBot();
		Ship duck1 = new SittingDuckBot();
		Ship duck2 = new SittingDuckBot();
		List<Ship> ships = new ArrayList<Ship>() {
			private static final long serialVersionUID = 1L;
			{ add(shooter); add(duck1); add(duck2); }
		};
		
		Movie movie = world.battle(ships);
		
		viewer.show(movie);
		
		checkMovie(movie,ships);
	}

	/**
	 * Check if movie has the expected structure
	 * @param movie to check 
	 * @param ships that appear in movie
	 */
	private void checkMovie(Movie movie,List<Ship> ships) {
		
		assertEquals(World.getRounds(),movie.getFrames().size());
		
		for(Frame frame:movie.getFrames()) {
			int alive = 0;
			for(Score score: frame.getScores()) {
				assertTrue("ship name should exist:"+score.getName(),
					ships.stream().anyMatch(s -> s.getName().equals(score.getName())));
				assertTrue("non-negative points",score.getPoints() >= 0);
				
				if(score.getStatus() > 0)
					alive++;
			}
			
			assertTrue("no less oblongs than alives", 
					frame.getOblongs().size() >= alive);
		}
	}
}
