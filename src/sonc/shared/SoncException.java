package sonc.shared;

public class SoncException extends Exception
{	
	//Attributes
	private static final long serialVersionUID = 1L;

	//Constructors
	public SoncException()
	{
		super("It has occurred an error on system.");
	}
	
	public SoncException(String message)
	{
		super(message);
	}
	
	public SoncException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public SoncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public SoncException(Throwable cause)
	{
		super(cause);
	}
}