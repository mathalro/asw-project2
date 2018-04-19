package sonc.shared;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import sonc.TestData;

/**
 * Exceptions raised by SonC
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class SoncExceptionTest extends TestData {

	@Test
	public void testSoncExceptionStringThrowable() {
		for(String text: TEXTS)
			assertNotNull(new SoncException(text, new Throwable()));
	}

	@Test
	public void testSoncExceptionString() {
		for(String text: TEXTS)
			assertNotNull(new SoncException(text));
	}

}
