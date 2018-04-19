package sonc.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests on safe executor - utility for executing bots in a safe environment
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class SafeExecutorTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExecuteSafelly() {
		checkCall(() -> { return; },null);
		
		checkCall(() -> { System.out.print("");  },null);
		
		checkCall(() -> { System.setSecurityManager(null); },
				"access denied (\"java.lang.RuntimePermission\" \"setSecurityManager\")");

		checkCall((() -> { System.exit(1); }),
				"access denied (\"java.lang.RuntimePermission\" \"exit\")");
		
		checkCall(() -> { try {	Thread.sleep(5000);	} catch (InterruptedException e) {} },"Timeout");
		
		checkCall(() -> { try { Thread.sleep(50); } catch (InterruptedException e) {} },null);
		
	}

	private void checkCall(Runnable runnable,String message) {
		try {
			SafeExecutor.executeSafelly(runnable);
			
			if(message != null)
				fail("Exception expected ");
		} catch(Exception cause) {
			if(message == null) 
				fail("Unexpected exception");	
			else
				assertEquals("Expected message", message,cause.getMessage());
		}
	}
}
