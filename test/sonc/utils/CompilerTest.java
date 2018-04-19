package sonc.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.naming.NameNotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sonc.TestData;
import sonc.battle.Ship;

/**
 * Tests on compiler - utility for compiling bots
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class CompilerTest extends TestData {
	static final String SOURCE_FILE = "sonc/utils/MyShip.txt";
	
	AgentBuilder compiler;
	String code;
	
	@BeforeClass
	public static void setUpClass() {
		AgentBuilder.addToClassPath(MY_CLASS_PATH); // YOU MAY NEED TO CHANGE THE CLASS PATH!
	}
	
	@Before
	public void setUp() throws Exception {
		Path path = Paths.get(ClassLoader.getSystemResource(SOURCE_FILE).toURI());
		
		code = new String(Files.readAllBytes(path));
		compiler= new AgentBuilder();
	}

	/**
	 * Check name of class and exception that is thrown if no class declaration is found 
	 */
	@Test
	public void testGetName() {
		try {
			assertEquals("MyShip",compiler.getClassName(code));
		} catch(NameNotFoundException cause) {
			fail("Unexpected exception");
		}
		
		String brokenCode = code.replace("class MyShip", "");
		
		try {
			compiler.getClassName(brokenCode);
			fail("Exception expected");
		} catch(NameNotFoundException cause) {
			assertNotNull(cause);
		}
	}
	
	/**
	 * Check instantiation of a ship
	 * Several exception may be thrown but are not checked here 
	 * 
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NameNotFoundException
	 */
	@Test
	public void testCompile() 
			throws IOException, InstantiationException, 
			IllegalAccessException, NameNotFoundException {
		Ship ship = compiler.getInstance(Ship.class,code,"player1");
		
		assertEquals(0,ship.getX(),0); // default position of a ship is (0,0)
		assertEquals(0,ship.getX(),0);
	}

}
