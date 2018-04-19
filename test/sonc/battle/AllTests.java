package sonc.battle;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BulletTest.class, ChangeSpeedCommandTest.class, FireCommandTest.class, GuidedMissileTest.class,
		MovingObjectTest.class, MunitionTest.class, RotateCommandTest.class, ShipTest.class, WorldTest.class })
public class AllTests {

}
