package sonc;

import java.util.Date;

/**
 * Test data used in unit tests
 * 
 * @author José Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class TestData {
	protected static final String MY_CLASS_PATH="war/WEB-INF/classes";
	
	protected static final double SIDE = 1000;
	protected static final double MARGIN = 100;
	protected static final int STATUS = 1000;
	protected static final double BULLET_SPEED = 20;
	protected static final double COLLISION_DISTANCE = 10;
	protected static final double SCALE = 0.8; // reduce this if you have a small screen

	protected static final String INVALID_NICK = "Player ZERO";
	protected static final String[] NICKS = { "PlayerOne", "PlayerTwo" };

	
	protected static String[] PASSWORDS = { "password1", "password2" };
	
	protected static int X1 = 200;
	protected static int Y1 = 200;
	
	protected static int X2 = 500;
	protected static int Y2 = 500;
	
	protected static int X3 = 700;
	protected static int Y3 = 700;

	protected static Date NOW = new Date();
	protected static int  SLACK = 100; 
	protected static Date[] DATES = { NOW, new Date(NOW.getTime()+SLACK) };
	protected static String[] TEXTS = { "hello", "two words", "some more words"}; 
	protected static int[] INTS = { 10, 20, 30 };
	protected static double[] DOUBLES = { -10, -1, 0, 1, 10 };
	protected static double DELTA = 1E-7; 		
	protected static float[] ANGLES = new float[] { 0, (float) Math.PI, (float) Math.PI/2 }; 
	protected static String COLORS[] = { "#FF0000", "#00FF00", "#0000FF" };
	
	protected static final String EMPTY_SHIP_CODE =
			"import sonc.battle.Ship; public class EmptyShip extends Ship {}";
	protected static final String INVALID_SHIP_CODE =
			"import sonc.battle.Ship; public clasZZ EmptyShip extends Ship {}";
	
	protected static final int REPETITIONS = 10;
	
	
	protected static final int SIZE = 20;

}
