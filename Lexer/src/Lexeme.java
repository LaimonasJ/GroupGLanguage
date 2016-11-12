package lexer;

public class Lexeme {

	private String lexType;
	private String lexValue;
	
public Lexeme(String lexType, String lexValue)
{
	this.lexType=lexType;
	this.lexValue=lexValue;	
}

public String getLexType ()
{
	return this.lexType;
}

public String getLexValue ()
{
	return this.lexValue;
}
	
}
