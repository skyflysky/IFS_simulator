package sky.example.ifsSimulator.exception;

public class NoRuleFoundException extends RuntimeException
{
	private static final long serialVersionUID = 6429068272337567903L;

	public NoRuleFoundException(String message)
	{
		super(message);
	}

}
