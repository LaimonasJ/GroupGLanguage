package lexer;

public class Lexeme {

	private String lexType;
	private String lexValue;
        private int line;
	
public Lexeme(String lexType, String lexValue, int line)
{
	this.lexType=lexType;
	this.lexValue=lexValue;
        this.line = line;
}

public String getLexType ()
{
	return this.lexType;
}

public String getLexValue ()
{
	return this.lexValue;
}

public int getLine()
{
    return this.line;
}
	
}
