package quad;

/**
 * Exception raised when the quad tree is used with a point outside its boundaries. 
 * Programmers can easily avoid these exceptions by checking points before attempting to 
 * insert them in a quad tree. Since it extends RuntimeException, 
 * it is not mandatory to handle this kind of exception. *
 * 
 * @author Matheus Rosa
 *
 */
public class PointOutOfBoundException extends RuntimeException {
	public PointOutOfBoundException() {
		super("The refered point is out of bound. ");
	}
}
