package sonc;

import java.util.Set;

import sonc.battle.MovingObject;
import sonc.battle.Ship;
import sonc.shared.Movie;
import sonc.shared.Movie.Frame;
import sonc.shared.Movie.Oblong;
import sonc.shared.Movie.Score;
import sonc.utils.WorldViewer;

/**
 * Graphical viewer for tests. Static properties can be used to
 * switch it off or prepare it for debugging.
 *  
 * @author José Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class TestViewer {
	/** The viewer  is visible */ 
	private static boolean visible 	= false;
	/** Tests are being debugged */
	static boolean debugging = false;
	
	/**
	 * The viewer is currently visible.
	 * This method should be invoked from @BeforeClass methods 
	 * @return {@code true} if visible; {@code false} otherwise
	 */
	public static boolean isVisible() {
		return visible;
	}

	/**
	 * Control {@code TestViewer} visibility.  
	 * This method should be invoked from @BeforeClass methods
	 *
	 * @param visible {@code true} for visible; {@code false} otherwise
	 */
	public static void setVisible(boolean visible) {
		TestViewer.visible = visible;
	}

	/** 
	 * The viewer is being used in debug mode.
	 * So then must be updated immediately 
	 * 
	 *@return {@code true} if in debug mode; {@code false} otherwise
	 */
	public static boolean isDebugging() {
		return debugging;
	}

	/**
	 * Control {@code TestViewer} debug mode.  
	 * If set then the screen is updated immediately.
	 * This method should be invoked from @BeforeClass methods
	 *
	 * @param debugging {@code true} for debugging; {@code false} otherwise
	 */
	
	public static void setDebugging(boolean debugging) {
		TestViewer.debugging = debugging;
	}

	WorldViewer viewer = null;
	
	/**
	 * Create a viewer if it is currently visible 
	 */
	public TestViewer() {
		if(visible)
			viewer = new WorldViewer();
	}

	/**
	 * Add a frame and show given list of moving objects, is viewer is visible
	 * 
	 * @param movings set of {@link sonc.battle.MovingObject} instances to show
	 */
	public void frameWith(Set<MovingObject> movings) {
		MovingObject[] array = new MovingObject[movings.size()];
		int cursor = 0;
		
		for(MovingObject moving: movings)
			array[cursor++] = moving; 
		
		frameWith(array); 
	}

	/**
	 * Add a frame and show given moving objects, is viewer is visible
	 * 
	 * @param movings as instaces of {@link sonc.battle.MovingObject} to be show
	 */
	public void frameWith(MovingObject... movings) {
		if(!visible)
			return;
		
		
		viewer.newFrame();
		for(MovingObject moving: movings) {
			final int x 		= (int) moving.getX();
			final int y 		= (int) moving.getY();
			final float heading	= (float) moving.getHeading();
			final int size 		= moving.getSize();
			final String color 	= moving.getColor();
			
			viewer.addOblong(x, y, heading, size, color);
		}
		
		if(debugging)
			viewer.repaint();
	}
	
	
	/**
	 * Show scores of given ships, if viewer is visible 
	 * @param ships set of {@link sonc.battle.Ship} instances to show
	 */
	public void showScores(Set<Ship> ships) {
		if(!visible)
			return;
		
		for(Ship ship: ships) {
			final String name 	= ship.getName();
			final int points 	= ship.getPoints();
			final int status 	= ship.getStatus();
			
			viewer.addScore(name,points,status);
		}
		
		if(debugging)
			viewer.repaint();
	}
	
	/**
	 * This should be the last method invoked in a simulation.
	 * It prevents the simulation window from being removed and
	 * ensures that visualization is completely shown.
	 */
	public void terminate() {	
		if(visible)
			viewer.waitForExit();
	}

	/**
	 * Show a given full movie, if viewer is visible 
	 * @param movie to be shown
	 */
	public void show(Movie movie) {
		if(!visible)
			return;
		
		for(Frame frame: movie.getFrames()) {
			viewer.newFrame();
			for(Oblong oblong: frame.getOblongs()) {
				final int x 		= oblong.getX();
				final int y 		= oblong.getY();
				final float heading	= oblong.getHeading();
				final int size 		= oblong.getSize();
				final String color 	= oblong.getColor();
				
				viewer.addOblong(x, y, heading, size, color);				
			}
			for(Score score: frame.getScores()) {
				final String name 	= score.getName();
				final int points 	= score.getPoints();
				final int status 	= score.getStatus();
				
				viewer.addScore(name,points,status);
			}
		}
		terminate();
	}
}

	