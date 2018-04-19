package sonc.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sonc.TestData;
import sonc.shared.Movie.Oblong;
import sonc.shared.Movie.Score;

/**
 * Tests on Movie - a visualization of a battle
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class MovieTest extends TestData {
	Movie movie = new Movie();
	
	@Before
	public void setUp() {
		movie = new Movie(); 
	}
	
	/**
	 * Check number of frames when creating one 
	 */
	@Test
	public void testNewFrameGetFrames() {
		assertEquals(0,movie.getFrames().size());
		
		movie.newFrame();
		
		assertEquals(1,movie.getFrames().size());
	}

	/**
	 * Check creating oblong without and with a frame
	 */
	@Test
	public void testAddOblong() {
		try {
			movie.addOblong(X1, Y1, ANGLES[0], INTS[0], COLORS[0]);
			fail("Exception expected");
		} catch(IllegalStateException cause){
			assertNotNull(cause);
		}
		
		movie.newFrame();
		
		movie.addOblong(X1, Y1, ANGLES[0], INTS[0], COLORS[0]);
		
		List<Oblong> oblongs = movie.getFrames().get(0).getOblongs();
		
		assertEquals(1,oblongs.size());
		
		movie.addOblong(X2, Y2, ANGLES[1], INTS[1], COLORS[1]);
		
		assertEquals(2,oblongs.size());
		
		Oblong oblong = oblongs.get(0);
		
		assertEquals(X1,oblong.getX());
		assertEquals(Y1,oblong.getY());
		assertEquals(ANGLES[0],oblong.getHeading(),DELTA);
		assertEquals(INTS[0],oblong.getSize());
		assertEquals(COLORS[0],oblong.getColor());
	}

	/**
	 * Check adding scores
	 */
	@Test
	public void testAddScore() {
		
		try {
			movie.addScore(NICKS[0],INTS[0],INTS[2]);
		} catch(IllegalStateException cause){
			assertNotNull(cause);
		}
		
		movie.newFrame();
		
		movie.addScore(NICKS[0],INTS[0],INTS[2]);
		
		List<Score> scores = movie.getFrames().get(0).getScores();
		
		assertEquals(1,scores.size());
		
		movie.addScore(NICKS[1],INTS[1],INTS[1]);
		
		assertEquals(2,scores.size());

		Score score = scores.get(0);
		
		assertEquals(NICKS[0],score.getName());
		assertEquals(INTS[0],score.getPoints());
		assertEquals(INTS[2],score.getStatus());
	}

}
