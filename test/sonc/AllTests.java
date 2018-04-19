package sonc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	sonc.game.AllTests.class, 
	sonc.battle.AllTests.class, 
	sonc.quad.AllTests.class, 
	sonc.shared.AllTests.class
	})
public class AllTests {

}
